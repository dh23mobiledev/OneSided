package com.onesidedcab.user.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import com.onesidedcab.user.BuildConfig
import com.onesidedcab.user.R
import com.wang.avi.AVLoadingIndicatorView

object Utils {

    // PERMISSIONS ------------------------------------------------------

    open fun checkPermissions(context: Context, permissions: Array<String>): Boolean {

        for (i in permissions.indices) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun requestPermissions(context: Context, permissions: Array<String>) {
        ActivityCompat.requestPermissions(context as Activity, permissions, Constant.REQUESTS.USER_PERMISSION)
    }




    fun showSnackBar(coordinatorLayout: View, msg: String) {

      //  Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG).show()
    }

    fun isNumeric(str: String?): Boolean {

        if (str == null) return false
        if (str.trim { it <= ' ' }.isEmpty()) return false

        try {
            val d = java.lang.Double.parseDouble(str)
        } catch (nfe: NumberFormatException) {
            return false
        }

        return true
    }

    // NETWORK ------------------------------------------------------

    fun isNetworkAvailable(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected


    }
    // DISPLAY ------------------------------------------------------

    fun popToast(context: Context, data: Any) {
        try {
            Toast.makeText(context, data.toString(), Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            checkLog("popToast", data.toString(), null)
        }

    }

    fun checkLog(TAG: String, data: Any, throwable: Throwable?) {
        if (BuildConfig.DEBUG) {
            if (throwable != null) {
                Log.d(TAG + ">>", data.toString(), throwable)
            } else {
                Log.d(TAG + ">>", data.toString())
            }
        }
    }


    // KEYBOARD ------------------------------------------------------

    fun closeKeyboard(context: Context) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow((context as Activity).window.decorView.rootView.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }

    fun closeKeyboard(context: Context, dialog: Dialog) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(dialog.window!!.decorView.rootView.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }


    fun getLoader(context: Context): Dialog {

        val loaderDialog = Dialog(context)

        val window = loaderDialog.window
        window!!.requestFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawableResource(R.drawable.dialog_basic_transparent)
        loaderDialog.setContentView(R.layout.dialog_loader)
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        loaderDialog.setCancelable(false)

        val loadingIndicatorView = loaderDialog.findViewById<AVLoadingIndicatorView>(R.id.loader)

        loaderDialog.setOnShowListener { loadingIndicatorView.show() }

        loaderDialog.setOnDismissListener { loadingIndicatorView.hide() }

        return loaderDialog
    }


    fun restartApp(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

//    fun getFormatDate(date: Date): String {
//        val format = SimpleDateFormat("dd-MM-yyyy, hh:mm a")
//        return format.format(date)
//    }
}