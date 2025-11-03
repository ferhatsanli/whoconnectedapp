package com.ferhat.whoconnected

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface ApiService{
    @GET("whoconnected")
    suspend fun getDevices(): Response<List<Device>>

    @GET("whoconnected")
    suspend fun getDevicesRaw(): Response<ResponseBody>
}