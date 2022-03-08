package com.amp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.TypeOfEnvironment
import kotlinx.coroutines.flow.Flow


@Dao
interface TypeOfEnvironmentDao:BaseDAO<TypeOfEnvironment>{

    //@Query("SELECT * FROM TypeOfEnvironment ORDER BY id ASC")
  //  fun getAlphabetizedWords(): List<TypeOfEnvironment>

    @Query("SELECT * FROM TypeOfEnvironment ORDER BY id ASC")
    fun getAlphabetizedWords(): Flow<List<TypeOfEnvironment>>

    @Query("DELETE FROM TypeOfEnvironment")
    suspend fun deleteAll()

  @Query(
         """INSERT INTO  "TypeOfEnvironment"
                                 VALUES (
                                     'earth'
                                 ),
                                 (
                                     'air'
                                 );"""
     )
     fun defaultGreate() {
     }

}