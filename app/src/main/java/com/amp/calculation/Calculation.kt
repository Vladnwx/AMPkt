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

    fun amperage (power: String, voltage: String, countPhase: String, cosf: String) :Double {
 //var temp:Double
 //var temp1:String

        return try {

            //temp1 = amperageFormula(power.toDouble(), voltage.toDouble(), countPhase.toDouble(), cosf.toDouble()).toString()
            val temp =
                amperageFormula(
                    power.toDouble(),
                    voltage.toDouble(),
                    countPhase.toDouble(),
                    cosf.toDouble()
                )
            temp
        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
            0.0
        }
    }

    fun amperage (power: Double, voltage: Double, countPhase: Double, cosf: Double) :Double {
        //var temp:Double
        //var temp1:String

        return try {

            //temp1 = amperageFormula(power.toDouble(), voltage.toDouble(), countPhase.toDouble(), cosf.toDouble()).toString()
            val temp =
                amperageFormula(
                    power,
                    voltage,
                    countPhase,
                    cosf
                )
            temp
        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
            0.0
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
     fun cableText (countPhase: Double, parallelCableCount : Int, nominalSize: Double) : String {
        val temp :String
        if (nominalSize<4){
            when(parallelCableCount) {
                1 -> temp = countJil(countPhase).toString() + "x" + nominalSize.toString()
                else -> temp = parallelCableCount.toString() + "x" + countJil(countPhase).toString() + "x" + nominalSize.toString()
            }

            return temp
        }
        else if (nominalSize<=50.0){
            when(parallelCableCount) {
                1 -> temp = countJil(countPhase).toString() + "x" + String.format("%.0f", nominalSize)
                else -> temp = parallelCableCount.toString() + "x" + countJil(countPhase).toString() + "x" + String.format("%.0f", nominalSize)
            }

            return temp
        }
        else
        {
            when(parallelCableCount) {
                1 -> temp = countJil(countPhase).toString() + "x" + "1" + "x" + String.format("%.0f", nominalSize)
                else -> temp = parallelCableCount.toString() + "x" + countJil(countPhase).toString() + "x" + "1" + "x"+ String.format("%.0f", nominalSize)
            }

            return temp
        }

        }
    }







