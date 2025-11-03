package com.ferhat.whoconnected

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize


@Composable
fun DeviceList(cells: List<Device>, onDeviceClick: (Device) -> Unit) {
    if (cells.isEmpty()) {
        Text("---No Devices---", modifier = Modifier.padding(16.dp))
        return
    }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = cells, key = { it.macAddress ?: it.deviceName ?: cells.indexOf(it) }) { dev ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDeviceClick(dev) }
                    .padding(12.dp)
            ) {
                // show a simple text if deviceName is null
                Text(dev.deviceName ?: "Unknown device")
            }
            HorizontalDivider()
        }
    }
}