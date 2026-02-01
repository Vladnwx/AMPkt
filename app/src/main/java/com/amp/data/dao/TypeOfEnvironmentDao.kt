package com.amp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.TypeOfEnvironment
import kotlinx.coroutines.flow.Flow

@Dao
interface TypeOfEnvironmentDao : BaseDAO<TypeOfEnvironment> {

    @Query("SELECT * FROM type_of_environment")
    fun getAll(): List<TypeOfEnvironment>

    @Query("SELECT * FROM type_of_environment")
    fun getAllFlow(): Flow<List<TypeOfEnvironment>>

    @Query("SELECT * FROM type_of_environment ORDER BY name ASC")
    fun getAlphabetized(): Flow<List<TypeOfEnvironment>>

    @Query("DELETE FROM type_of_environment")
    suspend fun deleteAll()

    @Query("""
        INSERT INTO type_of_environment (id, name) VALUES
        (1, 'В земле'),
        (2, 'В воздухе')
    """)
    suspend fun insertDefaultValues()
}