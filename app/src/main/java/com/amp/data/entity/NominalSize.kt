package com.amp.data.entity

import androidx.room.*


@Entity(tableName = "nominalSize")

data class NominalSize (

    @PrimaryKey
    @ColumnInfo(name = "value")

    val value: String) {

    override fun toString(): String {
        return value
    }

}
