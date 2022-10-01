package com.amp.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Класс для окружающей среды
@Entity(tableName = "typeOfEnvironment")
data class TypeOfEnvironment (
   // @PrimaryKey(autoGenerate = true)
   // val id: Int,

    @PrimaryKey
    @ColumnInfo(name = "value")
    @NonNull
    val value: String
                            )
