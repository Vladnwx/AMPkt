package com.amp.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "amperage")
data class Amperage (

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    val  id: Int,

    @NonNull
    @ColumnInfo(name = "methodOfLaying", index = true)
    val  methodOfLaying :String,

    @NonNull
    @ColumnInfo(name = "nominalSize", index = true)
    val  nominalSize :String,

    @NonNull
    @ColumnInfo(name = "materialType", index = true)
    val  materialType:String,

    @NonNull
    @ColumnInfo(name = "insulationType", index = true)
    val  insulationType:String,

    @NonNull
    @ColumnInfo(name = "typeAmperage", index = true)
    val  typeAmperage:String,

    @NonNull
    @ColumnInfo(name = "numberOfCore", index = true)
    val numberOfCore: String,

    @NonNull
    @ColumnInfo(name = "typeOfEnvironment", index = true)
    val typeOfEnvironment : String,

    @NonNull
    @ColumnInfo(name = "amperage")
    val  amperage : Double

                    )
{

    override fun toString(): String {
        return amperage.toString()
    }

}