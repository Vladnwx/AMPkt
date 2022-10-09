package com.amp.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity

data class Resistivity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "materialType", index = true)
    val materialType: String,

    @ColumnInfo(name = "nominalSize", index = true)
    val nominalSize: Double,

    @ColumnInfo(name = "R")
    val R: Double,

    @ColumnInfo(name = "X")
    val X: Double,

    @ColumnInfo(name = "p")
    val p: Double
)