package com.amp.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "amperageShort")

data class AmperageShort (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "nominalSize", index = true)
    val nominalSize: Double,

    @ColumnInfo(name = "materialType", index = true)
    val materialType : String,

    @ColumnInfo(name = "insulationType", index = true)
    val insulationType : String,

    @ColumnInfo(name = "amperageShort")
    val  amperageShort : Double

                            )
{

    override fun toString(): String {
        return amperageShort.toString()
    }

}