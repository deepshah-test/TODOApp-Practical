package com.develop.deep.todoapp_practical.fragments.add

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.develop.deep.todoapp_practical.R
import com.develop.deep.todoapp_practical.TODOViewModel
import com.develop.deep.todoapp_practical.interfaces.OperationInterface
import com.develop.deep.todoapp_practical.models.Task
import com.develop.deep.todoapp_practical.util.ToDoUtil
import kotlinx.android.synthetic.main.fragment_add_task.*
import kotlinx.android.synthetic.main.fragment_add_task.view.*
import java.text.SimpleDateFormat
import java.util.*

class AddTaskFragment : Fragment() {

    private val todoViewModel : TODOViewModel by viewModels()

    private lateinit var operationCallback : OperationInterface

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)

        var calendar : Calendar = Calendar.getInstance()
        //val calendar : Calendar = Calendar.getInstance()


        operationCallback =  object : OperationInterface {

            override fun onSuccess() {
                Log.e("AddTaskFragment","onSuccess")
                activity?.onBackPressed()
                Toast.makeText(context, "Task Added Successfully",Toast.LENGTH_LONG).show()

            }

            override fun onFailed() {
                Log.e("AddTaskFragment","onFailed")
                Toast.makeText(context, "Task Add Operation Failed",Toast.LENGTH_LONG).show()
            }

        }


        val dateSetListener = object : DatePickerDialog.OnDateSetListener{

            override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {

                calendar.set(Calendar.YEAR,year)
                calendar.set(Calendar.MONTH,monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)

                setDateInEditTextWithMyFormat()

            }

            fun setDateInEditTextWithMyFormat() {

                val myFormat = "dd/MM/yyyy" // format i need in EditText to Display
                val sdf = SimpleDateFormat(myFormat, Locale.US)

                view.etSelectDate.setText(sdf.format(calendar.getTime()))

            }
        }

        view.etSelectDate.setOnClickListener {
            DatePickerDialog(requireContext(),
                dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)).show()
        }


        view.btnAdd.setOnClickListener {

            val taskName = etTaskName.text.toString()
            //val date = etSelectDate.text.toString()
            val apiFormatDate = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(apiFormatDate, Locale.US)
            val formattdDate = sdf.format(calendar.time)
            val taskDescription = etTaskDescription.text.toString()

            if(!(taskName.isNullOrBlank() || formattdDate.isNullOrBlank() || taskDescription.isNullOrBlank())) {

                val task: Task = Task(null, null, taskName, formattdDate, taskDescription)

                if (ToDoUtil.isOnline(requireContext())) {
                    todoViewModel.addNewTask(task, operationCallback)
                }
            }
            else{
                Toast.makeText(context,"Field Required",Toast.LENGTH_LONG).show()
            }
        }

        return view
    }

}