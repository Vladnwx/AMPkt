package com.amp.data

import android.util.Log
import androidx.lifecycle.*
import com.amp.data.entity.TypeOfEnvironment
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: AppRepository) : ViewModel() {

    private var p: Double = 1.0
        get() = field
        set(value) {
            if(value. > 6) value else throw IllegalArgumentException("Passwords is too small")
            field = when {
            value < 1 -> 1.0
            else -> value
        }}


    private var v:Double = 220.0
        get() = field
        set(value) {
            field = when {
                value < 1 -> 1.0
                else -> value
            }
        }

    private var cos:Double = 1.0
        get() = field
        set(value) {
            field = when {
                value > 1 -> 1.0
                value < -1 -> -1.0
                else -> value
            }
        }


    private var amperage:Double = p/(1.73*v*cos)


    init {
        Log.i("MainActivityViewModel", "MainActivityViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MainActivityViewModel", "MainActivityViewModel destroyed!")
    }

    val allTypeOfEnvironment: LiveData<List<TypeOfEnvironment>> = repository.allTypeOfEnvironments.asLiveData()

    fun insert(typeOfEnvironment: TypeOfEnvironment) = viewModelScope.launch {
        repository.insert(typeOfEnvironment)
    }

    fun calculate (p:Int, u:Int, cos:Double): Double {
        return  (p/(u*cos*1.73))
    }

    fun calculate (p:String, u:String, cos:String): Double {
       try {
           return  (p.toInt()/(u.toInt()*cos.toDouble()*1.73))
       }
       catch (e: NumberFormatException){
            return 1.0
       }
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