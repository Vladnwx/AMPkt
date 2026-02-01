package com.amp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Справочник: род тока (например, "переменный", "постоянный")
 */
@Entity(tableName = "type_amperage")
data class TypeAmperage(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String
)