package com.develop.deep.todoapp_practical.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    @SerializedName("id")
    val id : Int?,

    @SerializedName("task_id")
    var taskId : Int?,

    @SerializedName("task")
    val task : String?,

    @SerializedName("date")
    val date : String?,

    @SerializedName("description")
    val description : String?
) : Parcelable
