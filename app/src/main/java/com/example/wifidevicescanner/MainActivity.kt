package com.example.wifidevicescanner

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.*

class MainActivity : AppCompatActivity() {

    private var btnScan: Button? = null
    private var listViewIp: ListView? = null
    internal var ipList: ArrayList<String>? = null
    internal var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnScan = findViewById(R.id.btn_scan) as Button
        listViewIp = findViewById(R.id.lv_deviceip) as ListView
        ipList = ArrayList()
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, ipList)
        listViewIp!!.setAdapter(adapter)

        val dialog = ProgressDialog(this)

        dialog.setMessage("Scanning...")
        dialog.setCanceledOnTouchOutside(false)

        val ipScanner = IpScanner()

        ipScanner.setOnScanListener(object : IpScanner.OnScanListener {
            override fun scanClear() {
                Log.d("MainActivity", "scanClear()")

                // Clear all variable contents
                ipList?.clear()
                adapter?.notifyDataSetInvalidated()
            }

            override fun addDevice(device: String) {
                Log.d("MainActivity", "addDevice() device: $device")

                ipList?.add(device)
                adapter?.notifyDataSetInvalidated()
            }

            override fun scanSuccess() {
                Log.d("MainActivity", "scanSuccess()")

                dialog.dismiss()
            }
        })

        ipScanner.startScan()
        dialog.show()

        btnScan!!.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v:View) {
                ipScanner.startScan()
                dialog.show()
            }
        })
    }

    private class IpScanner {
        private val mHandler = Handler(Looper.getMainLooper())//获取主线程的Looper
        private var listener: OnScanListener? = null

        /**
         * 获取本机 ip地址
         *
         * @return
         */
        private// skip ipv6
        val hostIP: String?
            get() {

                var hostIp: String? = null
                try {
                    val nis = NetworkInterface.getNetworkInterfaces()
                    var ia: InetAddress
                    while (nis.hasMoreElements()) {
                        val ni = nis.nextElement() as NetworkInterface
                        val ias = ni.inetAddresses
                        while (ias.hasMoreElements()) {
                            ia = ias.nextElement()
                            if (ia is Inet6Address) {
                                continue
                            }
                            val ip = ia.hostAddress
                            if ("127.0.0.1" != ip) {
                                hostIp = ia.hostAddress
                                break
                            }
                        }
                    }
                } catch (e: SocketException) {
                    Log.e("MainActivity", "SocketException")
                    e.printStackTrace()
                }

                return hostIp

            }

        /**
         * 获取局域网中的 存在的ip地址及对应的mac
         */
        fun startScan() {
            mHandler.post { listener!!.scanClear() }

            //获取本机所在的局域网地址
            val hostIP = hostIP
            val lastIndexOf = hostIP!!.lastIndexOf(".")
            val substring = hostIP.substring(0, lastIndexOf + 1)

            //创建线程池
            Thread(Runnable {
                val dp = DatagramPacket(ByteArray(0), 0, 0)
                var socket: DatagramSocket
                try {
                    socket = DatagramSocket()
                    var position = 2
                    while (position < 255) {
                        Log.d("MainActivity", "run: udp-$substring$position")
                        dp.address = InetAddress.getByName(substring + position.toString())
                        socket.send(dp)
                        position++
                        if (position == 125) {//分两段掉包，一次性发的话，达到236左右，会耗时3秒左右再往下发
                            socket.close()
                            socket = DatagramSocket()
                        }
                    }
                    socket.close()
                    execCatForArp()
                } catch (e: SocketException) {
                    e.printStackTrace()
                } catch (e: UnknownHostException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }).start()
        }

        /**
         * 执行 cat命令 查找android 设备arp表
         * arp表 包含ip地址和对应的mac地址
         */
        private fun execCatForArp() {
            Thread(Runnable {
                try {
                    val exec = Runtime.getRuntime().exec("cat proc/net/arp")
                    val `is` = exec.inputStream
                    val reader = BufferedReader(InputStreamReader(`is`))
                    var line: String
                    val macAddress = MACAddress()
                    var deviceManufacturer: String? = null

                    while (reader.readLine() != null) {
                        line = reader.readLine()
                        Log.d("MainActivity", "run: $line")

                        if (!line.contains("00:00:00:00:00:00") && !line.contains("IP")) {
                            val split = line.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//                            Log.d("MainActivity", "split: $split")
//                            Log.d("MainActivity", "split[0]: " + split[0])
//                            Log.d("MainActivity", "split[3]: " + split[3])
                            deviceManufacturer = macAddress.getDeviceInfo(split[3])
                            Log.d("MainActivity", "Device IP: " + split[0] + ", " + "MAC Address: " + split[3] + ", " + "Device Manufacturer: $deviceManufacturer" )

                            if(deviceManufacturer.equals("Broadlink")) {
                                Log.d("MainActivity", "Broadlink Detected!")
                                // Add New Device To Adapter
                                mHandler.post { listener!!.addDevice(split[0] + "\n" + "Broadlink" + "\n" + split[3]) }
                            }
                            else if(deviceManufacturer.equals("Espressif")) {
                                Log.d("MainActivity", "Espressif Detected!")
                                // Add New Device To Adapter
                                mHandler.post { listener!!.addDevice(split[0] + "\n" + "Espressif" + "\n" + split[3]) }
                            }
                            else if(deviceManufacturer.equals("Raspberry Pi")) {
                                Log.d("MainActivity", "Raspberry Pi Detected!")
                                // Add New Device To Adapter
                                mHandler.post { listener!!.addDevice(split[0] + "\n" + "Raspberry Pi" + "\n" + split[3]) }
                            }
                            else if(deviceManufacturer.equals("Amazon Echo Dot 2")) {
                                Log.d("MainActivity", "Amazon Echo Dot 2 Detected!")
                                // Add New Device To Adapter
                                mHandler.post { listener!!.addDevice(split[0] + "\n" + "Amazon Echo Dot 2" + "\n" + split[3]) }
                            }
                            else if(deviceManufacturer.equals("Amazon Echo Dot 3")) {
                                Log.d("MainActivity", "Amazon Echo Dot 3 Detected!")
                                // Add New Device To Adapter
                                mHandler.post { listener!!.addDevice(split[0] + "\n" + "Amazon Echo Dot 3" + "\n" + split[3]) }
                            }
                        }
                    }
                    mHandler.post { listener!!.scanSuccess() }

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }).start()
        }

        fun setOnScanListener(listener: OnScanListener) {
            this.listener = listener
        }

        interface OnScanListener {
            fun scanClear()

            fun addDevice(device: String)

            fun scanSuccess()
        }
    }
}
