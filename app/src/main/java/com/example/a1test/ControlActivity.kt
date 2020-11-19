package com.example.a1test

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.control_layout.*

class ControlActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.control_layout)

        var device = intent.getStringExtra(MainActivity.EXTRA_ADDRESS)
        Log.i("BUKHARIN", device)


        btn_check.setOnClickListener{
            Toast.makeText(this, "$device", Toast.LENGTH_SHORT).show()
        }
    }



}