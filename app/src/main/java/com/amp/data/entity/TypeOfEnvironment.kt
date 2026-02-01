package com.amp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Справочник: тип окружающей среды (например, "воздух", "земля", "бетон")
 */
@Entity(tableName = "type_of_environment")
data class TypeOfEnvironment(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String
)