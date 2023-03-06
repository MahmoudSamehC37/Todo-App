package com.example.todoapp.ui.home.database.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.ui.home.database.model.deo.TasksDeo

@Database(entities = [Task::class],
    version = 1,
    exportSchema = false)
abstract class MyDatabase:RoomDatabase() {
    abstract fun TasksDeo():TasksDeo

    companion object{
        private val databaseName="RouteTasksDataBase"
        private var myDatabase:MyDatabase?=null
        fun getDataBase(context:Context):MyDatabase{
            if(myDatabase==null){
                //intilization
                myDatabase=Room.databaseBuilder(
                    context,
                    MyDatabase::class.java,
                    databaseName
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return myDatabase!!
        }
    }
}