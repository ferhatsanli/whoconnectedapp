package com.ferhat.whoconnected

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Tester(val api: ApiService) {
    val TAG = "Ferhat Test"
    fun devices(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val devices = api.getDevices()
                devices.forEach { device ->
                    Log.i(TAG, "devices: ${device.deviceName}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "devices: $e", )
            }
        }
    }

}