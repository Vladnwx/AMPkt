package com.amp.data

class ElectricalLoad {

    var p: String
        get() {
            return p
        }
        set(value) {}
    var v: String
        get() {
            return v
        }
        set(value) {}
    var cos: String
        get() {
            return cos
        }
        set(value) {}
    var countPhase: String
        get() {
            return countPhase
        }
        set(value) {}
    var lineLength: String
        get() {
            return lineLength
        }
        set(value) {}

    enum class TypeLoad {Single, MultiLoad}
    var typeLoad : TypeLoad = TypeLoad.Single

    constructor()
    {
        p = "1.0"
        v = "220.0"
        cos = "1.0"
        countPhase = "1.0"
        lineLength = "1.0"
    }
}