package com.onesidedcab.user.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.onesidedcab.user.R
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Fabric.with(this, Crashlytics())



    }
}
