package com.amp

import android.app.Application
import com.amp.data.AppDatabase
import com.amp.data.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class AmperageApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { AppRepository(database.typeOfEnvironmentDao(), database.nominalSizeDao()) }
}