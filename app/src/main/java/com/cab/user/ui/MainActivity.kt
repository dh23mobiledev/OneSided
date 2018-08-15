package com.cab.user.ui

import android.Manifest
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.location.Location
import android.os.AsyncTask
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.cab.user.R
import com.cab.user.helper.DirectionsJSONParser
import com.cab.user.helper.ReadTask
import com.cab.user.utils.Utils
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat
import java.util.*


class MainActivity : AppCompatActivity(),OnMapReadyCallback,LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{


//    FragmentDrawer.FragmentDrawerListener


//    lateinit var adapter: CustomAdapter
//    private val TAG = MainActivity::class.java.simpleName
//    private var mToolbar: Toolbar? = null
//    private var drawerFragment: FragmentDrawer? = null
//
//
//    lateinit var fragment: Fragment

    internal lateinit var mapFragment: SupportMapFragment
    internal lateinit var map: GoogleMap
    //  MapView goMap;
    internal var view: View? = null
    internal var markerPoints: ArrayList<LatLng>? = null
    internal var originLat = 0.0
    internal var originLang = 0.0
    internal var destLat = 0.0
    internal var desLang = 0.0

    var lastLocation: Location? = null

    protected var mGoogleApiClient: GoogleApiClient? = null
    internal lateinit var mLocationRequest: LocationRequest

    internal var markerOptionsOrigin: MarkerOptions? = null
    internal var markerOptionsDest:MarkerOptions? = null

    internal var markerOrigin: Marker? = null
    internal var markerDest:Marker? = null


    internal var time = ""
    internal var distance_km = ""
    internal var Hours = ""

    internal var PLACE_PICKER_REQUEST = 141
    internal var PLACE_AIRPORT_REQUEST = 142

    internal var isPickUp = true


    internal lateinit var mDatePickerDialog: DatePickerDialog
    internal lateinit var mTimePickerDialog: TimePickerDialog


    var edPickup:EditText? = null
    var edDropp:EditText? = null

    var edDate:EditText? = null
    var edTime:EditText? = null

    var linearTimeDuration:LinearLayout? =null
    var btnNext:TextView? = null

    var txtEstTime:TextView? = null
    var txtEstDuration:TextView? = null

    internal lateinit var c: Calendar




    var linaerSeaden:LinearLayout? = null
    var imgSeaden:ImageView? = null
    var txtSeaden:TextView? = null

    var linearHatch:LinearLayout? = null
    var imgHatch:ImageView? = null
    var txtHatch:TextView? = null

    var linearSuv:LinearLayout? = null
    var imgSuv:ImageView? = null
    var txtSuv:TextView? = null

    var txtOneway:Button? = null
    var txtHourly:Button? = null
    var btnBookRide:Button? = null
    var btnRideDetail:Button? = null

    var imgNavDrawer:ImageView? = null












