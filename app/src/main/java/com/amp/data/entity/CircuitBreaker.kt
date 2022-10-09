package com.amp.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity

data class CircuitBreaker (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "materialType", index = true)
    val materialType: String,

    @ColumnInfo(name = "nominalSize", index = true)
    val nominalSize: Double,

    @ColumnInfo(name = "characteristic")
    val characteristic: String,

    @ColumnInfo(name = "rating")
    val rating: String,

    @ColumnInfo(name = "numberOfPoles")
    val numberOfPoles: String
)