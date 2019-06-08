//package com.example.wifidevicescanner
//
//import android.content.Context
//import android.os.AsyncTask
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.ArrayAdapter
//import android.widget.Button
//import android.widget.ListView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import java.io.IOException
//import java.net.InetAddress
//import java.net.UnknownHostException
//import java.util.ArrayList
//import android.text.format.Formatter.formatIpAddress
//import android.net.wifi.WifiInfo
//import android.content.Context.WIFI_SERVICE
//import androidx.core.content.ContextCompat.getSystemService
//import android.net.wifi.WifiManager
//import android.net.NetworkInfo
//import android.content.Context.CONNECTIVITY_SERVICE
//import android.net.ConnectivityManager
//import android.text.format.Formatter
//import java.lang.ref.WeakReference
//import android.text.format.Formatter.formatIpAddress
//import androidx.core.content.ContextCompat.getSystemService
//
//class MainActivity: AppCompatActivity() {
//
//    private var btnScan: Button? = null
//    private var listViewIp: ListView? = null
//    internal var ipList: ArrayList<String>? = null
//    internal var adapter: ArrayAdapter<String>? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        btnScan = findViewById(R.id.btn_scan) as Button
//        listViewIp = findViewById(R.id.lv_deviceip) as ListView
//        ipList = ArrayList()
//        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, ipList)
//        listViewIp!!.setAdapter(adapter)
//
//        btnScan!!.setOnClickListener(object:View.OnClickListener {
//            override fun onClick(v:View) {
//                ScanIpTask().execute()
//            }
//        })
//    }
//
//    private inner class ScanIpTask : AsyncTask<Void, String, Void?>() {
//
//        /*
//            Scan IP 192.168.1.100~192.168.1.255
//            you should try different timeout for your network/devices
//       */
//        internal val subnet = "192.168.0."
//        internal val lower = 1
//        internal val upper = 255
//        internal val timeout = 200
//
//        private var mContextRef: WeakReference<Context>? = null
//
//        fun ScanIpTask(context: Context) {
//            mContextRef = WeakReference(context)
//        }
//
//        override fun onPreExecute() {
//
//            Log.d("MainActivity", "onPreExecute()")
//
//            ipList?.clear()
//            adapter?.notifyDataSetInvalidated()
//
//            Toast.makeText(this@MainActivity, "Scan IP...", Toast.LENGTH_LONG).show()
//        }
//        override fun doInBackground(vararg params:Void): Void? {
//
//            Log.d("MainActivity", "doInBackground()")
//
////            for (i in lower..upper)
////            {
////                val hostIP = subnet + i
////
////                Log.d("MainActivity", "doInBackground() host: " + hostIP)
////
////                try
////                {
////                    val inetAddress = InetAddress.getByName(hostIP)
////
////                    Log.d("MainActivity", "doInBackground() inetAddress: " + inetAddress)
////
////                    if (inetAddress.isReachable(timeout))
////                    {
////                        Log.d("MainActivity", "doInBackground() inetAddress " + inetAddress + " Reachable!")
////                        publishProgress(inetAddress.canonicalHostName + "\n" + inetAddress.getAddress())
////                    }
////
////                    Log.d("MainActivity", "doInBackground() inetAddress Finished!")
////                }
////                catch (e:UnknownHostException) {
////                    e.printStackTrace()
////                }
////                catch (e:IOException) {
////                    e.printStackTrace()
////                }
////            }
//
//            // Other Method
//            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            val activeNetwork = cm.activeNetworkInfo
//            val wm = getApplicationContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
//
//            val connectionInfo = wm.connectionInfo
//            val ipAddress = connectionInfo.ipAddress
//            val ipString = formatIpAddress(ipAddress)
//
//            Log.d("MainActivity", "activeNetwork: $activeNetwork")
//            Log.d("MainActivity", "ipAddress: " + ipAddress.toString())
//            Log.d("MainActivity", "ipString: " + ipString.toString())
//
//            val prefix = ipString.substring(0, ipString.lastIndexOf(".") + 1)
//            Log.d("MainActivity", "prefix: $prefix")
//
//            for (i in 0..255) {
//                val testIp = prefix + i.toString()
//
//                Log.d("MainActivity", "testIp: $testIp")
//
////                val address = InetAddress.getByName(testIp)
//                val address = InetAddress.getByName(testIp)
//                val reachable = address.isReachable(timeout)
//                val hostName = address.hostName
//                val cHostName = address.canonicalHostName
//
//                if (reachable) {
//                    Log.d("MainActivity", "Host: " + hostName.toString() + "[" + cHostName.toString() + "](" + testIp + ") is reachable!");
//                    publishProgress(hostName.toString() + "\n" + testIp)
//                }
//            }
//
//            return null
//        }
//
//        override fun onProgressUpdate(vararg values:String) {
//
//            Log.d("MainActivity", "onProgressUpdate() values[0]: " + values[0])
//
//            ipList?.add(values[0])
//            adapter?.notifyDataSetInvalidated()
//
//            Toast.makeText(this@MainActivity, values[0], Toast.LENGTH_LONG).show()
//        }
//
//        fun onPostExecute(aVoid:Void) {
//
//            Log.d("MainActivity", "onPostExecute()")
//
//            Toast.makeText(this@MainActivity, "Done", Toast.LENGTH_LONG).show()
//        }
//    }
//}

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

        dialog.setMessage("正在扫描")
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

                    while (reader.readLine() != null) {
                        line = reader.readLine()
                        Log.d("MainActivity", "run: $line")

                        if (!line.contains("00:00:00:00:00:00") && !line.contains("IP")) {
                            val split = line.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//                            Log.d("MainActivity", "split: $split")
//                            Log.d("MainActivity", "split[0]: " + split[0])
//                            Log.d("MainActivity", "split[3]: " + split[3])
                            Log.d("MainActivity", "Device IP: " + split[0] + ", " + "MAC Address: " + split[3])

                            // Add New Device To Adapter
                            mHandler.post { listener!!.addDevice(split[0] + "\n" + split[3]) }
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
