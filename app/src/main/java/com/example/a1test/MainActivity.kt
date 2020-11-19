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
    val list = ArrayList<String>()
    var list2 = ArrayList<String>()


    companion object {
        const val EXTRA_ADDRESS: String = "Device_address"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        // Register for broadcasts when a device is discovered для сканирования


        button0.setOnClickListener {
            var checkPerm = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
            if (checkPerm == 0){
                Log.i("device", "PERMISSION_GRANTED")
            } else {
                Log.i("device", "PERMISSION_DENIED")
            }
//            requestPermissions()
        }

        LogLoopFor.setOnClickListener {
            val size = list.size
            Log.i("Loop", "LIST" + "        " + size)
            outerLoop@ for (index in list.indices){
                for (index2 in list.indices){
                    if (list[index] !== list[index2]){
                        Log.i("Loop", list[index])
                    }
                }
            }
            //unregisterReceiver(receiver)
            //bluetoothAdapter.startDiscovery()
        }

        button2.setOnClickListener{
            bluetoothAdapter.cancelDiscovery()
        }

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
                    var deviceName = device.name
                    var deviceAddress = device.address
                    var checkContain: Boolean = false
                    Log.i("bukh", device.name + " " + device.address)
                    list += (device.name+"\n"+device.address )
                    for (element in list){
                        checkContain = list2.contains(element)
                        if (checkContain){

                        }else{
                            list2.plusAssign(device.name+"\n"+device.address)
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
        bluetoothAdapter.startDiscovery()
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)
        TimeUnit.SECONDS.sleep(10)
        //toControlActivity()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        ...

        // Don't forget to unregister the ACTION_FOUND receiver.
        //unregisterReceiver(receiver)


    private fun pairedDeviceList() {
        pairedDevices = bluetoothAdapter.bondedDevices
        val list: ArrayList<BluetoothDevice> = ArrayList()

        if (pairedDevices.isNotEmpty()) {
            for (device: BluetoothDevice in pairedDevices) {
                list.add(device)
                Log.i("device", "" + device.name + "  " + device)
            }
        } else {
            Toast.makeText(this, "No pared bluetooth devices found", Toast.LENGTH_SHORT).show()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        select_device_list.adapter = adapter
    }

//    fun toControlActivity(){
//        select_device_list.onItemClickListener =
//            AdapterView.OnItemClickListener { _, _, position, _ ->
//                val device: BluetoothDevice = list2[position]
//                val address: String = device.address
//
//                val intent = Intent(this, ControlActivity::class.java)
//                intent.putExtra(EXTRA_ADDRESS, address)
//                startActivity(intent)
//            }
//    }

//    private fun getPairedDeviceList() { //Функция получения списка спаренных устройств
//        pairedDevices = bluetoothAdapter.bondedDevices //Получение набора спаренных устройств
//        for (check in pairedDevices){ //Цикл вывода в Logcat спаренных устройств
//            //Функция вывода в лог спаренных устройств
//            //tag это метка по которой можно найти сообщение в логе
//            // в msg вводится то, что нужно вывести
//            Log.i("device", check.name+"\n"+" Адрес" + check.address + " Состояние бонд" + check.bondState)
//        }
//        Log.i("device", "Размер массива" + pairedDevices.size)
//        val list = ArrayList<String>()
//        for (device:BluetoothDevice in pairedDevices){ //Заполнение ListVIew набором спаренных устройств
//            //Функция добавления в листвью имени устровства
//            //Адреса устроства
//            //Запись "\n" - переход на следующу строку
//            list.add(device.name+"\n"+device.address)
//            //TimeUnit.SECONDS.sleep(1)
//        }
//    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
//    select_device_list.adapter = adapter
//    }
}