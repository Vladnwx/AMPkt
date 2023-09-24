package com.amp.data

import com.amp.calculation.Calculation
import java.util.UUID

// Это класс для описания параметров нагрузки

class ElectricalLoad {

    val id = UUID.randomUUID()
        get() = field

    var name: String
        get() = field
        set(value) {
            field = value
        }
    var p: Double
        get() = field
        set(value) {
         if (value is Double)
          field = value
            else {
             field = 1.0
             logOrError = "ELectricalLoad : P не является допустимым числом, знчение устновлено = 1"
         }
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