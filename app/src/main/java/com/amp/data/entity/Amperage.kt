package com.amp.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "amperage")
data class Amperage (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val  id: Int,

    @ColumnInfo(name = "methodOfLaying", index = true)
    val  methodOfLaying :String,

    @ColumnInfo(name = "nominalSize", index = true)
    val  nominalSize :Double,

    @ColumnInfo(name = "materialType", index = true)
    val  materialType:String,

    @ColumnInfo(name = "insulationType", index = true)
    val  insulationType:String,

    @ColumnInfo(name = "typeAmperage", index = true)
    val  typeAmperage:String,

    @ColumnInfo(name = "numberOfCore", index = true)
    val numberOfCore: String,

    @ColumnInfo(name = "typeOfEnvironment", index = true)
    val typeOfEnvironment : String,

    @ColumnInfo(name = "amperage")
    val  amperage : Double

                    )
{

    override fun toString(): String {
        return amperage.toString()
    }

}