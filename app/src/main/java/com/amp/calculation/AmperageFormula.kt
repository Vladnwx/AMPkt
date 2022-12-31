package com.amp.calculation

import android.util.Log

class AmperageFormula{

    private var amperage :Double = 0.0
        get() = field
    private var power : Double = 0.0
        set(value) {
            field = value
        }
    private var voltage : Double = 1.0
        set(value) {
            field = value
        }
    private var cosF : Double = 1.0
        set(value) {
            field = value
        }
    private var countPhase : Double = 1.0
        set(value) {
            field = value
        }

    private fun calcOnePhase() {
        if (voltage>0 && cosF >0) {
            amperage = power * 1000 / (voltage * cosF)
        }
        else{println("Exception")}
    }

    private fun calcThreePhase() {
        calcOnePhase()
        amperage /= 1.73
    }


    public fun calculationOnePhase (power_: Double, voltage_: Double) {
        try {
            if (power_ >0 && voltage_ >0) {
                power = power_
                voltage = voltage_}
            calcOnePhase()
        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
        }
    }
    public fun calculationOnePhase (power_: Double, voltage_: Double, cosF_ :Double) {
        try {
            if (cosF_ !=0.0 && cosF_ <1 && cosF_ >-1.0) {
                cosF = cosF_
                calculationOnePhase(power_, voltage_)}
        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
        }
    }

    public fun calculationThreePhase (power_: Double, voltage_: Double, countPhase_ :Double, cosF_ :Double) {
        try {
            if (countPhase_ >0) {
                calculationOnePhase(power_, voltage_, cosF_)
            }
            calcThreePhase()
        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
        }
    }



}