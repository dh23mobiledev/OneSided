package com.cab.user.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cab.user.R
import com.cab.user.ui.RideDetailActivity
import com.cab.user.ui.RideDetailsActivity
import kotlinx.android.synthetic.main.fragment_on_going_trips.view.*


class OnGoingTripsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view:View =  inflater.inflate(R.layout.fragment_on_going_trips, container, false)
        view.linearCard.setOnClickListener(View.OnClickListener { startActivity(Intent(this!!.activity!!, RideDetailActivity::class.java)) })
        return view
    }


}
