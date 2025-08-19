package com.ferhat.whoconnected

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Device(
    @Json(name = "Timestamp") val timestamp: String,
    @Json(name = "MAC Address") val macAddress: String,
    @Json(name = "IP Address") val ipAddress: String,
    @Json(name = "Device Name") val deviceName: String
)
