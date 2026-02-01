// com.amp.data.AppRepository.kt
package com.amp.data

import com.amp.data.dao.*
import com.amp.data.entity.*
import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val typeOfEnvironmentDao: TypeOfEnvironmentDao,
    private val typeAmperageDao: TypeAmperageDao,
    private val resistivityDao: ResistivityDao,
    private val numberOfCoreDao: NumberOfCoreDao,
    private val nominalSizeDao: NominalSizeDao,
    private val methodOfLayingDao: MethodOfLayingDao,
    private val materialTypeDao: MaterialTypeDao,
    private val insulationTypeDao: InsulationTypeDao,
    private val amperageShortDao: AmperageShortDao,
    private val amperageDao: AmperageDao
) {

    // === Потоки данных для UI (справочники) ===
    val allTypeOfEnvironments: Flow<List<TypeOfEnvironment>> = typeOfEnvironmentDao.getAllFlow()
    val allTypeAmperage: Flow<List<TypeAmperage>> = typeAmperageDao.getAllFlow()
    val allNumberOfCore: Flow<List<NumberOfCore>> = numberOfCoreDao.getAllFlow()
    val allNominalSizes: Flow<List<NominalSize>> = nominalSizeDao.getAllFlow()
    val allMethodOfLaying: Flow<List<MethodOfLaying>> = methodOfLayingDao.getAllFlow()
    val allMaterialType: Flow<List<MaterialType>> = materialTypeDao.getAllFlow()
    val allInsulationType: Flow<List<InsulationType>> = insulationTypeDao.getAllFlow()

    // === Методы расчёта ===

    suspend fun getR(materialType: String, nominalSize: Double): Double {
        return resistivityDao.getR(materialType, nominalSize)
    }

    suspend fun getX(materialType: String, nominalSize: Double): Double {
        return resistivityDao.getX(materialType, nominalSize)
    }

    suspend fun getAmperageShort(
        materialType: String,
        nominalSize: Double,
        insulationType: String
    ): Double {
        return amperageShortDao.getAmperageShort(materialType, nominalSize, insulationType)
    }

    suspend fun getAmperage(
        methodOfLaying: String,
        nominalSize: Double,
        materialType: String,
        insulationType: String,
        typeAmperage: String,
        numberOfCore: String,
        typeOfEnvironment: String
    ): Double {
        return amperageDao.getAmperage(
            methodOfLaying,
            nominalSize,
            materialType,
            insulationType,
            typeAmperage,
            numberOfCore,
            typeOfEnvironment
        )
    }

    suspend fun getNominalSize(
        amperageCalculate: Double,
        methodOfLaying: String = "Одиночная прокладка",
        materialType: String = "Cu",
        insulationType: String = "PVC",
        typeAmperage: String = "AC",
        numberOfCore: String = "multicore3",
        typeOfEnvironment: String = "В воздухе"
    ): Double {
        return amperageDao.getNominalSize(
            amperageCalculate,
            methodOfLaying,
            materialType,
            insulationType,
            typeAmperage,
            numberOfCore,
            typeOfEnvironment
        )
    }

    suspend fun getAllNominalSizeList(): List<Double> {
        return nominalSizeDao.getAllListDouble()
    }

    // === Вспомогательные методы (опционально) ===

    suspend fun insert(typeOfEnvironment: TypeOfEnvironment) {
        typeOfEnvironmentDao.insert(typeOfEnvironment)
    }

    suspend fun insert(nominalSize: NominalSize) {
        nominalSizeDao.insert(nominalSize)
    }

    suspend fun insert(resistivity: Resistivity) {
        resistivityDao.insert(resistivity)
    }
    suspend fun updateAmperage(
        methodOfLaying: String,
        nominalSize: Double,
        materialType: String,
        insulationType: String,
        typeAmperage: String,
        numberOfCore: String,
        typeOfEnvironment: String,
        amperage: Double
    ) {
        amperageDao.updateAmperage(
            methodOfLaying = methodOfLaying,
            nominalSize = nominalSize,
            materialType = materialType,
            insulationType = insulationType,
            typeAmperage = typeAmperage,
            numberOfCore = numberOfCore,
            typeOfEnvironment = typeOfEnvironment,
            amperage = amperage
        )
    }
}