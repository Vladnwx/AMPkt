package com.amp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amp.data.entity.Type_of_environment

@Dao
abstract class Type_of_environment_Dao:Base_DAO<Type_of_environment>("Type_of_environment"){
    @Query(
        """INSERT INTO  (
                                    type_of_environment
                                )
                                VALUES (
                                    'earth'
                                ),
                                (
                                    'air'
                                );"""
    )
    fun defaultgreate() {
    }


}