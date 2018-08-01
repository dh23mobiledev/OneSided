package com.cab.user.ui

import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import com.google.gson.Gson
import com.inngeniustimeclock.Connection.ApiConnection
import com.inngeniustimeclock.Connection.ApiConnection.connectPost
import com.cab.user.R
import com.cab.user.model.BaseData
import com.cab.user.model.UserData
import com.cab.user.utils.Constant
import com.cab.user.utils.Utils
import com.cab.user.utils.ValidateHelper
import com.psmtech.inngenius.connection.ApiCallBack.GenerateOTP
import okhttp3.Headers

class LoginActivity : AppCompatActivity(),View.OnClickListener {

    var btnGenOtp:Button ?= null
//    var edUserName:EditText ?= null
//    var edUserEmail:EditText ?= null
//    var edUserMobile:EditText ?= null
//    var relLogin:RelativeLayout ?= null
//    var dialog:Dialog ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()

    }

    private fun initViews() {

//        edUserName = findViewById(R.id.edUserName)
//        edUserEmail = findViewById(R.id.edUserEmail)
//        edUserMobile = findViewById(R.id.edUserMobile)
        btnGenOtp = findViewById(R.id.btnGenOtp)
//        relLogin = findViewById(R.id.relLogin)
//        dialog = Utils.getLoader(this)

        btnGenOtp!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnGenOtp -> {

                val intent = Intent(this@LoginActivity,OtpActivity::class.java)
                //intnt.putExtra("userData",Gson().toJson(userData))
                startActivity(intent)
                finish()


            }
        }

//                if (validate()) {
//
//                if (!Utils.isNetworkAvailable(this)) {
//                    Utils.showSnackBar(relLogin!!, getString(R.string.internet_not_avail))
//                    return
//                }
//                Login()
//
//            }
//        }
    }

//    fun validate(): Boolean {
//
//        if (!ValidateHelper.validateEditText(edUserName)) {
//            Utils.showSnackBar(relLogin!!, getString(R.string.lbl_uname_not_null))
//            edUserName!!.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red, 0)
//            return false
//        }
//        if (!ValidateHelper.validateEditTextMobile(edUserMobile)) {
//            Utils.showSnackBar(relLogin!!, getString(R.string.lbl_pwd_not_null))
//            edUserMobile!!.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red, 0)
//            return false
//        }
//
//        return true
//    }
//
//    private fun setListenersToEditTexts() {
//
//        edUserName!!.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//
//            }
//            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//                if (!ValidateHelper.validateEditText(edUserName)) {
//                    edUserName!!.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red, 0)
//                } else {
//                    edUserName!!.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_mark_green, 0)
//                }
//            }
//
//            override fun afterTextChanged(editable: Editable) {
//
//            }
//        })
//
//
//        edUserMobile!!.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//
//            }
//            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//                if (!ValidateHelper.validateEditTextMobile(edUserMobile)) {
//                    edUserMobile!!.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red, 0)
//                } else {
//                    edUserMobile!!.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_mark_green, 0)
//                }
//            }
//
//            override fun afterTextChanged(editable: Editable) {
//
//            }
//        })
//
//    }
//
//
//    private fun Login() {
//        dialog!!.show()
//        connectPost(this@LoginActivity, Constant.TAG, false, null, GenerateOTP(edUserMobile!!.text.toString()), object : ApiConnection.ConnectListener {
//            override fun onResponseSuccess(response: String, headers: Headers, StatusCode: Int) {
//                //
//
//                val userData = Gson().fromJson(response, UserData::class.java)
//                userData.setUseremail(edUserEmail!!.text.toString())
//                userData.setUsername(edUserName!!.text.toString())
//
//                val intnt = Intent(this@LoginActivity,OtpActivity::class.java)
//                intnt.putExtra("userData",Gson().toJson(userData))
//                startActivity(intent)
//                finish()
//
//                Toast.makeText(this@LoginActivity,response.toString(),Toast.LENGTH_LONG).show()
//                dialog!!.dismiss()
//
//            }
//
//
//            override fun onResponseFailure(responseData: BaseData, headers: Headers, StatusCode: Int) {
//                Utils.popToast(applicationContext, responseData.getMessage())
//                dialog!!.dismiss()
//            }
//
//            override fun onFailure(headers: Headers) {
//                Utils.popToast(applicationContext, getString(R.string.failureSignUp))
//                dialog!!.dismiss()
//            }
//
//            override fun onConnectionFailure() {
//                Utils.popToast(applicationContext, getString(R.string.errorCheckNet))
//                dialog!!.dismiss()
//            }
//
//            override fun onException(headers: Headers, StatusCode: Int) {
//                Utils.popToast(applicationContext, getString(R.string.errorSomething))
//                dialog!!.dismiss()
//            }
//        })
//
//
//    }

}
