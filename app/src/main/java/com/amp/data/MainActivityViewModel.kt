// com.amp.data.MainActivityViewModel.kt
package com.amp.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        Log.i("MainActivityViewModel", "Created")
    }

    fun calculateCable(amperageRequired: Double, countPhase: Int) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                val allSizes = repository.getAllNominalSizeList().sorted()

                // Попытка подобрать кабель при countJil = 1..5
                var result: Pair<Double, Int>? = null
                for (countJil in 1..5) {
                    val size = findSuitableSize(
                        amperageRequired = amperageRequired,
                        countPhase = countPhase,
                        countJil = countJil,
                        allSizes = allSizes
                    )
                    if (size != null) {
                        result = size to countJil
                        break
                    }
                }

                if (result != null) {
                    val (nominalSize, countJil) = result

                    // Формируем Feeder
                    val feeder = Feeder(
                        countPhase = countPhase,
                        nominalSize = nominalSize,
                        countJil = countJil,
                        // остальные параметры — по умолчанию (соответствуют справочникам)
                    )

                    // Получаем R, X, Iкз
                    val r = repository.getR(feeder.materialType, feeder.nominalSize)
                    val x = repository.getX(feeder.materialType, feeder.nominalSize)
                    val amperageShort = repository.getAmperageShort(
                        feeder.materialType,
                        feeder.nominalSize,
                        feeder.insulationType
                    )
                    val amperage = repository.getAmperage(
                        feeder.methodOfLaying,
                        feeder.nominalSize,
                        feeder.materialType,
                        feeder.insulationType,
                        feeder.typeAmperage,
                        feeder.numberOfCore,
                        feeder.typeOfEnvironment
                    ) * feeder.countJil

                    _uiState.value = _uiState.value.copy(
                        feeder = feeder.copy(
                            r = r,
                            x = x,
                            amperageShort = amperageShort,
                            amperage = amperage
                        ),
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Не удалось подобрать кабель"
                    )
                }
            } catch (e: Exception) {
                Log.e("MainActivityViewModel", "Ошибка расчёта", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Ошибка: ${e.message}"
                )
            }
        }
    }

    private suspend fun findSuitableSize(
        amperageRequired: Double,
        countPhase: Int,
        countJil: Int,
        allSizes: List<Double>
    ): Double? {
        return allSizes.firstOrNull { size ->
            val coreType = if (countPhase == 1) "single" else "multicore3"
            val amp = repository.getAmperage(
                methodOfLaying = "Одиночная прокладка",
                nominalSize = size,
                materialType = "Cu",
                insulationType = "PVC",
                typeAmperage = "AC",
                numberOfCore = coreType,
                typeOfEnvironment = "В воздухе"
            ) * countJil
            amp >= amperageRequired
        }
    }

    data class UiState(
        val feeder: Feeder = Feeder(),
        val isLoading: Boolean = false,
        val error: String? = null
    )
}