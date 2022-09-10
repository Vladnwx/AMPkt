package com.amp.calculation

import android.util.Log

class RequestRepo {

    fun getNominalSize (current: String) : String {
        return try {
            return ""
        } catch (e: Exception) {
            println("Exception")
            println(e.message)
            Log.i("Exception", e.message.toString())
            "Error"
        }
    }
}