package com.inngeniustimeclock.Connection

import android.content.Context
import android.provider.SyncStateContract
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.onesidedcab.user.model.BaseData
import com.onesidedcab.user.utils.Constant
import com.onesidedcab.user.utils.MapDeserializerDoubleAsIntFix
import com.onesidedcab.user.utils.Utils
import okhttp3.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*

/**
 * Created by ADMIN on 1/11/2018.
 */
object ApiConnection {
    internal var retrofit: Retrofit? = null
    internal val MULTIPART_FORM_DATA = "multipart/form-data"


    interface ConnectListener {

        fun onResponseSuccess(response: String, headers: Headers, StatusCode: Int)

        fun onResponseFailure(responseData: BaseData, headers: Headers, StatusCode: Int)

        fun onFailure(headers: Headers)

        fun onConnectionFailure()

        fun onException(headers: Headers, StatusCode: Int)
    }

    fun connectPost(context: Context, TAG: String,
                    isLoader: Boolean, refreshLayout: SwipeRefreshLayout?,
                    connect: Call<BaseData>, listener: ConnectListener) {

        if (!Utils.isNetworkAvailable(context)) {
            listener.onConnectionFailure()
            return
        }

        val loader = Utils.getLoader(context)
        if (isLoader) {
            loader.show()
        }

        if (refreshLayout != null) {
            if (!refreshLayout.isRefreshing) {
                refreshLayout.isRefreshing = true
            }
        }


        Utils.checkLog(TAG, "Params: " + connect.request().url().toString(), null)

        connect.enqueue(object : retrofit2.Callback<BaseData> {
            override fun onResponse(call: Call<BaseData>, response: Response<BaseData>) {

                if (isLoader) {
                    loader.dismiss()
                }

                if (refreshLayout != null) {
                    if (refreshLayout.isRefreshing) {
                        refreshLayout.isRefreshing = false
                    }
                }

                try {
                    val gsonBuilder = GsonBuilder()
                    gsonBuilder.registerTypeAdapter(object : TypeToken<Map<String, Any>>() {

                    }.getType(), MapDeserializerDoubleAsIntFix())

                    val jsonString = /*new Gson()*/gsonBuilder.create().toJson(response.body())
                    Utils.checkLog(TAG, "Response: " + jsonString, null)
                //    Utils.popToast(context, jsonString.toString())
                    val responseData = response.body()

                    if (responseData.isSuccess()) {
                        listener.onResponseSuccess(/*new Gson()*/gsonBuilder.create().toJson(responseData.getData()),
                                response.headers(), response.code())
                    }/* else if (responseData.isSuccess() === false*//* Constants.STATUS_CODE.UNAUTHORIZED_ACCESS*//*) {
                      *//*  Utils.popToast(context, context.getString(R.string.sessionExpired))*//*
                       *//* Constant.clearUserData(context)
                        Utils.restartApp(context)*//*

                        listener.onResponseFailure(null,response.headers(),re)
                    }*/ else {
                        listener.onResponseFailure(responseData, response.headers(), response.code())
                    }

                } catch (e: Exception) {
                    if (e != null) {
                        e.printStackTrace()
                        Utils.checkLog(TAG, "Exception: " + e.message, e.cause)
                        Toast.makeText(context,e.message.toString(),Toast.LENGTH_LONG).show()
                    }
                    listener.onException(response.headers(), response.code())

                }

            }


            override fun onFailure(call: Call<BaseData>, t: Throwable) {

                if (isLoader) {
                    loader.dismiss()
                }

                if (refreshLayout != null) {
                    if (refreshLayout.isRefreshing) {
                        refreshLayout.isRefreshing = false
                    }
                }

                Utils.checkLog(TAG, "Failure: " + call.toString(), t)
                listener.onFailure(call.request().headers())

            }
        })
    }

    fun getClient(): Retrofit? {
      //  if (retrofit == null) {

            val gson = GsonBuilder()
                    .setLenient()
                    .create()

            retrofit = Retrofit.Builder()
                    .baseUrl(Constant.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
        //}
        return retrofit
    }

    fun prepareFilePart(partName: String, outputFile: String): MultipartBody.Part {
        val file = File(outputFile)
        val requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    fun createPartFromString(descriptionString: String?): RequestBody {
        var descriptionString = descriptionString
        if (descriptionString == null) {
            descriptionString = ""
        }
        return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), descriptionString)
    }

    @Throws(IOException::class)
    fun fromStream(`in`: InputStream): String {
        val reader = BufferedReader(InputStreamReader(`in`))
        val out = StringBuilder()
        val newLine = System.getProperty("line.separator")

        while ((reader.readLine()) != null) {
            out.append(reader.readLine())
            out.append(newLine)
        }
        return out.toString()
    }

    fun connectUpload(context: Context, TAG: String,
                      isLoader: Boolean, refreshLayout: SwipeRefreshLayout?,
                      connect: Call<ResponseBody>, listener: ConnectListener) {

        if (!Utils.isNetworkAvailable(context)) {
            listener.onConnectionFailure()
            return
        }

        val loader = Utils.getLoader(context)
        if (isLoader) {
            loader.show()
        }

        if (refreshLayout != null) {
            if (!refreshLayout.isRefreshing) {
                refreshLayout.isRefreshing = true
            }
        }

        Utils.checkLog(TAG, "Params: " + connect.request().body().toString(), null)

        connect.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (isLoader) {
                    loader.dismiss()
                }

                if (refreshLayout != null) {
                    if (refreshLayout.isRefreshing) {
                        refreshLayout.isRefreshing = false
                    }
                }

                try {

                    val responseBody = response.body()

                    val jsonObject = JSONObject(fromStream(responseBody.byteStream()))
                    Log.d(TAG, "Response: " + jsonObject.toString())
                    val responseData = Gson().fromJson<BaseData>(jsonObject.toString(), BaseData::class.java)

                    if (responseData.isSuccess()) {
                        listener.onResponseSuccess(Gson().toJson(responseData.getData()), response.headers(), response.code())
                    } else {
                        listener.onResponseFailure(responseData, response.headers(), response.code())
                    }

                } catch (e: Exception) {

                    if (e != null) {
                        e.printStackTrace()
                        Utils.checkLog(TAG, "Exception: " + e.message, e.cause)
                    }
                    listener.onException(response.headers(), response.code())

                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                if (isLoader) {
                    loader.dismiss()
                }

                if (refreshLayout != null) {
                    if (refreshLayout.isRefreshing) {
                        refreshLayout.isRefreshing = false
                    }
                }

                Utils.checkLog(TAG, "Failure: " + call.toString(), t)
                listener.onFailure(call.request().headers())
            }
        })

    }


}