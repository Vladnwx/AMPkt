// com.amp.data.SelectionData.kt
package com.amp.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectionData {

    // === Список всех номинальных сечений ===
    private val _allNominalSizeList = MutableStateFlow<List<Double>>(listOf(0.0))
    val allNominalSizeList: StateFlow<List<Double>> = _allNominalSizeList.asStateFlow()

    fun updateNominalSizeList(list: List<Double>) {
        _allNominalSizeList.value = list
    }

    // === Карта "сечение → ток" ===
    private val _nominalSizeAmperageMap = MutableStateFlow<Map<Double, Double>>(
        mapOf(0.5 to 0.0)
    )
    val nominalSizeAmperageMap: StateFlow<Map<Double, Double>> = _nominalSizeAmperageMap.asStateFlow()

    fun updateNominalSizeAmperageMap(map: Map<Double, Double>) {
        _nominalSizeAmperageMap.value = map
    }

    // === Параметры R, X, p ===
    private val _rValue = MutableStateFlow(0.0)
    val rValue: StateFlow<Double> = _rValue.asStateFlow()

    private val _xValue = MutableStateFlow(0.0)
    val xValue: StateFlow<Double> = _xValue.asStateFlow()

    private val _pValue = MutableStateFlow(1.0)
    val pValue: StateFlow<Double> = _pValue.asStateFlow()

    fun updateR(value: Double) { _rValue.value = value }
    fun updateX(value: Double) { _xValue.value = value }
    fun updateP(value: Double) { _pValue.value = value }

    // === Количество фаз ===
    val countPhaseList: List<String> = listOf("1", "2", "3")
}