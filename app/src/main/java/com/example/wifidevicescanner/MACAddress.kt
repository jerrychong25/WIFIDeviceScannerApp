package com.example.wifidevicescanner

import android.util.Log

class MACAddress {

    fun getDeviceInfo(MACAddress: String): String? {

        // Organizationally Unique Identifier (OUI)
        var OUI: String? = null
        var deviceManufacturer: String? = null

        Log.d("MACAddress", "MACAddress: $MACAddress")

        OUI = MACAddress.substring(0, 8).toUpperCase()

        Log.d("MACAddress", "OUI: $OUI")

        when(OUI) {
            "00:00:E2" -> deviceManufacturer = "Acer"
            "00:01:24" -> deviceManufacturer = "Acer"
            "00:60:67" -> deviceManufacturer = "Acer"
            "00:A0:60" -> deviceManufacturer = "Acer"
            "18:06:FF" -> deviceManufacturer = "Acer"
            "C0:98:79" -> deviceManufacturer = "Acer"
            "18:82:19" -> deviceManufacturer = "Alibaba Cloud"
            "D8:96:E0" -> deviceManufacturer = "Alibaba Cloud"
            "18:74:2E" -> deviceManufacturer = "Amazon Echo Dot 2"
            "68:9A:87" -> deviceManufacturer = "Amazon Echo Dot 3"
            "00:03:93" -> deviceManufacturer = "Apple"
            "F4:5C:89" -> deviceManufacturer = "Apple MacBook Pro 2015"
            "FC:FC:48" -> deviceManufacturer = "Apple"
            "B4:43:0D" -> deviceManufacturer = "Broadlink"
            "00:80:4F" -> deviceManufacturer = "Daikin"
            "60:18:03" -> deviceManufacturer = "Daikin"
            "18:FE:34" -> deviceManufacturer = "Espressif"
            "24:0A:C4" -> deviceManufacturer = "Espressif"
            "24:62:AB" -> deviceManufacturer = "Espressif"
            "24:6F:28" -> deviceManufacturer = "Espressif"
            "24:B2:DE" -> deviceManufacturer = "Espressif"
            "2C:3A:E8" -> deviceManufacturer = "Espressif"
            "2C:F4:32" -> deviceManufacturer = "Espressif"
            "30:AE:A4" -> deviceManufacturer = "Espressif"
            "3C:71:BF" -> deviceManufacturer = "Espressif"
            "4C:11:AE" -> deviceManufacturer = "Espressif"
            "54:5A:A6" -> deviceManufacturer = "Espressif"
            "5C:CF:7F" -> deviceManufacturer = "Espressif"
            "60:01:94" -> deviceManufacturer = "Espressif"
            "68:C6:3A" -> deviceManufacturer = "Espressif"
            "80:7D:3A" -> deviceManufacturer = "Espressif"
            "84:0D:8E" -> deviceManufacturer = "Espressif"
            "84:F3:EB" -> deviceManufacturer = "Espressif"
            "90:97:D5" -> deviceManufacturer = "Espressif"
            "A0:20:A6" -> deviceManufacturer = "Espressif"
            "A4:7B:9D" -> deviceManufacturer = "Espressif"
            "A4:CF:12" -> deviceManufacturer = "Espressif"
            "AC:D0:74" -> deviceManufacturer = "Espressif"
            "B4:E6:2D" -> deviceManufacturer = "Espressif"
            "BC:DD:C2" -> deviceManufacturer = "Espressif"
            "C4:4F:33" -> deviceManufacturer = "Espressif"
            "CC:50:E3" -> deviceManufacturer = "Espressif"
            "D8:A0:1D" -> deviceManufacturer = "Espressif"
            "D8:F1:5B" -> deviceManufacturer = "Espressif"
            "DC:4F:22" -> deviceManufacturer = "Espressif"
            "EC:FA:BC" -> deviceManufacturer = "Espressif"
            "00:18:82" -> deviceManufacturer = "Huawei"
            "10:C1:72" -> deviceManufacturer = "Huawei"
            "4C:B1:6C" -> deviceManufacturer = "Huawei"
            "D4:A1:48" -> deviceManufacturer = "Huawei"
            "FC:E3:3C" -> deviceManufacturer = "Huawei"
            "04:F1:28" -> deviceManufacturer = "HMD Global (Nokia)"
            "20:39:56" -> deviceManufacturer = "HMD Global (Nokia)"
            "4C:6A:F6" -> deviceManufacturer = "HMD Global (Nokia)"
            "6C:A9:28" -> deviceManufacturer = "HMD Global (Nokia)"
            "6C:C4:D5" -> deviceManufacturer = "HMD Global (Nokia)"
            "90:A3:65" -> deviceManufacturer = "HMD Global (Nokia)"
            "94:EE:9F" -> deviceManufacturer = "HMD Global (Nokia)"
            "A0:28:ED" -> deviceManufacturer = "HMD Global (Nokia)"
            "AC:57:75" -> deviceManufacturer = "HMD Global (Nokia)"
            "BC:02:4A" -> deviceManufacturer = "HMD Global (Nokia)"
            "C0:10:B1" -> deviceManufacturer = "HMD Global (Nokia)"
            "00:06:1B" -> deviceManufacturer = "Lenovo"
            "00:12:FE" -> deviceManufacturer = "Lenovo"
            "00:59:07" -> deviceManufacturer = "Lenovo"
            "10:C5:95" -> deviceManufacturer = "Lenovo"
            "14:36:C6" -> deviceManufacturer = "Lenovo"
            "14:9F:E8" -> deviceManufacturer = "Lenovo"
            "20:76:93" -> deviceManufacturer = "Lenovo"
            "50:3C:C4" -> deviceManufacturer = "Lenovo"
            "60:99:D1" -> deviceManufacturer = "Lenovo"
            "60:D9:A0" -> deviceManufacturer = "Lenovo"
            "6C:5F:1C" -> deviceManufacturer = "Lenovo"
            "70:72:0D" -> deviceManufacturer = "Lenovo"
            "74:04:2B" -> deviceManufacturer = "Lenovo"
            "80:96:21" -> deviceManufacturer = "Lenovo"
            "80:CF:41" -> deviceManufacturer = "Lenovo"
            "88:70:8C" -> deviceManufacturer = "Lenovo"
            "98:FF:D0" -> deviceManufacturer = "Lenovo"
            "A0:32:99" -> deviceManufacturer = "Lenovo"
            "A4:11:94" -> deviceManufacturer = "Lenovo"
            "A4:8C:DB" -> deviceManufacturer = "Lenovo"
            "AC:38:70" -> deviceManufacturer = "Lenovo"
            "C8:DD:C9" -> deviceManufacturer = "Lenovo"
            "CC:07:E4" -> deviceManufacturer = "Lenovo"
            "D4:22:3F" -> deviceManufacturer = "Lenovo"
            "D8:71:57" -> deviceManufacturer = "Lenovo"
            "E0:2C:B2" -> deviceManufacturer = "Lenovo"
            "EC:89:F5" -> deviceManufacturer = "Lenovo"
            "B8:27:EB" -> deviceManufacturer = "Raspberry Pi"
            "DC:A6:32" -> deviceManufacturer = "Raspberry Pi"
            "70:B0:35" -> deviceManufacturer = "Shenzhen Zowee Technology"
            "80:C5:48" -> deviceManufacturer = "Shenzhen Zowee Technology"
            "B0:D5:9D" -> deviceManufacturer = "Shenzhen Zowee Technology (Redmi)"
            "C8:D5:FE" -> deviceManufacturer = "Shenzhen Zowee Technology"
            "00:9E:C8" -> deviceManufacturer = "Xiaomi"
            "64:CC:2E" -> deviceManufacturer = "Xiaomi"
            "AC:F7:F3" -> deviceManufacturer = "Xiaomi"
            "D8:63:75" -> deviceManufacturer = "Xiaomi"
            "FC:64:BA" -> deviceManufacturer = "Xiaomi"
            else -> deviceManufacturer = "Unknown"
        }

        Log.d("MACAddress", "deviceManufacturer: $deviceManufacturer")

        return deviceManufacturer
    }
}