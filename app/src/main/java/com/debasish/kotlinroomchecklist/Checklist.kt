package com.debasish.kotlinroomchecklist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "table_checklist")
data class Checklist(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "note")
    var note: String,
    @ColumnInfo(name = "noteType")
    var noteType: String,
    @ColumnInfo(name = "noteCreated")
    var noteCreated: String,
    @ColumnInfo(name = "completed")
    var completed: Boolean
)