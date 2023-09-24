package com.amp.data

import android.util.Log
import androidx.lifecycle.*
import com.amp.RecyclerView.TableRowModel
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

    var allNominalSizeList: MutableList<Double> = mutableListOf(0.0)

    var mapNominalSizeAndAmperage : MutableMap<Double, Double> = mutableMapOf()

    fun fillNominalSizeListFromRepo () = viewModelScope.launch {
        allNominalSizeList = repository.getAllNominalSizeList()
            }
    fun fillmapNominalSizeAndAmperageFromRepo () = viewModelScope.launch {
        allNominalSizeList.forEach(){
            mapNominalSizeAndAmperage[it] = repository.getAmperage(feeder.methodOfLaying, it, feeder.materialType, feeder.insulationType, feeder.typeAmperage, feeder.numberOfCore, feeder.typeOfEnvironment)*feeder.countJil
            mapNominalSizeAndAmperage.values.removeIf{it < 1.0}
        }
    }
    fun searchAmperage () {

        allNominalSizeList.clear()
        mapNominalSizeAndAmperage.clear()
        feeder.countPhase = electricalLoad.countPhase.roundToInt()
        fillNominalSizeListFromRepo()
        fillmapNominalSizeAndAmperageFromRepo()
        mapNominalSizeAndAmperage.values.removeIf{it < electricalLoad.amperageCalculate}
        allNominalSizeList = mapNominalSizeAndAmperage.keys.toMutableList()

        if (allNominalSizeList.isNotEmpty()){
        feeder.nominalSize = allNominalSizeList[0]
       }
        else { feeder.countJil++
            searchAmperage ()}

        //    feeder.nominalSize = 0.0
        getDataFeederFromRepo()
        feeder.refresh()
    }

    fun getDataFeederFromRepo () = viewModelScope.launch {

        feeder.r = repository.getR(feeder)
        feeder.x = repository.getX(feeder)
        feeder.amperageShort = repository.getAmperageShort(feeder)
        feeder.amperage = repository.getAmperage(feeder)*feeder.countJil

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
