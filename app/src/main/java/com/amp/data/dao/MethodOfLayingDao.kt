package com.amp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.MethodOfLaying
import kotlinx.coroutines.flow.Flow


@Dao
interface MethodOfLayingDao:BaseDAO<MethodOfLaying>{

    @Query("SELECT * FROM MethodOfLaying")
    fun getAll(): List<String>

    @Query("SELECT * FROM MethodOfLaying")
    fun getAllFlow(): Flow<List<MethodOfLaying>>


    @Query("SELECT * FROM MethodOfLaying ORDER BY value ASC")
    fun getAlphabetized(): Flow<List<MethodOfLaying>>

    @Query("DELETE FROM MethodOfLaying")
    suspend fun deleteAll()

    @Query(
        """INSERT INTO MethodOfLaying (
                                 value
                             )
                             VALUES (
                                 'single'
                             ),
                             (
                                 'bundle'
                             );
"""
    )
    fun defaultgreate()

}