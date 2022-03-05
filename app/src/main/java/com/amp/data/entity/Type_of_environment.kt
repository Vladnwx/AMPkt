package com.amp.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "Type_of_environment")
data class Type_of_environment (
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    var name: String = "Type_of_environment")
