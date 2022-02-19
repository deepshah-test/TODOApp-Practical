package com.develop.deep.todoapp_practical.fragments.list

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.develop.deep.todoapp_practical.R
import com.develop.deep.todoapp_practical.TODOViewModel
import com.develop.deep.todoapp_practical.adapter.ToDoAdapter
import com.develop.deep.todoapp_practical.interfaces.AfterItemDeleteInterface
import com.develop.deep.todoapp_practical.interfaces.ItemDeleteInterface
import com.develop.deep.todoapp_practical.interfaces.ItemUpdateInterface
import com.develop.deep.todoapp_practical.models.Task
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private val todoViewModel : TODOViewModel by viewModels()

    private val todoAdapter : ToDoAdapter by lazy { ToDoAdapter() }

    private lateinit var afterItemDeleteCallback : AfterItemDeleteInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        view.fabAdd.setOnClickListener {

            findNavController().navigate(R.id.action_listFragment_to_addTaskFragment)
        }

        view.rvTaskList.adapter = todoAdapter
        view.rvTaskList.layoutManager = LinearLayoutManager(requireActivity())

        val onItemUpdateCallback = object : ItemUpdateInterface {

            override fun onItemUpdate(task: Task) {

                val builder = AlertDialog.Builder(requireContext())

                builder.setPositiveButton("Yes"){ _, _ ->

                    val action = ListFragmentDirections.actionListFragmentToUpdateTaskFragment(task)
                    findNavController().navigate(action)
                }

                builder.setNegativeButton("No"){ _, _ -> }

                builder.setTitle("Update Task?")
                builder.setMessage("Are you sure you want to Update this Task?")
                builder.create().show()
            }

        }

        val onItemDeleteCallback =  object : ItemDeleteInterface {

            override fun onItemDelete(taskId: Int) {

                val builder = AlertDialog.Builder(requireContext())

                builder.setPositiveButton("Yes"){ _, _ ->

                    deleteItem(taskId)

                    Toast.makeText(context,"Item Deleted",
                        Toast.LENGTH_SHORT).show()

                }

                builder.setNegativeButton("No"){ _, _ -> }

                builder.setTitle("Delete Task?")
                builder.setMessage("Are you sure you want to delete this Task?")
                builder.create().show()

            }
        }

        afterItemDeleteCallback =  object : AfterItemDeleteInterface {

            @SuppressLint("NotifyDataSetChanged")
            override fun afterItemDelete(taskId: Int) {

                for(item in todoAdapter.taskList){
                    if(item.id == taskId){
                        view.rvTaskList.post {
                            todoAdapter.taskList.remove(item)
                            todoAdapter.notifyDataSetChanged()
                        }
                        break
                    }
                }
            }
        }

        todoViewModel.taskListLiveData.observe(viewLifecycleOwner, Observer {
            todoAdapter.setData(it,onItemDeleteCallback,onItemUpdateCallback)
        })
        return view
    }

    private fun deleteItem(taskId: Int) {
        todoViewModel.deleteTask(taskId,afterItemDeleteCallback)
    }

    override fun onResume() {
        super.onResume()
        todoViewModel.getTaskList()
    }


}