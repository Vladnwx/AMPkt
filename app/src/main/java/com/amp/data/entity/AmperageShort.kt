package com.amp.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "amperageShort")

data class AmperageShort (

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,

    @NonNull
    @ColumnInfo(name = "nominalSize", index = true)
    val nominalSize: Double,

    @NonNull
    @ColumnInfo(name = "materialType", index = true)
    val materialType : String,

    @NonNull
    @ColumnInfo(name = "insulationType", index = true)
    val insulationType : String,

    @NonNull
    @ColumnInfo(name = "amperageShort")
    val  amperageShort : Double

                            )
{

    override fun toString(): String {
        return amperageShort.toString()
    }

}