package com.develop.deep.todoapp_practical.fragments.update

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.develop.deep.todoapp_practical.R
import com.develop.deep.todoapp_practical.TODOViewModel
import com.develop.deep.todoapp_practical.interfaces.OperationInterface
import com.develop.deep.todoapp_practical.models.Task
import kotlinx.android.synthetic.main.fragment_update_task.*
import kotlinx.android.synthetic.main.fragment_update_task.view.*
import java.text.SimpleDateFormat
import java.util.*


class UpdateTaskFragment : Fragment() {

    private val todoViewModel : TODOViewModel by viewModels()
    private val args by navArgs<UpdateTaskFragmentArgs>()
    var currentItemId : Int? = null

    private lateinit var operationCallback : OperationInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update_task, container, false)

        view.update_etTaskName.setText(args.currentItem.task)

        var format1 = SimpleDateFormat("yyyy-MM-dd")
        var format2 = SimpleDateFormat("dd-MM-yyyy")

        var date : Date = format1.parse(args.currentItem.date)
        var formattedDate = format2.format(date)


        view.update_etSelectDate.setText(formattedDate)

        view.update_etTaskDescription.setText(args.currentItem.description)

         currentItemId = args.currentItem.id


        var calendar : Calendar = Calendar.getInstance()

        /*to set the current selected date - date object to calendar-
        * view as a selected date */
        calendar.time = date


        operationCallback =  object : OperationInterface {

            override fun onSuccess() {

                Log.e("UpdateTaskFragment","onSuccess")
                activity?.onBackPressed()
                Toast.makeText(context, "Task Updated Successfully",Toast.LENGTH_LONG).show()
            }

            override fun onFailed() {
                Log.e("UpdateTaskFragment","onFailed")
                Toast.makeText(context, "Failed to Update Task",Toast.LENGTH_LONG).show()
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

                view.update_etSelectDate.setText(sdf.format(calendar.getTime()))
            }
        }

        view.update_etSelectDate
            .setOnClickListener {
            DatePickerDialog(requireContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        view.btnUpdate.setOnClickListener {
            val taskName = update_etTaskName.text.toString()
            val date = update_etSelectDate.text.toString()

            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            val formattedDate = sdf.format(calendar.time)


            val description = update_etTaskDescription.text.toString()
            val taskId = currentItemId

            val task : Task = Task(null,taskId,taskName,formattedDate,description)


            val builder = AlertDialog.Builder(requireContext())

            builder.setPositiveButton("Yes"){ _, _ ->
                todoViewModel.updateTask(task,operationCallback)
            }

            builder.setNegativeButton("No"){ _, _ -> }
            builder.setTitle("Update Task?")
            builder.setMessage("Are you sure you want to Update the Changes?")
            builder.create().show()

        }

        return view
    }


}