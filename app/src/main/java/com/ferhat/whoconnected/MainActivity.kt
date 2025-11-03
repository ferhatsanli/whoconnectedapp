package com.ferhat.whoconnected

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ferhat.whoconnected.ui.theme.WhoConnectedTheme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import okhttp3.OkHttpClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import androidx.compose.runtime.LaunchedEffect


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhoConnectedTheme {
                ButtonAndList()
            }
        }
    }
}
const val TAG = "FERHAT"

object RetrofitClient {

    // Create Moshi instance with Kotlin adapter so @Json and @JsonClass are honored
    val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val logging = HttpLoggingInterceptor { message -> Log.d(TAG, "OkHttp: $message") }
        .apply { level = HttpLoggingInterceptor.Level.BODY }

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://ferhatsanli.duckdns.org/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttp)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}

suspend fun gatherData(): List<Device> {
    return try {
        val resp = RetrofitClient.apiService.getDevices()
        if (resp.isSuccessful) {
            val body = resp.body()
            if (body != null) {
                Log.i(TAG, "gatherData: received ${body.size} items")
                body.forEachIndexed { i, d -> Log.i(TAG, "gatherData item[$i]=$d") }
                // normalize: ensure deviceName present
                body.map { d ->
                    if (d.deviceName.isNullOrBlank()) {
                        val fallback = d.ipAddress ?: d.macAddress ?: "Unknown device"
                        d.copy(deviceName = fallback)
                    } else d
                }
            } else {
                Log.e(TAG, "gatherData: response body is null")
                emptyList()
            }
        } else {
            Log.e(TAG, "gatherData: unsuccessful response: ${resp.code()} ${resp.message()}")
            emptyList()
        }
    } catch (e: Exception) {
        Log.e(TAG, "gatherData error", e)
        emptyList()
    }
}

@Composable
fun ButtonAndList() {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var data by remember { mutableStateOf<List<Device>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    val onDeviceClick: (Device) -> Unit = { device ->
        scope.launch {
            snackbarHostState.showSnackbar(
                "Timestamp: ${device.timestamp}\nMAC: ${device.macAddress}"
            )
        }
    }

    LaunchedEffect(Unit) {
        // auto-load on first composition
        isLoading = true
        try {
            data = gatherData()
        } catch (e: Exception) {
            Log.e(TAG, "Auto load error", e)
            errorMsg = e.message
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            // button at top center
            Button(onClick = {
                scope.launch {
                    isLoading = true
                    errorMsg = null
                    try {
                        data = gatherData()
                    } catch (e: Exception) {
                        Log.e(TAG, "ButtonAndList: load error", e)
                        errorMsg = e.message
                    } finally {
                        isLoading = false
                    }
                }
            }, modifier = Modifier.padding(top = 16.dp).size(120.dp, 48.dp)) {
                Text("Refresh")
            }

            // list area: takes remaining space
            Box(modifier = Modifier.weight(1f)) {
                if (isLoading) {
                    Text("Loading...")
                } else if (errorMsg != null) {
                    Text("Error: $errorMsg")
                } else {
                    DeviceList(data, onDeviceClick = onDeviceClick)
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonAndListPreview() {
    WhoConnectedTheme {
        val sampleCells = listOf(
            Device(
                "2025-09-02 23:07:05",
                "E2:41:9A:CA:C0:18",
                "192.168.178.37",
                "Ferhat-S23"
            ),
            Device(
                "2025-09-02 23:07:05",
                "B0:E4:D5:BF:65:BE",
                "192.168.178.39",
                "Living Room TV"
            )
        )
        DeviceList(sampleCells, onDeviceClick = { })
    }
}