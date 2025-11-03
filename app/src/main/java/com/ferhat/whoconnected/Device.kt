package com.ferhat.whoconnected

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Device(
    @Json(name = "Timestamp") var timestamp: String? = null,
    @Json(name = "MAC Address") var macAddress: String? = null,
    @Json(name = "IP Address") var ipAddress: String? = null,
    @Json(name = "Device Name") var deviceName: String? = null,
//    @Transient var icon: ImageVector? = null // deserialized
)