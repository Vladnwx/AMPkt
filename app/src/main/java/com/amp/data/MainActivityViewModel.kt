package com.amp.data

import android.util.Log
import androidx.lifecycle.*
import com.amp.calculation.Calculation
import com.amp.data.entity.*
import kotlinx.coroutines.launch
import java.lang.Double.parseDouble

class MainActivityViewModel(private val repository: AppRepository) : ViewModel() {

    init {
        Log.i("MainActivityViewModel", "MainActivityViewModel created!")
    }

    var p: String = "1.0"

    var v: String = "220.0"

    var cos: String = "1.0"

    var countPhase: String = "1.0"

    var amperageCalculate: Double = 0.0

    val allNominalSize: LiveData<List<NominalSize>> = repository.allNominalSizes.asLiveData()

    var nominalSize: Double = 4.0

    var allNominalSizeList: ArrayList<Double> = arrayListOf(0.0)




    val allTypeOfEnvironment: LiveData<List<TypeOfEnvironment>> = repository.allTypeOfEnvironments.asLiveData()

    var typeOfEnvironment: String = "air"

    val allTypeAmperage: LiveData<List<TypeAmperage>> = repository.allTypeAmperage.asLiveData()

    var typeAmperage: String = "AC"

    val allNumberOfCore: LiveData<List<NumberOfCore>> = repository.allNumberOfCore.asLiveData()

    var numberOfCore: String = "multicore3"

    val allMethodOfLaying: LiveData<List<MethodOfLaying>> = repository.allMethodOfLaying.asLiveData()

    var methodOfLaying: String = "single laying"

    val allMaterialType: LiveData<List<MaterialType>> = repository.allMaterialType.asLiveData()

    var materialType: String = "Cu"

    val allInsulationType: LiveData<List<InsulationType>> = repository.allInsulationType.asLiveData()

    var insulationType: String = "PVC"

    var R: String = "0.0"

    lateinit var RLiveData: LiveData<Double>

    var X: String = "0.0"

    lateinit var XLiveData: LiveData<Double>

    var amperageShort: String = "0.0"

    var amperage: Double = 0.0

    var pLiveData: MutableLiveData<Double> = MutableLiveData(1.0)

    var countPhaseList: List<String> = listOf("1","2", "3")

    var countJil: Int = 1

    var parallelCableCount: Int =1

    var cableText: String = "3x1.5"




    private fun getNominalSizeFromAmperage(){
        var i: Int =0
        parallelCableCount=1
        while (amperageCalculate>= amperage){
        nominalSize = allNominalSizeList[i]
            getAmperage()
            amperage = amperage*parallelCableCount
            if (i<(allNominalSizeList.size-1)) {
                i++
            }
            else {
                parallelCableCount +=1
                i /= parallelCableCount
                  }

        }

    }

    fun getR(materialType: String, nominalSize: Double) = viewModelScope.launch {
        R = repository.getR(materialType, nominalSize).toString()
        // RLiveData = repository.getRLiveData(materialType, nominalSize)
    }

    fun getX(materialType: String, nominalSize: Double) = viewModelScope.launch {
        X = repository.getX(materialType, nominalSize).toString()
    }
    fun getAmperage() = viewModelScope.launch {
        amperage = repository.getAmperage(methodOfLaying, nominalSize, materialType, insulationType, typeAmperage, numberOfCore, typeOfEnvironment)
    }
    fun getAmperageShort() = viewModelScope.launch {
        amperageShort = repository.getAmperageShort(materialType, nominalSize.toDouble(), insulationType).toString()
    }


    fun calculate () {
        amperageCalculate = Calculation().amperage(power = p, voltage = v, countPhase= countPhase, cosf = cos)
        getAmperage()
        getAmperageShort()
        getNominalSizeFromAmperage()
        getR(materialType, nominalSize.toDouble())
        getX(materialType, nominalSize.toDouble())
        countJil = Calculation().countJil(countPhase.toDouble())
        cableText =Calculation().cableText(countPhase.toDouble(), parallelCableCount, nominalSize)

    }

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