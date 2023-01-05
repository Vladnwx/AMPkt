package com.amp.data

import com.amp.calculation.Calculation

// Это класс для описания параметров нагрузки

class ElectricalLoad {

    var p: Double
        get() = field
        set(value) {
            field = value
        }
    var v: Double
        get() = field
        set(value) {
            field = value
        }
    var cos: Double
        get() = field
        set(value) {
            field = value
        }
    var countPhase: Double
        get() = field
        set(value) {
            field = value
        }
    var lineLength: Double
        get() = field
        set(value) {
            field = value
        }
    var amperageCalculate: Double
        get() = field
        set(value) {
            field = value
        }

    enum class TypeLoad {Single, MultiLoad}
    var typeLoad : TypeLoad
        get() = field
        set(value) {
            field = value
        }

    constructor()
    {
        p = 1.0
        v = 220.0
        cos = 1.0
        countPhase = 1.0
        lineLength = 1.0
        amperageCalculate = p/(v*cos*countPhase)
        typeLoad = TypeLoad.Single
    }
}