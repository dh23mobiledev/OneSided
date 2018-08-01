package com.cab.user.connection

import com.cab.user.model.BaseData
import com.cab.user.utils.Constant.API_USER_URL.Companion.GenerateOTP
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by ADMIN on 1/11/2018.
 */
interface Api {

    //-----For Signin


    @POST(GenerateOTP)
    fun GenerateOTP(@Query("userMobile") userMobile: String): Call<BaseData>
}