package com.amp.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Класс род тока
@Entity(tableName = "typeAmperage")

data class TypeAmperage (

    @PrimaryKey
    @ColumnInfo(name = "value")
    @NonNull
    val value: String
                        )
{

    override fun toString(): String {
        return value
    }

}