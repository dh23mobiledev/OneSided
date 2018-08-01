package com.psmtech.inngenius.connection

import com.inngeniustimeclock.Connection.ApiConnection
import com.cab.user.connection.Api
import com.cab.user.model.BaseData
import retrofit2.Call

/**
 * Created by ADMIN on 1/11/2018.
 */
object ApiCallBack {

    internal fun getRestInterface(): Api {

        val retrofit = ApiConnection.getClient()
        val restInterface = retrofit!!.create(Api::class.java)
        return restInterface
    }


    fun GenerateOTP(mobile:String): Call<BaseData> {
        return getRestInterface().GenerateOTP(mobile)
    }


}