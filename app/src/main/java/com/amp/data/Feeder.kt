// com.amp.data.Feeder.kt
package com.amp.data

data class Feeder(
    var name: String = "",
    var countPhase: Int = 1,
    var nominalSize: Double = 1.5,
    var typeOfEnvironment: String = "В воздухе",
    var typeAmperage: String = "AC",
    var numberOfCore: String = "multicore3",
    var methodOfLaying: String = "Одиночная прокладка",
    var materialType: String = "Cu",
    var insulationType: String = "PVC",
    var r: Double = 0.0,
    var x: Double = 0.0,
    var amperageShort: Double = 0.0,
    var amperage: Double = 1.0,
    var countJil: Int = 1,
    var parallelCableCount: Int = 1,
    var cableTypeText: String = "ВВГнг(А)-LS"
) {
    val cableText: String
        get() = buildCableText()

    private fun buildCableText(): String {
        val sizeStr = if (nominalSize < 4) nominalSize.toString() else nominalSize.toInt().toString()
        return if (countJil == 1) {
            "${countPhase + 2}x$sizeStr"
        } else {
            "$countJil x ${countPhase + 2}x$sizeStr"
        }
    }

    fun isValid(): Boolean {
        return countJil >= 1 && nominalSize > 0 && countPhase in 1..3
    }
}