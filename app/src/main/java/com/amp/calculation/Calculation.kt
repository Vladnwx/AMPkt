package com.amp.calculation

import com.amp.data.ElectricalLoad
import com.amp.data.Feeder

class Calculation {

    fun electricalLoad(e: ElectricalLoad?) {
        if (e != null) {
            e.amperageCalculate = AmperageCalculator.calculateAmperage(
                power = e.p * 1000, // Перевод в ватты
                voltage = e.v,
                cos = e.cos,
                phaseCount = e.countPhase.toInt() // Предполагаем, что countPhase Int
            )
        }
    }

    fun feeder(f: Feeder?) {
        // TODO: Логика для feeder
    }

    private fun automaticSelectionOfStandardVoltage(b: Boolean) {
        // TODO: Логика выбора напряжения
    }
}