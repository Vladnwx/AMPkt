package com.amp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.NominalSize
import kotlinx.coroutines.flow.Flow

@Dao
interface NominalSizeDao : BaseDAO<NominalSize> {

    @Query("SELECT * FROM nominal_size")
    fun getAll(): List<NominalSize>

    @Query("SELECT * FROM nominal_size")
    fun getAllFlow(): Flow<List<NominalSize>>

    @Query("SELECT size_mm2 FROM nominal_size")
    fun getAllListDouble(): List<Double>

    @Query("SELECT * FROM nominal_size ORDER BY size_mm2 ASC")
    fun getAlphabetized(): Flow<List<NominalSize>>

    @Query("DELETE FROM nominal_size")
    suspend fun deleteAll()

    @Query("""
        INSERT INTO nominal_size (id, size_mm2) VALUES
        (1, 0.5),
        (2, 0.75),
        (3, 1.5),
        (4, 2.5),
        (5, 4.0),
        (6, 6.0),
        (7, 10.0),
        (8, 16.0),
        (9, 25.0),
        (10, 35.0),
        (11, 50.0),
        (12, 70.0),
        (13, 95.0),
        (14, 120.0),
        (15, 150.0),
        (16, 185.0),
        (17, 240.0),
        (18, 300.0),
        (19, 400.0),
        (20, 500.0),
        (21, 630.0),
        (22, 800.0),
        (23, 1000.0)
    """)
    suspend fun insertDefaultValues()
}