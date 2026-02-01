package com.amp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Допустимая токовая нагрузка на кабель
 * Зависит от способа прокладки, сечения, материала, изоляции,
 * рода тока, количества жил и окружающей среды
 */
@Entity(
    tableName = "amperage",
    foreignKeys = [
        ForeignKey(
            entity = MethodOfLaying::class,
            parentColumns = ["id"],
            childColumns = ["method_of_laying_id"],
            onDelete = ForeignKey.CASCADE
        ),
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
        ),
        ForeignKey(
            entity = TypeAmperage::class,
            parentColumns = ["id"],
            childColumns = ["type_amperage_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = NumberOfCore::class,
            parentColumns = ["id"],
            childColumns = ["number_of_core_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TypeOfEnvironment::class,
            parentColumns = ["id"],
            childColumns = ["type_of_environment_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("method_of_laying_id"),
        Index("nominal_size_id"),
        Index("material_type_id"),
        Index("insulation_type_id"),
        Index("type_amperage_id"),
        Index("number_of_core_id"),
        Index("type_of_environment_id"),

        // Уникальный индекс по всем ключам — чтобы не было дублей
        Index(
            value = [
                "method_of_laying_id",
                "nominal_size_id",
                "material_type_id",
                "insulation_type_id",
                "type_amperage_id",
                "number_of_core_id",
                "type_of_environment_id"
            ],
            unique = true
        )
    ]
)
data class Amperage(
    @PrimaryKey(autoGenerate = true) val id: Int,

    @ColumnInfo(name = "method_of_laying_id") val methodOfLayingId: Int,
    @ColumnInfo(name = "nominal_size_id") val nominalSizeId: Int,
    @ColumnInfo(name = "material_type_id") val materialTypeId: Int,
    @ColumnInfo(name = "insulation_type_id") val insulationTypeId: Int,
    @ColumnInfo(name = "type_amperage_id") val typeAmperageId: Int,
    @ColumnInfo(name = "number_of_core_id") val numberOfCoreId: Int,
    @ColumnInfo(name = "type_of_environment_id") val typeOfEnvironmentId: Int,

    @ColumnInfo(name = "amperage_value") val amperageValue: Double
)