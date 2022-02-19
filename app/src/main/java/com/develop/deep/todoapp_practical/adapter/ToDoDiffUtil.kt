package com.develop.deep.todoapp_practical.adapter

import androidx.recyclerview.widget.DiffUtil
import com.develop.deep.todoapp_practical.models.Task

class ToDoDiffUtil(
    private val oldList : List<Task>,
    private val newList : List<Task>

) : DiffUtil.Callback(){


    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].task == newList[newItemPosition].task
                && oldList[oldItemPosition].date == newList[newItemPosition].date
                && oldList[oldItemPosition].description == newList[newItemPosition].description

    }


}