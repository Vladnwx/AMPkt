package com.amp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Ток короткого замыкания для кабеля
 * Зависит от сечения, материала и изоляции
 */
@Entity(
    tableName = "amperage_short",
    foreignKeys = [
        ForeignKey(
            entity = NominalSize::class,
            parentColumns = ["id"],
            childColumns = ["nominal_size_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MaterialType::class,
            parentColumns = ["id"],
            childColumns = ["material_type_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = InsulationType::class,
            parentColumns = ["id"],
            childColumns = ["insulation_type_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("nominal_size_id"),
        Index("material_type_id"),
        Index("insulation_type_id"),
        Index(value = ["nominal_size_id", "material_type_id", "insulation_type_id"], unique = true)
    ]
)
data class AmperageShort(
    @PrimaryKey(autoGenerate = true) val id: Int,

    @ColumnInfo(name = "nominal_size_id") val nominalSizeId: Int,
    @ColumnInfo(name = "material_type_id") val materialTypeId: Int,
    @ColumnInfo(name = "insulation_type_id") val insulationTypeId: Int,

    @ColumnInfo(name = "amperage_short_value") val amperageShortValue: Double
)