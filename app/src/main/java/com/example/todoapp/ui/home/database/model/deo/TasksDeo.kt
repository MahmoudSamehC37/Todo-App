package com.example.todoapp.ui.home.database.model.deo

import androidx.room.*
import com.example.todoapp.ui.home.database.model.Task

@Dao
interface TasksDeo {
    @Insert
    fun InsertTask(task : Task)
   @Delete
    fun DeleteTask(task :Task)
    @Update
    fun UpdateTask(task :Task)

    @Query("select * from tasks")
    fun getAllTasks():List<Task>

//    @Query("select * from tasks where date =: date")
//    fun getTaskByDate(date : Long):List<Task>
    @Query("select * from tasks where date=:date")
    fun getTasksByDate(date:Long):List<Task>
}