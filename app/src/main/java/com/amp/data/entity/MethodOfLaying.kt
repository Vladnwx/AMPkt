package com.amp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Справочник: способ прокладки кабеля
 */
@Entity(tableName = "method_of_laying")
data class MethodOfLaying(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String
)