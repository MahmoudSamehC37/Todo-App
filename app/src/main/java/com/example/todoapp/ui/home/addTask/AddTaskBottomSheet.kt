package com.example.todoapp.ui.home.addTask

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.ui.home.database.model.MyDatabase
import com.example.todoapp.ui.home.database.model.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTaskBottomSheet : BottomSheetDialogFragment() {
    lateinit var viewBinding : FragmentAddTaskBinding
    var currentDate=Calendar.getInstance()
    var Month =currentDate.get(Calendar.MONTH)+1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding=FragmentAddTaskBinding.inflate(inflater,container,false)
        return viewBinding.root
    }
    var onDismissListener:OnDismissListener?=null
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.onDismiss();
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        viewBinding.taskDataText.setText(""+currentDate.get(Calendar.DAY_OF_MONTH)+"/"+
                Month +"/"+currentDate.get(Calendar.YEAR)
        )
        viewBinding.taskData.setOnClickListener {
            showDataPicker()
        }
        viewBinding.submit.setOnClickListener{
            addTask()
        }
    }

    fun validate():Boolean{
        var valid=true
        val title=viewBinding.taskTitle.editText?.text.toString()
        val desc=viewBinding.taskDescrption.editText?.text.toString()

        if(title.isNullOrBlank()){
            viewBinding.taskTitle
                .error="Please Enter Title"
            valid=false
        }
        else{
            viewBinding.taskTitle.error=null
        }
        if(desc.isNullOrBlank()){
            viewBinding.taskDescrption
                .error="Please Enter Description"
            valid=false
        }
        else{
            viewBinding.taskDescrption.error=null
        }
        return valid
    }
     fun addTask() {
         if (validate() == false) {
             return
         }

         val title = viewBinding.taskTitle.editText?.text.toString()
         val desc = viewBinding.taskDescrption.editText?.text.toString()

         MyDatabase.getDataBase(requireActivity())
             .TasksDeo()
             .InsertTask(Task(
                 title=title,
                 descrption = desc,
                 date = currentDate.timeInMillis
             ))
         showTaskInsertedDialog()
    }

    fun showTaskInsertedDialog(){
        val alertDialogBuilder=AlertDialog.Builder(requireActivity())
            .setMessage("Task Inserted Successfully")
            .setPositiveButton(R.string.ok,
                { dialog, btnId ->
                    dialog.dismiss()
                    dismiss()
                }).setCancelable(false)
        alertDialogBuilder.show()
    }

    init {
        currentDate.set(Calendar.HOUR,0)
        currentDate.set(Calendar.MINUTE,0)
        currentDate.set(Calendar.SECOND,0)
        currentDate.set(Calendar.MILLISECOND,0)
    }
     @SuppressLint("SuspiciousIndentation")
     fun showDataPicker() {

       val dataPicker= DatePickerDialog(requireActivity(),
            { datePicker, year, month, day ->
                Month = month + 1
                currentDate.set(Calendar.YEAR,year)
                currentDate.set(Calendar.MONTH,month)
                currentDate.set(Calendar.DAY_OF_MONTH,day)
                viewBinding.taskDataText.setText(""+currentDate.get(Calendar.DAY_OF_MONTH)+"/"+
                        Month +"/"+currentDate.get(Calendar.YEAR)
                )
            },currentDate.get(Calendar.YEAR),
             currentDate.get(Calendar.MONTH),
             currentDate.get(Calendar.DAY_OF_MONTH)
            )
         dataPicker.show()
    }
}




