package com.amp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "nominalSize")
data class NominalSize (

    @PrimaryKey
    @ColumnInfo(name = "value")
    val value: String)