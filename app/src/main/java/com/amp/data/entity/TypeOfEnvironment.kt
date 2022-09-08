package com.amp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "typeOfEnvironment")
data class TypeOfEnvironment (
   // @PrimaryKey(autoGenerate = true)
   // val id: Int,

    @PrimaryKey
    @ColumnInfo(name = "value")
    val value: String
                            )
