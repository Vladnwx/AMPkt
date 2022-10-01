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
                mainActivityViewModel.allNominalSizeList.add(it[i].value) }
            mainActivityViewModel.allNominalSizeList.remove(0.0)
            spinnerNominalSize.setSelection(0)
            Log.i("Exception", ":LIST")
            Toast.makeText(this, "Лист поперечных сечений сформирован", Toast.LENGTH_SHORT).show()

        }



        val textViewNominalSize = findViewById<TextView>(R.id.TextViewNominalSize)
        val textViewCableValue = findViewById<TextView>(R.id.TextViewCableValue)
        val textViewVoltage = findViewById<TextView>(R.id.TextViewVoltage)
        val textViewCos = findViewById<TextView>(R.id.TextViewCos)
        val textViewCurrentAmperageValue = findViewById<TextView>(R.id.TextViewCurrentAmperageValue)
        val textViewR = findViewById<TextView>(R.id.TextViewR)
        val textViewRValue = findViewById<TextView>(R.id.TextViewRValue)
        val textViewX = findViewById<TextView>(R.id.TextViewX)
        val textViewXValue = findViewById<TextView>(R.id.TextViewXValue)
        val textViewAmperageShort = findViewById<TextView>(R.id.TextViewAmperageShort)
        val textViewAmperageShortValue = findViewById<TextView>(R.id.TextViewAmperageShortValue)
        val textViewAmperageValue = findViewById<TextView>(R.id.TextViewAmperageValue)

        val editTextVoltage = findViewById<EditText>(R.id.EditTextVoltage)
        val editTextCos = findViewById<EditText>(R.id.EditTextCos)
        val editTextPower = findViewById<EditText>(R.id.EditTextPower)
        val buttonGetCable = findViewById<Button>(R.id.ButtonGetCable)

        val switchExtendedMode = findViewById<Switch>(R.id.SwitchExtendedMode)


        editTextVoltage.setText(mainActivityViewModel.v)
        editTextCos.setText(mainActivityViewModel.cos)
        editTextPower.setText(mainActivityViewModel.p)

        mainActivityViewModel.pLiveData.observe(this){
            textViewXValue.text = it.toString()
        }

        switchExtendedMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                textViewNominalSize.visibility= View.VISIBLE
                spinnerNominalSize.visibility = View.VISIBLE
                textViewVoltage.visibility = View.VISIBLE
                editTextVoltage.visibility = View.VISIBLE
                textViewCos.visibility = View.VISIBLE
                editTextCos.visibility = View.VISIBLE
                textViewR.visibility = View.VISIBLE
                textViewRValue.visibility = View.VISIBLE
                textViewX.visibility = View.VISIBLE
                textViewXValue.visibility = View.VISIBLE
                textViewAmperageShort.visibility = View.VISIBLE
                textViewAmperageShortValue.visibility = View.VISIBLE

                        }
            else {
                textViewNominalSize.visibility= View.GONE
                spinnerNominalSize.visibility = View.GONE
                textViewVoltage.visibility = View.GONE
                editTextVoltage.visibility = View.GONE
                textViewCos.visibility = View.GONE
                editTextCos.visibility = View.GONE
                textViewR.visibility = View.GONE
                textViewRValue.visibility = View.GONE
                textViewX.visibility = View.GONE
                textViewXValue.visibility = View.GONE
                textViewAmperageShort.visibility = View.GONE
                textViewAmperageShortValue.visibility = View.GONE
                           }
            }

         fun sendDataToViewModel(){
            mainActivityViewModel.nominalSize = spinnerNominalSize.selectedItem as Double
            mainActivityViewModel.countPhase = spinnerCountPhase.selectedItem.toString()
            mainActivityViewModel.v = editTextVoltage.text.toString()
            mainActivityViewModel.cos = editTextCos.text.toString()
            mainActivityViewModel.p = editTextPower.text.toString()

            mainActivityViewModel.calculate()
        }

        fun getDataFromViewModel(){

            textViewCurrentAmperageValue.text = String.format("%.2f", mainActivityViewModel.amperageCalculate)
            editTextVoltage.setText(mainActivityViewModel.v)
            editTextCos.setText(mainActivityViewModel.cos)
            editTextPower.setText(mainActivityViewModel.p)
            textViewCableValue.text = mainActivityViewModel.cableText
            textViewRValue.text = mainActivityViewModel.R
            textViewXValue.text = mainActivityViewModel.X
            textViewAmperageValue.text = mainActivityViewModel.amperage.toString()
            textViewAmperageShortValue.text = mainActivityViewModel.amperageShort
        }



        buttonGetCable.setOnClickListener{
           // Toast.makeText(this, "Кабель подобран", Toast.LENGTH_SHORT).show()
           // var i = mainActivityViewModel.calculate(editTextPower.text.toString().toInt(), editTextVoltage.text.toString().toInt(), editTextCos.text.toString().toDouble()).toInt()
           // Log.i("Расчетный ток", i.toString())
            //Toast.makeText(this, i.toString(), Toast.LENGTH_SHORT).show()
            //textViewCurrentAmperageValue.setText(i.toString())
            sendDataToViewModel()

            getDataFromViewModel()
             Toast.makeText(this, "Кабель подобран", Toast.LENGTH_SHORT).show()
          //  mainActivityViewModel.pLiveData.postValue(mainActivityViewModel.p)
        }

    }



}