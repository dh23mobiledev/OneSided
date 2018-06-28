package customview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Paint
import android.graphics.Typeface
import android.support.annotation.Nullable
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.transition.Transition
import android.support.transition.TransitionSet
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.SparseIntArray
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import java.lang.ref.WeakReference


/**
 * Created by ADMIN on 12/20/2017.
 */
internal class BottomNavigationViewEx : BottomNavigationView {
    // used for animation
    private var mShiftAmount: Int = 0
    private var mScaleUpFactor: Float = 0.toFloat()
    private var mScaleDownFactor: Float = 0.toFloat()
    private var animationRecord: Boolean = false
    private var mLargeLabelSize: Float = 0.toFloat()
    private var mSmallLabelSize: Float = 0.toFloat()
    private var visibilityTextSizeRecord: Boolean = false
    private var visibilityHeightRecord: Boolean = false
    private var mItemHeight: Int = 0
    private var textVisibility = true
    // used for animation end

    // used for setupWithViewPager
    private var mViewPager: ViewPager? = null
    private var mMyOnNavigationItemSelectedListener: MyOnNavigationItemSelectedListener? = null
    private var mPageChangeListener: BottomNavigationViewExOnPageChangeListener? = null
    private var mMenuView: BottomNavigationMenuView? = null
    private var mButtons: Array<BottomNavigationItemView>? = null

    /**
     * get the current checked item position
     *
     * @return index of item, start from 0.
     */
    /**
     * set the current checked item
     *
     * @param item start from 0.
     */
    /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;
        2. get field in mMenuView
        private BottomNavigationItemView[] mButtons;
        3. get menu and traverse it to get the checked one
         */// 1. get mMenuView
    //        BottomNavigationMenuView mMenuView = getBottomNavigationMenuView();
    // 2. get mButtons
    // 3. get menu and traverse it to get the checked one
    // check bounds
    /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;
        2. get field in mMenuView
        private BottomNavigationItemView[] mButtons;
        private final OnClickListener mOnClickListener;
        3. call mOnClickListener.onClick();
         */// 1. get mMenuView
    // 2. get mButtons
    // get mOnClickListener
    //        System.out.println("mMenuView:" + mMenuView + " mButtons:" + mButtons + " mOnClickListener" + mOnClickListener);
    // 3. call mOnClickListener.onClick();
    var currentItem: Int
        get() {
            val mButtons = bottomNavigationItemViews
            val menu = menu
            for (i in mButtons!!.indices) {
                if (menu.getItem(i).isChecked) {
                    return i
                }
            }
            return 0
        }
        set(item) {
            if (item < 0 || item >= maxItemCount) {
                throw ArrayIndexOutOfBoundsException("item is out of bounds, we expected 0 - "
                        + (maxItemCount - 1) + ". Actually " + item)
            }
            val mMenuView = bottomNavigationMenuView
            val mButtons = bottomNavigationItemViews
            val mOnClickListener = getField<View.OnClickListener>(mMenuView.javaClass, mMenuView, "mOnClickListener")
            mOnClickListener!!.onClick(mButtons!![item])

        }

    /**
     * get OnNavigationItemSelectedListener
     *
     * @return
     */
    // private OnNavigationItemSelectedListener mListener;
    val onNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener?
        get() =
            getField<OnNavigationItemSelectedListener>(BottomNavigationView::class.java, this, "mSelectedListener")

    /**
     * get private mMenuView
     *
     * @return
     */
    private val bottomNavigationMenuView: BottomNavigationMenuView
        get() {
            if (null == mMenuView)
                mMenuView = getField<BottomNavigationMenuView>(BottomNavigationView::class.java, this, "mMenuView")
            return this.mMenuView!!
        }

    /**
     * get private mButtons in mMenuView
     *
     * @return
     */
    /*
         * 1 private final BottomNavigationMenuView mMenuView;
         * 2 private BottomNavigationItemView[] mButtons;
         */ val bottomNavigationItemViews: Array<BottomNavigationItemView>?
        get() {
            if (null != mButtons)
                return mButtons
            val mMenuView = bottomNavigationMenuView
            mButtons = getField<Array<BottomNavigationItemView>>(mMenuView.javaClass, mMenuView, "mButtons")
            return mButtons
        }

