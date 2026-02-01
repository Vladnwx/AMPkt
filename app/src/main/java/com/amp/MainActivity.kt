// com.amp.MainActivity.kt
package com.amp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.amp.data.ElectricalLoad
import com.amp.data.MainActivityViewModel
import com.amp.data.MainActivityViewModelFactory
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.content.Intent
import android.util.Log

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory((application as AmperageApplication).repository)
    }

    private lateinit var editTextPower: EditText
    private lateinit var editTextVoltage: EditText
    private lateinit var editTextCos: EditText
    private lateinit var spinnerCountPhase: Spinner
    private lateinit var spinnerNominalSize: Spinner
    private lateinit var switchAutoVoltage: SwitchCompat
    private lateinit var switchConsiderCos: SwitchCompat
    private lateinit var switchExtendedMode: SwitchCompat
    private lateinit var buttonGetCable: Button
    private lateinit var buttonActivityExtended: Button

    // TextView для отображения результата
    private lateinit var textViewCableValue: TextView
    private lateinit var textViewRValue: TextView
    private lateinit var textViewXValue: TextView
    private lateinit var textViewAmperageValue: TextView
    private lateinit var textViewAmperageShortValue: TextView
    private lateinit var textViewCurrentAmperageValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupSpinners()
        setupObservers()
        setupClickListeners()
        setupTextWatchers()
    }

    private fun initViews() {
        editTextPower = findViewById(R.id.EditTextPower)
        editTextVoltage = findViewById(R.id.EditTextVoltage)
        editTextCos = findViewById(R.id.EditTextCos)
        spinnerCountPhase = findViewById(R.id.SpinnerCountPhase)
        spinnerNominalSize = findViewById(R.id.SpinnerNominalSize)
        switchAutoVoltage = findViewById(R.id.SwitchAutoVoltage)
        switchConsiderCos = findViewById(R.id.SwitchConsiderCos)
        switchExtendedMode = findViewById(R.id.SwitchExtendedMode)
        buttonGetCable = findViewById(R.id.ButtonGetCable)
        buttonActivityExtended = findViewById(R.id.ButtonActivityExtended)

        textViewCableValue = findViewById(R.id.TextViewCableValue)
        textViewRValue = findViewById(R.id.TextViewRValue)
        textViewXValue = findViewById(R.id.TextViewXValue)
        textViewAmperageValue = findViewById(R.id.TextViewAmperageValue)
        textViewAmperageShortValue = findViewById(R.id.TextViewAmperageShortValue)
        textViewCurrentAmperageValue = findViewById(R.id.TextViewCurrentAmperageValue)
    }

    private fun setupSpinners() {
        // Фазы
        ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            listOf("1", "3")
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCountPhase.adapter = adapter
        }

        // Сечения — пока пусто, заполнится после загрузки
        ArrayAdapter<Double>(
            this,
            android.R.layout.simple_spinner_item,
            emptyList()
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerNominalSize.adapter = adapter
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                if (state.isLoading) {
                    // Можно показать ProgressBar
                } else if (state.error != null) {
                    Toast.makeText(this@MainActivity, state.error, Toast.LENGTH_SHORT).show()
                } else {
                    with(state.feeder) {
                        textViewCableValue.text = cableText
                        textViewRValue.text = String.format("%.4f", r)
                        textViewXValue.text = String.format("%.4f", x)
                        textViewAmperageValue.text = String.format("%.1f", amperage)
                        textViewAmperageShortValue.text = String.format("%.1f", amperageShort)
                    }
                }
            }
        }

        // Загружаем список сечений один раз
        lifecycleScope.launch {
            try {
                val sizes = (application as AmperageApplication).repository.getAllNominalSizeList()
                val adapter = spinnerNominalSize.adapter as ArrayAdapter<Double>
                adapter.clear()
                adapter.addAll(sizes)
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Log.e("MainActivity", "Ошибка загрузки сечений", e)
                Toast.makeText(this@MainActivity, "Ошибка", Toast.LENGTH_SHORT).show()}
        }
    }

    private fun setupClickListeners() {
        buttonGetCable.setOnClickListener {
            val power = editTextPower.text.toString().toDoubleOrNull() ?: return@setOnClickListener
            val voltage = editTextVoltage.text.toString().toDoubleOrNull() ?: return@setOnClickListener
            val cos = if (switchConsiderCos.isChecked) {
                editTextCos.text.toString().toDoubleOrNull() ?: 1.0
            } else 1.0
            val phaseCount = spinnerCountPhase.selectedItem.toString().toIntOrNull() ?: 1

            val load = ElectricalLoad(
                activePowerKw = power,
                voltageV = voltage,
                powerFactor = cos.coerceIn(0.0, 1.0),
                phaseCount = phaseCount,
                considerPowerFactor = switchConsiderCos.isChecked
            )

            viewModel.calculateCable(load.calculatedAmperage, phaseCount)
        }

        buttonActivityExtended.setOnClickListener {
            startActivity(Intent(this, ActivityExtended::class.java))
        }

        switchAutoVoltage.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val phase = spinnerCountPhase.selectedItem.toString().toIntOrNull() ?: 1
                val voltage = when (phase) {
                    1 -> 230.0
                    else -> 400.0
                }
                editTextVoltage.setText(voltage.toString())
                editTextVoltage.isEnabled = false
            } else {
                editTextVoltage.isEnabled = true
            }
        }

        switchConsiderCos.setOnCheckedChangeListener { _, isChecked ->
            editTextCos.isEnabled = isChecked
        }

        switchExtendedMode.setOnCheckedChangeListener { _, isChecked ->
            val visibility = if (isChecked) View.VISIBLE else View.GONE
            arrayOf(
                R.id.TextViewNominalSize,
                R.id.SpinnerNominalSize,
                R.id.TextViewVoltage,
                R.id.EditTextVoltage,
                R.id.TextViewCos,
                R.id.EditTextCos,
                R.id.TextViewR,
                R.id.TextViewRValue,
                R.id.TextViewX,
                R.id.TextViewXValue,
                R.id.TextViewAmperageShort,
                R.id.TextViewAmperageShortValue
            ).forEach { id ->
                findViewById<View>(id).visibility = visibility
            }
        }
    }

    // ✅ ВАЖНО: функция вынесена на уровень класса!
    private fun updateCalculatedAmperage() {
        val power = editTextPower.text.toString().toDoubleOrNull() ?: return
        val voltage = editTextVoltage.text.toString().toDoubleOrNull() ?: return
        val cos = if (switchConsiderCos.isChecked) {
            editTextCos.text.toString().toDoubleOrNull() ?: 1.0
        } else {
            1.0
        }
        val phaseCount = spinnerCountPhase.selectedItem.toString().toIntOrNull() ?: 1

        val load = ElectricalLoad(
            activePowerKw = power,
            voltageV = voltage,
            powerFactor = cos.coerceIn(0.0, 1.0),
            phaseCount = phaseCount,
            considerPowerFactor = switchConsiderCos.isChecked
        )
        textViewCurrentAmperageValue.text = String.format("%.2f", load.calculatedAmperage)
    }

    private fun setupTextWatchers() {
        editTextPower.addTextChangedListener(textWatcher)
        editTextVoltage.addTextChangedListener(textWatcher)
        editTextCos.addTextChangedListener(textWatcher)

        spinnerCountPhase.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                updateCalculatedAmperage()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            updateCalculatedAmperage()
        }
    }
}