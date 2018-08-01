package com.customview

/**
 * Created by android on 12/3/2016.
 */

import android.content.Context
import android.graphics.Color
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.ViewGroup


/**
 * Created by android on 12/3/2016.
 */

/**
 * Created by Alm on 9/26/2016.
 */
class CustomTabLayout : TabLayout {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun setupWithViewPager(viewPager: ViewPager?) {
        super.setupWithViewPager(viewPager)

        if (!isInEditMode) {
            // Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(), "Montserrat-Regular.otf");
            this.removeAllTabs()

            val slidingTabStrip = getChildAt(0) as ViewGroup

            val adapter = viewPager!!.adapter

            var i = 0
            val count = adapter!!.count
            while (i < count) {
                val tab = this.newTab()

                this.addTab(tab.setText(adapter.getPageTitle(i)))
                val view = (slidingTabStrip.getChildAt(i) as ViewGroup).getChildAt(1) as AppCompatTextView
                view.setTextColor(Color.WHITE)
                view.setAllCaps(true)

                i++
            }

        }

    }
}