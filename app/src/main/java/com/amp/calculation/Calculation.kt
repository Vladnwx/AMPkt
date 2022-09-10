package com.amp.calculation

import android.icu.math.BigDecimal
import android.util.Log
import kotlin.math.roundToLong

class Calculation {

    private fun amperageFormula(power: Double, voltage: Double, countPhase: Double, cosf: Double) : Double {
val temp :Double
         when(countPhase) {
            1.0 -> temp = power * 1000 / (voltage * cosf)
            2.0 -> temp = power * 1000 / (voltage * 1.73 * cosf)
            3.0 -> temp = power * 1000 / (voltage * 1.73 * cosf)
            else -> temp =0.0
        }
        return temp
    }

    private fun amperageFormula(power: Double, countPhase: Double, cosf: Double) : Double {

        return when(countPhase) {
            1.0 -> power * 1000 / (220 *  cosf)
            2.0 -> power * 1000 / (380 * 1.73 * cosf)
            3.0 -> power  * 1000/ (380 * 1.73 * cosf)
            else -> 0.0
        }
    }

    private fun amperageFormula(power: Double, countPhase: Double) : Double {

        return when(countPhase) {
            1.0 -> power * 1000 / (220 * countPhase)
            2.0 -> power * 1000 / (380 * 1.73)
            3.0 -> power * 1000 / (380 * 1.73)
            else -> 0.0
        }
    }

    private fun amperageFormula(power: Double) : Double {
        return   power * 1000 / (220)
    }

    fun amperage (power: String, voltage: String, countPhase: String, cosf: String) :String {
 var temp:String
 var temp1:String

        return try {

            temp1 = amperageFormula(power.toDouble(), voltage.toDouble(), countPhase.toDouble(), cosf.toDouble()).toString()
            temp = String.format("%.2f", amperageFormula(power.toDouble(), voltage.toDouble(), countPhase.toDouble(), cosf.toDouble()))
            return temp
         } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
            "Error"
        }
    }
    fun amperage (power: Double, countPhase: Double, cosf: Double) :String {

        return try {
            return String.format("%.2f",amperageFormula(power, countPhase, cosf))
        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
            "Error"
        }
    }

    fun amperage (power: Double, countPhase: Double) :String {

        return try {
            return String.format("%.2f",amperageFormula(power, countPhase))
        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
            "Error"
        }
    }

    fun amperage (power: Double) :String {

        return try {
            return String.format("%.2f",amperageFormula(power))
        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
            "Error"
        }
    }

    fun amperage (power: String, countPhase: String, cosf: String) :String {

        return try {
            return String.format("%.2f",amperageFormula(power.toDouble(), countPhase.toDouble(), cosf.toDouble()))
        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
            "Error"
        }
    }

    fun amperage (power: String, countPhase: String) :String {

        return try {
            return String.format("%.2f",amperageFormula(power.toDouble(), countPhase.toDouble()))
        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
            "Error"
        }
    }

    fun amperage (power: String) :String {

        return try {
            return String.format("%.2f",amperageFormula(power.toDouble()))
        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
            "Error"
        }
    }

    }