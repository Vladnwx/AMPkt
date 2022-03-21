package com.amp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.TypeOfEnvironment
import kotlinx.coroutines.flow.Flow


@Dao
interface TypeOfEnvironmentDao:BaseDAO<TypeOfEnvironment>{

   // @Query("SELECT * FROM TypeOfEnvironment")
  // abstract fun getAll(): List<String?>?

    @Query("SELECT * FROM TypeOfEnvironment")
    abstract fun getAllLiveData(): LiveData<List<TypeOfEnvironment?>?>?


    @Query("SELECT * FROM TypeOfEnvironment ORDER BY id ASC")
    abstract fun getAlphabetizedWords(): Flow<List<TypeOfEnvironment>>

    @Query("DELETE FROM TypeOfEnvironment")
    abstract fun deleteAll()

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