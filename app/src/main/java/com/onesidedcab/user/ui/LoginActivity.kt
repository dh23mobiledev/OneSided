package com.onesidedcab.user.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.onesidedcab.user.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnGetOtp.setOnClickListener(View.OnClickListener { startActivity(Intent(this@LoginActivity, OtpActivity::class.java)) })



    }
}