    private val asyncTaskForAddress: AsyncTaskForAddress? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         initViews()
//        clickListener()


    }

    private fun initViews() {

         mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment

        edPickup = findViewById(R.id.edPickup)
        edDropp = findViewById(R.id.edDropp)

        edDate = findViewById(R.id.edDate)
        edTime = findViewById(R.id.edTime)
        imgNavDrawer = findViewById(R.id.imgNavDrawer)

//        linearTimeDuration = findViewById(R.id.linearTimeDuration)
//        btnNext = findViewById(R.id.btnNext)
//
//        txtEstTime = findViewById(R.id.txtEstTime)
//        txtEstDuration = findViewById(R.id.txtEstDuration)

        linaerSeaden = findViewById(R.id.linaerSeaden)
        imgSeaden = findViewById(R.id.imgSeaden)
        txtSeaden = findViewById(R.id.txtSeaden)

        linearHatch = findViewById(R.id.linearHatch)
        imgHatch = findViewById(R.id.imgHatch)
        txtHatch = findViewById(R.id.txtHatch)

        linearSuv = findViewById(R.id.linearSuv)
        imgSuv = findViewById(R.id.imgSuv)
        txtSuv = findViewById(R.id.txtSuv)

        txtOneway = findViewById(R.id.txtOneway)
        txtHourly = findViewById(R.id.txtHourly)
        btnBookRide = findViewById(R.id.btnBookRide)
        btnRideDetail = findViewById(R.id.btnRideDetail)


        linaerSeaden!!.setOnClickListener(View.OnClickListener {

            imgSeaden!!.setColorFilter(getResources().getColor(R.color.red));
            txtSeaden!!.setTextColor(resources.getColor(R.color.red))

            imgHatch!!.setColorFilter(getResources().getColor(R.color.black));
            txtHatch!!.setTextColor(resources.getColor(R.color.black))


            imgSuv!!.setColorFilter(getResources().getColor(R.color.black));
            txtSuv!!.setTextColor(resources.getColor(R.color.black))


        })

        linearHatch!!.setOnClickListener(View.OnClickListener {

            imgSeaden!!.setColorFilter(getResources().getColor(R.color.black));
            txtSeaden!!.setTextColor(resources.getColor(R.color.black))

            imgHatch!!.setColorFilter(getResources().getColor(R.color.red));
            txtHatch!!.setTextColor(resources.getColor(R.color.red))


            imgSuv!!.setColorFilter(getResources().getColor(R.color.black));
            txtSuv!!.setTextColor(resources.getColor(R.color.black))


        })


        linearSuv!!.setOnClickListener(View.OnClickListener {

            imgSeaden!!.setColorFilter(getResources().getColor(R.color.black));
            txtSeaden!!.setTextColor(resources.getColor(R.color.black))

            imgHatch!!.setColorFilter(getResources().getColor(R.color.black));
            txtHatch!!.setTextColor(resources.getColor(R.color.black))


            imgSuv!!.setColorFilter(getResources().getColor(R.color.red));
            txtSuv!!.setTextColor(resources.getColor(R.color.red))

        })



        txtOneway!!.setOnClickListener(View.OnClickListener {

            txtOneway!!.setBackgroundColor(resources.getColor(R.color.red))
            txtHourly!!.setBackgroundColor(resources.getColor(R.color.white))
            txtHourly!!.setTextColor(resources.getColor(R.color.black))
            txtOneway!!.setTextColor(resources.getColor(R.color.white))


        })



        txtHourly!!.setOnClickListener(View.OnClickListener {

            txtOneway!!.setBackgroundColor(resources.getColor(R.color.white))
            txtHourly!!.setBackgroundColor(resources.getColor(R.color.red))
            txtHourly!!.setTextColor(resources.getColor(R.color.white))
            txtOneway!!.setTextColor(resources.getColor(R.color.black))


        })

        imgNavDrawer!!.setOnClickListener(View.OnClickListener {



            var dialog = Dialog(this@MainActivity)

            var window = dialog.getWindow();
            window.requestFeature(Window.FEATURE_NO_TITLE)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.navdrawer_dialoug)

            val params: WindowManager.LayoutParams = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.CENTER;


            window.setAttributes(params);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);

//            val layoutParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//            layoutParams.topMargin = 10 * window.getWindowManager().getDefaultDisplay().getHeight() / 100;
//            layoutParams.bottomMargin = 10 * window.getWindowManager().getDefaultDisplay().getHeight() / 100;
//            layoutParams.leftMargin = 6 * window.getWindowManager().getDefaultDisplay().getWidth() / 100;
//            layoutParams.rightMargin = 6 * window.getWindowManager().getDefaultDisplay().getWidth() / 100;
//

         //   var linearDialogue = dialog.findViewById<RelativeLayout>(R.id.linearDialogue)
        //    linearDialogue.setLayoutParams(layoutParams)
//            lp.gravity = Gravity.CENTER




            var imgClose = dialog.findViewById<ImageView>(R.id.ivClose)
            var txtHome = dialog.findViewById<TextView>(R.id.txtHome)
            var txtMyRides = dialog.findViewById<TextView>(R.id.txtMyRides)
            var txtProfile = dialog.findViewById<TextView>(R.id.txtProfile)
            var txtlogout = dialog.findViewById<TextView>(R.id.txtlogout)



            txtHome.setOnClickListener {
                // your code to perform when the user clicks on the button
                dialog.dismiss();
            }



            txtMyRides.setOnClickListener {
                // your code to perform when the user clicks on the button
                dialog.dismiss();
                startActivity(Intent(this@MainActivity, MyRidesActivity::class.java))
            }



            txtProfile.setOnClickListener {
                // your code to perform when the user clicks on the button
                dialog.dismiss();
                Utils.popToast(this@MainActivity,"Profile")
                //startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
            }

            txtlogout.setOnClickListener {
                // your code to perform when the user clicks on the button
                dialog.dismiss();
            }







            txtHome.setOnClickListener {
                // your code to perform when the user clicks on the button
                dialog.dismiss();
            }


            imgClose.setOnClickListener {
                // your code to perform when the user clicks on the button
                dialog.dismiss();
            }


            dialog.show()

        })


        mapFragment.getMapAsync(this)

        buildGoogleApiClient()


        btnBookRide!!.setOnClickListener(View.OnClickListener {


            var intent = Intent(this@MainActivity,MyRidesDetailsActivity::class.java)
            intent.putExtra("type","1")
            startActivity(intent)

        })


        btnRideDetail!!.setOnClickListener(View.OnClickListener {

           // startActivity(Intent(this@MainActivity,RideDetailsActivity::class.java))

        })




        edDate!!.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            hideSoftKeyboard()

            mDatePickerDialog.show()

            false
        })

        edTime!!.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            hideSoftKeyboard()

            mTimePickerDialog.show()

            false
        })


         c = Calendar.getInstance()

        val mDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> edDate!!.setText(DecimalFormat("00").format(dayOfMonth.toLong()) + "/" + DecimalFormat("00").format((monthOfYear + 1).toLong()) + "/" + year) }

        mDatePickerDialog = DatePickerDialog(this@MainActivity, mDateSetListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
        c.add(Calendar.DAY_OF_YEAR, 3)
        mDatePickerDialog.datePicker.minDate = c.timeInMillis
//        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


        val mTimeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
            var Hour = 0
            var Minute = 0
            var new_hour = 0
            var new_minute:String? = null


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
                edTime!!.setText((StringBuilder().append(DecimalFormat("00").format(new_hour.toLong()))
                                        .append(":").append(new_minute).toString() + " PM"))
            } else if (Hour == 12) {

                new_hour = hourOfDay
                edTime!!.setText(((StringBuilder().append(DecimalFormat("00").format(new_hour.toLong()))
                        .append(":").append(new_minute)).toString() + " PM"))
            } else {
                new_hour = hourOfDay
                edTime!!.setText(((StringBuilder().append(DecimalFormat("00").format(new_hour.toLong()))
                        .append(":").append(new_minute)).toString() + " AM"))
            }
        }

        mTimePickerDialog = TimePickerDialog(this@MainActivity, mTimeSetListener, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false)


        edPickup!!.setOnClickListener(View.OnClickListener {
            isPickUp = true

                //  final LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(15.8700, 100.9925), new LatLng(28.20453, 97.34466));
                callPlacePicker(null)
        })

        edDropp!!.setOnClickListener(View.OnClickListener {
            isPickUp = false

            //  final LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(15.8700, 100.9925), new LatLng(28.20453, 97.34466));
            callPlacePicker(null)
        })

    }

    internal fun callPlacePicker(bounds: LatLngBounds?) {


        try {
            val builder = PlacePicker.IntentBuilder()


            if (bounds != null) {

                builder.setLatLngBounds(bounds)


            }
            startActivityForResult(builder.build(this@MainActivity), PLACE_PICKER_REQUEST)
        } catch (e: Exception) {
            Utils.popToast(this@MainActivity, e.message!!)
        }

    }

    @Synchronized
    protected fun buildGoogleApiClient() {

        if (checkPermission()) {
            mGoogleApiClient = GoogleApiClient.Builder(this@MainActivity)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build()
        } else {
            requestPermission()
        }


    }


    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap

        if (ActivityCompat.checkSelfPermission(this@MainActivity!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@MainActivity,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        } else {
            map.isMyLocationEnabled = true
        }

    }

    override fun onLocationChanged(p0: Location?) {

    }

    override fun onConnected(p0: Bundle?) {
        startLocation()

    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    private fun checkPermission(): Boolean {

        val coarse_location = ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        val fine_location = ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)

        return if (coarse_location == PackageManager.PERMISSION_GRANTED && fine_location == PackageManager.PERMISSION_GRANTED) {

            true

        } else {

            //   requestPermission();
            false

        }
    }

    private fun requestPermission() {

        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION), 101)

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.d("requestCode>>", "req code > " + requestCode + "   res code > " + permissions.toString() + "    gr  >> " + grantResults.size)
        when (requestCode) {
            101 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //  atStartUp();
                //   Snackbar.make(rel, "Permission Granted.", Snackbar.LENGTH_LONG).show();

                if (isLocationEnabled(this@MainActivity)) {
                    Toast.makeText(this@MainActivity, "Location is on. Going to start location", Toast.LENGTH_SHORT).show()
                    startLocation()


                } else {

                    Toast.makeText(this@MainActivity, "Turn ON location", Toast.LENGTH_SHORT).show()
                }


            } else {

                Toast.makeText(this@MainActivity, "Permisson cannot be denied.", Toast.LENGTH_SHORT).show()
                //Snackbar.make(getCurrentFocus(), "Permisson cannot be denied.", Snackbar.LENGTH_LONG).show();
                //    atStartUp();

            }
        }
    }


    fun isLocationEnabled(context: Context?): Boolean {
        var locationMode = 0
        val locationProviders: String

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context!!.contentResolver, Settings.Secure.LOCATION_MODE)

            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
                return false
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF

        } else {
            locationProviders = Settings.Secure.getString(context!!.contentResolver, Settings.Secure.LOCATION_PROVIDERS_ALLOWED)
            return !TextUtils.isEmpty(locationProviders)
        }


    }


    internal fun startLocation() {

        if (mGoogleApiClient == null) {
            Toast.makeText(this@MainActivity, "null", Toast.LENGTH_SHORT).show()
            //  buildGoogleApiClient();
            //  startLocation();

            return
        }

        if (!mGoogleApiClient!!.isConnected()) {
            mGoogleApiClient!!.connect()

            return
        }
        if (!checkPermission()) {
            return
        }

        /*   if (!isLocationEnabled(getActivity())) {

            Toast.makeText(getActivity(), "Turn ON location", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final AlertDialog dialog = builder.create();
            dialog.setMessage("Smart Limo requires your location. Please turn it on.");
            builder.setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub


                    dialog.dismiss();
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(myIntent, 110);
                    //get gps


                }
            });

            dialog.show();
            return;
        }*/

        mLocationRequest = LocationRequest.create()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 100 // Update location every second

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this@MainActivity)

        if (ActivityCompat.checkSelfPermission(this@MainActivity,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermission()
        }

        if (LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient) != null) {


            lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)


            Log.d("lastLocation>>", lastLocation!!.getLatitude().toString() + " , " + lastLocation!!.getLongitude())
            val sydney = LatLng(lastLocation!!.getLatitude(), lastLocation!!.getLongitude())


            if (originLat != 0.0 || originLang != 0.0 || destLat != 0.0 || desLang != 0.0) {

                if (originLat != 0.0 && originLang != 0.0) {

                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(originLat, originLang), 16f))

                }

                if (destLat != 0.0 && desLang != 0.0) {

                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(destLat, desLang), 16f))
                }

            } else {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f))
            }
            //


        } else {
            //startLocationUpdates();
        }

    }


    public override fun onStart() {
        super.onStart()

        if (mGoogleApiClient != null) {
            mGoogleApiClient!!.connect()
        }


    }

    public override fun onStop() {
        super.onStop()
        if (asyncTaskForAddress != null) {

            asyncTaskForAddress.onCancelled()
        }
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient!!.isConnected()) {
                mGoogleApiClient!!.disconnect()
            }
        }


    }

    public override fun onPause() {
        super.onPause()

        if (asyncTaskForAddress != null) {
            asyncTaskForAddress.onCancelled()

        }

        /* if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }*/
    }

    public override fun onDestroy() {
        super.onDestroy()
        if (asyncTaskForAddress != null) {

            asyncTaskForAddress.onCancelled()

            //handler.removeCallbacks(UpdateLocation);

        }


        if (mGoogleApiClient != null && mGoogleApiClient!!.isConnected()) {
            mGoogleApiClient!!.disconnect()
        }
    }


    internal inner class AsyncTaskForAddress : AsyncTask<String, String, String> {

        lateinit var address: String
        var latLng: LatLng
        lateinit var asyncAddressListner: AsyncAddressListner


        constructor(latLng: LatLng, asyncAddressListner: AsyncAddressListner) {
            this.latLng = latLng
            this.asyncAddressListner = asyncAddressListner
        }

        constructor(latLng: LatLng) {
            this.latLng = latLng
        }

        override fun onPreExecute() {
            super.onPreExecute()


        }

        override fun doInBackground(vararg params: String?): String? {

            address = getCompleteAddressString(latLng)

            return null
        }


        public override fun onCancelled() {

            cancel(true)
        }

         override fun onPostExecute(result: String?) {
            super.onPostExecute(result)


            //Toast.makeText(getActivity(), address, Toast.LENGTH_LONG).show();
            // if (autocompleteFragment != null && task_location != null ) {
            asyncAddressListner.onAddress(address)
            //}


        }
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

             if (requestCode == PLACE_PICKER_REQUEST) {

            if (resultCode == RESULT_OK) {

                val place = PlacePicker.getPlace(data!!, this@MainActivity)

                Log.d("Locationresponse", place.toString())

                if (isPickUp) {

                    originLat = place.latLng.latitude
                    originLang = place.latLng.longitude

                    if (originLat != 0.0 && originLang != 0.0) {

                        edPickup!!.setText(place.address)
                        //Pickuplocality = place.name.toString()
                    }


                    // pickUp.setText(place.getAddress() + "");


                    //                    Log.d("Place_id", place.getId());
                    //
                    //                    Geocoder geocoder;
                    //
                    //                    geocoder = new Geocoder(getActivity());
                    //
                    //                    try {
                    //                        addresses = geocoder.getFromLocationName((String) place.getName(), 1);
                    //                        // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    //                        if (addresses.size() > 0) {
                    //
                    //                            Pickzone = addresses.get(0).getLocality();
                    //                            Pickregion = addresses.get(0).getCountryName();
                    //
                    //
                    //                            if (Utils.isEmptyString(Pickzone) && Utils.isEmptyString(Pickregion)) {
                    //
                    //                                int lastIndex = place.getAddress().toString().lastIndexOf(",");
                    //                                int prevIndex = place.getAddress().toString().lastIndexOf(",", lastIndex - 1);
                    //                                Pickzone = place.getAddress().toString().substring(prevIndex + 1, lastIndex).trim();
                    //
                    //                                if (Pickzone.contains(" ")) {
                    //                                    String[] parts = Pickzone.split(" ");
                    //                                    Pickzone = parts[0];
                    //                                    // String postalCode = parts[1];
                    //                                } else {
                    //                                }
                    //                                Pickregion = place.getAddress().toString().substring(place.getAddress().toString().lastIndexOf(",") + 1, place.getAddress().toString().length());
                    //
                    //                            }
                    //
                    //                        } else {
                    //                            Utils.popToast(getActivity(), "Please enter proper pickup and dropoff address, We can't get details for this locations.");
                    //                        }
                    //
                    //
                    //                    } catch (IOException e) {
                    //                        e.printStackTrace();
                    //                    }


                    map.mapType = GoogleMap.MAP_TYPE_NORMAL


                    if (markerOrigin != null) {
                        markerOrigin!!.remove()
                        markerOrigin = null
                    }

                    markerOptionsOrigin = MarkerOptions().position(place.latLng).title("Pickup").icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap((R.drawable.locationpin).toString(), 64, 64))).draggable(true)

                    handleMarkerandPolyLine(markerOptionsOrigin, markerOptionsDest)
                    val Liberty = CameraPosition.builder().target(place.latLng).zoom(16f).bearing(0f).tilt(45f).build()
                    map.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty))


                } else {

                    //Dropofflocality = (String) place.getName();
                    //dropOff.setText(place.getAddress() + "");
                    destLat = place.latLng.latitude
                    desLang = place.latLng.longitude

                    if (destLat != 0.0 && desLang != 0.0) {

                        //                        getDropAddress(destLat, desLang);
                        edDropp!!.setText(place.address)
                        //Dropofflocality = place.name.toString()
                    }


                    //                    Geocoder geocoder1;
                    //
                    //                    geocoder1 = new Geocoder(getActivity());
                    //
                    //                    try {
                    //
                    //                        addresses1 = geocoder1.getFromLocationName((String) place.getName(), 1);
                    //                        // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    //                        if (addresses1.size() > 0) {
                    //
                    //                            Dropzone = addresses1.get(0).getLocality();
                    //                            Dropregion = addresses1.get(0).getCountryName();
                    //
                    //                            // Toast.makeText(activity, "G" + region + "" + zone, Toast.LENGTH_SHORT).show();
                    //
                    //                            if (Utils.isEmptyString(Dropzone) && Utils.isEmptyString(Dropregion)) {
                    //
                    //                                int lastIndex = place.getAddress().toString().lastIndexOf(",");
                    //                                int prevIndex = place.getAddress().toString().lastIndexOf(",", lastIndex - 1);
                    //                                Dropzone = place.getAddress().toString().substring(prevIndex + 1, lastIndex).trim();
                    //
                    //                                if (Dropzone.contains(" ")) {
                    //                                    String[] parts = Dropzone.split(" ");
                    //                                    Dropzone = parts[0];
                    //                                    // String postalCode = parts[1];
                    //                                } else {
                    //                                }
                    //
                    //                                Dropregion = place.getAddress().toString().substring(place.getAddress().toString().lastIndexOf(",") + 1, place.getAddress().toString().length());
                    //
                    //                            }
                    //
                    //                        } else {
                    //                            Utils.popToast(getActivity(), "Please enter proper pickup and dropoff address, We can't get details for this locations.");
                    //                        }
                    //
                    //
                    //                        if (markerDest != null) {
                    //                            markerDest.remove();
                    //                            markerDest = null;
                    //                        }


                    map.mapType = GoogleMap.MAP_TYPE_NORMAL
                    if (markerDest != null) {
                        markerDest!!.remove()
                        markerDest = null
                    }

                    //                    markerOptionsDest = new MarkerOptions()
                    //                            .position(new LatLng(destLat, desLang)).title("Drop off").draggable(true);

                    markerOptionsDest = MarkerOptions().position(place.latLng).title("Drop off").icon(BitmapDescriptorFactory.fromBitmap(resizeBitmap((R.drawable.locationpin).toString(), 64, 64))).draggable(true)

                    handleMarkerandPolyLine(markerOptionsOrigin, markerOptionsDest)

                    val Liberty = CameraPosition.builder().target(place.latLng).zoom(16f).bearing(0f).tilt(45f).build()
                    map.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty))

                    //                    } catch (IOException e) {
                    //                        e.printStackTrace();
                    //                    }
                    //infoWindow();

                }

            }


        }
    }


