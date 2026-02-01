package com.amp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.NumberOfCore
import kotlinx.coroutines.flow.Flow

@Dao
interface NumberOfCoreDao : BaseDAO<NumberOfCore> {

    @Query("SELECT * FROM number_of_core")
    fun getAll(): List<NumberOfCore>

    @Query("SELECT * FROM number_of_core")
    fun getAllFlow(): Flow<List<NumberOfCore>>

    @Query("SELECT * FROM number_of_core ORDER BY name ASC")
    fun getAlphabetized(): Flow<List<NumberOfCore>>

    @Query("DELETE FROM number_of_core")
    suspend fun deleteAll()

    @Query("""
        INSERT INTO number_of_core (id, name) VALUES
        (1, '1'),
        (2, '2'),
        (3, '3'),
        (4, '3+1'),
        (5, '4'),
        (6, '5')
    """)
    suspend fun insertDefaultValues()
}