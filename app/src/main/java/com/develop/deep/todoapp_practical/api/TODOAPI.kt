package com.develop.deep.todoapp_practical.api

import com.develop.deep.todoapp_practical.models.Task
import com.develop.deep.todoapp_practical.models.TaskResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface TODOAPI {


    @POST("task-list")
    suspend fun getAllTask(@Query("page") pageNumber : Int = 1) : Response<TaskResponse>

    @POST("add-task")
    suspend fun addTask(@Body task : Task) : Response<Task>

//    @POST("add-task")
//    suspend fun addTask(@Body task : Task) : Response<TaskResponse>

    @POST("update-task")
    suspend fun updateTask(@Body task : Task) : Response<Task>

    @POST("remove-task")
    suspend fun deleteTask(@Query("task_id") taskId : Int) : Response<TaskResponse>
}