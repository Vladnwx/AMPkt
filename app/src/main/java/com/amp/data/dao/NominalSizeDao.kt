package com.amp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.NominalSize
import kotlinx.coroutines.flow.Flow

@Dao
interface NominalSizeDao :BaseDAO<NominalSize> {
    @Query("SELECT * FROM nominalSize")
    fun getAll(): List<String>

    @Query("SELECT * FROM nominalSize")
    fun getAllFlow(): Flow<List<NominalSize>>


    @Query("SELECT * FROM nominalSize ORDER BY value ASC")
    fun getAlphabetized(): Flow<List<NominalSize>>

    @Query("DELETE FROM nominalSize")
    suspend fun deleteAll()

    @Query(
        """INSERT INTO nominalSize (
                             value
                         )
                         VALUES (
                             0.5
                         ),
                         (
                             0.75
                         ),
                         (
                             1.5
                         ),
                         (
                             2.5
                         ),
                         (
                             4
                         ),
                         (
                             6
                         ),
                         (
                             10
                         ),
                         (
                             16
                         ),
                         (
                             25
                         ),
                         (
                             35
                         ),
                         (
                             50
                         ),
                         (
                             70
                         ),
                         (
                             95
                         ),
                         (
                             120
                         ),
                         (
                             150
                         ),
                         (
                             185
                         ),
                         (
                             240
                         ),
                         (
                             300
                         ),
                         (
                             400
                         ),
                         (
                             500
                         ),
                         (
                             630
                         ),
                         (
                             800
                         ),
                         (
                             1000
                         );
"""
    )
    fun defaultgreate()


}