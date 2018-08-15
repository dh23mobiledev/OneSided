package com.cab.user.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cab.user.R
import com.cab.user.ui.MyRidesDetailsActivity
import kotlinx.android.synthetic.main.fragment_on_going_trips.view.*


class OnGoingTripsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view:View =  inflater.inflate(R.layout.fragment_on_going_trips, container, false)
        view.linearCard.setOnClickListener(View.OnClickListener {

            var intent = Intent(activity, MyRidesDetailsActivity::class.java)
            intent.putExtra("type","2")
            startActivity(intent)

        })
        return view
    }


}
