// com.amp.ui.CableSelectionFragment.kt
package com.amp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.amp.AmperageApplication
import com.amp.data.MainActivityViewModel
import com.amp.data.MainActivityViewModelFactory
import com.amp.databinding.FragmentCableSelectionBinding
import com.amp.ui.adapter.TableRowAdapter
import com.amp.ui.model.TableRowModel
import kotlinx.coroutines.launch

class CableSelectionFragment : Fragment() {

    private var _binding: FragmentCableSelectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory((requireActivity().application as AmperageApplication).repository)
    }

    private lateinit var adapter: TableRowAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCableSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()

        // Кнопка "Назад" — не нужна, если это стартовый экран
        // Или замени на кнопку "Новый расчёт"
    }

    private fun setupRecyclerView() {
        adapter = TableRowAdapter()
        binding.recyclerViewActivityExtended.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CableSelectionFragment.adapter
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                updateTable(state.feeder)
            }
        }
    }

    private fun updateTable(feeder: com.amp.data.Feeder) {
        val rows = listOf(
            TableRowModel("Окружающая среда", feeder.typeOfEnvironment),
            TableRowModel("Материал жилы", feeder.materialType),
            TableRowModel("Тип изоляции", feeder.insulationType),
            TableRowModel("Способ прокладки", feeder.methodOfLaying),
            TableRowModel("Конструкция жилы", feeder.numberOfCore),
            TableRowModel("Род тока", feeder.typeAmperage),
            TableRowModel("Сечение, мм²", "%.1f".format(feeder.nominalSize)),
            TableRowModel("Количество фаз", feeder.countPhase.toString()),
            TableRowModel("Кабель", feeder.cableText),
            TableRowModel("R, Ом/км", "%.4f".format(feeder.r)),
            TableRowModel("X, Ом/км", "%.4f".format(feeder.x)),
            TableRowModel("Iкз, А", "%.1f".format(feeder.amperageShort)),
            TableRowModel("Доп. ток, А", "%.1f".format(feeder.amperage))
        )
        adapter.updateList(rows)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}