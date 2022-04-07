package com.amp.data

import androidx.annotation.WorkerThread
import com.amp.data.dao.NominalSizeDao
import com.amp.data.dao.TypeOfEnvironmentDao
import com.amp.data.entity.NominalSize
import com.amp.data.entity.TypeOfEnvironment
import kotlinx.coroutines.flow.Flow

public class AppRepository (private val typeOfEnvironmentDao: TypeOfEnvironmentDao,
                            private val nominalSizeDao: NominalSizeDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allTypeOfEnvironments: Flow<List<TypeOfEnvironment>> = typeOfEnvironmentDao.getAllFlow()
    val allNominalsizes: Flow<List<NominalSize>> = nominalSizeDao.getAllFlow()


    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(typeOfEnvironment: TypeOfEnvironment) {
        typeOfEnvironmentDao.insert(typeOfEnvironment)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert( nominalSize: NominalSize) {
        nominalSizeDao.insert(nominalSize)
    }

}