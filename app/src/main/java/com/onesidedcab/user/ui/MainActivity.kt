package com.onesidedcab.user.ui

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.onesidedcab.user.R
import com.crashlytics.android.Crashlytics
import com.onesidedcab.user.adapter.CustomAdapter
import io.fabric.sdk.android.Fabric
import java.text.DecimalFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var adapter: CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Fabric.with(this, Crashlytics())



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
