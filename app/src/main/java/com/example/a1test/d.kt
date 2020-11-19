package com.example.a1test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class d {
    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(
            context: Context,
            intent: Intent
        ) {
            val action = intent.action
        }
    }
}