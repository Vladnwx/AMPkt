// com.amp.data.ElectricalLoad.kt
package com.amp.data

data class ElectricalLoad(
    val name: String = "UnknownLoad",
    val activePowerKw: Double = 1.0,      // P, кВт
    val voltageV: Double = 220.0,         // U, В
    val powerFactor: Double = 1.0,        // cos φ (0.0 .. 1.0)
    val phaseCount: Int = 1,              // 1, 3
    val lineLengthM: Double = 1.0,        // длина линии, м
    val typeLoad: TypeLoad = TypeLoad.Single,
    val windingConnection: WindingConnectionDiagram = WindingConnectionDiagram.Star,
    val autoSelectVoltage: Boolean = true,
    val considerPowerFactor: Boolean = false
) {
    // Вычисляемый ток нагрузки (А)
    val calculatedAmperage: Double
        get() = if (phaseCount == 1) {
            activePowerKw * 1000 / (voltageV * (if (considerPowerFactor) powerFactor else 1.0))
        } else {
            activePowerKw * 1000 / (Math.sqrt(3.0) * voltageV * (if (considerPowerFactor) powerFactor else 1.0))
        }

    enum class TypeLoad {
        Single, MultiLoad
    }

    enum class WindingConnectionDiagram {
        Star, Triangle, Zigzag
    }

    // Опционально: валидация
    fun isValid(): Boolean {
        return activePowerKw > 0 &&
                voltageV > 0 &&
                powerFactor in 0.0..1.0 &&
                phaseCount in 1..3 &&
                lineLengthM > 0
    }
}