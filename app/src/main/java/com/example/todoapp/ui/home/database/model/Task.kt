package com.example.todoapp.ui.home.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) var id : Int=0,
   @ColumnInfo var title: String?=null,
    @ColumnInfo var descrption : String?=null,
    @ColumnInfo var date: Long?=null,
    @ColumnInfo var isDone : Boolean=false
):java.io.Serializable
