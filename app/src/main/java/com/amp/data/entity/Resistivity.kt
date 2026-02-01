package com.amp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Погонные параметры кабеля: R, X, p
 * Зависят от материала и сечения
 */
@Entity(
    tableName = "resistivity",
    foreignKeys = [
        ForeignKey(
            entity = MaterialType::class,
            parentColumns = ["id"],
            childColumns = ["material_type_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = NominalSize::class,
            parentColumns = ["id"],
            childColumns = ["nominal_size_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("material_type_id"),
        Index("nominal_size_id"),
        Index(value = ["material_type_id", "nominal_size_id"], unique = true)
    ]
)
data class Resistivity(
    @PrimaryKey(autoGenerate = true) val id: Int,

    @ColumnInfo(name = "material_type_id") val materialTypeId: Int,
    @ColumnInfo(name = "nominal_size_id") val nominalSizeId: Int,

    @ColumnInfo(name = "r") val r: Double,      // активное сопротивление (Ом/км)
    @ColumnInfo(name = "x") val x: Double,      // реактивное сопротивление (Ом/км)
    @ColumnInfo(name = "p") val p: Double       // удельное сопротивление (мкОм·см)
)