// com.amp.ui.TableEditorFragment.kt
package com.amp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.amp.AmperageApplication
import com.amp.data.AppRepository
import com.amp.databinding.FragmentTableEditorBinding
import com.amp.ui.adapter.ParameterAdapter
import com.amp.ui.model.ParameterItem
import kotlinx.coroutines.launch

class TableEditorFragment : Fragment() {

    private var _binding: FragmentTableEditorBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: AppRepository // ← ПОЛУЧАЕМ НАПРЯМУЮ

    private var materialIndex = 0 // Cu
    private var insulationIndex = 0 // PVC
    private var currentTypeIndex = 0 // AC
    private var coreTypeIndex = 0 // multicore3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = (requireActivity().application as AmperageApplication).repository
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTableEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinners()
        loadTableData()
    }

    private fun setupSpinners() {
        // Материал жилы
        ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, listOf("Cu", "Al"))
            .also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            .also { binding.spinnerMaterial.adapter = it }
            .also { binding.spinnerMaterial.setSelection(materialIndex) }

        // Изоляция
        ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, listOf("PVC", "XLPE"))
            .also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            .also { binding.spinnerInsulation.adapter = it }
            .also { binding.spinnerInsulation.setSelection(insulationIndex) }

        // Род тока
        ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, listOf("AC", "DC"))
            .also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            .also { binding.spinnerCurrentType.adapter = it }
            .also { binding.spinnerCurrentType.setSelection(currentTypeIndex) }

        // Конструкция
        ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, listOf("multicore3", "single"))
            .also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            .also { binding.spinnerCoreType.adapter = it }
            .also { binding.spinnerCoreType.setSelection(coreTypeIndex) }

        // Обработчики изменений
        binding.spinnerMaterial.onItemSelectedListener = createSpinnerListener { materialIndex = it }
        binding.spinnerInsulation.onItemSelectedListener = createSpinnerListener { insulationIndex = it }
        binding.spinnerCurrentType.onItemSelectedListener = createSpinnerListener { currentTypeIndex = it }
        binding.spinnerCoreType.onItemSelectedListener = createSpinnerListener { coreTypeIndex = it }
    }

    private fun createSpinnerListener(callback: (Int) -> Unit) =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                callback(pos)
                loadTableData()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    private fun loadTableData() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val material = listOf("Cu", "Al")[materialIndex]
                val insulation = listOf("PVC", "XLPE")[insulationIndex]
                val currentType = listOf("AC", "DC")[currentTypeIndex]
                val coreType = listOf("multicore3", "single")[coreTypeIndex]

                // Получаем все сечения
                val sizes = repository.getAllNominalSizeList().sorted()

                // Собираем строки таблицы
                val items = mutableListOf<ParameterItem>()
                items.add(ParameterItem.Header("Таблица допустимых токов"))

                for (size in sizes) {
                    val amperage = repository.getAmperage(
                        methodOfLaying = "Одиночная прокладка",
                        nominalSize = size,
                        materialType = material,
                        insulationType = insulation,
                        typeAmperage = currentType,
                        numberOfCore = coreType,
                        typeOfEnvironment = "В воздухе"
                    )
                    items.add(ParameterItem.EditText(
                        key = "%.1f мм²".format(size),
                        value = "%.1f".format(amperage)
                    ))
                }

                updateRecyclerView(items)
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }

    private fun updateRecyclerView(items: List<ParameterItem>) {
        val adapter = ParameterAdapter(
            onEditTextChange = { key, value ->
                // Парсим сечение из ключа
                val sizeStr = key.replace(" мм²", "")
                val size = sizeStr.toDoubleOrNull() ?: return@ParameterAdapter
                val newAmperage = value.toDoubleOrNull() ?: return@ParameterAdapter

                // Сохраняем в БД
                viewLifecycleOwner.lifecycleScope.launch {
                    repository.updateAmperage(
                        methodOfLaying = "Одиночная прокладка",
                        nominalSize = size,
                        materialType = listOf("Cu", "Al")[materialIndex],
                        insulationType = listOf("PVC", "XLPE")[insulationIndex],
                        typeAmperage = listOf("AC", "DC")[currentTypeIndex],
                        numberOfCore = listOf("multicore3", "single")[coreTypeIndex],
                        typeOfEnvironment = "В воздухе",
                        amperage = newAmperage
                    )
                }
            }
        )
        adapter.updateList(items)
        binding.recyclerViewTable.adapter = adapter
        binding.recyclerViewTable.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}