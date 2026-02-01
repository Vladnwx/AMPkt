package com.amp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Справочник: тип изоляции кабеля
 * Примеры: "ПВХ", "XLPE", "Резина", "Бумажная"
 */
@Entity(tableName = "insulation_type")
data class InsulationType(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String
)