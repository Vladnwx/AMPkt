package com.amp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.TypeAmperage
import kotlinx.coroutines.flow.Flow


@Dao
interface TypeAmperageDao:BaseDAO<TypeAmperage>{

    @Query("SELECT * FROM TypeAmperage")
    fun getAll(): List<String>

    @Query("SELECT * FROM TypeAmperage")
    fun getAllFlow(): Flow<List<TypeAmperage>>


    @Query("SELECT * FROM TypeAmperage ORDER BY value ASC")
    fun getAlphabetized(): Flow<List<TypeAmperage>>

    @Query("DELETE FROM TypeAmperage")
    suspend fun deleteAll()

    @Query(
        """INSERT INTO TypeAmperage (
                              value
                          )
                          VALUES (
                              'AC'
                          ),
                          (
                              'DC'
                          );"""
    )
    fun defaultgreate()

}