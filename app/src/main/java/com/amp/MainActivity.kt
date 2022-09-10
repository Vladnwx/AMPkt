package com.amp

import android.os.Bundle
import android.util.Log
import android.view.View
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

        val spinnerNominalSize = findViewById<Spinner>(R.id.SpinnerNominalSize)

        val nominalSizeAdapter = ArrayAdapter(this, R.layout.spinner_item, mainActivityViewModel.allNominalSizeList)
        nominalSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNominalSize.adapter = nominalSizeAdapter

        val spinnerCountPhase = findViewById<Spinner>(R.id.SpinnerCountPhase)
        val countPhaseAdapter = ArrayAdapter(this, R.layout.spinner_item, mainActivityViewModel.countPhaseList)
        countPhaseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCountPhase.adapter = countPhaseAdapter

        mainActivityViewModel.allNominalSize.observe(this){

            for (i in it.indices) {
                mainActivityViewModel.allNominalSizeList.add(it[i].toString()) }
            mainActivityViewModel.allNominalSizeList.remove("0")
            spinnerNominalSize.setSelection(2)
            Log.i("Exception", ":LIST")
            Toast.makeText(this, "Лист поперечных сечений сформирован", Toast.LENGTH_SHORT).show()

        }




        val textViewCableValue = findViewById<TextView>(R.id.TextViewCableValue)
        val textViewCurrentAmperageValue = findViewById<TextView>(R.id.TextViewCurrentAmperageValue)
        val textViewRValue = findViewById<TextView>(R.id.TextViewRValue)
        val textViewXValue = findViewById<TextView>(R.id.TextViewXValue)
        val textViewAmperageShortValue = findViewById<TextView>(R.id.TextViewAmperageShortValue)
        val textViewAmperageValue = findViewById<TextView>(R.id.TextViewAmperageValue)

        val editTextVoltage = findViewById<EditText>(R.id.EditTextVoltage)
        val editTextCos = findViewById<EditText>(R.id.EditTextCos)
        val editTextPower = findViewById<EditText>(R.id.EditTextPower)
        val buttonNaytipomochnosti = findViewById<Button>(R.id.ButtonNaytipomochnosti)

        val SwitchTypePodbor = findViewById<Switch>(R.id.SwitchTypePodbor)


        editTextVoltage.setText(mainActivityViewModel.v.toString())
        editTextCos.setText(mainActivityViewModel.cos.toString())
        editTextPower.setText(mainActivityViewModel.p.toString())

        mainActivityViewModel.pLiveData.observe(this){
            textViewXValue.text = it.toString()
        }

        SwitchTypePodbor.setOnClickListener{
            if (SwitchTypePodbor.isEnabled){
               // spinnerNominalSize.isEnabled = false
                spinnerNominalSize.visibility = View.INVISIBLE
            }
            else {spinnerNominalSize.visibility = View.VISIBLE
                spinnerNominalSize.setSelection(spinnerNominalSize.selectedItemPosition +1)
                }
           // spinnerNominalSize.setSelection(3)


        }
        fun SendDataToViewModel(){
            mainActivityViewModel.nominalSize =  spinnerNominalSize.selectedItem.toString().toDouble()
           // mainActivityViewModel.countPhase = spinnerCountPhase.selectedItem.toString()
            mainActivityViewModel.v = editTextVoltage.text.toString().toDouble()
            mainActivityViewModel.cos = editTextCos.text.toString().toDouble()
            mainActivityViewModel.p = editTextPower.text.toString().toDouble()


            mainActivityViewModel.calculate()
        }

        fun GetDataFromViewModel(){

            textViewCurrentAmperageValue.text = mainActivityViewModel.amperageCalculate.toString()
            editTextVoltage.setText(mainActivityViewModel.v.toString())
            editTextCos.setText(mainActivityViewModel.cos.toString())
            editTextPower.setText(mainActivityViewModel.p.toString())
            textViewCableValue.text = mainActivityViewModel.countJil.toString() + "X" + mainActivityViewModel.nominalSize.toString()
            textViewRValue.text = mainActivityViewModel.R.toString()
        }



        buttonNaytipomochnosti.setOnClickListener{
           // Toast.makeText(this, "Кабель подобран", Toast.LENGTH_SHORT).show()
           // var i = mainActivityViewModel.calculate(editTextPower.text.toString().toInt(), editTextVoltage.text.toString().toInt(), editTextCos.text.toString().toDouble()).toInt()
           // Log.i("Расчетный ток", i.toString())
            //Toast.makeText(this, i.toString(), Toast.LENGTH_SHORT).show()
            //textViewCurrentAmperageValue.setText(i.toString())
            SendDataToViewModel()

            GetDataFromViewModel()

            mainActivityViewModel.pLiveData.postValue(mainActivityViewModel.p)
        }

    }



}