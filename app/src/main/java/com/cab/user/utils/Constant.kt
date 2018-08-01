package com.cab.user.utils

import android.content.Context
import com.google.gson.Gson
import com.cab.user.BuildConfig
import com.cab.user.model.UserData

object Constant {


    // global topic to receive app wide push notifications
    val TOPIC_GLOBAL = "global"

    // broadcast receiver intent filters
    val REGISTRATION_COMPLETE = "registrationComplete"
    val PUSH_NOTIFICATION = "pushNotification"

    // id to handle the notification in the notification tray
    val NOTIFICATION_ID = 100
    val NOTIFICATION_ID_BIG_IMAGE = 101

    val SHARED_PREF = "ah_firebase"

    var APP_PHASE = 0



    interface REQUESTS {
        companion object {
            val USER_PERMISSION = 0
            val CODE = 1
            val GALLERY_PHOTO_PERMISSION = 2
            val CAPTURE_PHOTO_PERMISSION = 3
            val GALLERY_VIDEO_PERMISSION = 4
            val CAPTURE_VIDEO_PERMISSION = 5
            val PAYPAL_PERMISSION = 6
            const val WIFI_ENABLE_REQUEST = 7
        }
    }


    interface DEVICE_TYPE {
        companion object {
            val ANDROID = 1
            val IOS = 2
        }
    }

    interface STATUS_CODE {
        companion object {
            val EMPTY_DATABASE = 0
            val OK = 1
            val success = true
            val UNAUTHORIZED_ACCESS = 401
            val UNDEFINED = 3
            val BAD_REQUEST = 4
            val FILE_NOT_UPLOAD = 5
        }
    }

    interface BASE_URL {
        companion object {



            val LOCAL = "http://demo.onesidedcab.com/api/"
            val DEVELOPMENT = "http://demo.onesidedcab.com/api/"
            val LIVE = "http://demo.onesidedcab.com/api/"

        }
    }


    interface API_USER_URL {
        companion object {

            const val GenerateOTP = "GenerateOTP"


        }

    }


    fun setUserData(context: Context, userData: UserData) {
        val jsonString = Gson().toJson(userData)
        val sharedPreferences = context.getSharedPreferences(PREFERENCES.USER, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(PREFERENCES.USER, jsonString)
        editor.commit()
    }


   /* fun setUrl(context: Context, urlData: UrlData) {
        val jsonString = Gson().toJson(urlData)
        val sharedPreferences = context.getSharedPreferences(PREFERENCES.URL, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(PREFERENCES.URL, jsonString)
        editor.commit()
    }
*/

    fun getBaseUrl(): String {
        if (BuildConfig.DEBUG) {
            if (APP_PHASE == 0) {
                return BASE_URL.LOCAL
            } else {
                return BASE_URL.DEVELOPMENT
            }
        } else {
            return BASE_URL.LIVE
        }
    }


    var SPLASH_TIME_OUT = 3000


    val RESULT_CAMERA = 2
    val RESULT_GALLERY = 1

    val TAG = "OneSided"
    // "http://18.220.127.24/smart_limo/api/passenger/";


    interface PREFERENCES {
        companion object {
            val URL = "urlPrefs"
            val USER = "userPrefs"
        }
    }


    /* fun storeDataSignup1(context: Context, prof: String, fname: String, lname: String) {

         val preferences = PreferenceManager.getDefaultSharedPreferences(context)
         val editor = preferences.edit()
         editor.putString(Constants.USER_PROF, prof)
         editor.putString(Constants.USER_FNAME, fname)
         editor.putString(Constants.USER_LNAME, lname)
         editor.commit()

     }

     fun storeDataSignup2(context: Context, email: String, password: String, mobile: String, countrycode: String, countryname: String) {

         val preferences = PreferenceManager.getDefaultSharedPreferences(context)
         val editor = preferences.edit()
         editor.putString(Constants.USER_EMAIL, email)
         editor.putString(Constants.USER_PASSWORD, password)
         editor.putString(Constants.USER_MOBILE, mobile)
         editor.putString(Constants.USER_COUNTRY_CODE, countrycode)
         editor.putString(Constants.USER_COUNTRY_NAME, countryname)
         editor.commit()

     }

     fun clearRegisteraationData(context: Context) {

         val preferences = PreferenceManager.getDefaultSharedPreferences(context)
         val editor = preferences.edit()
         editor.putString(Constants.USER_PROF, "")
         editor.putString(Constants.USER_FNAME, "")
         editor.putString(Constants.USER_LNAME, "")
         editor.putString(Constants.USER_EMAIL, "")
         editor.putString(Constants.USER_PASSWORD, "")
         editor.putString(Constants.USER_MOBILE, "")
         editor.putString(Constants.USER_COUNTRY_CODE, "")
         editor.putString(Constants.USER_COUNTRY_NAME, "")
         editor.clear()
         editor.commit()

     }*/

    fun clearUserData(context: Context) {

        val sharedPreferences = context.getSharedPreferences(PREFERENCES.USER, Context.MODE_PRIVATE)
        if (sharedPreferences.contains(PREFERENCES.USER)) {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.commit()
        }

    }


    fun checkUserData(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES.USER, Context.MODE_PRIVATE)
        if (sharedPreferences.contains(PREFERENCES.USER)) {
            return true
        }
        return false
    }


    fun getUserData(context: Context): UserData? {

        val gson = Gson()
        val sharedPreferences = context.getSharedPreferences(PREFERENCES.USER, Context.MODE_PRIVATE)
        if (sharedPreferences.contains(PREFERENCES.USER)) {
            val userData = gson.fromJson<UserData>(sharedPreferences.getString(PREFERENCES.USER, null), UserData::class.java!!)
            if (userData != null) {
                return userData
            }
        }
        return null
    }

    /*fun getUrl(context: Context): UrlData? {

        val gson = Gson()
        val sharedPreferences = context.getSharedPreferences(PREFERENCES.URL, Context.MODE_PRIVATE)
        if (sharedPreferences.contains(PREFERENCES.URL)) {
            val urlData = gson.fromJson<UrlData>(sharedPreferences.getString(PREFERENCES.URL, null), UrlData::class.java!!)
            if (urlData != null) {
                return urlData
            }
        }
        return null
    }*/


}