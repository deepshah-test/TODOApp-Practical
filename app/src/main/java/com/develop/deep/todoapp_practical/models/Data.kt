package com.develop.deep.todoapp_practical.models

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("total")
    val total : Int?,

    @SerializedName("tasks")
    val tasks : List<Task>?
)
