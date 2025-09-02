package com.ferhat.whoconnected

import android.graphics.drawable.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Device(
    @Json(name = "Timestamp") var timestamp: String,
    @Json(name = "MAC Address") var macAddress: String,
    @Json(name = "IP Address") var ipAddress: String,
    @Json(name = "Device Name") var deviceName: String,
    @Transient var icon: ImageVector? = null // deserialized
)