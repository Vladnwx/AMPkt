package com.amp.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity

data class Resistivity (

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @NonNull
    @ColumnInfo(name = "materialType", index = true)
    val materialType: String,

    @NonNull
    @ColumnInfo(name = "nominalSize", index = true)
    val nominalSize: String,

    @NonNull
    @ColumnInfo(name = "R")
    val R: Double,

    @NonNull
    @ColumnInfo(name = "X")
    val X: Double,

    @NonNull
    @ColumnInfo(name = "p")
    val p: Double
)