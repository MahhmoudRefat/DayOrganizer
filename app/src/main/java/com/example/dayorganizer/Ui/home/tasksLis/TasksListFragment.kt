package com.example.dayorganizer.Ui.home.tasksLis

import CalendarExtensions.getDateOnly
import CalendarExtensions.showDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dayorganizer.Ui.home.editTask.EditTask
import com.example.dayorganizer.Ui.model.Constants
import com.example.dayorganizer.database.model.Task
import com.example.dayorganizer.database.myDataBase
import com.example.dayorganizer.databinding.FragmentListTasksBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar


class TasksListFragment : Fragment() {
    lateinit var binding: FragmentListTasksBinding
    val adapter = TaskAdapter()
    var currentDate = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListTasksBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        retreiveTasksList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        retreiveTasksList()
        onDeleteTask()

    }

    fun retreiveTasksList() {
        val allTasks = myDataBase.getInstance(requireContext())
            .getTasksDao()
            .getAllTasksByDate(currentDate.getDateOnly())
        adapter.changeData(allTasks)
    }


    private fun setUpViews() {
        binding.rvTasks.adapter = adapter
        binding.calendarView.selectedDate = CalendarDay.today()
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            if (selected) {
                currentDate.set(date.year, date.month - 1, date.day)
                retreiveTasksList()
            }
        }
    }


    private fun onDeleteTask() {
        adapter.onDeleteClickListener = TaskAdapter
            .OnItemClickListener { item, _ ->

           /*     showDialog("Are you Sure to delete the task ") {
                    onDelete(item)
                }*/
                showDialog("Are you Sure to delete the task  ",
                    posActionName = "ٍDelete",
                    isCancelable = true,
                    posActionCallBack = {
                        onDelete(item)
                    }
                )

            }
    }
    private fun onEditTask() {
        adapter.onItemClickListener = TaskAdapter.OnItemClickListener { item, _ ->
            openEditActivity(item)
        }
    }

    private fun openEditActivity(task: Task) {
        val intent = Intent(activity, EditTask::class.java)
        intent.putExtra(Constants.TASK_KAY, task)
        startActivity(intent)
    }

    private fun onDelete(item: Task) {
        myDataBase.getInstance(requireContext())
            .getTasksDao()
            .deleteTask(item)
        retreiveTasksList()
    }


}