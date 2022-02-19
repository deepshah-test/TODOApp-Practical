package com.develop.deep.todoapp_practical.models

import com.google.gson.annotations.SerializedName

data class TaskResponse(
    @SerializedName("status")
    val status : Int?,
    @SerializedName("message")
    val message : String?,
    @SerializedName("data")
    val data : Data?

)
