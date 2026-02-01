package com.amp.calculation

import kotlin.math.sqrt

// Объект без создания экземпляра
object AmperageCalculator {

    // Шаблонная функция для одиночного расчета (1 фаза)
    fun <T : Number> calculateAmperage(power: T, voltage: T): Double {
        val p = power.toDouble()
        val v = voltage.toDouble()
        return if (p > 0 && v > 0) p / v else 0.0
    }

    // Шаблонная функция для расчета с cos (1 фаза)
    fun <T : Number> calculateAmperage(power: T, voltage: T, cos: T): Double {
        var c = cos.toDouble()
        if (c !in -1.0..1.0 || c == 0.0) c = 1.0 // Замена ошибочного значения
        val base = calculateAmperage(power, voltage)
        return base / c
    }

    // Шаблонная функция для многофазного расчета
    fun <T : Number> calculateAmperage(
        power: T,
        voltage: T,
        cos: T,
        phaseCount: Int
    ): Double {
        var c = cos.toDouble()
        if (c !in -1.0..1.0 || c == 0.0) c = 1.0
        val base = calculateAmperage(power, voltage, c.toBigDecimal().toDouble())

        val result = when (phaseCount) {
            1 -> base
            2, 3 -> base / sqrt(3.0)
            else -> 0.0
        }
        return result
    }
}