package com.amp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.amp.data.dao.TypeOfEnvironmentDao
import com.amp.data.entity.TypeOfEnvironment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities =
                    arrayOf(TypeOfEnvironment::class),
                    version = 1,
                    exportSchema = false)

public abstract class AppDatabase: RoomDatabase() {

    abstract fun typeOfEnvironmentDao():TypeOfEnvironmentDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.typeOfEnvironmentDao())
                }
            }
        }

        suspend fun populateDatabase(typeOfEnvironmentDao: TypeOfEnvironmentDao) {
            // Delete all content here.
            typeOfEnvironmentDao.deleteAll()
            typeOfEnvironmentDao.defaultGreate()
            Log.i(" typeOfEnvironmentDao", "defaultGreate!")



        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Amperage"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}