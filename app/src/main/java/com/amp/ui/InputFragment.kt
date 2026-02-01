// com.amp.ui.InputFragment.kt
package com.amp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.amp.AmperageApplication
import com.amp.R
import com.amp.data.MainActivityViewModel
import com.amp.data.MainActivityViewModelFactory
import com.amp.databinding.FragmentInputBinding
import com.amp.ui.adapter.ParameterAdapter
import com.amp.ui.model.ParameterItem

class InputFragment : Fragment() {

    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory((requireActivity().application as AmperageApplication).repository)
    }

    private lateinit var parameterAdapter: ParameterAdapter

    // Хранилище текущих значений
    private var powerValue = "10.0"
    private var voltageValue = "400"
    private var cosValue = "0.85"
    private var phaseIndex = 1 // 0 = 1 фаза, 1 = 3 фазы
    private var autoVoltageEnabled = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupButtons()
        updateAutoVoltage()
    }

    private fun setupRecyclerView() {
        parameterAdapter = ParameterAdapter(
            onEditTextChange = { key, value ->
                when (key) {
                    "Мощность, кВт" -> powerValue = value
                    "Напряжение, В" -> if (!autoVoltageEnabled) voltageValue = value
                    "cos φ" -> cosValue = value
                }
            },
            onSpinnerSelect = { key, index ->
                if (key == "Количество фаз") {
                    phaseIndex = index
                    if (autoVoltageEnabled) {
                        updateAutoVoltage()
                    }
                }
            }
        )

        binding.recyclerViewParameters.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = parameterAdapter
        }

        updateParameterList()
    }

    private fun updateParameterList() {
        val items = mutableListOf<ParameterItem>()

        items.add(ParameterItem.Header("Основные параметры"))
        items.add(ParameterItem.EditText("Мощность, кВт", powerValue))
        items.add(ParameterItem.EditText("Напряжение, В", voltageValue))
        items.add(ParameterItem.EditText("cos φ", cosValue))
        items.add(ParameterItem.Spinner("Количество фаз", listOf("1", "3"), phaseIndex))

        parameterAdapter.updateList(items)
    }

    private fun updateAutoVoltage() {
        val phase = if (phaseIndex == 0) 1 else 3
        voltageValue = when (phase) {
            1 -> "230"
            else -> "400"
        }
        if (autoVoltageEnabled) {
            updateParameterList()
        }
    }

    private fun setupButtons() {
        binding.btnCalculate.setOnClickListener {
            calculateAndNavigate()
        }

        binding.btnAdvanced.setOnClickListener {
            findNavController().navigate(R.id.action_input_to_advanced)
        }
    }

    private fun calculateAndNavigate() {
        val power = powerValue.toDoubleOrNull() ?: return
        val voltage = voltageValue.toDoubleOrNull() ?: return
        val cos = cosValue.toDoubleOrNull() ?: 1.0
        val phaseCount = if (phaseIndex == 0) 1 else 3

        viewModel.calculateCable(
            amperageRequired = calculateAmperage(power, voltage, cos, phaseCount),
            countPhase = phaseCount,
            materialType = "Cu",
            insulationType = "PVC",
            methodOfLaying = "Одиночная прокладка",
            typeOfEnvironment = "В воздухе",
            numberOfCore = if (phaseCount == 1) "single" else "multicore3",
            typeAmperage = "AC"
        )

        findNavController().navigate(R.id.action_input_to_result)
    }

    private fun calculateAmperage(p: Double, v: Double, cos: Double, phase: Int): Double {
        return if (phase == 1) {
            p * 1000 / (v * cos.coerceIn(0.0, 1.0))
        } else {
            p * 1000 / (Math.sqrt(3.0) * v * cos.coerceIn(0.0, 1.0))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}