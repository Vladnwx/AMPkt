package com.amp.data

// Это Класс для описанния фидера питающего нагрузку

class Feeder {

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

}