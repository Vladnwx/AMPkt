package com.amp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Данные автоматического выключателя (АВ)
 * Зависит от материала и сечения кабеля
 */
@Entity(
    tableName = "circuit_breaker",
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
        Index("characteristic"),
        Index("rating")
    ]
)
data class CircuitBreaker(
    @PrimaryKey(autoGenerate = true) val id: Int,

    @ColumnInfo(name = "material_type_id") val materialTypeId: Int,
    @ColumnInfo(name = "nominal_size_id") val nominalSizeId: Int,

    @ColumnInfo(name = "characteristic") val characteristic: String,  // B, C, D
    @ColumnInfo(name = "rating") val rating: String,                  // "16", "25", "32" (в амперах, без "A" — или с ним, как в данных)
    @ColumnInfo(name = "number_of_poles") val numberOfPoles: String   // "1P", "2P", "3P+N"
)