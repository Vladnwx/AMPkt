package com.amp.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "typeOfEnvironment")
data class TypeOfEnvironment (
    @NonNull
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val value: String)

