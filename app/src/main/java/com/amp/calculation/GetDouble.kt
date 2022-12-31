package com.amp.calculation

import android.util.Log

open class GetDouble  {

    private var value :Double =0.0
        get() {
            return field
        }

    public fun getValue (value_ :String) {
        try {
            value = value_.toDoubleOrNull()!!

        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
        }
    }

    public fun getValue (value_ :Int) {
        try {
            value = value_.toDouble()

        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
        }
    }

    public fun getValue (value_ :Double) {
        try {
            value = value_

        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
        }
    }

    public fun getValue (value_ :Float) {
        try {
            value = value_.toDouble()

        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
        }
    }
    public fun getValue (value_ :Long) {
        try {
            value = value_.toDouble()

        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
        }
    }

    override fun toString(): String {
       return String.format("%.2f", value)
    }

}