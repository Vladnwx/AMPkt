// com.amp.ActivityExtended.kt
package com.amp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.amp.data.MainActivityViewModel
import com.amp.data.MainActivityViewModelFactory
import com.amp.databinding.ActivityExtendedBinding
import com.amp.ui.adapter.TableRowAdapter
import com.amp.ui.model.TableRowModel

class ActivityExtended : AppCompatActivity() {

    private lateinit var binding: ActivityExtendedBinding
    private lateinit var adapter: TableRowAdapter
    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory((application as AmperageApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExtendedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TableRowAdapter()
        binding.recyclerViewActivityExtended.adapter = adapter
        binding.recyclerViewActivityExtended.setHasFixedSize(true)

        binding.ButtonActivityMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Подписка на данные
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                val feeder = state.feeder
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
        }
    }
}