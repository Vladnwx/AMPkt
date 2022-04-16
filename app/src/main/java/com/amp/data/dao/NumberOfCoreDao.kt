package com.amp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.NumberOfCore
import kotlinx.coroutines.flow.Flow


@Dao
interface NumberOfCoreDao:BaseDAO<NumberOfCore>{

    @Query("SELECT * FROM NumberOfCore")
    fun getAll(): List<String>

    @Query("SELECT * FROM NumberOfCore")
    fun getAllFlow(): Flow<List<NumberOfCore>>


    @Query("SELECT * FROM NumberOfCore ORDER BY value ASC")
    fun getAlphabetized(): Flow<List<NumberOfCore>>

    @Query("DELETE FROM NumberOfCore")
    suspend fun deleteAll()

    @Query(
        """INSERT INTO NumberOfCore (
                                value
                            )
                            VALUES (
                                'single'
                            ),
                            (
                                'multicore5'
                            ),
                            (
                                'multicore3'
                            );"""
    )
    fun defaultgreate()

}