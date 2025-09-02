package com.ferhat.whoconnected

import android.os.Bundle
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
import androidx.lifecycle.lifecycleScope
import com.ferhat.whoconnected.ui.theme.WhoConnectedTheme
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //retrofit instance
        var moshi = Moshi.Builder().build()
        var retrofit = Retrofit.Builder()
            .baseUrl("https://ferhatsanli.duckdns.org:3000/whoconnected")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(OkHttpClient.Builder().build())
            .build()
        var apiService = retrofit.create(ApiService::class.java)
        setContent {
            WhoConnectedTheme {
                MainScreen(apiService)
            }
        }
    }
}
@Composable
fun MainScreen(api: ApiService) {
    var devices by remember { mutableStateOf<List<Device>>(emptyList()) }
    Button(onClick = {
        lifecycleScope.launch { devices = api.getDevices() }
    }) { Text("Refresh Data") }
    Views().DeviceList(devices)
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WhoConnectedTheme {
        Greeting("Android")
    }
}