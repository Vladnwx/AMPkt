// com.amp.ui.AdvancedInputFragment.kt
package com.amp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.amp.AmperageApplication
import com.amp.data.MainActivityViewModel
import com.amp.data.MainActivityViewModelFactory
import com.amp.databinding.FragmentAdvancedInputBinding
import com.amp.ui.adapter.ParameterAdapter
import com.amp.ui.model.ParameterItem
import kotlinx.coroutines.launch

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
        setupCalculateButton()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        parameterAdapter = ParameterAdapter(
            onEditNumberChange = { key, value ->
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

        updateParameterList(null)
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                if (!state.isLoading && state.error == null && state.feeder.nominalSize > 0) {
                    updateParameterList(state.feeder)
                } else if (state.error != null) {
                    Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateParameterList(feeder: com.amp.data.Feeder?) {
        val items = mutableListOf<ParameterItem>()

        items.add(ParameterItem.Header("Основные параметры"))
        items.add(ParameterItem.EditNumber("Мощность, кВт", powerValue))
        items.add(ParameterItem.EditNumber("Напряжение, В", voltageValue))
        items.add(ParameterItem.EditNumber("cos φ", cosValue))
        items.add(ParameterItem.Spinner("Количество фаз", listOf("1", "3"), phaseIndex))

        items.add(ParameterItem.Header("Параметры кабеля"))
        items.add(ParameterItem.Spinner("Материал жилы", listOf("Cu", "Al"), materialIndex))
        items.add(ParameterItem.Spinner("Тип изоляции", listOf("PVC", "XLPE"), insulationIndex))
        items.add(ParameterItem.Spinner("Окружающая среда", listOf("В воздухе", "В земле"), environmentIndex))
        items.add(ParameterItem.Spinner("Способ прокладки", listOf("Одиночная прокладка"), layingIndex))

        if (feeder != null) {
            items.add(ParameterItem.Header("Результат подбора"))
            items.add(ParameterItem.Text("Подобранный кабель", feeder.cableText))
            items.add(ParameterItem.Text("Допустимый ток, А", "%.1f".format(feeder.amperage)))
        }

        parameterAdapter.updateList(items)
    }

    private fun setupCalculateButton() {
        binding.btnCalculate.setOnClickListener {
            val power = powerValue.toDoubleOrNull() ?: run {
                Toast.makeText(context, "Введите корректную мощность", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val voltage = voltageValue.toDoubleOrNull() ?: run {
                Toast.makeText(context, "Введите корректное напряжение", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val cos = cosValue.toDoubleOrNull() ?: 1.0
            val phaseCount = if (phaseIndex == 0) 1 else 3

            val materialType = listOf("Cu", "Al")[materialIndex]
            val insulationType = listOf("PVC", "XLPE")[insulationIndex]
            val typeOfEnvironment = listOf("В воздухе", "В земле")[environmentIndex]
            val methodOfLaying = listOf("Одиночная прокладка")[layingIndex]

            val amperageRequired = if (phaseCount == 1) {
                power * 1000 / (voltage * cos.coerceIn(0.0, 1.0))
            } else {
                power * 1000 / (Math.sqrt(3.0) * voltage * cos.coerceIn(0.0, 1.0))
            }

            viewModel.calculateCable(
                amperageRequired = amperageRequired,
                countPhase = phaseCount,
                materialType = materialType,
                insulationType = insulationType,
                methodOfLaying = methodOfLaying,
                typeOfEnvironment = typeOfEnvironment,
                numberOfCore = if (phaseCount == 1) "single" else "multicore3",
                typeAmperage = "AC"
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}