package com.amp.RecyclerView

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.amp.MainActivity
import com.amp.R

class ActivityExtended : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extended)

        val buttonActivityMain =  findViewById<Button>(R.id.ButtonActivityMain)

        buttonActivityMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}