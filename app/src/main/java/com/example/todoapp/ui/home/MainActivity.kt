package com.example.todoapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.ui.home.addTask.AddTaskBottomSheet
import com.example.todoapp.ui.home.addTask.OnDismissListener
import com.example.todoapp.ui.home.list.ListTaskFragment
import com.example.todoapp.ui.home.setting.SettingsFragment
import java.nio.file.Files.list
import java.util.Collections.list

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityMainBinding
    //lateinit var tasksListFragment: ListTaskFragment
    val tasksListFragment=ListTaskFragment();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.bottomNav
            .setOnItemSelectedListener{item->
                when(item.itemId){
                    R.id.list->{
                        viewBinding.screenTitle.setText(R.string.title_tasks_list)
                        ShowFragment(tasksListFragment)
                    }
                    R.id.settings->{
                        viewBinding.screenTitle.setText(R.string.title_tasks_settings)
                        ShowFragment(SettingsFragment())
                    }
                }

                return@setOnItemSelectedListener true
            }
        viewBinding.bottomNav.selectedItemId=R.id.list
        viewBinding.addTask.setOnClickListener{
            showAddTaskBottomSheet()
        }
    }

     fun showAddTaskBottomSheet() {
        val addTaskBottomSheet=AddTaskBottomSheet()
         addTaskBottomSheet.onDismissListener= OnDismissListener {

        // ListTaskFragment().loadtask()
             tasksListFragment.loadtask()

         }
         addTaskBottomSheet.show(supportFragmentManager,null)
    }

    fun ShowFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commit()
    }
}