    /**
     * return item count
     *
     * @return
     */
    val itemCount: Int
        get() {
            val bottomNavigationItemViews = bottomNavigationItemViews ?: return 0
            return bottomNavigationItemViews.size
        }

    /**
     * get menu item height
     *
     * @return in px
     */
    /**
     * set menu item height
     *
     * @param height in px
     */
    // 1. get mMenuView
    // 2. get private final int mItemHeight in mMenuView
    // 1. get mMenuView
    // 2. set private final int mItemHeight in mMenuView
    var itemHeight: Int
        get() {
            val mMenuView = bottomNavigationMenuView
            return getField<Int>(mMenuView.javaClass, mMenuView, "mItemHeight")!!
        }
        @SuppressLint("RestrictedApi")
        set(height) {
            val mMenuView = bottomNavigationMenuView
            setField(mMenuView.javaClass, mMenuView, "mItemHeight", height)

            mMenuView.updateMenuView()
        }

    constructor(context: Context) : super(context) {
        //        init();
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        //        init();
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        //        init();
    }

    private fun init() {
        try {
            addAnimationListener()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun addAnimationListener() {
        /**
         * 1. BottomNavigationMenuView mMenuView
         * 2. private final BottomNavigationAnimationHelperBase mAnimationHelper;
         * 3. private final TransitionSet mSet;
         */
        val mMenuView = bottomNavigationMenuView
        val mAnimationHelper = getField<Any>(mMenuView.javaClass, mMenuView, "mAnimationHelper")
        val mSet = getField<TransitionSet>(mAnimationHelper!!.javaClass, mAnimationHelper, "mSet")
        mSet!!.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {}

            override fun onTransitionEnd(transition: Transition) {
                refreshTextViewVisibility()
            }

            override fun onTransitionCancel(transition: Transition) {
                refreshTextViewVisibility()
            }

            override fun onTransitionPause(transition: Transition) {}

            override fun onTransitionResume(transition: Transition) {}
        })
    }

    @SuppressLint("RestrictedApi")
    private fun refreshTextViewVisibility() {
        if (!textVisibility)
            return
        // 2. get mButtons
        val mButtons = bottomNavigationItemViews

        val currentItem = currentItem

        // 3. get field mShiftingMode and TextView in mButtons
        for (button in mButtons!!) {
            val mLargeLabel = getField<TextView>(button.javaClass, button, "mLargeLabel")
            val mSmallLabel = getField<TextView>(button.javaClass, button, "mSmallLabel")

            mLargeLabel!!.clearAnimation()
            mSmallLabel!!.clearAnimation()

            // mShiftingMode
            val mShiftingMode = getField<Boolean>(button.javaClass, button, "mShiftingMode")!!
            val selected = button.itemPosition == currentItem
            if (mShiftingMode) {
                if (selected) {
                    mLargeLabel.visibility = View.VISIBLE
                } else {
                    mLargeLabel.visibility = View.INVISIBLE
                }
                mSmallLabel.visibility = View.INVISIBLE
            } else {
                if (selected) {
                    mLargeLabel.visibility = View.VISIBLE
                    mSmallLabel.visibility = View.INVISIBLE
                } else {
                    mLargeLabel.visibility = View.INVISIBLE
                    mSmallLabel.visibility = View.VISIBLE
                }
            }
        }
    }


