package com.amp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.MaterialType
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialTypeDao : BaseDAO<MaterialType> {

    @Query("SELECT * FROM material_type")
    fun getAll(): List<MaterialType>

    @Query("SELECT * FROM material_type")
    fun getAllFlow(): Flow<List<MaterialType>>

    @Query("SELECT * FROM material_type ORDER BY name ASC")
    fun getAlphabetized(): Flow<List<MaterialType>>

    @Query("DELETE FROM material_type")
    suspend fun deleteAll()

    @Query("""
        INSERT INTO material_type (id, name) VALUES
        (1, 'Cu'),
        (2, 'Al')
    """)
    suspend fun insertDefaultValues()
}