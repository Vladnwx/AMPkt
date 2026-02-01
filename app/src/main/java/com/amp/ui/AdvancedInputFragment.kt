// com.amp.ui.AdvancedInputFragment.kt
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
import com.amp.databinding.FragmentAdvancedInputBinding
import com.amp.ui.adapter.ParameterAdapter
import com.amp.ui.model.ParameterItem

class AdvancedInputFragment : Fragment() {

    private var _binding: FragmentAdvancedInputBinding? = null
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
    private var materialIndex = 0 // 0 = Cu, 1 = Al
    private var insulationIndex = 0 // 0 = PVC, 1 = XLPE
    private var environmentIndex = 0 // 0 = В воздухе, 1 = В земле
    private var layingIndex = 0 // Только "Одиночная прокладка"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdvancedInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupButtons()
    }

    private fun setupRecyclerView() {
        parameterAdapter = ParameterAdapter(
            onEditTextChange = { key, value ->
                when (key) {
                    "Мощность, кВт" -> powerValue = value
                    "Напряжение, В" -> voltageValue = value
                    "cos φ" -> cosValue = value
                }
            },
            onSpinnerSelect = { key, index ->
                when (key) {
                    "Количество фаз" -> phaseIndex = index
                    "Материал жилы" -> materialIndex = index
                    "Тип изоляции" -> insulationIndex = index
                    "Окружающая среда" -> environmentIndex = index
                    "Способ прокладки" -> layingIndex = index
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

        items.add(ParameterItem.Header("Параметры кабеля"))
        items.add(ParameterItem.Spinner("Материал жилы", listOf("Cu", "Al"), materialIndex))
        items.add(ParameterItem.Spinner("Тип изоляции", listOf("PVC", "XLPE"), insulationIndex))
        items.add(ParameterItem.Spinner("Окружающая среда", listOf("В воздухе", "В земле"), environmentIndex))
        items.add(ParameterItem.Spinner("Способ прокладки", listOf("Одиночная прокладка"), layingIndex))

        parameterAdapter.updateList(items)
    }

    private fun setupButtons() {
        binding.btnCalculate.setOnClickListener {
            calculateAndNavigate()
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun calculateAndNavigate() {
        val power = powerValue.toDoubleOrNull() ?: return
        val voltage = voltageValue.toDoubleOrNull() ?: return
        val cos = cosValue.toDoubleOrNull() ?: 1.0
        val phaseCount = if (phaseIndex == 0) 1 else 3

        val materialType = listOf("Cu", "Al")[materialIndex]
        val insulationType = listOf("PVC", "XLPE")[insulationIndex]
        val typeOfEnvironment = listOf("В воздухе", "В земле")[environmentIndex]
        val methodOfLaying = listOf("Одиночная прокладка")[layingIndex]

        val amperage = if (phaseCount == 1) {
            power * 1000 / (voltage * cos.coerceIn(0.0, 1.0))
        } else {
            power * 1000 / (Math.sqrt(3.0) * voltage * cos.coerceIn(0.0, 1.0))
        }

        viewModel.calculateCable(
            amperageRequired = amperage,
            countPhase = phaseCount,
            materialType = materialType,
            insulationType = insulationType,
            methodOfLaying = methodOfLaying,
            typeOfEnvironment = typeOfEnvironment,
            numberOfCore = if (phaseCount == 1) "single" else "multicore3",
            typeAmperage = "AC"
        )

        findNavController().navigate(R.id.action_advanced_to_result)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}