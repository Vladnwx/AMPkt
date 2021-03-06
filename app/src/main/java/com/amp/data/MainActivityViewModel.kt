package com.amp.data

import android.util.Log
import androidx.lifecycle.*
import com.amp.data.entity.*
import kotlinx.coroutines.launch
import java.lang.Math.pow
import kotlin.math.pow

class MainActivityViewModel(private val repository: AppRepository) : ViewModel() {


    init {
        Log.i("MainActivityViewModel", "MainActivityViewModel created!")
    }

    var cable : String  = ""

    val allTypeOfEnvironment: LiveData<List<TypeOfEnvironment>> = repository.allTypeOfEnvironments.asLiveData()

    var typeOfEnvironment : String  = "air"

    val allTypeAmperage: LiveData<List<TypeAmperage>> = repository.allTypeAmperage.asLiveData()

    var typeAmperage : String  = "AC"

    val allNumberOfCore: LiveData<List<NumberOfCore>> = repository.allNumberOfCore.asLiveData()

    var numberOfCore : String  = "multicore3"

    val allNominalSize: LiveData<List<NominalSize>> = repository.allNominalSizes.asLiveData()

    var nominalSize : Double  = 1.5

    val allNominalSizeList : ArrayList<String> = arrayListOf("0")

    val allMethodOfLaying: LiveData<List<MethodOfLaying>> = repository.allMethodOfLaying.asLiveData()

    var methodOfLaying : String  = "single laying"

    val allMaterialType: LiveData<List<MaterialType>> = repository.allMaterialType.asLiveData()

    var materialType : String  = "Cu"

    val allInsulationType: LiveData<List<InsulationType>> = repository.allInsulationType.asLiveData()

    var insulationType : String  = "PVC"

    var R:Double = 0.0

    var X:Double = 0.0

    var amperageShort:Double = 0.0

    var amperage:Double = 0.0

    var pLiveData : MutableLiveData<Double> = MutableLiveData(1.0)

    var p: Double = 1.0
         set(value) {
            field = try {
                p.toDouble()
                when {
                    value < 0 -> 1.0
                    else -> value}
            } catch (e: Exception) {
                println("Exception")
                println(e.message)
                Log.i("Exception", e.message.toString())
                1.0
            }
            return
        }

     var v:Double = 220.0
         set(value) {
            field = when {
                value < 1 -> 1.0
                else -> value
            }
        }

     var cos:Double = 1.0
         set(value) {
            field = when {
                value > 1 -> 1.0
                value < -1 -> -1.0
                value ==0.0  -> 1.0
                else -> value
            }
        }

     var amperageCalculate :Double =0.0



    var countPhaseList: List<String> = listOf("1","2", "3")

    var countPhase : Int = 1

    var countJil : Int = countPhase +2


    override fun onCleared() {
        super.onCleared()
        Log.i("MainActivityViewModel", "MainActivityViewModel destroyed!")
    }




    fun insert(typeOfEnvironment: TypeOfEnvironment) = viewModelScope.launch {
        repository.insert(typeOfEnvironment)
         }

    fun insert(nominalSize: NominalSize) = viewModelScope.launch {
        repository.insert(nominalSize)
          }

    fun getR() = viewModelScope.launch {
        R = repository.getR(materialType, nominalSize)

    }

    fun getX() = viewModelScope.launch {
        X = repository.getX(materialType, nominalSize)
    }

    fun getAmperageShort() = viewModelScope.launch {
        amperageShort = repository.getAmperageShort(materialType, nominalSize, insulationType)

    }

    fun getAmperage() = viewModelScope.launch {
        amperage = repository.getAmperage(methodOfLaying, nominalSize, materialType, insulationType, typeAmperage, numberOfCore, typeOfEnvironment)

    }

    fun chekCountPhase() {
        when (countPhase)
        {
            1 -> v= 220.0
            2 -> v= 380.0
            3 -> v= 380.0
        }
        when (countPhase)
        {
            1 -> countJil= 3
            2 -> countJil= 4
            3 -> countJil= 5
        }
        cable = countJil.toString() + "x" + nominalSize.toString()
    }

    fun calculate() {
        chekCountPhase()
        when (countPhase)
        {
            1 -> amperageCalculate = (p*1000/(v*cos))
            2 -> amperageCalculate = (p*1000/ (v * cos * 1.73))
            3 -> amperageCalculate = (p*1000/ (v * cos * 1.73))
        }
        getAmperage()
        chekAmperage()

    }

    fun chekAmperage ()
    {
        var i : Int =0

        while ( amperage < amperageCalculate  )
        {
            i++
            nominalSize = allNominalSizeList[i].toDouble()
            getAmperage()

        }
        getR()
        getX()
        getAmperageShort()
        cable = countJil.toString() + "x" + nominalSize.toString()

    }

}

private fun AppRepository.insert(typeOfEnvironment: TypeOfEnvironment) {

}

class MainActivityViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}