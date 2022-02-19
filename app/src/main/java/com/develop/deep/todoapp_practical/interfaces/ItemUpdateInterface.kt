package com.develop.deep.todoapp_practical.interfaces

import com.develop.deep.todoapp_practical.models.Task

interface ItemUpdateInterface {

    fun onItemUpdate(task : Task)
}