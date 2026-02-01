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

    /**
     * Рассчитывает кабель с учётом всех параметров
     */
    fun calculateCable(
        amperageRequired: Double,
        countPhase: Int,
        materialType: String = "Cu",
        insulationType: String = "PVC",
        methodOfLaying: String = "Одиночная прокладка",
        typeOfEnvironment: String = "В воздухе",
        numberOfCore: String = "multicore3",
        typeAmperage: String = "AC"
    ) {
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
                        allSizes = allSizes,
                        materialType = materialType,
                        insulationType = insulationType,
                        methodOfLaying = methodOfLaying,
                        typeOfEnvironment = typeOfEnvironment,
                        numberOfCore = numberOfCore,
                        typeAmperage = typeAmperage
                    )
                    if (size != null) {
                        result = size to countJil
                        break
                    }
                }

                if (result != null) {
                    val (nominalSize, countJil) = result

                    // Формируем Feeder с ВСЕМИ параметрами
                    val feeder = Feeder(
                        countPhase = countPhase,
                        nominalSize = nominalSize,
                        countJil = countJil,
                        materialType = materialType,
                        insulationType = insulationType,
                        methodOfLaying = methodOfLaying,
                        typeOfEnvironment = typeOfEnvironment,
                        numberOfCore = numberOfCore,
                        typeAmperage = typeAmperage
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

    /**
     * Находит подходящее сечение по всем параметрам
     */
    private suspend fun findSuitableSize(
        amperageRequired: Double,
        countPhase: Int,
        countJil: Int,
        allSizes: List<Double>,
        materialType: String,
        insulationType: String,
        methodOfLaying: String,
        typeOfEnvironment: String,
        numberOfCore: String,
        typeAmperage: String
    ): Double? {
        return allSizes.firstOrNull { size ->
            val amp = repository.getAmperage(
                methodOfLaying = methodOfLaying,
                nominalSize = size,
                materialType = materialType,
                insulationType = insulationType,
                typeAmperage = typeAmperage,
                numberOfCore = numberOfCore,
                typeOfEnvironment = typeOfEnvironment
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