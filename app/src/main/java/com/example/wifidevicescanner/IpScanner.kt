//package com.example.wifidevicescanner
//
//import android.os.Handler
//import android.os.Looper
//import android.util.Log
//
//import java.io.BufferedReader
//import java.io.IOException
//import java.io.InputStreamReader
//import java.net.DatagramPacket
//import java.net.DatagramSocket
//import java.net.Inet6Address
//import java.net.InetAddress
//import java.net.NetworkInterface
//import java.net.SocketException
//import java.net.UnknownHostException
//import java.util.ArrayList
//import java.util.HashMap
//
///**
// * Created by kalshen on 2017/7/5 0005.
// * ip 扫描类
// */
//
//class IpScanner {
//    private val mHandler = Handler(Looper.getMainLooper())//获取主线程的Looper
//    private var listener: OnScanListener? = null
//
//    /**
//     * 获取本机 ip地址
//     *
//     * @return
//     */
//    private// skip ipv6
//    val hostIP: String?
//        get() {
//
//            var hostIp: String? = null
//            try {
//                val nis = NetworkInterface.getNetworkInterfaces()
//                var ia: InetAddress
//                while (nis.hasMoreElements()) {
//                    val ni = nis.nextElement() as NetworkInterface
//                    val ias = ni.inetAddresses
//                    while (ias.hasMoreElements()) {
//                        ia = ias.nextElement()
//                        if (ia is Inet6Address) {
//                            continue
//                        }
//                        val ip = ia.hostAddress
//                        if ("127.0.0.1" != ip) {
//                            hostIp = ia.hostAddress
//                            break
//                        }
//                    }
//                }
//            } catch (e: SocketException) {
//                Log.e("IpScanner", "SocketException")
//                e.printStackTrace()
//            }
//
//            return hostIp
//
//        }
//
//    /**
//     * 获取局域网中的 存在的ip地址及对应的mac
//     */
//    fun startScan() {
//        //局域网内存在的ip集合
//        val ipList = ArrayList<String>()
//        val map = HashMap<String, String>()
//
//        //获取本机所在的局域网地址
//        val hostIP = hostIP
//        val lastIndexOf = hostIP!!.lastIndexOf(".")
//        val substring = hostIP.substring(0, lastIndexOf + 1)
//        //创建线程池
//        //        final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
//        Thread(Runnable {
//            val dp = DatagramPacket(ByteArray(0), 0, 0)
//            var socket: DatagramSocket
//            try {
//                socket = DatagramSocket()
//                var position = 2
//                while (position < 255) {
//                    Log.d("IpScanner", "run: udp-$substring$position")
//                    dp.address = InetAddress.getByName(substring + position.toString())
//                    socket.send(dp)
//                    position++
//                    if (position == 125) {//分两段掉包，一次性发的话，达到236左右，会耗时3秒左右再往下发
//                        socket.close()
//                        socket = DatagramSocket()
//                    }
//                }
//                socket.close()
//                execCatForArp()
//            } catch (e: SocketException) {
//                e.printStackTrace()
//            } catch (e: UnknownHostException) {
//                e.printStackTrace()
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }).start()
//    }
//
//    /**
//     * 执行 cat命令 查找android 设备arp表
//     * arp表 包含ip地址和对应的mac地址
//     */
//    private fun execCatForArp() {
//        Thread(Runnable {
//            try {
//                val map = HashMap<String, String>()
//                val exec = Runtime.getRuntime().exec("cat proc/net/arp")
//                val `is` = exec.inputStream
//                val reader = BufferedReader(InputStreamReader(`is`))
//                var line: String
////                while ((line = reader.readLine()) != null) {
//                while (reader.readLine() != null) {
//                    line = reader.readLine()
//                    Log.d("IpScanner", "run: $line")
//                    if (!line.contains("00:00:00:00:00:00") && !line.contains("IP")) {
//                        val split = line.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//                        Log.d("MainActivity", "split: $split")
//                        Log.d("MainActivity", "split[0]: " + split[0])
//                        Log.d("MainActivity", "split[3]: " + split[3])
//
//                        map[split[3]] = split[0]
//                        Log.d("MainActivity", "map: $map")
//                    }
//                }
//                mHandler.post { listener!!.scan(map) }
//
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }).start()
//    }
//
//    fun setOnScanListener(listener: OnScanListener) {
//        this.listener = listener
//    }
//
//    interface OnScanListener {
//        fun scan(resultMap: Map<String, String>)
//    }
//
//}