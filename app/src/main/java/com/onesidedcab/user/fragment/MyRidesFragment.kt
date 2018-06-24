package com.onesidedcab.user.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.onesidedcab.user.R
import android.support.v4.view.ViewPager
import android.support.design.widget.TabLayout
import android.support.v7.widget.Toolbar
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentManager;


class MyRidesFragment : Fragment() {

    private var toolbar: Toolbar? = null
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View  =   inflater.inflate(R.layout.fragment_my_rides, container, false)
        initViews(view)
        return view;
    }

    private fun initViews(view: View) {

        toolbar = view.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true)
        viewPager = view.findViewById(R.id.viewpager)
        setupViewPager(viewPager!!);
        tabLayout = view.findViewById(R.id.tabs);
        tabLayout!!.setupWithViewPager(viewPager);


    }


    private fun setupViewPager(viewPager: ViewPager) {

        val adapter = ViewPagerAdapter(activity!!.getSupportFragmentManager())
        adapter.addFragment(OnGoingTripsFragment(), getString(R.string.ongoingtrips))
        adapter.addFragment(PastTripsFragment(), getString(R.string.pasttrips))
        viewPager.adapter = adapter
    }


    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

         val mFragmentList: ArrayList<Fragment>? = null
         val mFragmentTitleList:ArrayList<String>? = null

        override fun getItem(position: Int): Fragment {
            return mFragmentList!!.get(position)
        }

        override fun getCount(): Int {
            return mFragmentList!!.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList!!.add(fragment)
            mFragmentTitleList!!.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList!!.get(position)
        }
    }




}
