package com.amp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

//Это класс для выбора данных

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
        }

    var nominalSizeAmperageMap : MutableMap<Double, Double>

    //abstract val allTypeOfEnvironment: LiveData<List<TypeOfEnvironment>>

    //abstract val allTypeAmperage: LiveData<List<TypeAmperage>>

    //abstract val allNumberOfCore: LiveData<List<NumberOfCore>>

    //abstract val allMethodOfLaying: LiveData<List<MethodOfLaying>>

    //abstract val allMaterialType: LiveData<List<MaterialType>>

    //abstract val allInsulationType: LiveData<List<InsulationType>>

    lateinit var RLiveData: LiveData<Double>

    lateinit var XLiveData: LiveData<Double>

    var pLiveData: MutableLiveData<Double> = MutableLiveData(1.0)

    var countPhaseList: List<String> = listOf("1","2", "3")
        get() = field
        set(value) {
            field = value
        }

    constructor(){
        nominalSizeAmperageMap = mutableMapOf(0.5 to 0.0)
    }

}