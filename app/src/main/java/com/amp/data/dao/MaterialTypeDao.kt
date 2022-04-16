package com.amp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.MaterialType
import kotlinx.coroutines.flow.Flow


@Dao
interface MaterialTypeDao:BaseDAO<MaterialType>{

    @Query("SELECT * FROM MaterialType")
    fun getAll(): List<String>

    @Query("SELECT * FROM MaterialType")
    fun getAllFlow(): Flow<List<MaterialType>>


    @Query("SELECT * FROM MaterialType ORDER BY value ASC")
    fun getAlphabetized(): Flow<List<MaterialType>>

    @Query("DELETE FROM MaterialType")
    suspend fun deleteAll()

    @Query(
        """INSERT INTO MaterialType (
                              value
                          )
                          VALUES (
                              'Cu'
                          ),
                          (
                              'Al'
                          );"""
    )
    fun defaultgreate()

}