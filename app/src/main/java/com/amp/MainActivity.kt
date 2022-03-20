package com.amp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewNominalSize = findViewById<TextView>(R.id.TextViewNominalSize)
        val textViewCountPhase = findViewById<TextView>(R.id.TextViewCountPhase)
        val textViewCable = findViewById<TextView>(R.id.TextViewCable)
        val textViewCurrentAmperage = findViewById<TextView>(R.id.TextViewCurrentAmperage)
        val textViewVoltage= findViewById<TextView>(R.id.TextViewVoltage)
        val textViewCos = findViewById<TextView>(R.id.TextViewCos)
        val textViewPower = findViewById<TextView>(R.id.TextViewPower)

        val spinnerNominalSize = findViewById<Spinner>(R.id.SpinnerNominalSize)
        val spinnerCountPhase = findViewById<Spinner>(R.id.SpinnerCountPhase)

        val textViewCableValue = findViewById<TextView>(R.id.TextViewCableValue)
        val textViewCurrentAmperageValue = findViewById<TextView>(R.id.TextViewCurrentAmperageValue)
        val editTextVoltage = findViewById<EditText>(R.id.EditTextVoltage)
        val editTextCos = findViewById<EditText>(R.id.EditTextCos)
        val editTextPower = findViewById<EditText>(R.id.EditTextPower)
        val buttonNaytipomochnosti = findViewById<Button>(R.id.ButtonNaytipomochnosti)

    }
}