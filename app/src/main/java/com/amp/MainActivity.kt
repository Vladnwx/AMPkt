package com.amp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amp.RecyclerView.ActivityExtended
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

        val buttonActivityExtended = findViewById<Button>(R.id.ButtonActivityExtended)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        val adapter = AppListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        /*  mainActivityViewModel.allTypeOfEnvironment.observe(this){
            it.let { adapter.submitList(it) }
        }
*/
        //   mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        Log.i("MainActivityViewModel", "Called ViewModelProvider.get")

        val spinnerNominalSize = findViewById<Spinner>(R.id.SpinnerNominalSize)

        val nominalSizeAdapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            mainActivityViewModel.selectionData.allNominalSizeList
        )
        nominalSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNominalSize.adapter = nominalSizeAdapter

        spinnerNominalSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                mainActivityViewModel.feeder.nominalSize = spinnerNominalSize.selectedItem as Double
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        val spinnerCountPhase = findViewById<Spinner>(R.id.SpinnerCountPhase)
        val countPhaseAdapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            mainActivityViewModel.selectionData.countPhaseList
        )
        countPhaseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCountPhase.adapter = countPhaseAdapter

        mainActivityViewModel.allNominalSize.observe(this) {

            for (i in it.indices) {
                mainActivityViewModel.selectionData.allNominalSizeList.add(it[i].sizeMm2)
            }
            mainActivityViewModel.selectionData.allNominalSizeList.remove(0.0)
            spinnerNominalSize.setSelection(0)
            Log.i("Exception", ":LIST")
            Toast.makeText(this, "Лист поперечных сечений сформирован", Toast.LENGTH_SHORT).show()


        }

        val switchAutoVoltage = findViewById<SwitchCompat>(R.id.SwitchAutoVoltage)
        val switchConsiderCos = findViewById<SwitchCompat>(R.id.SwitchConsiderCos)
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

        val switchExtendedMode = findViewById<SwitchCompat>(R.id.SwitchExtendedMode)

        editTextVoltage.setText(mainActivityViewModel.electricalLoad.v.toString())
        editTextCos.setText(mainActivityViewModel.electricalLoad.cos.toString())

            editTextPower.setText(mainActivityViewModel.electricalLoad.p.toString())

        mainActivityViewModel.selectionData.pLiveData.observe(this) {
            textViewXValue.text = it.toString()
        }
        textViewCurrentAmperageValue.text = String.format("%.2f", mainActivityViewModel.electricalLoad.amperageCalculate)

        switchExtendedMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                textViewNominalSize.visibility = View.VISIBLE
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

            } else {
                textViewNominalSize.visibility = View.GONE
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


        fun getDataFromViewModel() {

            textViewCableValue.text = mainActivityViewModel.feeder.cableText
            textViewRValue.text = mainActivityViewModel.feeder.r.toString()
            textViewXValue.text = mainActivityViewModel.feeder.x.toString()
            textViewAmperageValue.text = mainActivityViewModel.feeder.amperage.toString()
            textViewAmperageShortValue.text = mainActivityViewModel.feeder.amperageShort.toString()
        }

        buttonGetCable.setOnClickListener {
            // Toast.makeText(this, "Кабель подобран", Toast.LENGTH_SHORT).show()
            // var i = mainActivityViewModel.calculate(editTextPower.text.toString().toInt(), editTextVoltage.text.toString().toInt(), editTextCos.text.toString().toDouble()).toInt()
            // Log.i("Расчетный ток", i.toString())
            //Toast.makeText(this, i.toString(), Toast.LENGTH_SHORT).show()
            //textViewCurrentAmperageValue.setText(i.toString())
            mainActivityViewModel.feeder.countJil = 1
            mainActivityViewModel.searchAmperage()

            getDataFromViewModel()
            Toast.makeText(this, "Кабель подобран", Toast.LENGTH_SHORT).show()
            //  mainActivityViewModel.pLiveData.postValue(mainActivityViewModel.p)
        }
        buttonActivityExtended.setOnClickListener {
            val intent = Intent(this, ActivityExtended::class.java)
            startActivity(intent)
        }
        editTextCos.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //  Toast.makeText(this@MainActivity, "Текст изменен", Toast.LENGTH_SHORT).show()
            }
            override fun afterTextChanged(p0: Editable?) {
                if (editTextCos.text.isNotEmpty()) {
                    this@MainActivity.mainActivityViewModel.electricalLoad.cos = editTextCos.text.toString().toDoubleOrNull()!!
                    mainActivityViewModel.calculation.electricalLoad(mainActivityViewModel.electricalLoad)
                    textViewCurrentAmperageValue.text =  String.format("%.2f", mainActivityViewModel.electricalLoad.amperageCalculate)
                }
                Toast.makeText(this@MainActivity, mainActivityViewModel.electricalLoad.logOrError, Toast.LENGTH_SHORT).show()
            }
        })

        editTextVoltage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //  Toast.makeText(this@MainActivity, "Текст изменен", Toast.LENGTH_SHORT).show()
            }
            override fun afterTextChanged(p0: Editable?) {
                if (editTextVoltage.text.isNotEmpty()) {
                    this@MainActivity.mainActivityViewModel.electricalLoad.v = editTextVoltage.text.toString().toDoubleOrNull()!!
                    mainActivityViewModel.calculation.electricalLoad(mainActivityViewModel.electricalLoad)
                    textViewCurrentAmperageValue.text =  String.format("%.2f", mainActivityViewModel.electricalLoad.amperageCalculate)
                }
                Toast.makeText(this@MainActivity, mainActivityViewModel.electricalLoad.logOrError, Toast.LENGTH_SHORT).show()
            }
        })

        editTextPower.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

              //  Toast.makeText(this@MainActivity, "Текст изменен", Toast.LENGTH_SHORT).show()
            }

            override fun afterTextChanged(p0: Editable?) {
                if (editTextPower.text.isNotEmpty()) {
                this@MainActivity.mainActivityViewModel.electricalLoad.p = editTextPower.text.toString().toDoubleOrNull()!!
                    mainActivityViewModel.calculation.electricalLoad(mainActivityViewModel.electricalLoad)
                    textViewCurrentAmperageValue.text =  String.format("%.2f", mainActivityViewModel.electricalLoad.amperageCalculate)
                }
                Toast.makeText(this@MainActivity, mainActivityViewModel.electricalLoad.logOrError, Toast.LENGTH_SHORT).show()
            }
        })

        spinnerCountPhase.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                this@MainActivity.mainActivityViewModel.electricalLoad.countPhase = spinnerCountPhase.selectedItem.toString().toDoubleOrNull()!!
                mainActivityViewModel.calculation.electricalLoad(mainActivityViewModel.electricalLoad)
                textViewCurrentAmperageValue.text =  String.format("%.2f", mainActivityViewModel.electricalLoad.amperageCalculate)
                Toast.makeText(this@MainActivity, mainActivityViewModel.electricalLoad.logOrError, Toast.LENGTH_SHORT).show()
                if(switchAutoVoltage.isChecked) {
                    switchAutoVoltage.isChecked = false
                    switchAutoVoltage.isChecked = true
                    }
                            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        switchAutoVoltage.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                when (mainActivityViewModel.electricalLoad.countPhase) {
                    1.0 -> mainActivityViewModel.electricalLoad.v = 230.0
                    2.0 -> mainActivityViewModel.electricalLoad.v = 400.0
                    3.0 -> mainActivityViewModel.electricalLoad.v = 400.0
                }
                editTextVoltage.setText(mainActivityViewModel.electricalLoad.v.toString())
                editTextVoltage.isEnabled = false
            } else {
                editTextVoltage.setText(mainActivityViewModel.electricalLoad.v.toString())
                editTextVoltage.isEnabled = true
            }
        }

        switchConsiderCos.setOnCheckedChangeListener { _, isChecked ->
            editTextCos.isEnabled = isChecked
        }


    }



    }


