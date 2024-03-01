package com.example.dayorganizer.Ui.home.addTask

import CalendarExtensions.getDateOnly
import CalendarExtensions.getTimeOnly
import CalendarExtensions.showDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dayorganizer.database.model.Task
import com.example.dayorganizer.database.myDataBase
import com.example.dayorganizer.databinding.FragmentAddTaskBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar

class AddTaskBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddTaskBottomSheetBinding
    val calender = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()

    }

    private fun setUpView() {
        binding.addTaskBtn.setOnClickListener {
            addTask()

        }
        binding.selectDateTil.setOnClickListener {
            showDateDialog()
        }
        binding.selectTimeTil.setOnClickListener {
            showTimeDialog()
        }


    }

    private fun showTimeDialog() {
        val timePicker = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                calender.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calender.set(Calendar.MINUTE, minute)
                binding.selectTimeTv.text = calender.formatTime()
                binding.selectTimeTv.error = null
            },
            calender.get(Calendar.HOUR_OF_DAY),
            calender.get(Calendar.MINUTE),
            false,
        )
        timePicker.show()
    }

    private fun showDateDialog() {
        val datePicker = DatePickerDialog(requireContext())
        datePicker.setOnDateSetListener { dialog, year, month, day ->
            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, month)
            calender.set(Calendar.DAY_OF_MONTH, day)
            binding.selectDateTv.text = calender.formatDate()
            binding.selectDateTv.error = null
        }
        datePicker.show()

    }

    /*   private fun onSelectTimeClick() {
           binding.selectTimeTil.setOnClickListener {
               val calendar = Calendar.getInstance()
           }
       }*/

    private fun addTask() {
        if (!isValidTask()) {
            return
        }
        myDataBase.getInstance(requireContext())
            .getTasksDao()
            .insertTask(
                Task(
                    title = binding.title.text.toString(),
                    content = binding.description.text.toString(),
                    date = calender.getDateOnly(),
                    time = calender.getTimeOnly()
                ),
            )
        showDialog("Task inserted Sucessfoly ",
            posActionName = "OK",
            isCancelable = true,
            posActionCallBack = {
                dismiss()
                onTaskAddedListener?.onTaskAdded()
            }
        )
     /*   Toast.makeText(
            requireContext(),
            "Task saved successfully",
            Toast.LENGTH_LONG,
        ).show()
        dismiss()
        onTaskAddedListener?.onTaskAdded()*/

    }


    private fun isValidTask(): Boolean {
        var isValid = true
        val title = binding.title.text.toString()
        val description = binding.description.text.toString()
        if (title.isBlank()) {
            binding.titleTil.error = "required task title"
            isValid = false
        } else {
            binding.titleTil.error = null
        }
        if (description.isBlank()) {
            binding.descriptionTil.error = "required task description"
            isValid = false
        } else {
            binding.descriptionTil.error = null
        }
        if (binding.selectTimeTv.text.isBlank()) {
            binding.titleTil.error = "required task time"
            isValid = false
        } else {
            binding.titleTil.error = null
        }
        if (binding.selectDateTv.text.isBlank()) {
            binding.selectDateTil.error = "required task date"
            isValid = false
        } else {
            binding.selectDateTil.error = null
        }

        return isValid
    }

    fun Calendar.formatTime(): String {
        val dateFormated = SimpleDateFormat("hh/mm/a");
        return dateFormated.format(time)
    }

    fun Calendar.formatDate(): String {
        val dateFormated = SimpleDateFormat("dd:MM:yyyy");
        return dateFormated.format(time)
    }

    var onTaskAddedListener: OnTaskAddedListener? = null

    fun interface OnTaskAddedListener {
        fun onTaskAdded()
    }
}


/* fun Calendar.getDayOnly(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(
        get(Calendar.YEAR), get(Calendar.MONTH),
        get(Calendar.DATE), 0, 0, 0
    )
    calender.set(Calendar.MILLISECOND, 0)
    return calender.time.time
}
fun Calendar.getTimeOnly(): Long {
    val calendar = Calendar.getInstance()
    calendar.set(
        0,0,get(Calendar.HOUR_OF_DAY), get(Calendar.MINUTE),0)

    calender.set(Calendar.MILLISECOND, 0)
    return calender.time.time
}*/
