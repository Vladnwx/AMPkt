package com.amp.data

import androidx.lifecycle.*
import com.amp.data.entity.TypeOfEnvironment
import kotlinx.coroutines.launch

class AmperageViewModel (private val repository: AppRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allTypeOfEnvironments: LiveData<List<TypeOfEnvironment>> = repository.allTypeOfEnvironments.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(typeOfEnvironment: TypeOfEnvironment) = viewModelScope.launch {
        repository.insert(typeOfEnvironment)
    }

}

class TypeOfEnvironmentViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AmperageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AmperageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}