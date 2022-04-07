package com.amp

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
        var list1 : ArrayList<String> = arrayListOf("0")


        //list1.add(mainActivityViewModel.allNominalSizeString)

        mainActivityViewModel.allNominalSize.observe(this){

           // list1.add(it[0].toString())
           // list1.addAll(arrayListOf(it.toTypedArray().toString()))
            for (i in it.indices) {
                list1.add(it[i].toString())
                //println(it[i].toString())
                Log.i("Exception", it[i].toString())
            }


       // list1 = mutableListOf(it.joinToString())
         // list1 += it.toList().

            Log.i("Exception", ":LIST")
        }

        val nominalSizeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list1)
        nominalSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNominalSize.adapter = nominalSizeAdapter

        val spinnerCountPhase = findViewById<Spinner>(R.id.SpinnerCountPhase)
        val countPhaseAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mainActivityViewModel.CountPhase)
        countPhaseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCountPhase.adapter = countPhaseAdapter




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
            mainActivityViewModel.calculate()
            textViewCurrentAmperageValue.text = mainActivityViewModel.amperage.toString()
            editTextVoltage.setText(mainActivityViewModel.v.toString())
            editTextCos.setText(mainActivityViewModel.cos.toString())
            editTextPower.setText(mainActivityViewModel.p.toString())

        }
    }
}