package com.onesidedcab.user.ui


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.crashlytics.android.Crashlytics
import com.onesidedcab.user.R
import com.onesidedcab.user.adapter.CustomAdapter
import com.onesidedcab.user.fragment.*
import com.onesidedcab.user.helper.FragmentDrawer
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),FragmentDrawer.FragmentDrawerListener {


    lateinit var adapter: CustomAdapter
    private val TAG = MainActivity::class.java.simpleName

    private var mToolbar: Toolbar? = null
    private var drawerFragment: FragmentDrawer? = null


    lateinit var fragment : Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        clickListener()




    }

    private fun clickListener() {


    }




    fun replaceFragment(id: Int) {

        when (id) {
            R.id.nav_oneway -> {
                fragment = BookRideFragment.newInstance()
                supportActionBar!!.setTitle(getString(R.string.nav_title_bookacab))
                //tvTitle.setText("Dashboard")
            }
            R.id.nav_hourly ->{fragment = BookRideFragment.newInstance()
                supportActionBar!!.setTitle(getString(R.string.nav_title_hourly))
                //tvTitle.setText("Notifications")
            }
            R.id.nav_outstation -> {fragment = OutStationFragment.newInstance()
                supportActionBar!!.setTitle(getString(R.string.nav_title_outstation))
                //tvTitle.setText("Message")
            }
        }

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_body, fragment, "")
                .commit()
    }



    private fun initViews() {

        Fabric.with(this, Crashlytics())
        mToolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.



        drawerFragment = supportFragmentManager.findFragmentById(R.id.fragment_navigation_drawer) as FragmentDrawer
        drawerFragment!!.setUp(R.id.fragment_navigation_drawer, findViewById(R.id.drawer_layout) as DrawerLayout, mToolbar)
        drawerFragment!!.setDrawerListener(this@MainActivity)

        // display the first navigation drawer view on app launch
        displayView(0)

        supportFragmentManager
                .beginTransaction()
                .add(R.id.container_body,BookRideFragment.newInstance(), "")
                .commit()


        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_oneway -> {
                    replaceFragment(R.id.nav_oneway)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_hourly -> {
                    replaceFragment(R.id.nav_hourly)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_outstation -> {
                    replaceFragment(R.id.nav_outstation)
                    return@OnNavigationItemSelectedListener true
                }


            }
            false
        }


        navigation.selectedItemId = R.id.nav_oneway

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }



    override fun onDrawerItemSelected(view: View?, position: Int) {
        displayView(position)

    }

    private fun displayView(position: Int) {

        var fragment: Fragment? = null
        var title = getString(R.string.app_name)
        when (position) {
            0 -> {
                fragment = BookRideFragment()
                title = getString(R.string.nav_title_bookacab)
                navigation.visibility = View.VISIBLE
            }
            1 -> {
                fragment = MyRidesFragment()
                title = getString(R.string.nav_title_myrides)
                navigation.visibility = View.VISIBLE
            }
            2 -> {
                fragment = ReferFragment()
                title = getString(R.string.nav_title_refer)
                navigation.visibility = View.GONE
            }
            3 -> {
                fragment = WalletFragment()
                title = getString(R.string.nav_title_wallet)
                navigation.visibility = View.GONE
            }
            4 -> {
                fragment = ProfileFragment()
                title = getString(R.string.nav_title_profile)
                navigation.visibility = View.GONE
            }
            5 -> {
                fragment = HelpFragment()
                title = getString(R.string.nav_title_help)
                navigation.visibility = View.GONE
            }
            else -> {
            }
        }

        if (fragment != null) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container_body, fragment)
            fragmentTransaction.commit()

            // set the toolbar title
            supportActionBar!!.setTitle(title)
        }
    }







   /* private fun initView() {

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )

        val linearLayoutManager = LinearLayoutManager(this@EmployeeListActivity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvEmployeelist.setLayoutManager(linearLayoutManager)

        imgBack.setOnClickListener { view ->

            finish()
        }

        if (intent.getIntExtra("type", -1) == 1) {

            bindEmpTaskData(Constant.getUserData(this@EmployeeListActivity)!!.employeeList!!)
            txtLabel.setText("Employee list")

        } else if (intent.getIntExtra("type", -1) == 2) {

            bindDeptTaskData(Constant.getUserData(this@EmployeeListActivity)!!.lstDepts!!)
            txtLabel.setText("Department list")
            search.visibility = View.GONE
        }

        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                adapter.listener.filterData(search.text.toString().trim())
            }
        })

    }

    private fun bindEmpTaskData(_empList: List<UserData.EmployeeListBean>) {

        Collections.sort(_empList, object : Comparator<UserData.EmployeeListBean> {

            override fun compare(arg0: UserData.EmployeeListBean, arg1: UserData.EmployeeListBean): Int {

                var compareResult = 0

                compareResult = arg0.employeeInfo!!.firstName!!.compareTo(arg1.employeeInfo!!.firstName!!)

                return compareResult
            }
        })

        var originalList = ArrayList<UserData.EmployeeListBean>()
        var empList = ArrayList<UserData.EmployeeListBean>()
        originalList.addAll(_empList)
        empList.addAll(_empList)

        adapter = CustomAdapter(object : CustomAdapter.AdapterListener {
            override fun filterData(countryName: String) {

                empList.clear()
                if (countryName.equals("")) {
                    empList.addAll(originalList)
                } else {
                    for (emp in originalList) {
                        if (emp.employeeInfo!!.firstName!!.toLowerCase().contains(countryName.toLowerCase())
                                ||emp.employeeInfo!!.lastName!!.toLowerCase().contains(countryName.toLowerCase())
                                || (emp.employeeInfo!!.firstName!!.toLowerCase()+" "+ emp.employeeInfo!!.lastName!!.toLowerCase()).contains(countryName.toLowerCase())) {
                            empList.add(emp)
                        }
                    }
                }

                Collections.sort(empList, object : Comparator<UserData.EmployeeListBean> {

                    override fun compare(arg0: UserData.EmployeeListBean, arg1: UserData.EmployeeListBean): Int {

                        var compareResult = 0

                        compareResult = arg0.employeeInfo!!.firstName!!.compareTo(arg1.employeeInfo!!.firstName!!)

                        return compareResult
                    }
                })
                adapter.notifyDataSetChanged()

            }

            override val itemCount: Int
                get() = empList.size


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return EmpViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.emp_item, parent, false))
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val viewHolder = holder as EmpViewHolder

                val SourceDataBean = empList.get(position)


                viewHolder.empName.setText(SourceDataBean.employeeInfo!!.firstName + " " + SourceDataBean.employeeInfo!!.lastName)
                viewHolder.passcode.setText(SourceDataBean.employeeInfo!!.passCode.toString())

                Picasso.with(this@EmployeeListActivity).load(Constant.getUrl(this@EmployeeListActivity)!!.url + SourceDataBean.employeeInfo!!.photo).placeholder(resources.getDrawable(R.drawable.ic_action_name)).into(viewHolder.profileImage);

            }


            override fun getItemViewType(position: Int): Int {
                return 0
            }
        })

        rvEmployeelist.adapter = adapter

    }

    private fun bindDeptTaskData(deptList: List<UserData.LstDeptsBean>) {


        adapter = CustomAdapter(object : CustomAdapter.AdapterListener {
            override fun filterData(countryName: String) {

            }

            override val itemCount: Int
                get() = deptList.size


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return DeptViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dept_item, parent, false))
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val viewHolder = holder as DeptViewHolder

                val SourceDataBean = deptList.get(position)

                viewHolder.deptName.setText(SourceDataBean.empDepartmentName)

                viewHolder.deptId.setText(DecimalFormat("00").format(SourceDataBean.departmentCode!!).toString()+" - ")

            }


            override fun getItemViewType(position: Int): Int {
                return 0
            }
        })

        rvEmployeelist.adapter = adapter

    }*/

}
