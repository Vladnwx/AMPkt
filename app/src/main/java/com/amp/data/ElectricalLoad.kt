package com.amp.data

import com.amp.calculation.Calculation
import java.util.UUID

// Это класс для описания параметров нагрузки

class ElectricalLoad {

    val id = UUID.randomUUID()
        get() = field

    var name: String = ""
        get() = field
        set(value) {
            field = value
        }
    var p: Double = 0.0
        get() = field
        set(value) {
            field = value
        }
    var v: Double = 0.0
        get() = field
        set(value) {
            field = value
        }
    var cos: Double = 0.0
        get() = field
        set(value) {
            field = value
        }
    var countPhase: Double = 0.0
        get() = field
        set(value) {
            field = value
        }
    var lineLength: Double = 0.0
        get() = field
        set(value) {
            field = value
        }
    var amperageCalculate: Double = 0.0
        get() = field
        set(value) {
            field = value
        }

    enum class TypeLoad {Single, MultiLoad}
    var typeLoad : TypeLoad = TODO()
        get() = field
        set(value) {
            field = value
        }

    enum class WindingConnectionDiagram { Star, Triangle, Zigzag}
    var windingConnectionDiagram : WindingConnectionDiagram
        get() = field
        set(value) {
            field = value
        }

    var automaticSelectionOfStandardVoltage :Boolean

    var considerСos :Boolean

    var logOrError : String =""

    constructor()
    {
        //id = 1
        name = "UnknownLoad"
        p = 1.0
        v = 220.0
        cos = 1.0
        countPhase = 1.0
        lineLength = 1.0
        this.amperageCalculate = p*1000/(v*cos*countPhase)
        typeLoad = TypeLoad.Single
        windingConnectionDiagram = WindingConnectionDiagram.Star
        automaticSelectionOfStandardVoltage = true
        considerСos = false
        logOrError  ="ELectricalLoad OK"

    }
}