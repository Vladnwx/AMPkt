package com.amp.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity

data class CircuitBreaker (

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @NonNull
    @ColumnInfo(name = "materialType", index = true)
    val materialType: String,

    @NonNull
    @ColumnInfo(name = "nominalSize", index = true)
    val nominalSize: Double,

    @NonNull
    @ColumnInfo(name = "characteristic")
    val characteristic: String,

    @NonNull
    @ColumnInfo(name = "rating")
    val rating: String,

    @NonNull
    @ColumnInfo(name = "numberOfPoles")
    val numberOfPoles: String
)