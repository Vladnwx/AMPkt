package com.amp.data

import android.util.Log
import androidx.lifecycle.*
import com.amp.calculation.Calculation
import com.amp.data.entity.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivityViewModel(private val repository: AppRepository) : ViewModel() {

    init {
        Log.i("MainActivityViewModel", "MainActivityViewModel created!")
    }

    val electricalLoad : ElectricalLoad = ElectricalLoad()

    var feeder : Feeder = Feeder()

    var nominalSizeListSformirovan = false

    var nominalSizeAmperageMapSformirovan = false

    val allNominalSize: LiveData<List<NominalSize>> = repository.allNominalSizes.asLiveData()

    var allNominalSizeList: ArrayList<Double> = arrayListOf(0.0)

    var nominalSizeAmperageMap = mutableMapOf(0.5 to 0.0)

    val allTypeOfEnvironment: LiveData<List<TypeOfEnvironment>> = repository.allTypeOfEnvironments.asLiveData()

    val allTypeAmperage: LiveData<List<TypeAmperage>> = repository.allTypeAmperage.asLiveData()

        val allNumberOfCore: LiveData<List<NumberOfCore>> = repository.allNumberOfCore.asLiveData()


    val allMethodOfLaying: LiveData<List<MethodOfLaying>> = repository.allMethodOfLaying.asLiveData()


    val allMaterialType: LiveData<List<MaterialType>> = repository.allMaterialType.asLiveData()

    val allInsulationType: LiveData<List<InsulationType>> = repository.allInsulationType.asLiveData()

    lateinit var RLiveData: LiveData<Double>

     lateinit var XLiveData: LiveData<Double>

    var pLiveData: MutableLiveData<Double> = MutableLiveData(1.0)

    var countPhaseList: List<String> = listOf("1","2", "3")



       private fun getNominalSizeFromAmperage()  {
           feeder.parallelCableCount = 1
           feeder.nominalSize = nominalSizeAmperageMap.keys.firstOrNull()!!
           var i: Int =0
        var gain: Int = 1
           var size = nominalSizeAmperageMap.keys.size
        var parallelCableCounttemp = 1.0
        var amperageTemp :Double = 1.0
           var maxAmperageTemp : Double = 1.0
           maxAmperageTemp = nominalSizeAmperageMap.maxOfOrNull { it.value }!!

           if (electricalLoad.amperageCalculate>maxAmperageTemp && amperageTemp>0 && maxAmperageTemp>0) {
              var parallelCableCounttemp = (electricalLoad.amperageCalculate/maxAmperageTemp)
               while (parallelCableCounttemp.roundToInt()>feeder.parallelCableCount) { feeder.parallelCableCount++}
               var nominalSizeAmperageMapTemp = nominalSizeAmperageMap
               nominalSizeAmperageMapTemp.forEach {nominalSizeAmperageMapTemp.put(it.key, it.value*feeder.parallelCableCount )}

               feeder.nominalSize = nominalSizeAmperageMap.values.firstOrNull()!!

               for (it in nominalSizeAmperageMapTemp) {
                   if (it.value > electricalLoad.amperageCalculate) {feeder.amperage = it.value
                       feeder.nominalSize = it.key
                       break}
                   else continue
               }
           }
           else if (electricalLoad.amperageCalculate<maxAmperageTemp) {
               feeder.parallelCableCount = 1
               feeder.nominalSize = nominalSizeAmperageMap.values.firstOrNull()!!

                      for (it in nominalSizeAmperageMap) {
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
             allNominalSizeList.forEach { i ->
                 var temp = repository.getAmperage(
                     "single laying",
                     i,
                     "Cu",
                     "PVC",
                     "AC",
                     "multicore3",
                     "air"
                 )
                 nominalSizeAmperageMap.put(i, temp)
             }
             nominalSizeAmperageMapSformirovan = true
         }
     }






    fun getAmperageShort() = viewModelScope.launch {
        feeder.amperageShort = repository.getAmperageShort(feeder.materialType, feeder.nominalSize, feeder.insulationType)
    }


     fun calculate () {
         if (!nominalSizeAmperageMapSformirovan){getAmperage(easy = true)}
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
