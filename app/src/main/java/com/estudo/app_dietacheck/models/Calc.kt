package com.estudo.app_dietacheck.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Calc(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "res")
    val res: Double,

    @ColumnInfo(name = "classification")
    val classification: String,

    @ColumnInfo(name = "createdDate")
    val createdDate: Date = Date()
)
