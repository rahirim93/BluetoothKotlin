package com.example.bluetoothkotlin

import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.lang.StringBuilder

    class ConnectedThread (mmSocket: BluetoothSocket?, myHandler: Handler) : Thread() {
        private val mmInStream: InputStream? = mmSocket?.inputStream
        private val mmBuffer: ByteArray = ByteArray(1024) // mmBuffer store for the stream
        private var handler = myHandler

        override fun run() {
            var numBytes: Int // bytes returned from read()
            var stringBuilder = StringBuilder()
            lateinit var stringToPrint: String

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                // Read from the InputStream.
                try {
                    numBytes = mmInStream?.read(mmBuffer)!!
                    var stringIncome = String(mmBuffer,0, numBytes)
                    stringBuilder.append(stringIncome)
                    var endLineIndex = stringBuilder.indexOf("\r\n")
                    if (endLineIndex > 0) {
                        stringToPrint = stringBuilder.substring(0, endLineIndex)
                        Log.d("MyLog", "Message: $stringToPrint")
                        stringBuilder.delete(0, stringBuilder.length)

                        var msg = handler.obtainMessage(1, stringToPrint)
                        handler.sendMessage(msg)
                    }
                } catch (e: IOException) {
                    Log.d("MyLog", "Input stream was disconnected", e)
                    break
                }
            }
        }
    }
