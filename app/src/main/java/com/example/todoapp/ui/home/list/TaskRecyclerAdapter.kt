package com.example.todoapp.ui.home.list

import android.graphics.Color
import android.graphics.Color.GREEN
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.ui.home.database.model.Task
import com.zerobranch.layout.SwipeLayout
import com.zerobranch.layout.SwipeLayout.SwipeActionsListener

class TaskRecyclerAdapter(var items :List<Task>?):RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>() {
    class ViewHolder (val viewBinding:ItemTaskBinding)
        : RecyclerView.ViewHolder(viewBinding.root){

        }

    var onItemClicked :OnItemClicked?=null
    var onItemDeleteClicked :OnItemDeleteClicked?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val viewBinding=ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.viewBinding.title.text=items?.get(position)?.title
        holder.viewBinding.desc.text=items?.get(position)?.descrption

        if(items!![position].isDone) {
            holder.viewBinding.doneBtn.setBackgroundColor(GREEN)
            holder.viewBinding.title.setTextColor(GREEN)
            holder.viewBinding.doneBtn.setBackgroundResource(R.drawable.makedone)
        }
        holder.viewBinding.swap.setOnActionsListener(object :SwipeLayout.SwipeActionsListener{
            override fun onClose() {

            }

            override fun onOpen(direction: Int, isContinuous: Boolean) {

            }
        })
       if (onItemClicked!=null) {
           holder.viewBinding.card.setOnLongClickListener {
               onItemClicked?.onItemClick(items!![position])
               true
           }
       }


        holder.viewBinding.delete.setOnClickListener{
        onItemDeleteClicked?.onItemDeleteClick(position,items!![position])
            holder.viewBinding.swap.close()
        }

    }

    override fun getItemCount(): Int =items?.size?:0

    fun changeData(newListOfTasks:List<Task>?){
        items=newListOfTasks
        notifyDataSetChanged()
    }
}

interface OnItemClicked{

    fun onItemClick(task: Task)
}

interface OnItemDeleteClicked{

    fun onItemDeleteClick(pos: Int,task: Task)
}