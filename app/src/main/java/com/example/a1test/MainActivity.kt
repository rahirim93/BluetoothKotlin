package com.example.a1test

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
import java.util.concurrent.TimeUnit

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class MainActivity : AppCompatActivity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var pairedDevices: Set<BluetoothDevice>

    //Массивы для поиска устройств
    val list = ArrayList<BluetoothDevice>()
    var list2 = ArrayList<BluetoothDevice>()


    companion object {
        const val EXTRA_ADDRESS: String = "Device_address"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()


        button1.setOnClickListener{
        discoverUnpaired()
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action: String? = intent?.action
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    Log.i("bukh", action)
                    val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    var checkContain: Boolean
                    Log.i("bukh", device.name + " " + device.address)
                    list += device
                    //list += (device.name+"\n"+device.address )
                    for (element in list){
                        checkContain = list2.contains(element)
                        if (checkContain){

                        }else{
                            //list2.plusAssign(device.name+"\n"+device.address)
                            list2.add(device)
                            //list2.subList()
                        }
                    }
                    //list += (device.name+"\n"+device.address)
                    val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, list2)
                    select_device_list.adapter = adapter
                }
            }
            Log.i("bukh", "Цикл завершен")
        }
    }

    private fun discoverUnpaired (){
        //select_device_list
//        val list = ArrayList<String>()
//        var list2 = ArrayList<String>()
        bluetoothAdapter.startDiscovery()
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)
        //TimeUnit.SECONDS.sleep(10)
        toControlActivity()
    }
    
    private fun toControlActivity(){
        select_device_list.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val device: BluetoothDevice = list2[position]
                val address: String = device.address

                val intent = Intent(this, ControlActivity::class.java)
                intent.putExtra(EXTRA_ADDRESS, address)
                startActivity(intent)
            }
    }

}