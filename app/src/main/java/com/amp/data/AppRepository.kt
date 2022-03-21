package com.amp.data

import androidx.annotation.WorkerThread
import com.amp.data.dao.TypeOfEnvironmentDao
import com.amp.data.entity.TypeOfEnvironment
import kotlinx.coroutines.flow.Flow

public class AppRepository (private val typeOfEnvironmentDao: TypeOfEnvironmentDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
   //val allTypeOfEnvironments: List<String?>? = typeOfEnvironmentDao.getAll()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(typeOfEnvironment: TypeOfEnvironment) {
        typeOfEnvironmentDao.insert(typeOfEnvironment)
    }
}