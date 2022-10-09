package com.amp.data.entity

import androidx.annotation.NonNull
import androidx.room.*


@Entity(tableName = "nominalSize")

data class NominalSize (

    @PrimaryKey
    @ColumnInfo (name = "value")


    val value: Double) {

    override fun toString(): String {
        return String.format("%.1f", value)
    }

}
