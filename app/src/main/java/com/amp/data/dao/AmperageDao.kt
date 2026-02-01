// com.amp.data.dao.AmperageDao.kt
package com.amp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.Amperage
import kotlinx.coroutines.flow.Flow

@Dao
interface AmperageDao : BaseDAO<Amperage> {

    @Query("SELECT * FROM amperage")
    fun getAllFlow(): Flow<List<Amperage>>

    @Query("DELETE FROM amperage")
    suspend fun deleteAll()

    // Подбор минимального сечения по заданному току и условиям
    @Query("""
        SELECT ns.size_mm2
        FROM amperage a
        JOIN nominal_size ns ON a.nominal_size_id = ns.id
        JOIN method_of_laying ml ON a.method_of_laying_id = ml.id
        JOIN material_type mt ON a.material_type_id = mt.id
        JOIN insulation_type it ON a.insulation_type_id = it.id
        JOIN type_amperage ta ON a.type_amperage_id = ta.id
        JOIN number_of_core nc ON a.number_of_core_id = nc.id
        JOIN type_of_environment te ON a.type_of_environment_id = te.id
        WHERE a.amperage_value >= :amperageCalculate
          AND ml.name = :methodOfLaying
          AND mt.name = :materialType
          AND it.name = :insulationType
          AND ta.name = :typeAmperage
          AND nc.name = :numberOfCore
          AND te.name = :typeOfEnvironment
        ORDER BY ns.size_mm2 ASC
        LIMIT 1
    """)
    suspend fun getNominalSize(
        amperageCalculate: Double,
        methodOfLaying: String = "Одиночная прокладка",
        materialType: String = "Cu",
        insulationType: String = "PVC",
        typeAmperage: String = "AC",
        numberOfCore: String = "multicore3",
        typeOfEnvironment: String = "В воздухе"
    ): Double

    // Получение допустимого тока по полным параметрам
    @Query("""
        SELECT a.amperage_value
        FROM amperage a
        JOIN method_of_laying ml ON a.method_of_laying_id = ml.id
        JOIN nominal_size ns ON a.nominal_size_id = ns.id
        JOIN material_type mt ON a.material_type_id = mt.id
        JOIN insulation_type it ON a.insulation_type_id = it.id
        JOIN type_amperage ta ON a.type_amperage_id = ta.id
        JOIN number_of_core nc ON a.number_of_core_id = nc.id
        JOIN type_of_environment te ON a.type_of_environment_id = te.id
        WHERE ml.name = :methodOfLaying
          AND ns.size_mm2 = :nominalSize
          AND mt.name = :materialType
          AND it.name = :insulationType
          AND ta.name = :typeAmperage
          AND nc.name = :numberOfCore
          AND te.name = :typeOfEnvironment
    """)
    fun getAmperage(
        methodOfLaying: String,
        nominalSize: Double,
        materialType: String,
        insulationType: String,
        typeAmperage: String,
        numberOfCore: String,
        typeOfEnvironment: String
    ): Double

    @Query("""
        UPDATE amperage
        SET amperage_value = :amperage
        WHERE method_of_laying_id = (
            SELECT id FROM method_of_laying WHERE name = :methodOfLaying
        )
        AND nominal_size_id = (
            SELECT id FROM nominal_size WHERE size_mm2 = :nominalSize
        )
        AND material_type_id = (
            SELECT id FROM material_type WHERE name = :materialType
        )
        AND insulation_type_id = (
            SELECT id FROM insulation_type WHERE name = :insulationType
        )
        AND type_amperage_id = (
            SELECT id FROM type_amperage WHERE name = :typeAmperage
        )
        AND number_of_core_id = (
            SELECT id FROM number_of_core WHERE name = :numberOfCore
        )
        AND type_of_environment_id = (
            SELECT id FROM type_of_environment WHERE name = :typeOfEnvironment
        )
    """)
    suspend fun updateAmperage(
        methodOfLaying: String,
        nominalSize: Double,
        materialType: String,
        insulationType: String,
        typeAmperage: String,
        numberOfCore: String,
        typeOfEnvironment: String,
        amperage: Double
    )

}