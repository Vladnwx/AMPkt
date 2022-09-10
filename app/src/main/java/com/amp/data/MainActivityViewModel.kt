package com.amp.data

import android.util.Log
import androidx.lifecycle.*
import com.amp.R
import com.amp.calculation.Calculation
import com.amp.data.entity.*
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: AppRepository) : ViewModel() {

    init {
        Log.i("MainActivityViewModel", "MainActivityViewModel created!")
    }

    var p: String = "1.0"

    var v: String = "220.0"

    var cos: String = "1.0"

    var countPhase: String = "1.0"

    var amperageCalculate: String = "300.0"

    val allNominalSize: LiveData<List<NominalSize>> = repository.allNominalSizes.asLiveData()

    var nominalSize: String = "1.5"

    val allNominalSizeList: ArrayList<String> = arrayListOf("0")




    val allTypeOfEnvironment: LiveData<List<TypeOfEnvironment>> =
        repository.allTypeOfEnvironments.asLiveData()

    var typeOfEnvironment: String = ""

    val allTypeAmperage: LiveData<List<TypeAmperage>> = repository.allTypeAmperage.asLiveData()

    var typeAmperage: String = ""

    val allNumberOfCore: LiveData<List<NumberOfCore>> = repository.allNumberOfCore.asLiveData()

    var numberOfCore: String = ""



    val allMethodOfLaying: LiveData<List<MethodOfLaying>> =
        repository.allMethodOfLaying.asLiveData()

    var methodOfLaying: String = ""

    val allMaterialType: LiveData<List<MaterialType>> = repository.allMaterialType.asLiveData()

    var materialType: String = "Cu"

    val allInsulationType: LiveData<List<InsulationType>> =
        repository.allInsulationType.asLiveData()

    var insulationType: String = ""

    var R: Double = 0.0

    lateinit var RLiveData: LiveData<Double>

    var X: Double = 0.0

    lateinit var XLiveData: LiveData<Double>

    var amperageShort: Double = 0.0

    var amperage: String = "0.0"

    var pLiveData: MutableLiveData<Double> = MutableLiveData(1.0)




    var countJil: String = try {
        (countPhase.toInt() + 2).toString()
    } catch (e: Exception) {
        println("Exception")
        println(e.message)
        Log.i("Exception", e.message.toString())
        "3"
    }

    var countPhaseList: List<String> = listOf("1","2", "3")




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

    fun getNominalSize(amperageCalculate: String) = viewModelScope.launch {
        nominalSize = repository.getNominalSize(amperageCalculate.toDouble())
    }


    fun getR(materialType: String, nominalSize: Double) = viewModelScope.launch {
        R = repository.getR(materialType, nominalSize)
       // RLiveData = repository.getRLiveData(materialType, nominalSize)
    }

    fun getX(materialType: String, nominalSize: Double) = viewModelScope.launch {
        X = repository.getX(materialType, nominalSize)
    }
    fun calculate () {
        amperageCalculate = Calculation().amperage(power = p, voltage = v, countPhase= countPhase, cosf = cos)
       // getNominalSize(amperageCalculate)
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