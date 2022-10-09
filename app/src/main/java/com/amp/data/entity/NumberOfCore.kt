package com.amp.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Класс конструкция жилы
@Entity(tableName = "numberOfCore")

data class NumberOfCore (

    @PrimaryKey
    @ColumnInfo(name = "value")
    val value: String) {

    override fun toString(): String {
        return value
    }

}
