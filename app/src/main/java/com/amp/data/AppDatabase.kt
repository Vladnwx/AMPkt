// com.amp.data.AppDatabase.kt
package com.amp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.amp.data.dao.*
import com.amp.data.dao.insertamperage.*
import com.amp.data.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [
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
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // Справочники
    abstract fun typeOfEnvironmentDao(): TypeOfEnvironmentDao
    abstract fun typeAmperageDao(): TypeAmperageDao
    abstract fun numberOfCoreDao(): NumberOfCoreDao
    abstract fun nominalSizeDao(): NominalSizeDao
    abstract fun methodOfLayingDao(): MethodOfLayingDao
    abstract fun materialTypeDao(): MaterialTypeDao
    abstract fun insulationTypeDao(): InsulationTypeDao

    // Основные таблицы
    abstract fun resistivityDao(): ResistivityDao
    abstract fun amperageShortDao(): AmperageShortDao
    abstract fun amperageDao(): AmperageDao

    // Новые DAO для вставки Amperage
    abstract fun insertCopperPvcDao(): InsertCopperPvcDao
    abstract fun insertCopperXlpeDao(): InsertCopperXlpeDao
    abstract fun insertAluminumPvcDao(): InsertAluminumPvcDao
    abstract fun insertAluminumXlpeDao(): InsertAluminumXlpeDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    // 1. Заполняем справочники
                    populateReferenceTables(database)

                    // 2. Заполняем основные таблицы (зависят от справочников)
                    populateResistivityAndShort(database)

                    // 3. Заполняем Amperage (самая большая таблица)
                    populateAmperageData(database)
                }
            }
        }

        private suspend fun populateReferenceTables(db: AppDatabase) {
            db.typeOfEnvironmentDao().apply {
                deleteAll()
                insertDefaultValues()
                Log.i("DB", "TypeOfEnvironment filled")
            }
            db.typeAmperageDao().apply {
                deleteAll()
                insertDefaultValues()
                Log.i("DB", "TypeAmperage filled")
            }
            db.numberOfCoreDao().apply {
                deleteAll()
                insertDefaultValues()
                Log.i("DB", "NumberOfCore filled")
            }
            db.nominalSizeDao().apply {
                deleteAll()
                insertDefaultValues()
                Log.i("DB", "NominalSize filled")
            }
            db.methodOfLayingDao().apply {
                deleteAll()
                insertDefaultValues()
                Log.i("DB", "MethodOfLaying filled")
            }
            db.materialTypeDao().apply {
                deleteAll()
                insertDefaultValues()
                Log.i("DB", "MaterialType filled")
            }
            db.insulationTypeDao().apply {
                deleteAll()
                insertDefaultValues()
                Log.i("DB", "InsulationType filled")
            }
        }

        private suspend fun populateResistivityAndShort(db: AppDatabase) {
            db.resistivityDao().apply {
                deleteAll()
                insertDefaultValues()
                Log.i("DB", "Resistivity filled")
            }
            db.amperageShortDao().apply {
                deleteAll()
                insertDefaultValues()
                Log.i("DB", "AmperageShort filled")
            }
        }

        private suspend fun populateAmperageData(db: AppDatabase) {
            db.amperageDao().deleteAll()
            db.insertCopperPvcDao().insertCopperPvcData()
            db.insertCopperXlpeDao().insertCopperXlpeData()
            db.insertAluminumPvcDao().insertAluminumPvcData()
            db.insertAluminumXlpeDao().insertAluminumXlpeData()
            Log.i("DB", "Amperage fully filled with 630 records")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Amperage"
                )
                    .allowMainThreadQueries() // только для разработки! В продакшене убрать
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}