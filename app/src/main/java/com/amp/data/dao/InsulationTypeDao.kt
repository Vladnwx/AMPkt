package com.amp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.InsulationType
import kotlinx.coroutines.flow.Flow


@Dao
interface InsulationTypeDao:BaseDAO<InsulationType>{

    @Query("SELECT * FROM InsulationType")
    fun getAll(): List<String>

    @Query("SELECT * FROM InsulationType")
    fun getAllFlow(): Flow<List<InsulationType>>


    @Query("SELECT * FROM InsulationType ORDER BY value ASC")
    fun getAlphabetized(): Flow<List<InsulationType>>

    @Query("DELETE FROM InsulationType")
    suspend fun deleteAll()

    @Query(
        """INSERT INTO InsulationType (
                                value
                            )
                            VALUES (
                                'PEX'
                            ),
                            (
                                'PVC'
                            );"""
    )
    fun defaultgreate()

}