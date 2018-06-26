package com.onesidedcab.user.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.model.LatLngBounds
import com.onesidedcab.user.R
import com.onesidedcab.user.utils.Utils
import java.text.DecimalFormat
import java.util.*

class RideDetailsActivity : AppCompatActivity() {

    lateinit var imgBak: ImageView
    lateinit var bookNow: Button
    lateinit var edtPickDate: EditText
    lateinit var edtPickTime: EditText

    lateinit var edtPickup: EditText
    lateinit var edtDropp: EditText

    internal lateinit var mDatePickerDialog: DatePickerDialog
    internal lateinit var mTimePickerDialog: TimePickerDialog

    internal var PLACE_PICKER_REQUEST = 141

    internal lateinit var c: Calendar

    internal var isPickUp = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_details)

        initViews()
        clicklistener()
    }


    private fun initViews() {

        imgBak = findViewById(R.id.imgBak) as ImageView
        bookNow = findViewById(R.id.bookNow) as Button
        edtPickDate = findViewById(R.id.edtPickDate) as EditText
        edtPickTime = findViewById(R.id.edtPickTime) as EditText
        edtPickup = findViewById(R.id.edtPickup) as EditText
        edtDropp = findViewById(R.id.edtDrop) as EditText


        //--------------------Date Picker
        c = Calendar.getInstance()

        val mDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> edtPickDate.setText(DecimalFormat("00").format(dayOfMonth.toLong()) + "/" + DecimalFormat("00").format((monthOfYear + 1).toLong()) + "/" + year) }

        mDatePickerDialog = DatePickerDialog(this!!, mDateSetListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
//        c.add(Calendar.DAY_OF_YEAR)
        mDatePickerDialog.datePicker.minDate = c.timeInMillis
//        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


//---------------------Time Picker

        val mTimeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
            var Hour = 0
            var Minute = 0
            var new_hour = 0
            var new_minute: String? = null


            Hour = hourOfDay
            Minute = minute

            val time_form = hourOfDay.toString() + ":" + minute + ":00"
            val r = minute / 10
            if (r == 0) {
                new_minute = "0$minute"
            } else {
                new_minute = minute.toString() + ""
            }
            if (Hour > 12) {
                new_hour = hourOfDay - 12
                edtPickTime.setText((StringBuilder().append(DecimalFormat("00").format(new_hour.toLong()))
                        .append(":").append(new_minute).toString() + " PM"))
            } else if (Hour == 12) {

                new_hour = hourOfDay
                edtPickTime.setText(((StringBuilder().append(DecimalFormat("00").format(new_hour.toLong()))
                        .append(":").append(new_minute)).toString() + " PM"))
            } else {
                new_hour = hourOfDay
                edtPickTime.setText(((StringBuilder().append(DecimalFormat("00").format(new_hour.toLong()))
                        .append(":").append(new_minute)).toString() + " AM"))
            }
        }

        mTimePickerDialog = TimePickerDialog(this, mTimeSetListener, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false)


    }


    private fun clicklistener() {

        imgBak.setOnClickListener(View.OnClickListener { startActivity(Intent(this@RideDetailsActivity, MainActivity::class.java)) })

        bookNow.setOnClickListener(View.OnClickListener { startActivity(Intent(this@RideDetailsActivity, PaymentOptionActivity::class.java)) })

        edtPickDate.setOnTouchListener(View.OnTouchListener { view, motionEvent ->

            hideSoftKeyboard()

            mDatePickerDialog.show()


            false
        })

        edtPickTime.setOnTouchListener(View.OnTouchListener { view, motionEvent ->

            hideSoftKeyboard()

            mTimePickerDialog.show()


            false
        })

        edtPickup.setOnTouchListener(View.OnTouchListener { view, motionEvent ->

            isPickUp = true
            hideSoftKeyboard()
            callPlacePicker(null)


            false
        })

        edtDropp.setOnTouchListener(View.OnTouchListener { view, motionEvent ->

            hideSoftKeyboard()

            callPlacePicker(null)


            false
        })


    }

    fun hideSoftKeyboard() {
        // Check if no view has focus:

        val view = this!!.getCurrentFocus()

        if (view != null) {
            val imm = this!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }

    internal fun callPlacePicker(bounds: LatLngBounds?) {


        try {
            val builder = PlacePicker.IntentBuilder()


            if (bounds != null) {

                builder.setLatLngBounds(bounds)


            }
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)
        } catch (e: Exception) {
            Utils.popToast(this, e.message!!)
        }

    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == PLACE_PICKER_REQUEST) {

            if (resultCode == Activity.RESULT_OK) {

                val place = PlacePicker.getPlace(data!!, this!!)

                Log.d("Locationresponse", place.toString())

                if (isPickUp) {

//                    originLat = place.latLng.latitude
//                    originLang = place.latLng.longitude

//                    if (originLat != 0.0 && originLang != 0.0) {

                        edtPickup.setText(place.address)
//                        Pickuplocality = place.name.toString()
                   }
                else {

                        //Dropofflocality = (String) place.getName();
                        //dropOff.setText(place.getAddress() + "");
//                        destLat = place.latLng.latitude
//                        desLang = place.latLng.longitude

//                        if (destLat != 0.0 && desLang != 0.0) {

                            //                        getDropAddress(destLat, desLang);
                            edtDropp.setText(place.address)
//                            Dropofflocality = place.name.toString()
//                        }


                }

            }
        }
    }
}





