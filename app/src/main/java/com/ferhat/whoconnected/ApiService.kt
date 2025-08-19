package com.ferhat.whoconnected

import retrofit2.http.GET

interface ApiService {
    @GET("whoconnected")
    suspend fun getDevices(): List<Device>
}