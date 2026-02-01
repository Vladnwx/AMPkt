// com.amp.data.MainActivityViewModel.kt
package com.amp.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amp.calculation.Calculation
import com.amp.data.entity.Feeder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivityViewModel(
    private val repository: AppRepository,
    private val calculation: Calculation
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        Log.i("MainActivityViewModel", "Created")
    }

    fun calculateCable(electricalLoadAmperage: Double, countPhase: Int) {
        viewModelScope.launch {
            try {
                // 1. Получаем все сечения
                val allSizes = repository.getAllNominalSizeList()

                // 2. Подбираем минимальное сечение
                var nominalSize = findSuitableNominalSize(
                    amperageRequired = electricalLoadAmperage,
                    countPhase = countPhase,
                    allSizes = allSizes
                )

                // 3. Если не нашли — увеличиваем количество жил и повторяем (но без рекурсии!)
                var countJil = 1
                while (nominalSize == null && countJil <= 5) {
                    countJil++
                    nominalSize = findSuitableNominalSize(
                        amperageRequired = electricalLoadAmperage,
                        countPhase = countPhase,
                        allSizes = allSizes,
                        countJil = countJil
                    )
                }

                if (nominalSize != null) {
                    // 4. Заполняем Feeder
                    val feeder = Feeder().apply {
                        this.countPhase = countPhase
                        this.countJil = countJil
                        this.nominalSize = nominalSize
                        // остальные параметры — по умолчанию или из UI
                        methodOfLaying = "Одиночная прокладка"
                        materialType = "Cu"
                        insulationType = "PVC"
                        typeAmperage = "AC"
                        numberOfCore = "multicore3"
                        typeOfEnvironment = "В воздухе"
                    }

                    // 5. Получаем R, X, Iкз
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
                        isLoading = false,
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Не удалось подобрать кабель"
                    )
                }
            } catch (e: Exception) {
                Log.e("MainActivityViewModel", "Calculation error", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Ошибка расчёта: ${e.message}"
                )
            }
        }
    }

    private suspend fun findSuitableNominalSize(
        amperageRequired: Double,
        countPhase: Int,
        allSizes: List<Double>,
        countJil: Int = 1
    ): Double? {
        return allSizes
            .sorted()
            .firstOrNull { size ->
                val amp = repository.getAmperage(
                    methodOfLaying = "Одиночная прокладка",
                    nominalSize = size,
                    materialType = "Cu",
                    insulationType = "PVC",
                    typeAmperage = "AC",
                    numberOfCore = if (countPhase == 1) "single" else "multicore3",
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