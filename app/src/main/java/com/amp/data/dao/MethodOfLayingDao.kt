package com.amp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.MethodOfLaying
import kotlinx.coroutines.flow.Flow

@Dao
interface MethodOfLayingDao : BaseDAO<MethodOfLaying> {

    @Query("SELECT * FROM method_of_laying")
    fun getAll(): List<MethodOfLaying>

    @Query("SELECT * FROM method_of_laying")
    fun getAllFlow(): Flow<List<MethodOfLaying>>

    @Query("SELECT * FROM method_of_laying ORDER BY name ASC")
    fun getAlphabetized(): Flow<List<MethodOfLaying>>

    @Query("DELETE FROM method_of_laying")
    suspend fun deleteAll()

    @Query("""
        INSERT INTO method_of_laying (id, name) VALUES
        (1, 'Одиночная прокладка'),
        (2, 'Прокладка в пучке')
    """)
    suspend fun insertDefaultValues()
}