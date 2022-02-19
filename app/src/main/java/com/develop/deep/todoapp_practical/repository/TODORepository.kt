package com.develop.deep.todoapp_practical.repository

import com.develop.deep.todoapp_practical.api.RetrofitInstance
import com.develop.deep.todoapp_practical.models.Task

class TODORepository() {

    suspend fun getAllTaskList(pageNumber : Int) =

        RetrofitInstance.api.getAllTask(pageNumber)


    suspend fun addNewTask(task : Task) =

        RetrofitInstance.api.addTask(task)


    suspend fun updateTask(task : Task) =

        RetrofitInstance.api.updateTask(task)


    suspend fun deleteTask(taskId : Int) =

        RetrofitInstance.api.deleteTask(taskId)

}