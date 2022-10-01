package com.amp.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "methodOfLaying")

data class MethodOfLaying(

    @PrimaryKey
    @ColumnInfo(name = "value")
    @NonNull
    val value: String) {

    override fun toString(): String {
        return value
    }

}
