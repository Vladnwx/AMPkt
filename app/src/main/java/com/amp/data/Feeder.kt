package com.amp.data

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.UUID

// Это Класс для описанния фидера питающего нагрузку

class Feeder {

    val id = UUID.randomUUID()!!
        get() = field

    var name: String = ""
        get() = field
        set(value) {
            field = value
        }
    var start= UUID.randomUUID()!!
        get() = field
        set(value) {
            field = value
        }
    var finish= UUID.randomUUID()!!
        get() = field
        set(value) {
            field = value
        }
    var countPhase : Int =1
        get() = field
        set(value) {
            field = value
        }

    var nominalSize: Double
        get() = field
        set(value) {
            field = value
        }
    var typeOfEnvironment: String
        get() = field
        set(value) {
            field = value
        }
    var typeAmperage: String
        get() = field
        set(value) {
            field = value
        }
    var numberOfCore: String
        get() = field
        set(value) {
            field = value
        }
    var methodOfLaying: String
        get() = field
        set(value) {
            field = value
        }
    var materialType: String
        get() = field
        set(value) {
            field = value
        }
    var insulationType: String
        get() = field
        set(value) {
            field = value
        }
    var r: Double
        get() = field
        set(value) {
            field = value
        }
    var x: Double
        get() = field
        set(value) {
            field = value
        }
    var amperageShort: Double
        get() = field
        set(value) {
            field = value
        }
    var amperage: Double
        get() = field
        set(value) {
            field = value
        }
    var countJil: Int
        get() = field
        set(value) {
            field = value
        }
    var parallelCableCount: Int
        get() = field
        set(value) {
            field = value
        }
    var cableTypeText: String
        get() = field
        set(value) {
            field = value
        }
    var cableText: String
        get() = field
        set(value) {
            field = value
        }

    constructor(){
        nominalSize = 1.5
        typeOfEnvironment = "air"
        typeAmperage = "AC"
        numberOfCore = "multicore3"
        methodOfLaying = "single laying"
        materialType = "Cu"
        insulationType = "PVC"
        r = 0.0
        x = 0.0
        amperageShort = 0.0
        amperage = 1.0
        countJil = 1
        parallelCableCount =1
        cableTypeText = "ВВГнг(А)-LS"
        cableText = "3x1.5"
    }

    fun refresh () {
        if (countJil==1) {

            if (nominalSize < 4) {
                cableText = (countPhase + 2).toString() + "x" + nominalSize.toString()
            } else cableText = (countPhase + 2).toString() + "x" + nominalSize.toInt().toString()
        }
        else { if (nominalSize < 4) {
            cableText = countJil.toString() +  "x" +  (countPhase + 2).toString() + nominalSize.toString()
        } else cableText = countJil.toString() +  "x" + (countPhase + 2).toString() + "x" + nominalSize.toInt().toString()

        }

    }


}