// com.amp.ui.InputFragment.kt
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
import com.amp.databinding.FragmentInputBinding
import com.amp.ui.adapter.ParameterAdapter
import com.amp.ui.model.ParameterItem
import kotlinx.coroutines.launch

class InputFragment : Fragment() {

    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory((requireActivity().application as AmperageApplication).repository)
    }

    private lateinit var parameterAdapter: ParameterAdapter

    private var powerValue = "10.0"
    private var phaseIndex = 0 // 0 = 1 фаза, 1 = 3 фазы

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
        setupCalculateButton()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        parameterAdapter = ParameterAdapter(
            onEditNumberChange = { key, value ->
                if (key == "Мощность, кВт") powerValue = value
            },
            onSpinnerSelect = { key, index ->
                if (key == "Количество фаз") phaseIndex = index
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

        items.add(ParameterItem.Header("Параметры нагрузки"))
        items.add(ParameterItem.EditNumber("Мощность, кВт", powerValue))
        items.add(ParameterItem.Spinner("Количество фаз", listOf("1", "3"), phaseIndex))

        items.add(ParameterItem.Header("Условия по умолчанию"))
        items.add(ParameterItem.Text("Напряжение", if (phaseIndex == 0) "230 В" else "400 В"))
        items.add(ParameterItem.Text("cos φ", "1.0"))
        items.add(ParameterItem.Text("Материал жилы", "Cu"))
        items.add(ParameterItem.Text("Окружающая среда", "В воздухе"))

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
            val phaseCount = if (phaseIndex == 0) 1 else 3
            val voltage = if (phaseCount == 1) 230.0 else 400.0
            val cos = 1.0

            val amperageRequired = if (phaseCount == 1) {
                power * 1000 / (voltage * cos)
            } else {
                power * 1000 / (Math.sqrt(3.0) * voltage * cos)
            }

            viewModel.calculateCable(
                amperageRequired = amperageRequired,
                countPhase = phaseCount,
                materialType = "Cu",
                insulationType = "PVC",
                methodOfLaying = "Одиночная прокладка",
                typeOfEnvironment = "В воздухе",
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