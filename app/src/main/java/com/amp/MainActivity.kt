package com.amp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amp.data.AppListAdapter
import com.amp.data.MainActivityViewModel
import com.amp.data.MainActivityViewModelFactory

class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels{
        MainActivityViewModelFactory((application as AmperageApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = AppListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        mainActivityViewModel.allTypeOfEnvironment.observe(this){
            it.let { adapter.submitList(it) }
        }

     //   mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        Log.i("MainActivityViewModel", "Called ViewModelProvider.get")

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
        editTextVoltage.setText(mainActivityViewModel.v.toString())
        editTextCos.setText(mainActivityViewModel.cos.toString())
        editTextPower.setText(mainActivityViewModel.p.toString())




        buttonNaytipomochnosti.setOnClickListener{
           // Toast.makeText(this, "Кабель подобран", Toast.LENGTH_SHORT).show()
           // var i = mainActivityViewModel.calculate(editTextPower.text.toString().toInt(), editTextVoltage.text.toString().toInt(), editTextCos.text.toString().toDouble()).toInt()
           // Log.i("Расчетный ток", i.toString())
            //Toast.makeText(this, i.toString(), Toast.LENGTH_SHORT).show()
            //textViewCurrentAmperageValue.setText(i.toString())
            mainActivityViewModel.p = editTextPower.text.toString().toDouble()
            mainActivityViewModel.v = editTextVoltage.text.toString().toDouble()
            mainActivityViewModel.cos = editTextCos.text.toString().toDouble()
            textViewCurrentAmperageValue.text = mainActivityViewModel.amperage.toString()

        }
    }
}