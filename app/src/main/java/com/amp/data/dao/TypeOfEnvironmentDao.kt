package com.amp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.TypeOfEnvironment
import kotlinx.coroutines.flow.Flow


@Dao
interface TypeOfEnvironmentDao:BaseDAO<TypeOfEnvironment>{

    @Query("SELECT * FROM typeOfEnvironment")
    fun getAll(): List<String>

    @Query("SELECT * FROM typeOfEnvironment")
    fun getAllFlow(): Flow<List<TypeOfEnvironment>>


    @Query("SELECT * FROM typeOfEnvironment ORDER BY value ASC")
    fun getAlphabetized(): Flow<List<TypeOfEnvironment>>

    @Query("DELETE FROM typeOfEnvironment")
    suspend fun deleteAll()

  @Query("""INSERT INTO typeOfEnvironment
      (
      value
  )
          VALUES
          (
                  "earth"
                  ),
      (
              "air"
              )
    """)
      fun defaultgreate()

}