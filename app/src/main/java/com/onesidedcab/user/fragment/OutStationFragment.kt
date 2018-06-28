package com.onesidedcab.user.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.model.LatLngBounds

import com.onesidedcab.user.R
import com.onesidedcab.user.ui.MainActivity
import com.onesidedcab.user.ui.PaymentOptionActivity
import com.onesidedcab.user.utils.Utils
import java.text.DecimalFormat
import java.util.*

class OutStationFragment : Fragment(),View.OnClickListener  {


    lateinit var linearCity: LinearLayout
    lateinit var edtPickDate: EditText
    lateinit var edtPickTime: EditText

    lateinit var edtPickup: EditText
    lateinit var edtDropp: EditText



    internal lateinit var mDatePickerDialog: DatePickerDialog
    internal lateinit var mTimePickerDialog: TimePickerDialog

    internal var PLACE_PICKER_REQUEST = 141

    internal lateinit var c: Calendar

    internal var isPickUp = true


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view:View =  inflater.inflate(R.layout.fragment_out_station, container, false)
        initViews(view)
        clicklistener()
        return view
    }


    fun initViews(view: View) {

        linearCity = view.findViewById(R.id.linearCity)
        linearCity.setOnClickListener(this)

        edtPickDate = view.findViewById(R.id.edtPickDate) as EditText
        edtPickTime = view.findViewById(R.id.edtPickTime) as EditText
        edtPickup = view.findViewById(R.id.edtPickupcity) as EditText


        //--------------------Date Picker
        c = Calendar.getInstance()

        val mDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> edtPickDate.setText(DecimalFormat("00").format(dayOfMonth.toLong()) + "/" + DecimalFormat("00").format((monthOfYear + 1).toLong()) + "/" + year) }

        mDatePickerDialog = DatePickerDialog(activity!!, mDateSetListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
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

        mTimePickerDialog = TimePickerDialog(activity, mTimeSetListener, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false)


    }


    private fun clicklistener() {

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




    }

    fun hideSoftKeyboard() {
        // Check if no view has focus:

        val view = activity!!.getCurrentFocus()

        if (view != null) {
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }

    internal fun callPlacePicker(bounds: LatLngBounds?) {


        try {
            val builder = PlacePicker.IntentBuilder()


            if (bounds != null) {

                builder.setLatLngBounds(bounds)


            }
            startActivityForResult(builder.build(activity), PLACE_PICKER_REQUEST)
        } catch (e: Exception) {
            Utils.popToast(activity!!, e.message!!)
        }

    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == PLACE_PICKER_REQUEST) {

            if (resultCode == Activity.RESULT_OK) {

                val place = PlacePicker.getPlace(data, activity)

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






    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.linearCity -> {
                getCityDialog().show()
            }

//            else -> {
//                // else condition
//            }

        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    private fun getCityDialog(): Dialog {


        var dialog = Dialog(activity)

        var window = dialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.city_list_dialog)

        val params: WindowManager.LayoutParams = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;


        window.setAttributes(params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        val layoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        layoutParams.topMargin = 10 * window.getWindowManager().getDefaultDisplay().getHeight() / 100;
        layoutParams.bottomMargin = 10 * window.getWindowManager().getDefaultDisplay().getHeight() / 100;
        layoutParams.leftMargin = 6 * window.getWindowManager().getDefaultDisplay().getWidth() / 100;
        layoutParams.rightMargin = 6 * window.getWindowManager().getDefaultDisplay().getWidth() / 100;


        var linearDialogue = dialog.findViewById<LinearLayout>(R.id.linearDialogue)

        linearDialogue.setOrientation(LinearLayout.VERTICAL)
        linearDialogue.setLayoutParams(layoutParams)

        var ivClose = dialog.findViewById<ImageView>(R.id.ChativDriverClose);
//        MyTextViewRegular tvTitle = (MyTextViewRegular) dialog.findViewById(R.id.ChattvDriverTitle);
//        final RecyclerView rvList = (RecyclerView) dialog.findViewById(R.id.ChatrvDriverList);
//
//        ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.Chatprogress);
//
        //val llm : LinearLayoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.VERTICAL, false);
        // rvList.setLayoutManager(llm);
//BindDriverMessageData(rvList,dialog);

        // tvTitle.setText(R.string.showLocation);
        // getDriverMessageData(progressBar);

//        final List<String> list = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.chatSampleList)));
//        listPosition = -1;

//        btnSelect.setText(getString(R.string.send));
        // tvTitle.setText("Report Trip");

/*        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etChat.setText(messageStatusList.get(listPosition).getMessage_status());


//                chatList.add(list.get(listPosition));
//                chatAdapter.notifyItemInserted(chatList.size() - 1);
//                rvChat.scrollToPosition(chatList.size() - 1);
                dialog.dismiss();
            }
        });*/
        ivClose.setOnClickListener {
            // your code to perform when the user clicks on the button
            dialog.dismiss();
        }



        return dialog


    }




    companion object {
        fun newInstance(): OutStationFragment {
            return OutStationFragment()
        }
    }
}
