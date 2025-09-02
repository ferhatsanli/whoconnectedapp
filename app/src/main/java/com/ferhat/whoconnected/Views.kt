package com.ferhat.whoconnected

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ferhat.whoconnected.ui.theme.WhoConnectedTheme

class Views {

    @Composable
    fun DeviceList(cells: List<Device>){
        Column {
            for (dev in cells){
                Row{
                    Icon(
                        imageVector = dev.icon ?: Icons.Filled.Computer,
                        contentDescription = dev.deviceName,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(dev.deviceName)
                }
                if (dev != cells.last())
                    HorizontalDivider(thickness = 2.dp)
            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun DeviceListPreview(){
        var sampleCells = listOf(
            Device("2025-09-02 23:07:05", "E2:41:9A:CA:C0:18", "192.168.178.37", "Ferhat-S23", icon = Icons.Filled.Smartphone),
            Device("2025-09-02 23:07:05", "B0:E4:D5:BF:65:BE", "192.168.178.39", "Living Room TV", icon = Icons.Filled.Tv),
            Device("2025-09-02 23:07:05", "AA:4D:52:67:C6:D3", "192.168.178.46", "Ferhat-MacBook", icon = Icons.Filled.Computer),
            Device("2025-09-02 23:07:05", "8C:17:59:43:CC:EA", "192.168.178.61", "Ashish-Phone", icon = Icons.Filled.Smartphone),
            Device("2025-09-02 23:07:05", "EE:2A:0A:66:75:37", "192.168.178.64", "Jesse-iPhone(?)", icon = Icons.Filled.Smartphone)
        )
        DeviceList(sampleCells)
    }
}