package com.develop.deep.todoapp_practical.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.develop.deep.todoapp_practical.R
import com.develop.deep.todoapp_practical.fragments.list.ListFragmentDirections
import com.develop.deep.todoapp_practical.interfaces.ItemDeleteInterface
import com.develop.deep.todoapp_practical.interfaces.ItemUpdateInterface
import com.develop.deep.todoapp_practical.models.Task
import kotlinx.android.synthetic.main.raw_layout.view.*
import kotlinx.android.synthetic.main.raw_layout.view.tvTaskName
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ToDoAdapter : RecyclerView.Adapter<ToDoAdapter.MyToDoViewHolder>() {

    //var taskList = emptyList<Task>()
    var taskList = ArrayList<Task>()
    lateinit var itemDeleteInterface : ItemDeleteInterface
    lateinit var itemUpdateInterface: ItemUpdateInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyToDoViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.raw_layout,parent,false)
        return MyToDoViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyToDoViewHolder, position: Int) {

        holder.itemView.tvTaskName.text = taskList[position].task

        //
        var apiDate = taskList[position].date

        var format1 = SimpleDateFormat("yyyy-MM-dd")
        var format2 = SimpleDateFormat("dd-MM-yyyy")

        var date : Date = format1.parse(apiDate)
        var formattedDate = format2.format(date)

        holder.itemView.tvDate.text = formattedDate

        //

        holder.itemView.tvDescription.text = taskList[position].description

        holder.itemView.ivEdit.setOnClickListener {

            itemUpdateInterface.onItemUpdate(taskList[position])
//            val action = ListFragmentDirections.actionListFragmentToUpdateTaskFragment(taskList[position])
//            holder.itemView.findNavController().navigate(action)

        }

        holder.itemView.ivDelete.setOnClickListener {
            taskList[position].id?.let { id -> itemDeleteInterface.onItemDelete(id) }
        }

    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun setData(
        toDoTaskList: List<Task>,
        onItemDeleteCallback: ItemDeleteInterface,
        onItemUpdateCallback: ItemUpdateInterface){

        this.itemUpdateInterface = onItemUpdateCallback
        this.itemDeleteInterface = onItemDeleteCallback

        val toDoDiffUtil = ToDoDiffUtil(taskList,toDoTaskList)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.taskList = toDoTaskList as ArrayList<Task>

        toDoDiffResult.dispatchUpdatesTo(this)
    }


    class MyToDoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    }

}