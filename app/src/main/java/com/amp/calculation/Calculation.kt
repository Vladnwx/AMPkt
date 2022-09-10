package com.amp.calculation

import android.icu.math.BigDecimal
import android.util.Log
import kotlin.math.roundToLong

class Calculation {

    private fun amperageFormula(power: Double, voltage: Double, countPhase: Double, cosf: Double) : Double {

        return when(countPhase) {
            1.0 -> (power / (voltage * countPhase * cosf * 1000))
            2.0 -> power / (voltage * 1.73 * cosf * 1000)
            3.0 -> power / (voltage * 1.73 * cosf * 1000)
            else -> 0.0
        }
    }

    private fun amperageFormula(power: Double, countPhase: Double, cosf: Double) : Double {

        return when(countPhase) {
            1.0 -> power / (220 * countPhase * cosf * 1000)
            2.0 -> power / (380 * 1.73 * cosf * 1000)
            3.0 -> power / (380 * 1.73 * cosf * 1000)
            else -> 0.0
        }
    }

    private fun amperageFormula(power: Double, countPhase: Double) : Double {

        return when(countPhase) {
            1.0 -> power / (220 * countPhase  * 1000)
            2.0 -> power / (380 * 1.73  * 1000)
            3.0 -> power / (380 * 1.73  * 1000)
            else -> 0.0
        }
    }

    private fun amperageFormula(power: Double) : Double {
        return   power / (220 * 1000)
    }

    fun amperage (power: String, voltage: String, countPhase: String, cosf: String) :String {

        return try {
            return String.format("%.2f",amperageFormula(power.toDouble(), voltage.toDouble(), countPhase.toDouble(), cosf.toDouble()))
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