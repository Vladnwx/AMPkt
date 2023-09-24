package com.amp.calculation

import kotlin.math.sqrt

 class AmperageFormula {

    fun get (power :Double, voltage :Double) : Double{
        if (power>0 && voltage>0){
            return power/voltage
        }
        else  return 0.0
    }

    fun get (power :Double, voltage :Double, cos :Double) : Double{
        if (cos<=1 && cos>=-1 && cos !=0.0){
            return get(power, voltage)/cos
        }
        else  return 1.0
    }

    fun get (power :Double, voltage :Double, cos :Double, countPhase: Double) : Double{
        when (countPhase){
            1.0 -> return get(power, voltage, cos)
            2.0 -> return get(power, voltage, cos)/(sqrt(3.0))
            3.0 -> return get(power, voltage, cos)/(sqrt(3.0))
            else -> return 0.0
        }
            }





}