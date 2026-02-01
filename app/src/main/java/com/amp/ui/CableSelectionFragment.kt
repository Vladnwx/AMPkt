// com.amp.ui.CableSelectionFragment.kt
package com.amp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.amp.AmperageApplication
import com.amp.R
import com.amp.data.MainActivityViewModel
import com.amp.data.MainActivityViewModelFactory
import com.amp.databinding.FragmentCableSelectionBinding
import com.amp.ui.adapter.ParameterAdapter
import com.amp.ui.model.ParameterItem
import kotlinx.coroutines.launch

class CableSelectionFragment : Fragment() {

    private var _binding: FragmentCableSelectionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory((requireActivity().application as AmperageApplication).repository)
    }

    private lateinit var adapter: ParameterAdapter

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
        observeViewModel()
        setupBackButton()
    }

    private fun setupRecyclerView() {
        adapter = ParameterAdapter() // Без коллбэков — только для отображения
        binding.recyclerViewActivityExtended.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CableSelectionFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                updateTable(state.feeder)
            }
        }
    }

    private fun updateTable(feeder: com.amp.data.Feeder) {
        val rows = listOf(
            ParameterItem.Text("Окружающая среда", feeder.typeOfEnvironment),
            ParameterItem.Text("Материал жилы", feeder.materialType),
            ParameterItem.Text("Тип изоляции", feeder.insulationType),
            ParameterItem.Text("Способ прокладки", feeder.methodOfLaying),
            ParameterItem.Text("Конструкция жилы", feeder.numberOfCore),
            ParameterItem.Text("Род тока", feeder.typeAmperage),
            ParameterItem.Text("Сечение, мм²", "%.1f".format(feeder.nominalSize)),
            ParameterItem.Text("Количество фаз", feeder.countPhase.toString()),
            ParameterItem.Text("Кабель", feeder.cableText),
            ParameterItem.Text("R, Ом/км", "%.4f".format(feeder.r)),
            ParameterItem.Text("X, Ом/км", "%.4f".format(feeder.x)),
            ParameterItem.Text("Iкз, А", "%.1f".format(feeder.amperageShort)),
            ParameterItem.Text("Доп. ток, А", "%.1f".format(feeder.amperage))
        )
        adapter.updateList(rows)
    }

    private fun setupBackButton() {
        binding.btnNewCalculation.setOnClickListener {
            findNavController().navigate(R.id.inputFragment)     }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}