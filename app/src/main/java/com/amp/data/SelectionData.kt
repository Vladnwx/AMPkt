package com.amp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SelectionData {
    var nominalSizeListSformirovan = false
        get() = field
        set(value) {
            field = value
        }

    var nominalSizeAmperageMapSformirovan = false
        get() = field
        set(value) {
            field = value
        }

    var allNominalSizeList: ArrayList<Double> = arrayListOf(0.0)
        get() = field
        set(value) {
            field = value
            Log.d("SelectionData", "allNominalSizeList updated: $value") // DEBUG
        }

    var nominalSizeAmperageMap: MutableMap<Double, Double> = mutableMapOf(0.5 to 0.0)

    lateinit var RLiveData: LiveData<Double>
    lateinit var XLiveData: LiveData<Double>

    var pLiveData: MutableLiveData<Double> = MutableLiveData(1.0)

    var countPhaseList: List<String> = listOf("1", "2", "3")
        get() = field
        set(value) {
            field = value
        }

    init {
        Log.d("SelectionData", "SelectionData initialized with list: $allNominalSizeList") // DEBUG
    }
}