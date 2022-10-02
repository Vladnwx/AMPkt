package com.amp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ActivityExtended : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extended)
    }
}