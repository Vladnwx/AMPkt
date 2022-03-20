package com.amp.data

import android.util.Log
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    init {
        Log.i("MainActivityViewModel", "MainActivityViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MainActivityViewModel", "MainActivityViewModel destroyed!")
    }
}