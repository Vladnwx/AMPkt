package com.amp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NumberOfCore")

data class NumberOfCore (

    @PrimaryKey
    @ColumnInfo(name = "value")

    val value: String) {

    override fun toString(): String {
        return value
    }

}
