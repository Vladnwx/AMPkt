package com.amp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Справочник: количество и конструкция жил кабеля (например, "1", "3+1", "5")
 */
@Entity(tableName = "number_of_core")
data class NumberOfCore(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String
)