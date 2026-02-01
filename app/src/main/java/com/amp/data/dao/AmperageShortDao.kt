package com.amp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.AmperageShort
import kotlinx.coroutines.flow.Flow

@Dao
interface AmperageShortDao : BaseDAO<AmperageShort> {

    @Query("SELECT * FROM amperage_short")
    fun getAllFlow(): Flow<List<AmperageShort>>

    @Query("SELECT * FROM amperage_short ORDER BY id ASC")
    fun getAlphabetized(): Flow<List<AmperageShort>>

    // Поиск через JOIN по читаемым значениям
    @Query("""
        SELECT a.amperage_short_value
        FROM amperage_short a
        JOIN material_type mt ON a.material_type_id = mt.id
        JOIN nominal_size ns ON a.nominal_size_id = ns.id
        JOIN insulation_type it ON a.insulation_type_id = it.id
        WHERE mt.name = :materialName 
          AND ns.size_mm2 = :nominalSize 
          AND it.name = :insulationName
    """)
    fun getAmperageShort(
        materialName: String,
        nominalSize: Double,
        insulationName: String
    ): Double

    @Query("DELETE FROM amperage_short")
    suspend fun deleteAll()

    // Вставка значений с использованием ID
    // Основано на:
    // - material_type: 1=Cu, 2=Al
    // - insulation_type: 1=PVC, 2=XLPE
    // - nominal_size: 1=0.5, 2=0.75, 3=1.5, ..., 23=1000.0
    @Query("""
        INSERT INTO amperage_short (
            amperage_short_value,
            insulation_type_id,
            material_type_id,
            nominal_size_id
        ) VALUES
        -- PVC, Cu
        (0.17, 1, 1, 3),
        (0.27, 1, 1, 4),
        (0.43, 1, 1, 5),
        (0.65, 1, 1, 6),
        (1.09, 1, 1, 7),
        (1.74, 1, 1, 8),
        (2.78, 1, 1, 9),
        (3.86, 1, 1, 10),
        (5.23, 1, 1, 11),
        (7.54, 1, 1, 12),
        (10.48, 1, 1, 13),
        (13.21, 1, 1, 14),
        (16.3, 1, 1, 15),
        (20.39, 1, 1, 16),
        (26.8, 1, 1, 17),
        (33.49, 1, 1, 18),
        (39.6, 1, 1, 19),
        (49.5, 1, 1, 20),
        (62.37, 1, 1, 21),
        (79.2, 1, 1, 22),   -- исправлено: было 7920 → 79.2
        (99.0, 1, 1, 23),
        -- PVC, Al
        (0.0, 1, 2, 3),
        (0.18, 1, 2, 4),
        (0.29, 1, 2, 5),
        (0.42, 1, 2, 6),
        (0.7, 1, 2, 7),
        (1.13, 1, 2, 8),
        (1.81, 1, 2, 9),
        (2.5, 1, 2, 10),
        (3.38, 1, 2, 11),
        (4.95, 1, 2, 12),
        (6.86, 1, 2, 13),
        (8.66, 1, 2, 14),
        (10.64, 1, 2, 15),
        (13.37, 1, 2, 16),
        (17.54, 1, 2, 17),
        (21.9, 1, 2, 18),
        (26.0, 1, 2, 19),
        (32.5, 1, 2, 20),
        (40.95, 1, 2, 21),
        (52.0, 1, 2, 22),
        (65.0, 1, 2, 23),
        -- XLPE, Cu
        (0.21, 2, 1, 3),
        (0.34, 2, 1, 4),
        (0.54, 2, 1, 5),
        (0.81, 2, 1, 6),
        (1.36, 2, 1, 7),
        (2.16, 2, 1, 8),
        (3.46, 2, 1, 9),
        (4.8, 2, 1, 10),
        (6.5, 2, 1, 11),
        (9.38, 2, 1, 12),
        (13.03, 2, 1, 13),
        (16.43, 2, 1, 14),
        (20.26, 2, 1, 15),
        (25.35, 2, 1, 16),
        (33.32, 2, 1, 17),
        (41.64, 2, 1, 18),
        (55.2, 2, 1, 19),
        (69.0, 2, 1, 20),
        (86.95, 2, 1, 21),
        (110.4, 2, 1, 22),
        (138.0, 2, 1, 23),
        -- XLPE, Al
        (0.0, 2, 2, 3),
        (0.22, 2, 2, 4),
        (0.36, 2, 2, 5),
        (0.52, 2, 2, 6),
        (0.87, 2, 2, 7),
        (1.4, 2, 2, 8),
        (2.24, 2, 2, 9),
        (3.09, 2, 2, 10),
        (4.18, 2, 2, 11),
        (6.12, 2, 2, 12),
        (8.48, 2, 2, 13),
        (10.71, 2, 2, 14),
        (13.16, 2, 2, 15),
        (16.53, 2, 2, 16),
        (21.7, 2, 2, 17),
        (27.12, 2, 2, 18),
        (36.16, 2, 2, 19),
        (45.2, 2, 2, 20),
        (56.95, 2, 2, 21),
        (72.33, 2, 2, 22),
        (90.4, 2, 2, 23)
    """)
    suspend fun insertDefaultValues()
}