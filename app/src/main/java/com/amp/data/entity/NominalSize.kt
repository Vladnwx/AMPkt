package com.amp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Справочник стандартных номинальных сечений кабелей (в мм²)
 * Примеры: 1.5, 2.5, 4.0, 6.0, 10.0 и т.д.
 */
@Entity(tableName = "nominal_size")
data class NominalSize(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "size_mm2") val sizeMm2: Double
)