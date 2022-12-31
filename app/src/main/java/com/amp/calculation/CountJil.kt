package com.amp.calculation

class CountJil:GetDouble() {

    fun countJil ( countPhase: Double) : Int {
        val temp :Int
        when(countPhase) {
            1.0 -> temp = 3
            2.0 -> temp = 5
            3.0 -> temp = 5
            else -> temp = 2
        }
        return temp

    }
    }