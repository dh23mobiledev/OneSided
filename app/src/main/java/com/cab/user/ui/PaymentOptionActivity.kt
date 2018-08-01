package com.cab.user.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.cab.user.R

class PaymentOptionActivity : AppCompatActivity() {

    lateinit var imgBak:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_option)

        initViews()
        clicklistener()
    }

    private fun initViews() {

        imgBak = findViewById(R.id.imgBak) as ImageView


    }



    private fun clicklistener() {

        imgBak.setOnClickListener(View.OnClickListener { startActivity(Intent(this@PaymentOptionActivity, MainActivity::class.java)) })


    }
}
