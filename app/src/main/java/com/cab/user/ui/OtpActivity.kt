package com.cab.user.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.cab.user.R
import kotlinx.android.synthetic.main.activity_otp.*

class OtpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

       // Toast.makeText(this,intent.getStringExtra("userData"),Toast.LENGTH_LONG).show()

        btn_Continue.setOnClickListener(View.OnClickListener { startActivity(Intent(this@OtpActivity, MainActivity::class.java)) })




    }
}
