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

    var feeder : Feeder = Feeder()

    var selectionData : SelectionData = SelectionData()

    val allNominalSize: LiveData<List<NominalSize>> = repository.allNominalSizes.asLiveData()

       private fun getNominalSizeFromAmperage()  {
           feeder.parallelCableCount = 1
           feeder.nominalSize = selectionData.nominalSizeAmperageMap.keys.firstOrNull()!!
           var i: Int =0
        var gain: Int = 1
           var size = selectionData.nominalSizeAmperageMap.keys.size
        var parallelCableCounttemp = 1.0
        var amperageTemp :Double = 1.0
           var maxAmperageTemp : Double = 1.0
           maxAmperageTemp = selectionData.nominalSizeAmperageMap.maxOfOrNull { it.value }!!

           if (electricalLoad.amperageCalculate>maxAmperageTemp && amperageTemp>0 && maxAmperageTemp>0) {
              var parallelCableCounttemp = (electricalLoad.amperageCalculate/maxAmperageTemp)
               while (parallelCableCounttemp.roundToInt()>feeder.parallelCableCount) { feeder.parallelCableCount++}
               var nominalSizeAmperageMapTemp = selectionData.nominalSizeAmperageMap
               nominalSizeAmperageMapTemp.forEach {nominalSizeAmperageMapTemp.put(it.key, it.value*feeder.parallelCableCount )}

               feeder.nominalSize = selectionData.nominalSizeAmperageMap.values.firstOrNull()!!

               for (it in nominalSizeAmperageMapTemp) {
                   if (it.value > electricalLoad.amperageCalculate) {feeder.amperage = it.value
                       feeder.nominalSize = it.key
                       break}
                   else continue
               }
           }
           else if (electricalLoad.amperageCalculate<maxAmperageTemp) {
               feeder.parallelCableCount = 1
               feeder.nominalSize = selectionData.nominalSizeAmperageMap.values.firstOrNull()!!

                      for (it in selectionData.nominalSizeAmperageMap) {
                          if (it.value > electricalLoad.amperageCalculate) {feeder.amperage = it.value
                              feeder.nominalSize = it.key
                          break}
                          else continue
                      }
                                   }



       /* while (amperageCalculate>= amperageTemp) {
            if ((amperageCalculate>maxAmperageTemp) and (amperageTemp>0)){
                parallelCableCounttemp +=1
                    maxAmperageTemp *= parallelCableCounttemp
                    amperageTemp = nominalSizeAmperageMap.getValue(allNominalSizeList[i])*parallelCableCounttemp
                    continue
            }
            if (amperageCalculate>0){
            gain = (maxAmperageTemp/amperageCalculate).toInt()}

            if ((gain>0) and (gain<2)) {
                i=size/2
                continue
            }

            amperageTemp = nominalSizeAmperageMap.getValue(allNominalSizeList[i])*parallelCableCounttemp


            if ((i<(size)) and (amperageCalculate>amperageTemp))  {
                i++
                amperageTemp = nominalSizeAmperageMap.getValue(allNominalSizeList[i])*parallelCableCounttemp
                nominalSize = allNominalSizeList[i]
                amperage = amperageTemp
                parallelCableCount =parallelCableCounttemp
                continue}

            nominalSize = allNominalSizeList[i]
            amperage = amperageTemp
            parallelCableCount =parallelCableCounttemp
        }*/

    }

    fun getR(materialType: String, nominalSize: Double) = viewModelScope.launch {
        feeder.r = repository.getR(materialType, nominalSize)
        // RLiveData = repository.getRLiveData(materialType, nominalSize)
    }

    fun getX(materialType: String, nominalSize: Double) = viewModelScope.launch {
        feeder.x = repository.getX(materialType, nominalSize)
    }
    fun getAmperage() = viewModelScope.launch {
        feeder.amperage = repository.getAmperage(feeder.methodOfLaying, feeder.nominalSize, feeder.materialType, feeder.insulationType, feeder.typeAmperage, feeder.numberOfCore, feeder.typeOfEnvironment)
    }
     fun getAmperage(easy : Boolean) = viewModelScope.launch {
         if (easy) {
             selectionData.allNominalSizeList.forEach { i ->
                 var temp = repository.getAmperage(
                     "single laying",
                     i,
                     "Cu",
                     "PVC",
                     "AC",
                     "multicore3",
                     "air"
                 )
                 selectionData.nominalSizeAmperageMap.put(i, temp)
             }
             selectionData.nominalSizeAmperageMapSformirovan = true
         }
     }

    fun getAmperageShort() = viewModelScope.launch {
        feeder.amperageShort = repository.getAmperageShort(feeder.materialType, feeder.nominalSize, feeder.insulationType)
    }


     fun calculate () {
         if (!selectionData.nominalSizeAmperageMapSformirovan){getAmperage(easy = true)}
         electricalLoad.amperageCalculate = Calculation().amperage(power = electricalLoad.p, voltage = electricalLoad.v, countPhase= electricalLoad.countPhase, cosf = electricalLoad.cos)
        //getAmperage()
        //getAmperageShort()
        if (electricalLoad.amperageCalculate>= feeder.amperage) {getNominalSizeFromAmperage()}
        getR(feeder.materialType, feeder.nominalSize.toDouble())
        getX(feeder.materialType, feeder.nominalSize.toDouble())
         feeder.countJil = Calculation().countJil(electricalLoad.countPhase.toDouble())
         feeder.cableText =Calculation().cableText(electricalLoad.countPhase.toDouble(), feeder.parallelCableCount, feeder.nominalSize)

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
