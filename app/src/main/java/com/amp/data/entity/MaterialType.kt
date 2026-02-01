package com.amp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Справочник: материал токопроводящей жилы
 * Примеры: "Медь", "Алюминий"
 */
@Entity(tableName = "material_type")
data class MaterialType(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String
)