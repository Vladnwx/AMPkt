package com.amp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.Resistivity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResistivityDao : BaseDAO<Resistivity> {

    @Query("SELECT * FROM resistivity")
    fun getAllFlow(): Flow<List<Resistivity>>

    // Используем JOIN для поиска по имени материала и значению сечения
    @Query("""
        SELECT r.R 
        FROM resistivity r
        JOIN material_type mt ON r.material_type_id = mt.id
        JOIN nominal_size ns ON r.nominal_size_id = ns.id
        WHERE mt.name = :materialName AND ns.size_mm2 = :nominalSize
    """)
    fun getR(materialName: String, nominalSize: Double): Double

    @Query("""
        SELECT r.R 
        FROM resistivity r
        JOIN material_type mt ON r.material_type_id = mt.id
        JOIN nominal_size ns ON r.nominal_size_id = ns.id
        WHERE mt.name = :materialName AND ns.size_mm2 = :nominalSize
    """)
    fun getLiveDataR(materialName: String, nominalSize: Double): LiveData<Double>

    @Query("""
        SELECT r.X 
        FROM resistivity r
        JOIN material_type mt ON r.material_type_id = mt.id
        JOIN nominal_size ns ON r.nominal_size_id = ns.id
        WHERE mt.name = :materialName AND ns.size_mm2 = :nominalSize
    """)
    fun getX(materialName: String, nominalSize: Double): Double

    @Query("""
        SELECT r.X 
        FROM resistivity r
        JOIN material_type mt ON r.material_type_id = mt.id
        JOIN nominal_size ns ON r.nominal_size_id = ns.id
        WHERE mt.name = :materialName AND ns.size_mm2 = :nominalSize
    """)
    fun getLiveDataX(materialName: String, nominalSize: Double): LiveData<Double>

    @Query("SELECT * FROM resistivity ORDER BY id ASC")
    fun getAlphabetized(): LiveData<List<Resistivity>>

    @Query("DELETE FROM resistivity")
    suspend fun deleteAll()

    // Вставка с использованием ID из справочников
    // Предполагается, что:
    // - Cu -> material_type.id = 1
    // - Al -> material_type.id = 2
    // - nominal_size.id соответствует порядку из NominalSizeDao
    @Query("""
        INSERT INTO resistivity (id, material_type_id, nominal_size_id, r, x, p) VALUES
        (1, 1, 23, 0.0189, 0.06, 0.0189),
        (2, 1, 22, 0.023625, 0.06, 0.0189),
        (3, 1, 21, 0.03, 0.06, 0.0189),
        (4, 1, 20, 0.0378, 0.06, 0.0189),
        (5, 1, 19, 0.04725, 0.06, 0.0189),
        (6, 1, 18, 0.063, 0.06, 0.0189),
        (7, 1, 17, 0.07875, 0.06, 0.0189),
        (8, 1, 16, 0.10216216216216, 0.06, 0.0189),
        (9, 1, 15, 0.126, 0.06, 0.0189),
        (10, 1, 14, 0.1575, 0.06, 0.0189),
        (11, 1, 13, 0.19894736842105, 0.06, 0.0189),
        (12, 1, 12, 0.27, 0.06, 0.0189),
        (13, 1, 11, 0.378, 0.06, 0.0189),
        (14, 1, 10, 0.54, 0.06, 0.0189),
        (15, 1, 9, 0.756, 0.06, 0.0189),
        (16, 1, 8, 1.18125, 0.06, 0.0189),
        (17, 1, 7, 1.89, 0.06, 0.0189),
        (18, 1, 6, 3.15, 0.06, 0.0189),
        (19, 1, 5, 4.725, 0.06, 0.0189),
        (20, 1, 4, 7.56, 0.06, 0.0189),
        (21, 1, 3, 12.6, 0.06, 0.0189),
        (22, 1, 2, 25.2, 0.06, 0.0189),
        (23, 1, 1, 37.8, 0.06, 0.0189),
        (24, 2, 23, 0.0315, 0.06, 0.0315),
        (25, 2, 22, 0.039375, 0.06, 0.0315),
        (26, 2, 21, 0.05, 0.06, 0.0315),
        (27, 2, 20, 0.063, 0.06, 0.0315),
        (28, 2, 19, 0.07875, 0.06, 0.0315),
        (29, 2, 18, 0.105, 0.06, 0.0315),
        (30, 2, 17, 0.13125, 0.06, 0.0315),
        (31, 2, 16, 0.17027027027027, 0.06, 0.0315),
        (32, 2, 15, 0.21, 0.06, 0.0315),
        (33, 2, 14, 0.2625, 0.06, 0.0315),
        (34, 2, 13, 0.33157894736842, 0.06, 0.0315),
        (35, 2, 12, 0.45, 0.06, 0.0315),
        (36, 2, 11, 0.63, 0.06, 0.0315),
        (37, 2, 10, 0.9, 0.06, 0.0315),
        (38, 2, 9, 1.26, 0.06, 0.0315),
        (39, 2, 8, 1.96875, 0.06, 0.0315),
        (40, 2, 7, 3.15, 0.06, 0.0315),
        (41, 2, 6, 5.25, 0.06, 0.0315),
        (42, 2, 5, 7.875, 0.06, 0.0315),
        (43, 2, 4, 12.6, 0.06, 0.0315),
        (44, 2, 3, 21, 0.06, 0.0315),
        (45, 2, 2, 42, 0.06, 0.0315),
        (46, 2, 1, 63, 0.06, 0.0315)
    """)
    suspend fun insertDefaultValues()
}