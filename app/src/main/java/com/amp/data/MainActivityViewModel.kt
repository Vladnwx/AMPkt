package com.amp.data

import android.util.Log
import androidx.lifecycle.*
import com.amp.calculation.Calculation
import com.amp.data.entity.*
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivityViewModel(private val repository: AppRepository) : ViewModel() {

    init {
        Log.i("MainActivityViewModel", "MainActivityViewModel created!")
    }

    val electricalLoad : ElectricalLoad = ElectricalLoad()

    val feeder : Feeder = Feeder()

    val selectionData : SelectionData = SelectionData()

    val calculation : Calculation = Calculation()


    val allNominalSize: LiveData<List<NominalSize>> = repository.allNominalSizes.asLiveData()

    fun getR(materialType: String, nominalSize: Double) = viewModelScope.launch {
        feeder.r = repository.getR(materialType, nominalSize)
        // RLiveData = repository.getRLiveData(materialType, nominalSize)
    }

    fun getX(materialType: String, nominalSize: Double) = viewModelScope.launch {
        feeder.x = repository.getX(materialType, nominalSize)
    }

    fun getAmperageShort() = viewModelScope.launch {
        feeder.amperageShort = repository.getAmperageShort(feeder.materialType, feeder.nominalSize, feeder.insulationType)
    }

    fun insert(typeOfEnvironment: TypeOfEnvironment) = viewModelScope.launch {
        repository.insert(typeOfEnvironment)
         }

    fun insert(nominalSize: NominalSize) = viewModelScope.launch {
        repository.insert(nominalSize)
          }

    override fun onCleared() {
        super.onCleared()
        Log.i("MainActivityViewModel", "MainActivityViewModel destroyed!")
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
