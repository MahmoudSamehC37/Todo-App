package com.example.todoapp.ui.home.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import androidx.navigation.ui.AppBarConfiguration
import com.example.todoapp.databinding.ActivityEditBinding
import com.example.todoapp.ui.home.MainActivity
import com.example.todoapp.ui.home.database.model.MyDatabase
import com.example.todoapp.ui.home.database.model.Task
import com.example.todoapp.ui.home.list.Constant.Companion.TASK
import java.text.SimpleDateFormat
import java.util.*


class EditActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration :AppBarConfiguration
    private lateinit var binding :ActivityEditBinding
    private lateinit var task :Task
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

       task=((intent.getSerializableExtra(TASK) as? Task)!!)

        showDate(task)

        binding.submit.setOnClickListener{
            updateTodo()
        }
    }

    private fun isDateVaild(): Boolean {
        var isVaild=true

        if(binding.titleContiener.editText?.text.toString().isBlank()){
            binding.titleContiener.error="Please Enter Title"
            isVaild=false

        }else binding.titleContiener.error=null

        if(binding.decContainer.editText?.text.toString().isBlank()){
            binding.decContainer.error="Please Enter Your Descrption"
            isVaild=false
        }else binding.decContainer.error=null

        if(binding.date.text.isNullOrBlank()){
            binding.dateContainer.error="Please Select Date"
            isVaild=false
        }else binding.dateContainer.error=null
        return isVaild
    }
    private fun updateTodo() {
        if(isDateVaild()){
            task.title=binding.titleContiener.editText?.text.toString()
            task.descrption=binding.decContainer.editText?.text.toString()

            MyDatabase.getDataBase(this).TasksDeo().UpdateTask(task)
           startActivity(Intent(this,MainActivity::class.java))
            finish()

        }
    }



    private fun showDate(task: Task) {
        binding.titleContiener.editText?.setText(task.title)
        val date = convertLongToTime(task.date)
        binding.date.text=date
        binding.decContainer.editText?.setText(task.descrption)
    }

    private fun convertLongToTime(date: Long?): String {
        val date = Date(date!!)
        val format=SimpleDateFormat("yyyy.MM.dd")
        return format.format(date)
    }
}