interface AsyncAddressListner {

    fun onAddress(address: String)

}


    fun getCompleteAddressString(latLng: LatLng): String {
        var strAdd = ""
        val geocoder = Geocoder(this@MainActivity)
        try {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses != null) {
                val returnedAddress = addresses[0]
                val strReturnedAddress = StringBuilder("")

                for (i in 0 until returnedAddress.maxAddressLineIndex) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(" ")
                }
                strAdd = strReturnedAddress.toString()
                Log.w("My Current", "" + strReturnedAddress.toString())
            } else {
                Log.w("My Current", "No Address returned!")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.w("My Current", "Canont get Address!")
        }

        return strAdd
    }

    fun getCalculateDistance() {

        //
        //        String url = getDirectionsUrl(new LatLng(Double.parseDouble(rideData.getPickup_latitude()),
        //                        Double.parseDouble(rideData.getPickup_longitude())),
        //                new LatLng(Double.parseDouble(rideData.getDropoff_latitude()),
        //                        Double.parseDouble(rideData.getDropoff_longitude())));


        val url = getDirectionsUrl(LatLng(originLat,
                originLang),
                LatLng(destLat,
                        desLang))

        Log.d("DIRECTIONURL>", url)

        DownloadTask().execute(url)


    }

    inner class DownloadTask : AsyncTask<String, Void, String>() {


        override fun onPreExecute() {
            super.onPreExecute()


        }

        // Downloading data in non-ui thread
        override fun doInBackground(vararg url: String): String {

            // For storing data from web service
            var data = ""

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0])
            } catch (e: Exception) {
                Log.d("Background Task", e.toString())
            }

            return data
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        override fun onPostExecute(result: String) {
            super.onPostExecute(result)

            val parserTask = ParserTask()

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result)


        }

        @Throws(IOException::class)
        private fun downloadUrl(strUrl: String): String {
            var data = ""
            var iStream: InputStream? = null
            var urlConnection: HttpURLConnection? = null
            try {
                val url = URL(strUrl)

                // Creating an http connection to communicate with url
                urlConnection = url.openConnection() as HttpURLConnection

                // Connecting to url
                urlConnection.connect()

                // Reading data from url
                iStream = urlConnection.inputStream

                val br = BufferedReader(InputStreamReader(iStream!!))

                val sb = StringBuffer()

                var line = ""


                while ((line == br.readLine()) != null) {
                    sb.append(line)
                }

                data = sb.toString()

                br.close()

            } catch (e: Exception) {
                Log.d("Exception while url", e.toString())
            } finally {
                iStream!!.close()
                urlConnection!!.disconnect()
            }
            return data
        }
    }

    inner class ParserTask : AsyncTask<String, Int, List<List<HashMap<String, String>>>>() {

        // Parsing the data in non-ui thread


        var jObject: JSONObject? = null

       // internal var rideData = RideData()

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg jsonData: String): List<List<HashMap<String, String>>>? {


            var routes: List<List<HashMap<String, String>>>? = null

            try {
                jObject = JSONObject(jsonData[0])
                val parser = DirectionsJSONParser()

                // Starts parsing data
                routes = parser.parse(jObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return routes
        }

        // Executes in UI thread, after the parsing process
        override fun onPostExecute(result: List<List<HashMap<String, String>>>) {


            try {
                if (jObject == null) {

                    return
                }

                Log.d("jObejct", jObject!!.toString())
                if (jObject!!.has("routes")) {
                    val arrayRoutes = jObject!!.getJSONArray("routes")
                    if (arrayRoutes != null && arrayRoutes.length() > 0) {
                        if (arrayRoutes.getJSONObject(0).has("legs")) {
                            val arrayLegs = arrayRoutes.getJSONObject(0).getJSONArray("legs")

                            distance_km = arrayLegs.getJSONObject(0).getJSONObject("distance").getString("value")
                            time = arrayLegs.getJSONObject(0).getJSONObject("duration").getString("text")


                            var distance = java.lang.Double.parseDouble(distance_km) / 1000
                            distance = round(distance, 2)
                            val df = DecimalFormat("#.##")
                            distance_km = df.format(distance).toString()



                            linearTimeDuration!!.visibility = View.VISIBLE

                            txtEstDuration!!.text = distance_km
                            txtEstTime!!.text = time


                            if (Utils.isEmptyString(distance_km) && Utils.isEmptyString(time)) {

                                /* dis_km = Double.parseDouble(distance_km);

                                distance_miles = dis_km * 0.000621371;

                                distance_miles = Math.round(distance_miles * 100.0) / 100.0;

                                DecimalFormat df = new DecimalFormat("###.##");
                                distance_miles = Double.parseDouble(df.format(distance_miles));*/

                                val builder1 = AlertDialog.Builder(this@MainActivity)
                                builder1.setMessage("Something wen't wrong Please try again.")
                                builder1.setCancelable(true)

                                builder1.setPositiveButton(
                                        "Ok"
                                ) { dialog, id -> dialog.cancel() }

                                builder1.setNegativeButton(
                                        "Cancel"
                                ) { dialog, id -> dialog.cancel() }

                                val alert11 = builder1.create()
                                alert11.show()


                                /* if (Utils.isEmptyString(Pickregion) && Utils.isEmptyString(Pickzone) && Utils.isEmptyString(Dropregion) && Utils.isEmptyString(Dropzone) && Utils.isEmptyString(distance_km)) {


                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                    builder1.setMessage("Please enter proper pickup and dropoff address, We can't get details for this locations.");
                                    builder1.setCancelable(true);

                                    builder1.setPositiveButton(
                                            "Ok",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                                    builder1.setNegativeButton(
                                            "Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();


                                }*/
                            } else {

//                                val intent = Intent(getActivity(), ExtraInfoActivity::class.java)
//                                intent.putExtra("data", setRideData())
//                                getActivity()!!.startActivity(intent)
                            }


                            /* } else {

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                builder1.setMessage("Something wen't wrong Please try again.");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                builder1.setNegativeButton(
                                        "Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();

                            }*/

                        }
                    }
                }

                Log.d("Time&Distnace", time + " " + distance_km)

            } catch (e: Exception) {
            }

        }

    }

    private fun getDirectionsUrl(origin: LatLng, dest: LatLng): String {

        // Origin of route
        val str_origin = "origin=" + origin.latitude + "," + origin.longitude

        // Destination of route
        val str_dest = "destination=" + dest.latitude + "," + dest.longitude


        // Sensor enabled
        val sensor = "sensor=false"
        /*
        String units = "units=imperial";*/

        // Building the parameters to the web service
        val parameters = "$str_origin&$str_dest&$sensor"

        // Output format
        val output = "json"

        // Building the url to the web service
        val url = "https://maps.googleapis.com/maps/api/directions/$output?$parameters"


        return url
    }


    fun round(value: Double, places: Int): Double {
        var value = value
        if (places < 0) throw IllegalArgumentException()

        val factor = Math.pow(10.0, places.toDouble()).toLong()
        value = value * factor
        val tmp = Math.round(value)
        return tmp.toDouble() / factor
    }

    fun hideSoftKeyboard() {
        // Check if no view has focus:

        val view = this@MainActivity.getCurrentFocus()

        if (view != null) {
            val imm = this@MainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }


    fun resizeBitmap(drawableName: String, width: Int, height: Int): Bitmap {

        val imageBitmap = BitmapFactory.decodeResource(resources, resources.getIdentifier(drawableName, "drawable",this@MainActivity.getPackageName()))
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false)

    }

    internal fun handleMarkerandPolyLine(marker: MarkerOptions?, marker1: MarkerOptions?) {

        map.clear()

        if (marker != null) {
            markerOrigin = map.addMarker(marker)
        }

        if (marker1 != null) {
            markerDest = map.addMarker(marker1)
        }

        if (originLat != 0.0 && originLang != 0.0 && destLat != 0.0 && desLang != 0.0) {

            val url = Utils.getMapsApiDirectionsUrl(LatLng(originLat, originLang), LatLng(destLat, desLang))
            val downloadTask = ReadTask(map, this@MainActivity)
            // Start downloading json data from Google Directions API
            downloadTask.execute(url)


        }


    }



}

