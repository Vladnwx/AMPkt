package com.amp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.InsulationType
import kotlinx.coroutines.flow.Flow

@Dao
interface InsulationTypeDao : BaseDAO<InsulationType> {

    @Query("SELECT * FROM insulation_type")
    fun getAll(): List<InsulationType>

    @Query("SELECT * FROM insulation_type")
    fun getAllFlow(): Flow<List<InsulationType>>

    @Query("SELECT * FROM insulation_type ORDER BY name ASC")
    fun getAlphabetized(): Flow<List<InsulationType>>

    @Query("DELETE FROM insulation_type")
    suspend fun deleteAll()

    @Query("""
        INSERT INTO insulation_type (id, name) VALUES
        (1, 'PVC'),
        (2, 'XLPE')
    """)
    suspend fun insertDefaultValues()
}