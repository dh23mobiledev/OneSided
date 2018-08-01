package com.psmtech.inngenius.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.cab.user.utils.Constant.REQUESTS.Companion.WIFI_ENABLE_REQUEST


/**
 * Created by ADMIN on 10/5/2017.
 */

open class BaseActivity : AppCompatActivity() {

    private val mNetworkDetectReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            checkInternetConnection()
        }
    }
    var mInternetDialog: AlertDialog? = null
    lateinit var mGPSDialog: AlertDialog

    lateinit var sp : SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sp = PreferenceManager.getDefaultSharedPreferences(this@BaseActivity)
        editor = sp.edit()

    }


    private fun checkInternetConnection() {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = manager.activeNetworkInfo

        if (ni != null && ni.state == NetworkInfo.State.CONNECTED) {
            if (mInternetDialog != null && mInternetDialog!!.isShowing) {
                mInternetDialog!!.dismiss()
            }
        } else {
            showNoInternetDialog()
        }
    }

    private fun showNoInternetDialog() {

        if (mInternetDialog != null && mInternetDialog!!.isShowing) {
            return
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Internet Disabled!")
        builder.setMessage("No active Internet connection found.")
        builder.setPositiveButton("Turn On") { dialog, which ->
            val gpsOptionsIntent = Intent(Settings.ACTION_WIFI_SETTINGS)
            startActivityForResult(gpsOptionsIntent, WIFI_ENABLE_REQUEST)
        }
        mInternetDialog = builder.create()
        mInternetDialog!!.show()
    }

    fun showGPSDiabledDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("GPS Disabled")
        builder.setMessage("Gps is disabled, in order to use the application properly you need to enable GPS of your device")
        builder.setPositiveButton("Enable GPS") { dialog, which ->
            val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(myIntent, 110)
        }
        mGPSDialog = builder.create()
        mGPSDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    fun closeKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(window.decorView.rootView.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }
}