    @SuppressLint("RestrictedApi")
            /**
     * change the visibility of icon
     *
     * @param visibility
     */
    fun setIconVisibility(visibility: Boolean) {
        /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;
        2. get field in mButtons
        private BottomNavigationItemView[] mButtons;
        3. get mIcon in mButtons
        private ImageView mIcon
        4. set mIcon visibility gone
        5. change mItemHeight to only text size in mMenuView
         */
        // 1. get mMenuView
        val mMenuView = bottomNavigationMenuView
        // 2. get mButtons
        val mButtons = bottomNavigationItemViews
        // 3. get mIcon in mButtons
        for (button in mButtons!!) {
            val mIcon = getField<ImageView>(button.javaClass, button, "mIcon")
            // 4. set mIcon visibility gone
            mIcon!!.setVisibility(if (visibility) View.VISIBLE else View.INVISIBLE)
        }

        // 5. change mItemHeight to only text size in mMenuView
        if (!visibility) {
            // if not record mItemHeight
            if (!visibilityHeightRecord) {
                visibilityHeightRecord = true
                mItemHeight = itemHeight
            }

            // change mItemHeight
            val button = mButtons[0]
          //  if (null != button) {
                val mIcon = getField<ImageView>(button.javaClass, button, "mIcon")
                //                System.out.println("mIcon.getMeasuredHeight():" + mIcon.getMeasuredHeight());
                if (null != mIcon) {
                    mIcon.post(Runnable {
                        //                            System.out.println("mIcon.getMeasuredHeight():" + mIcon.getMeasuredHeight());
                        itemHeight = mItemHeight - mIcon.getMeasuredHeight()
                    })
                }
           // }
        } else {
            // if not record the mItemHeight, we need do nothing.
            if (!visibilityHeightRecord)
                return

            // restore it
            itemHeight = mItemHeight
        }

        mMenuView.updateMenuView()
    }

