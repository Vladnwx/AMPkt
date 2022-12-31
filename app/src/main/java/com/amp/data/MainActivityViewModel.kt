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

    var nominalSizeListSformirovan = false

    var nominalSizeAmperageMapSformirovan = false

    var p: String = "1.0"

    var v: String = "220.0"

    var cos: String = "1.0"

    var countPhase: String = "1.0"

    var amperageCalculate: Double = 1.0

    val allNominalSize: LiveData<List<NominalSize>> = repository.allNominalSizes.asLiveData()

    var nominalSize: Double = 4.0

    var allNominalSizeList: ArrayList<Double> = arrayListOf(0.0)

    var nominalSizeAmperageMap = mutableMapOf(0.5 to 0.0)


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

    var r: String = "0.0"

    lateinit var RLiveData: LiveData<Double>

    var x: String = "0.0"

    lateinit var XLiveData: LiveData<Double>

    var amperageShort: String = "0.0"

    var amperage: Double = 1.0

    var pLiveData: MutableLiveData<Double> = MutableLiveData(1.0)

    var countPhaseList: List<String> = listOf("1","2", "3")

    var countJil: Int = 1

    var parallelCableCount: Int =1

    var cableText: String = "3x1.5"


       private fun getNominalSizeFromAmperage()  {
        parallelCableCount = 1
           nominalSize = nominalSizeAmperageMap.keys.firstOrNull()!!
           var i: Int =0
        var gain: Int = 1
           var size = nominalSizeAmperageMap.keys.size
        var parallelCableCounttemp = 1.0
        var amperageTemp :Double = 1.0
           var maxAmperageTemp : Double = 1.0
           maxAmperageTemp = nominalSizeAmperageMap.maxOfOrNull { it.value }!!

           if (amperageCalculate>maxAmperageTemp && amperageTemp>0 && maxAmperageTemp>0) {
              var parallelCableCounttemp = (amperageCalculate/maxAmperageTemp)
               while (parallelCableCounttemp.roundToInt()>parallelCableCount) { parallelCableCount++}
               var nominalSizeAmperageMapTemp = nominalSizeAmperageMap
               nominalSizeAmperageMapTemp.forEach {nominalSizeAmperageMapTemp.put(it.key, it.value*parallelCableCount )}

               nominalSize = nominalSizeAmperageMap.values.firstOrNull()!!

               for (it in nominalSizeAmperageMapTemp) {
                   if (it.value > amperageCalculate) {amperage = it.value
                       nominalSize = it.key
                       break}
                   else continue
               }
           }
           else if (amperageCalculate<maxAmperageTemp) {
               parallelCableCount = 1
               nominalSize = nominalSizeAmperageMap.values.firstOrNull()!!

                      for (it in nominalSizeAmperageMap) {
                          if (it.value > amperageCalculate) {amperage = it.value
                              nominalSize = it.key
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
        r = repository.getR(materialType, nominalSize).toString()
        // RLiveData = repository.getRLiveData(materialType, nominalSize)
    }

    fun getX(materialType: String, nominalSize: Double) = viewModelScope.launch {
        x = repository.getX(materialType, nominalSize).toString()
    }
    fun getAmperage() = viewModelScope.launch {
        amperage = repository.getAmperage(methodOfLaying, nominalSize, materialType, insulationType, typeAmperage, numberOfCore, typeOfEnvironment)
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
        amperageShort = repository.getAmperageShort(materialType, nominalSize.toDouble(), insulationType).toString()
    }


     fun calculate () {
         if (!nominalSizeAmperageMapSformirovan){getAmperage(easy = true)}
        amperageCalculate = Calculation().amperage(power = p, voltage = v, countPhase= countPhase, cosf = cos)
        //getAmperage()
        //getAmperageShort()
        if (amperageCalculate>= amperage) {getNominalSizeFromAmperage()}
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
