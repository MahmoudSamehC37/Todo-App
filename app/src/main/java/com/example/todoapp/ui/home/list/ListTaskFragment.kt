package com.example.todoapp.ui.home.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoapp.databinding.FragmentListBinding
import com.example.todoapp.ui.home.base.BaseFragment
import com.example.todoapp.ui.home.database.model.MyDatabase
import com.example.todoapp.ui.home.database.model.Task
import com.example.todoapp.ui.home.edit.EditActivity
import com.example.todoapp.ui.home.list.Constant.Companion.TASK
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar

class ListTaskFragment : BaseFragment() {
    lateinit var viewBinding : FragmentListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        viewBinding=FragmentListBinding.inflate(inflater,container,false)
        return viewBinding.root
    }
    lateinit var taskAdapter: TaskRecyclerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskAdapter= TaskRecyclerAdapter(null)
        viewBinding.tasksRecycler.adapter=taskAdapter
        viewBinding.calendarView.setOnDateChangedListener { widget, date, selected ->

            if(selected){
                currentDate.set(Calendar.DAY_OF_MONTH,date.day)
                currentDate.set(Calendar.MONTH,date.month-1)
                currentDate.set(Calendar.YEAR,date.year)
                loadtask()
            }
        }
        viewBinding.calendarView.selectedDate= CalendarDay.today()

        taskAdapter.onItemClicked=object :OnItemClicked{
            override fun onItemClick(task: Task) {
              //  showMassage("what do you want to do")
                ShowMassage("what do you want to do?",
                "Update",
                    {_,i->updateTodo(task)},
                    "Make Done",
                    {_,i->makeTodoDone(task)}
                    )
            }

        }

        taskAdapter.onItemDeleteClicked=object :OnItemDeleteClicked{
            override fun onItemDeleteClick(pos: Int, task: Task) {
                deleteTask(task)
            }

        }

    }

    private fun deleteTask(task: Task) {

        ShowMassage("Are you want to Delete this task"
        , posActionTitle ="Yes",
            posAction = {dialog,_->dialog.dismiss()
            MyDatabase.getDataBase(requireActivity()).TasksDeo().DeleteTask(task)
                refreshRecyclerView()
            },
            negActionTitle = "Cancle",
            negAction ={dialog,_->dialog.dismiss()}
        )

    }

    private fun makeTodoDone(task: Task) {
        task.isDone=true
        MyDatabase.getDataBase(requireActivity()).TasksDeo().UpdateTask(task)
        refreshRecyclerView()
    }

    private fun refreshRecyclerView() {
        taskAdapter.changeData(MyDatabase.getDataBase(requireActivity()).TasksDeo().getAllTasks())
        taskAdapter.notifyDataSetChanged()
    }

    private fun updateTodo(task: Task) {
        var intent = Intent(requireActivity(),EditActivity::class.java)
        intent.putExtra(TASK,task)
        startActivity(intent)
    }

    var currentDate =Calendar.getInstance()
    init {
        currentDate.set(Calendar.HOUR,0)
        currentDate.set(Calendar.MINUTE,0)
        currentDate.set(Calendar.SECOND,0)
        currentDate.set(Calendar.MILLISECOND,0)
    }
    override fun onResume() {
        super.onResume()
        loadtask()
    }

     fun loadtask() {
         if(!isResumed){
             return
         }
            val tasks= MyDatabase.getDataBase(requireActivity())
            .TasksDeo()
                .getTasksByDate(currentDate.timeInMillis)
         taskAdapter.changeData(tasks)
    }

}