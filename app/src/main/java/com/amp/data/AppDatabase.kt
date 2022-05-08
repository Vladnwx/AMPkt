package com.amp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.amp.data.dao.*
import com.amp.data.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities =
                    arrayOf(
                        TypeOfEnvironment::class,
                        TypeAmperage::class,
                        Resistivity::class,
                        NumberOfCore::class,
                        NominalSize::class,
                        MethodOfLaying::class,
                        MaterialType::class,
                        InsulationType::class,
                        AmperageShort::class,
                        Amperage::class
                        ),
                    version = 1,
                    exportSchema = false)

public abstract class AppDatabase: RoomDatabase() {

    abstract fun typeOfEnvironmentDao():TypeOfEnvironmentDao
    abstract fun typeAmperageDao():TypeAmperageDao
    abstract fun resistivityDao():ResistivityDao
    abstract fun numberOfCoreDao():NumberOfCoreDao
    abstract fun nominalSizeDao():NominalSizeDao
    abstract fun methodOfLayingDao():MethodOfLayingDao
    abstract fun materialTypeDao():MaterialTypeDao
    abstract fun insulationTypeDao():InsulationTypeDao
    abstract fun amperageShortDao():AmperageShortDao
    abstract fun amperageDao():AmperageDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.typeOfEnvironmentDao())
                    populateDatabase(database.typeAmperageDao())
                    populateDatabase(database.resistivityDao())
                    populateDatabase(database.numberOfCoreDao())
                    populateDatabase(database.nominalSizeDao())
                    populateDatabase(database.methodOfLayingDao())
                    populateDatabase(database.materialTypeDao())
                    populateDatabase(database.insulationTypeDao())
                    populateDatabase(database.amperageShortDao())
                    populateDatabase(database.amperageDao())

                }
            }
        }

        suspend fun populateDatabase(typeOfEnvironmentDao: TypeOfEnvironmentDao) {
            typeOfEnvironmentDao.deleteAll()
            typeOfEnvironmentDao.defaultgreate()
            Log.i("typeOfEnvironmentDao", "defaultGreate!")
           // nominalSizeDao.deleteAll()
           // nominalSizeDao.defaultgreate()
           // Log.i("nominalSizeDao", "defaultGreate!")

        }
        suspend fun populateDatabase(typeAmperageDao: TypeAmperageDao) {
            typeAmperageDao.deleteAll()
            typeAmperageDao.defaultgreate()
            Log.i("typeAmperageDao", "defaultGreate!")
        }
        suspend fun populateDatabase(resistivityDao: ResistivityDao) {
            resistivityDao.deleteAll()
            resistivityDao.defaultgreate()
            Log.i("resistivityDao", "defaultGreate!")
        }
        suspend fun populateDatabase(numberOfCoreDao: NumberOfCoreDao) {
            numberOfCoreDao.deleteAll()
            numberOfCoreDao.defaultgreate()
            Log.i("numberOfCoreDao", "defaultGreate!")
        }

        suspend fun populateDatabase(nominalSizeDao: NominalSizeDao) {
            nominalSizeDao.deleteAll()
            nominalSizeDao.defaultgreate()
            Log.i("nominalSizeDao", "defaultGreate!")
        }

        suspend fun populateDatabase(methodOfLayingDao: MethodOfLayingDao) {
            methodOfLayingDao.deleteAll()
            methodOfLayingDao.defaultgreate()
            Log.i("methodOfLayingDao", "defaultGreate!")
        }
        suspend fun populateDatabase(materialTypeDao: MaterialTypeDao) {
            materialTypeDao.deleteAll()
            materialTypeDao.defaultgreate()
            Log.i("materialTypeDao", "defaultGreate!")
        }

        suspend fun populateDatabase(insulationTypeDao: InsulationTypeDao) {
            insulationTypeDao.deleteAll()
            insulationTypeDao.defaultgreate()
            Log.i("insulationTypeDao", "defaultGreate!")
        }

        suspend fun populateDatabase(amperageShortDao: AmperageShortDao) {
            amperageShortDao.deleteAll()
            amperageShortDao.defaultgreate()
            Log.i("amperageShortDao", "defaultGreate!")
        }

        suspend fun populateDatabase(amperageDao: AmperageDao) {
            amperageDao.deleteAll()
            amperageDao.defaultgreate1()
            amperageDao.defaultgreate2()
            amperageDao.defaultgreate3()
            amperageDao.defaultgreate4()
            amperageDao.defaultgreate5()
            Log.i("amperageDao", "defaultGreate!")
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
                    .allowMainThreadQueries()
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}