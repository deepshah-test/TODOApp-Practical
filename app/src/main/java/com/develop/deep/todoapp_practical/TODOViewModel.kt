package com.develop.deep.todoapp_practical

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.develop.deep.todoapp_practical.interfaces.AfterItemDeleteInterface
import com.develop.deep.todoapp_practical.interfaces.OperationInterface
import com.develop.deep.todoapp_practical.models.Task
import com.develop.deep.todoapp_practical.repository.TODORepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TODOViewModel(app : Application) : AndroidViewModel(app) {

    private val todoRepository : TODORepository

    val taskListLiveData : MutableLiveData<List<Task>> = MutableLiveData()
    //val isTaskAddedResponse : MutableLiveData<Response<Task>> = MutableLiveData()

    init {
        todoRepository = TODORepository()

    }

    //
    fun getTaskList(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = todoRepository.getAllTaskList(1)
            Log.e("API","getTaskList API call")
            if(response.isSuccessful){
                val taskList = response.body()?.data?.tasks
                if(!taskList.isNullOrEmpty())
                taskListLiveData.postValue(taskList)
            }
        }
    }

    fun addNewTask(task: Task, operationCallback: OperationInterface){
        viewModelScope.launch() {
            val response = todoRepository.addNewTask(task)
            Log.e("ADD", Gson().toJson(task).toString())
            Log.e("API","AddTask API call")


            if(response.isSuccessful){
                Log.e("IF","IF Success AddTask")
                operationCallback.onSuccess()

            }
            else{
                Log.e("ELSE","ELSE Failed AddTask")
                operationCallback.onFailed()
            }



            //isTaskAddedResponse.postValue(response)

        }
    }

    fun updateTask(task: Task, operationCallback: OperationInterface){
        viewModelScope.launch() {
            val response = todoRepository.updateTask(task)
            Log.e("UPDATE",Gson().toJson(task).toString())
            Log.e("API","UpdateTask API call")

            if(response.isSuccessful){
                Log.e("IF","IF Success UpdateTask")
                operationCallback.onSuccess()

            }
            else{
                Log.e("ELSE","ELSE Failed UpdateTask")
                operationCallback.onFailed()
            }
        }
    }

    fun deleteTask(taskId: Int, afterItemDeleteCallback: AfterItemDeleteInterface){
        viewModelScope.launch(Dispatchers.IO) {
            val response = todoRepository.deleteTask(taskId)
            //Log.e("DELETE",taskId.toString())
            Log.e("API","DeleteTask API call")
            if(response.isSuccessful){
                afterItemDeleteCallback.afterItemDelete(taskId)
                //create interface and pass taskId in it
                //delete taskId item from arrayList
                //inside that interface call back
            }

        }
    }
}