//    private fun clickListener() {
//
//        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.nav_oneway -> {
//                    replaceFragment(R.id.nav_oneway)
//                    return@OnNavigationItemSelectedListener true
//                }
//
//                R.id.nav_hourly -> {
//                    replaceFragment(R.id.nav_hourly)
//                    return@OnNavigationItemSelectedListener true
//                }
//                R.id.nav_outstation -> {
//                    replaceFragment(R.id.nav_outstation)
//                    return@OnNavigationItemSelectedListener true
//                }
//
//            }
//            false
//        }
//
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
//
//    }
//
//    private fun initViews() {
//
//        Fabric.with(this, Crashlytics())
//        mToolbar = findViewById(R.id.toolbar) as Toolbar
//        setSupportActionBar(mToolbar)
//        supportActionBar!!.setDisplayShowHomeEnabled(true)
//
//
////        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//
//
//        drawerFragment = supportFragmentManager.findFragmentById(R.id.fragment_navigation_drawer) as FragmentDrawer
//        drawerFragment!!.setUp(R.id.fragment_navigation_drawer, findViewById(R.id.drawer_layout) as DrawerLayout, mToolbar)
//        drawerFragment!!.setDrawerListener(this@MainActivity)
//
//        // display the first navigation drawer view on app launch
//        displayView(0)
//        clickListener()
//
//
//    }
//
//
//    override fun onDrawerItemSelected(view: View?, position: Int) {
//        displayView(position)
//
//    }
//
//    private fun displayView(position: Int) {
//
//        var fragment: Fragment? = null
//        var title = getString(R.string.app_name)
//        when (position) {
//            0 -> {
//                fragment = BookRideFragment()
//                title = getString(R.string.nav_title_bookacab)
//                navigation.visibility = View.VISIBLE
//            }
//            1 -> {
//                fragment = MyRidesFragment()
//                title = getString(R.string.nav_title_myrides)
//                navigation.visibility = View.GONE
//            }
//            2 -> {
//                fragment = ReferFragment()
//                title = getString(R.string.nav_title_refer)
//                navigation.visibility = View.GONE
//            }
//            3 -> {
//                fragment = WalletFragment()
//                title = getString(R.string.nav_title_wallet)
//                navigation.visibility = View.GONE
//            }
//            4 -> {
//                fragment = ProfileActivity()
//                title = getString(R.string.nav_title_profile)
//                navigation.visibility = View.GONE
//            }
//            5 -> {
//                fragment = HelpFragment()
//                title = getString(R.string.nav_title_help)
//
//            }
//            6 -> {
//
//            }
//            else -> {
//            }
//        }
//
//        if (fragment != null) {
//            val fragmentManager = supportFragmentManager
//            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.container_body, fragment)
//            fragmentTransaction.commit()
//
//            // set the toolbar title
//            supportActionBar!!.setTitle(title)
//        }
//    }
//
//    fun replaceFragment(id: Int) {
//
//        when (id) {
//            R.id.nav_oneway -> {
//                fragment = BookRideFragment.newInstance()
//                supportActionBar!!.setTitle(getString(R.string.nav_title_bookacab))
//            }
//            R.id.nav_hourly -> {
//                fragment = BookRideFragment.newInstance()
//                supportActionBar!!.setTitle(getString(R.string.nav_title_hourly))
//            }
//            R.id.nav_outstation -> {
//                fragment = OutStationFragment.newInstance()
//                supportActionBar!!.setTitle(getString(R.string.nav_title_outstation))
//            }
//
//        }
//
//        supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.container_body, fragment, "")
//                .commit()
//    }


    /* private fun initView() {

         requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

         window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
         window.setSoftInputMode(
                 WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
         )

         val linearLayoutManager = LinearLayoutManager(this@EmployeeListActivity)
         linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
         rvEmployeelist.setLayoutManager(linearLayoutManager)

         imgBack.setOnClickListener { view ->

             finish()
         }

         if (intent.getIntExtra("type", -1) == 1) {

             bindEmpTaskData(Constant.getUserData(this@EmployeeListActivity)!!.employeeList!!)
             txtLabel.setText("Employee list")

         } else if (intent.getIntExtra("type", -1) == 2) {

             bindDeptTaskData(Constant.getUserData(this@EmployeeListActivity)!!.lstDepts!!)
             txtLabel.setText("Department list")
             search.visibility = View.GONE
         }

         search.addTextChangedListener(object : TextWatcher {
             override fun afterTextChanged(s: Editable?) {

             }

             override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

             }

             override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                 adapter.listener.filterData(search.text.toString().trim())
             }
         })

     }

     private fun bindEmpTaskData(_empList: List<UserData.EmployeeListBean>) {

         Collections.sort(_empList, object : Comparator<UserData.EmployeeListBean> {

             override fun compare(arg0: UserData.EmployeeListBean, arg1: UserData.EmployeeListBean): Int {

                 var compareResult = 0

                 compareResult = arg0.employeeInfo!!.firstName!!.compareTo(arg1.employeeInfo!!.firstName!!)

                 return compareResult
             }
         })

         var originalList = ArrayList<UserData.EmployeeListBean>()
         var empList = ArrayList<UserData.EmployeeListBean>()
         originalList.addAll(_empList)
         empList.addAll(_empList)

         adapter = CustomAdapter(object : CustomAdapter.AdapterListener {
             override fun filterData(countryName: String) {

                 empList.clear()
                 if (countryName.equals("")) {
                     empList.addAll(originalList)
                 } else {
                     for (emp in originalList) {
                         if (emp.employeeInfo!!.firstName!!.toLowerCase().contains(countryName.toLowerCase())
                                 ||emp.employeeInfo!!.lastName!!.toLowerCase().contains(countryName.toLowerCase())
                                 || (emp.employeeInfo!!.firstName!!.toLowerCase()+" "+ emp.employeeInfo!!.lastName!!.toLowerCase()).contains(countryName.toLowerCase())) {
                             empList.add(emp)
                         }
                     }
                 }

                 Collections.sort(empList, object : Comparator<UserData.EmployeeListBean> {

                     override fun compare(arg0: UserData.EmployeeListBean, arg1: UserData.EmployeeListBean): Int {

                         var compareResult = 0

                         compareResult = arg0.employeeInfo!!.firstName!!.compareTo(arg1.employeeInfo!!.firstName!!)

                         return compareResult
                     }
                 })
                 adapter.notifyDataSetChanged()

             }

             override val itemCount: Int
                 get() = empList.size


             override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                 return EmpViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.emp_item, parent, false))
             }

             override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                 val viewHolder = holder as EmpViewHolder

                 val SourceDataBean = empList.get(position)


                 viewHolder.empName.setText(SourceDataBean.employeeInfo!!.firstName + " " + SourceDataBean.employeeInfo!!.lastName)
                 viewHolder.passcode.setText(SourceDataBean.employeeInfo!!.passCode.toString())

                 Picasso.with(this@EmployeeListActivity).load(Constant.getUrl(this@EmployeeListActivity)!!.url + SourceDataBean.employeeInfo!!.photo).placeholder(resources.getDrawable(R.drawable.ic_action_name)).into(viewHolder.profileImage);

             }


             override fun getItemViewType(position: Int): Int {
                 return 0
             }
         })

         rvEmployeelist.adapter = adapter

     }

     private fun bindDeptTaskData(deptList: List<UserData.LstDeptsBean>) {


         adapter = CustomAdapter(object : CustomAdapter.AdapterListener {
             override fun filterData(countryName: String) {

             }

             override val itemCount: Int
                 get() = deptList.size


             override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                 return DeptViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dept_item, parent, false))
             }

             override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                 val viewHolder = holder as DeptViewHolder

                 val SourceDataBean = deptList.get(position)

                 viewHolder.deptName.setText(SourceDataBean.empDepartmentName)

                 viewHolder.deptId.setText(DecimalFormat("00").format(SourceDataBean.departmentCode!!).toString()+" - ")

             }


             override fun getItemViewType(position: Int): Int {
                 return 0
             }
         })

         rvEmployeelist.adapter = adapter

     }*/


