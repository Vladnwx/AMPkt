package com.amp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.TypeAmperage
import kotlinx.coroutines.flow.Flow

@Dao
interface TypeAmperageDao : BaseDAO<TypeAmperage> {

    @Query("SELECT * FROM type_amperage")
    fun getAll(): List<TypeAmperage>

    @Query("SELECT * FROM type_amperage")
    fun getAllFlow(): Flow<List<TypeAmperage>>

    @Query("SELECT * FROM type_amperage ORDER BY name ASC")
    fun getAlphabetized(): Flow<List<TypeAmperage>>

    @Query("DELETE FROM type_amperage")
    suspend fun deleteAll()

    @Query("""
        INSERT INTO type_amperage (id, name) VALUES
        (1, 'Переменный ток'),
        (2, 'Постоянный ток')
    """)
    suspend fun insertDefaultValues()
}