    @SuppressLint("RestrictedApi")
            /**
     * change the visibility of text
     *
     * @param visibility
     */
    fun setTextVisibility(visibility: Boolean) {
        this.textVisibility = visibility
        /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;
        2. get field in mButtons
        private BottomNavigationItemView[] mButtons;
        3. set text size in mButtons
        private final TextView mLargeLabel
        private final TextView mSmallLabel
        4. change mItemHeight to only icon size in mMenuView
         */
        // 1. get mMenuView
        val mMenuView = bottomNavigationMenuView
        // 2. get mButtons
        val mButtons = bottomNavigationItemViews

        // 3. change field mShiftingMode value in mButtons
        for (button in mButtons!!) {
            val mLargeLabel = getField<TextView>(button.javaClass, button, "mLargeLabel")
            val mSmallLabel = getField<TextView>(button.javaClass, button, "mSmallLabel")

            if (!visibility) {
                // if not record the font size, record it
                if (!visibilityTextSizeRecord && !animationRecord) {
                    visibilityTextSizeRecord = true
                    mLargeLabelSize = mLargeLabel!!.textSize
                    mSmallLabelSize = mSmallLabel!!.textSize
                }

                // if not visitable, set font size to 0
                mLargeLabel!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0f)
                mSmallLabel!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0f)

            } else {
                // if not record the font size, we need do nothing.
                if (!visibilityTextSizeRecord)
                    break

                // restore it
                mLargeLabel!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLargeLabelSize)
                mSmallLabel!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize)
            }
        }

        // 4 change mItemHeight to only icon size in mMenuView
        if (!visibility) {
            // if not record mItemHeight
            if (!visibilityHeightRecord) {
                visibilityHeightRecord = true
                mItemHeight = itemHeight
            }

            // change mItemHeight to only icon size in mMenuView
            // private final int mItemHeight;

            // change mItemHeight
            //            System.out.println("mLargeLabel.getMeasuredHeight():" + getFontHeight(mSmallLabelSize));
            itemHeight = mItemHeight - getFontHeight(mSmallLabelSize)

        } else {
            // if not record the mItemHeight, we need do nothing.
            if (!visibilityHeightRecord)
                return
            // restore mItemHeight
            itemHeight = mItemHeight
        }

        mMenuView.updateMenuView()
    }

    /**
     * enable or disable click item animation(text scale and icon move animation in no item shifting mode)
     *
     * @param enable It means the text won't scale and icon won't move when active it in no item shifting mode if false.
     */
    @SuppressLint("RestrictedApi")
    fun enableAnimation(enable: Boolean) {
        /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;
        2. get field in mButtons
        private BottomNavigationItemView[] mButtons;
        3. chang mShiftAmount to 0 in mButtons
        private final int mShiftAmount
        change mScaleUpFactor and mScaleDownFactor to 1f in mButtons
        private final float mScaleUpFactor
        private final float mScaleDownFactor
        4. change label font size in mButtons
        private final TextView mLargeLabel
        private final TextView mSmallLabel
         */

        // 1. get mMenuView
        val mMenuView = bottomNavigationMenuView
        // 2. get mButtons
        val mButtons = bottomNavigationItemViews
        // 3. change field mShiftingMode value in mButtons
        for (button in mButtons!!) {
            val mLargeLabel = getField<TextView>(button.javaClass, button, "mLargeLabel")
            val mSmallLabel = getField<TextView>(button.javaClass, button, "mSmallLabel")

            // if disable animation, need animationRecord the source value
            if (!enable) {
                if (!animationRecord) {
                    animationRecord = true
                    mShiftAmount = getField<Int>(button.javaClass, button, "mShiftAmount")!!
                    mScaleUpFactor = getField<Float>(button.javaClass, button, "mScaleUpFactor")!!
                    mScaleDownFactor = getField<Float>(button.javaClass, button, "mScaleDownFactor")!!

                    mLargeLabelSize = mLargeLabel!!.textSize
                    mSmallLabelSize = mSmallLabel!!.textSize

                    //                    System.out.println("mShiftAmount:" + mShiftAmount + " mScaleUpFactor:"
                    //                            + mScaleUpFactor + " mScaleDownFactor:" + mScaleDownFactor
                    //                            + " mLargeLabel:" + mLargeLabelSize + " mSmallLabel:" + mSmallLabelSize);
                }
                // disable
                setField(button.javaClass, button, "mShiftAmount", 0)
                setField(button.javaClass, button, "mScaleUpFactor", 1)
                setField(button.javaClass, button, "mScaleDownFactor", 1)

                // let the mLargeLabel font size equal to mSmallLabel
                mLargeLabel!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize)

                // debug start
                //                mLargeLabelSize = mLargeLabel.getTextSize();
                //                System.out.println("mLargeLabel:" + mLargeLabelSize);
                // debug end

            } else {
                // haven't change the value. It means it was the first call this method. So nothing need to do.
                if (!animationRecord)
                    return
                // enable animation
                setField(button.javaClass, button, "mShiftAmount", mShiftAmount)
                setField(button.javaClass, button, "mScaleUpFactor", mScaleUpFactor)
                setField(button.javaClass, button, "mScaleDownFactor", mScaleDownFactor)
                // restore
                mLargeLabel!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLargeLabelSize)
            }
        }
        mMenuView.updateMenuView()
    }

    /**
     * enable the shifting mode for navigation
     *
     * @param enable It will has a shift animation if true. Otherwise all items are the same width.
     */
    @SuppressLint("RestrictedApi")
    fun enableShiftingMode(enable: Boolean) {
        /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;
        2. change field mShiftingMode value in mMenuView
        private boolean mShiftingMode = true;
         */
        // 1. get mMenuView
        val mMenuView = bottomNavigationMenuView
        // 2. change field mShiftingMode value in mMenuView
        setField(mMenuView.javaClass, mMenuView, "mShiftingMode", enable)

        mMenuView.updateMenuView()
    }

    /**
     * enable the shifting mode for each item
     *
     * @param enable It will has a shift animation for item if true. Otherwise the item text always be shown.
     */
    @SuppressLint("RestrictedApi")
    fun enableItemShiftingMode(enable: Boolean) {
        /*
        1. get field in this class
        private final BottomNavigationMenuView mMenuView;
        2. get field in this mMenuView
        private BottomNavigationItemView[] mButtons;
        3. change field mShiftingMode value in mButtons
        private boolean mShiftingMode = true;
         */
        // 1. get mMenuView
        val mMenuView = bottomNavigationMenuView
        // 2. get mButtons
        val mButtons = bottomNavigationItemViews
        // 3. change field mShiftingMode value in mButtons
        for (button in mButtons!!) {
            setField(button.javaClass, button, "mShiftingMode", enable)
        }
        mMenuView.updateMenuView()
    }

    /**
     * get menu item position in menu
     *
     * @param item
     * @return position if success, -1 otherwise
     */
    fun getMenuItemPosition(item: MenuItem): Int {
        // get item id
        val itemId = item.getItemId()
        // get meunu
        val menu = menu
        val size = menu.size()
        for (i in 0 until size) {
            if (menu.getItem(i).itemId === itemId) {
                return i
            }
        }
        return -1
    }

    override fun setOnNavigationItemSelectedListener(@Nullable listener: BottomNavigationView.OnNavigationItemSelectedListener?) {
        // if not set up with view pager, the same with father
        if (null == mMyOnNavigationItemSelectedListener) {
            super.setOnNavigationItemSelectedListener(listener)
            return
        }

        mMyOnNavigationItemSelectedListener!!.setOnNavigationItemSelectedListener(listener)
    }

    /**
     * get private mButton in mMenuView at position
     *
     * @param position
     * @return
     */
    fun getBottomNavigationItemView(position: Int): BottomNavigationItemView {
        return bottomNavigationItemViews!![position]
    }

    /**
     * get icon at position
     *
     * @param position
     * @return
     */
    fun getIconAt(position: Int): ImageView? {
        /*
         * 1 private final BottomNavigationMenuView mMenuView;
         * 2 private BottomNavigationItemView[] mButtons;
         * 3 private ImageView mIcon;
         */
        val mButtons = getBottomNavigationItemView(position)
        return getField<ImageView>(BottomNavigationItemView::class.java, mButtons, "mIcon")
    }

    /**
     * get small label at position
     * Each item has tow label, one is large, another is small.
     *
     * @param position
     * @return
     */
    fun getSmallLabelAt(position: Int): TextView? {
        /*
         * 1 private final BottomNavigationMenuView mMenuView;
         * 2 private BottomNavigationItemView[] mButtons;
         * 3 private final TextView mSmallLabel;
         */
        val mButtons = getBottomNavigationItemView(position)
        return getField<TextView>(BottomNavigationItemView::class.java, mButtons, "mSmallLabel")
    }

    /**
     * get large label at position
     * Each item has tow label, one is large, another is small.
     *
     * @param position
     * @return
     */
    fun getLargeLabelAt(position: Int): TextView? {
        /*
         * 1 private final BottomNavigationMenuView mMenuView;
         * 2 private BottomNavigationItemView[] mButtons;
         * 3 private final TextView mLargeLabel;
         */
        val mButtons = getBottomNavigationItemView(position)
        return getField<TextView>(BottomNavigationItemView::class.java, mButtons, "mLargeLabel")
    }

    /**
     * set all item small TextView size
     * Each item has tow label, one is large, another is small.
     * Small one will be shown when item state is normal
     * Large one will be shown when item checked.
     *
     * @param sp
     */
    @SuppressLint("RestrictedApi")
    fun setSmallTextSize(sp: Float) {
        val count = itemCount
        for (i in 0 until count) {
            getSmallLabelAt(i)!!.textSize = sp
        }
        mMenuView!!.updateMenuView()
    }

    /**
     * set all item large TextView size
     * Each item has tow label, one is large, another is small.
     * Small one will be shown when item state is normal.
     * Large one will be shown when item checked.
     *
     * @param sp
     */
    @SuppressLint("RestrictedApi")
    fun setLargeTextSize(sp: Float) {
        val count = itemCount
        for (i in 0 until count) {
            getLargeLabelAt(i)!!.textSize = sp
        }
        mMenuView!!.updateMenuView()
    }

    /**
     * set all item large and small TextView size
     * Each item has tow label, one is large, another is small.
     * Small one will be shown when item state is normal
     * Large one will be shown when item checked.
     *
     * @param sp
     */
    fun setTextSize(sp: Float) {
        setLargeTextSize(sp)
        setSmallTextSize(sp)
    }

    /**
     * set item ImageView size which at position
     *
     * @param position position start from 0
     * @param width    in dp
     * @param height   in dp
     */
    @SuppressLint("RestrictedApi")
    fun setIconSizeAt(position: Int, width: Float, height: Float) {
        val icon = getIconAt(position)
        // update size
        val layoutParams = icon!!.getLayoutParams()
        layoutParams.width = dp2px(context, width)
        layoutParams.height = dp2px(context, height)
        icon.setLayoutParams(layoutParams)

        mMenuView!!.updateMenuView()
    }

    /**
     * set all item ImageView size
     *
     * @param width  in dp
     * @param height in dp
     */
    fun setIconSize(width: Float, height: Float) {
        val count = itemCount
        for (i in 0 until count) {
            setIconSizeAt(i, width, height)
        }
    }

    /**
     * set Typeface for all item TextView
     *
     * @attr ref android.R.styleable#TextView_typeface
     * @attr ref android.R.styleable#TextView_textStyle
     */
    @SuppressLint("RestrictedApi")
    fun setTypeface(typeface: Typeface, style: Int) {
        val count = itemCount
        for (i in 0 until count) {
            getLargeLabelAt(i)!!.setTypeface(typeface, style)
            getSmallLabelAt(i)!!.setTypeface(typeface, style)
        }
        mMenuView!!.updateMenuView()
    }

    @SuppressLint("RestrictedApi")
            /**
     * set Typeface for all item TextView
     *
     * @attr ref android.R.styleable#TextView_typeface
     */
    fun setTypeface(typeface: Typeface) {
        val count = itemCount
        for (i in 0 until count) {
            getLargeLabelAt(i)!!.typeface = typeface
            getSmallLabelAt(i)!!.typeface = typeface
        }
        mMenuView!!.updateMenuView()
    }

    /**
     * get private filed in this specific object
     *
     * @param targetClass
     * @param instance    the filed owner
     * @param fieldName
     * @param <T>
     * @return field if success, null otherwise.
    </T> */
    private fun <T> getField(targetClass: Class<*>, instance: Any, fieldName: String): T? {
        try {
            val field = targetClass.getDeclaredField(fieldName)
            field.isAccessible = true
            return field.get(instance) as T
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * change the field value
     *
     * @param targetClass
     * @param instance    the filed owner
     * @param fieldName
     * @param value
     */
    private fun setField(targetClass: Class<*>, instance: Any, fieldName: String, value: Any) {
        try {
            val field = targetClass.getDeclaredField(fieldName)
            field.isAccessible = true
            field.set(instance, value)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

    /**
     * This method will link the given ViewPager and this BottomNavigationViewEx together so that
     * changes in one are automatically reflected in the other. This includes scroll state changes
     * and clicks.
     *
     * @param viewPager
     * @param smoothScroll whether ViewPager changed with smooth scroll animation
     */
    @JvmOverloads
    fun setupWithViewPager(@Nullable viewPager: ViewPager?, smoothScroll: Boolean = false) {
        if (mViewPager != null) {
            // If we've already been setup with a ViewPager, remove us from it
            if (mPageChangeListener != null) {
                mViewPager!!.removeOnPageChangeListener(mPageChangeListener!!)
            }
        }

        if (null == viewPager) {
            mViewPager = null
            super.setOnNavigationItemSelectedListener(null)
            return
        }

        mViewPager = viewPager

        // Add our custom OnPageChangeListener to the ViewPager
        if (mPageChangeListener == null) {
            mPageChangeListener = BottomNavigationViewExOnPageChangeListener(this)
        }
        viewPager.addOnPageChangeListener(mPageChangeListener!!)

        // Now we'll add a navigation item selected listener to set ViewPager's current item
        val listener = onNavigationItemSelectedListener
        mMyOnNavigationItemSelectedListener = MyOnNavigationItemSelectedListener(viewPager, this, smoothScroll, listener)
        super.setOnNavigationItemSelectedListener(mMyOnNavigationItemSelectedListener)
    }

    /**
     * A [ViewPager.OnPageChangeListener] class which contains the
     * necessary calls back to the provided [BottomNavigationViewEx] so that the tab position is
     * kept in sync.
     *
     *
     *
     * This class stores the provided BottomNavigationViewEx weakly, meaning that you can use
     * [ addOnPageChangeListener(OnPageChangeListener)][ViewPager.addOnPageChangeListener] without removing the listener and
     * not cause a leak.
     */
    private class BottomNavigationViewExOnPageChangeListener(bnve: BottomNavigationViewEx) : ViewPager.OnPageChangeListener {
        private val mBnveRef: WeakReference<BottomNavigationViewEx>

        init {
            mBnveRef = WeakReference(bnve)
        }

        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(position: Int, positionOffset: Float,
                                    positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            val bnve = mBnveRef.get()
            if (null != bnve && !isNavigationItemClicking)
                bnve.currentItem = position
            //            Log.d("onPageSelected", "--------- position " + position + " ------------");
        }
    }

    /**
     * Decorate OnNavigationItemSelectedListener for setupWithViewPager
     */
    private class MyOnNavigationItemSelectedListener internal constructor(viewPager: ViewPager, bnve: BottomNavigationViewEx, private val smoothScroll: Boolean, private var listener: BottomNavigationView.OnNavigationItemSelectedListener?) : BottomNavigationView.OnNavigationItemSelectedListener {
        private val viewPagerRef: WeakReference<ViewPager>
        private val items: SparseIntArray// used for change ViewPager selected item
        private var previousPosition = -1


        init {
            this.viewPagerRef = WeakReference(viewPager)

            // create items
            val menu = bnve.menu
            val size = menu.size()
            items = SparseIntArray(size)
            for (i in 0 until size) {
                val itemId = menu.getItem(i).itemId
                items.put(itemId, i)
            }
        }

        fun setOnNavigationItemSelectedListener(listener: BottomNavigationView.OnNavigationItemSelectedListener?) {
            this.listener = listener
        }

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            val position = items.get(item.getItemId())
            // only set item when item changed
            if (previousPosition == position) {
                return true
            }
            //            Log.d("onNavigationItemSelecte", "position:"  + position);
            // user listener
            if (null != listener) {
                val bool = listener!!.onNavigationItemSelected(item)
                // if the selected is invalid, no need change the view pager
                if (!bool)
                    return false
            }

            // change view pager
            val viewPager = viewPagerRef.get() ?: return false

            // use isNavigationItemClicking flag to avoid `ViewPager.OnPageChangeListener` trigger
            isNavigationItemClicking = true
            viewPager.setCurrentItem(items.get(item.getItemId()), smoothScroll)
            isNavigationItemClicking = false

            // update previous position
            previousPosition = position

            return true
        }

    }

    @SuppressLint("RestrictedApi")
    fun enableShiftingMode(position: Int, enable: Boolean) {
        getBottomNavigationItemView(position).setShiftingMode(enable)
    }

    @SuppressLint("RestrictedApi")
    fun setItemBackground(position: Int, background: Int) {
        getBottomNavigationItemView(position).setItemBackground(background)
    }

    @SuppressLint("RestrictedApi")
    fun setIconTintList(position: Int, tint: ColorStateList) {
        getBottomNavigationItemView(position).setIconTintList(tint)
    }

    @SuppressLint("RestrictedApi")
    fun setTextTintList(position: Int, tint: ColorStateList) {
        getBottomNavigationItemView(position).setTextColor(tint)
    }

    /**
     * set margin top for all icons
     *
     * @param marginTop in px
     */
    fun setIconsMarginTop(marginTop: Int) {
        for (i in 0 until itemCount) {
            setIconMarginTop(i, marginTop)
        }
    }

    @SuppressLint("RestrictedApi")
            /**
     * set margin top for icon
     *
     * @param position
     * @param marginTop in px
     */
    fun setIconMarginTop(position: Int, marginTop: Int) {
        /*
        1. BottomNavigationItemView
        2. private final int mDefaultMargin;
         */
        val itemView = getBottomNavigationItemView(position)
        setField(BottomNavigationItemView::class.java, itemView, "mDefaultMargin", marginTop)
        mMenuView!!.updateMenuView()
    }

    companion object {
        // used for setupWithViewPager end

        // detect navigation tab changes when the user clicking on navigation item
        private var isNavigationItemClicking = false

        /**
         * get text height by font size
         *
         * @param fontSize
         * @return
         */
        private fun getFontHeight(fontSize: Float): Int {
            val paint = Paint()
            paint.setTextSize(fontSize)
            val fm = paint.getFontMetrics()
            return Math.ceil((fm.descent - fm.top).toDouble()).toInt() + 2
        }

        /**
         * dp to px
         *
         * @param context
         * @param dpValue dp
         * @return px
         */
        fun dp2px(context: Context, dpValue: Float): Int {
            val scale = context.getResources().getDisplayMetrics().density
            return (dpValue * scale + 0.5f).toInt()
        }
    }

}
/**
 * This method will link the given ViewPager and this BottomNavigationViewEx together so that
 * changes in one are automatically reflected in the other. This includes scroll state changes
 * and clicks.
 *
 * @param viewPager
 */