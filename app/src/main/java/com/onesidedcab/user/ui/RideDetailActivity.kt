package com.onesidedcab.user.ui

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import com.onesidedcab.user.R
import kotlinx.android.synthetic.main.activity_ride_detail.*
import kotlinx.android.synthetic.main.cancel_dialog.*

class RideDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_detail)

        btnCancel.setOnClickListener(View.OnClickListener { openDialog(); })
    }

    private fun openDialog() {
        var dialog = Dialog(this@RideDetailActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.cancel_dialog)

        dialog.btnDialogCancel.setOnClickListener(View.OnClickListener { dialog.dismiss() })

        dialog.show();
    }
}
