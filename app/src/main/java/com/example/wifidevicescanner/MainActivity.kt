package com.example.wifidevicescanner

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.ArrayList

class MainActivity: AppCompatActivity() {

    private var btnScan: Button? = null
    private var listViewIp: ListView? = null
    internal var ipList: ArrayList<String>? = null
    internal var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnScan = findViewById(R.id.scan) as Button
        listViewIp = findViewById(R.id.listviewip) as ListView
        ipList = ArrayList()
        adapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, android.R.id.text1, ipList)
        listViewIp!!.setAdapter(adapter)

        btnScan!!.setOnClickListener(object:View.OnClickListener {
            override fun onClick(v:View) {
                ScanIpTask().execute()
            }
        })
    }

    private inner class ScanIpTask : AsyncTask<Void, String, Void?>() {

        /*
            Scan IP 192.168.1.100~192.168.1.255
            you should try different timeout for your network/devices
       */
        internal val subnet = "192.168.1."
        internal val lower = 100
        internal val upper = 255
        internal val timeout = 3000

        override fun onPreExecute() {

            Log.d("MainActivity", "onPreExecute()")

            ipList?.clear()
            adapter?.notifyDataSetInvalidated()

            Toast.makeText(this@MainActivity, "Scan IP...", Toast.LENGTH_LONG).show()
        }
        override fun doInBackground(vararg params:Void): Void? {

            Log.d("MainActivity", "doInBackground()")

            for (i in lower..upper)
            {
                val host = subnet + i

                Log.d("MainActivity", "doInBackground() host: " + host)

                try
                {
                    val inetAddress = InetAddress.getByName(host)

                    Log.d("MainActivity", "doInBackground() inetAddress: " + inetAddress)

                    if (inetAddress.isReachable(timeout))
                    {
                        Log.d("MainActivity", "doInBackground() inetAddress Reachable")
                        publishProgress(inetAddress.toString())
                    }

                    Log.d("MainActivity", "doInBackground() inetAddress Finished")
                }
                catch (e:UnknownHostException) {
                    e.printStackTrace()
                }
                catch (e:IOException) {
                    e.printStackTrace()
                }
            }
            return null
        }

        override fun onProgressUpdate(vararg values:String) {

            Log.d("MainActivity", "onProgressUpdate() values[0]: " + values[0])

            ipList?.add(values[0])
            adapter?.notifyDataSetInvalidated()

            Toast.makeText(this@MainActivity, values[0], Toast.LENGTH_LONG).show()
        }

        fun onPostExecute(aVoid:Void) {

            Log.d("MainActivity", "onPostExecute()")

            Toast.makeText(this@MainActivity, "Done", Toast.LENGTH_LONG).show()
        }
    }
}