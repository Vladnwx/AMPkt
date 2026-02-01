// com.amp.AmperageApplication.kt
package com.amp

import android.app.Application
import com.amp.data.AppDatabase
import com.amp.data.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class AmperageApplication : Application() {

    // Scope для фоновых задач приложения
    val applicationScope = CoroutineScope(SupervisorJob())

    // База данных — создаётся один раз
    val database by lazy {
        AppDatabase.getDatabase(this, applicationScope)
    }

    // Репозиторий — зависит только от DAO, не от вставки
    val repository by lazy {
        AppRepository(
            typeOfEnvironmentDao = database.typeOfEnvironmentDao(),
            typeAmperageDao = database.typeAmperageDao(),
            resistivityDao = database.resistivityDao(),
            numberOfCoreDao = database.numberOfCoreDao(),
            nominalSizeDao = database.nominalSizeDao(),
            methodOfLayingDao = database.methodOfLayingDao(),
            materialTypeDao = database.materialTypeDao(),
            insulationTypeDao = database.insulationTypeDao(),
            amperageShortDao = database.amperageShortDao(),
            amperageDao = database.amperageDao()
        )
    }
}