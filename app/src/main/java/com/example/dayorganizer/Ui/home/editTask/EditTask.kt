package com.example.dayorganizer.Ui.home.editTask

import CalendarExtensions.formatDate
import CalendarExtensions.formatTime
import CalendarExtensions.getDateOnly
import CalendarExtensions.getTimeOnly
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import com.example.dayorganizer.R
import com.example.dayorganizer.Ui.model.Constants
import com.example.dayorganizer.database.model.Task
import com.example.dayorganizer.database.myDataBase
import com.example.dayorganizer.databinding.ActivityEditTaskBinding
import com.example.dayorganizer.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class EditTask : AppCompatActivity() {
    lateinit var binding: ActivityEditTaskBinding
    private var task: Task? = null
    private val calendar = Calendar.getInstance()
    private val timeFormatter = SimpleDateFormat("hh:mm a")
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.homeToolbar)
        initView()
        getTask()
        setUpViews()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }
    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun getTask() {
        task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.TASK_KAY, Task::class.java)
        } else {
            intent.getParcelableExtra(Constants.TASK_KAY)
        }
    }

    private fun setUpViews() {
        binding.editTaskContent.editTitle.setText(task?.title)
        binding.editTaskContent.editDescription.setText(task?.content)
        binding.editTaskContent.editTime.text = timeFormatter.format(task?.time)
        binding.editTaskContent.editDate.text = dateFormatter.format(task?.date)
        binding.editTaskContent.saveChangesBtn.setOnClickListener {
            updateTask()
        }
        binding.editTaskContent.editDateLayout.setOnClickListener {
            showDateDialog()
        }
        binding.editTaskContent.editTimeLayout.setOnClickListener {
            showTimeDialog()
        }
    }

    private fun showTimeDialog() {
        val timePicker = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                binding.editTaskContent.editTime.text = calendar.formatTime()
                binding.editTaskContent.editTimeLayout.error = null
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        timePicker.show()
    }

    private fun showDateDialog() {
        val datePicker = DatePickerDialog(this)
        datePicker.setOnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            binding.editTaskContent.editDate.text = calendar.formatDate()
            binding.editTaskContent.editDateLayout.error = null
        }
        datePicker.show()
    }

    private fun isValidTask(): Boolean {
        var isValid = true
        val newTitle = binding.editTaskContent.editTitle.text.toString()
        val newDescription = binding.editTaskContent.editDescription.text.toString()
        if (newTitle.isBlank()) {
            binding.editTaskContent.editTitleLayout.error = "required task title"
            isValid = false
        } else {
            task?.title = newTitle
            binding.editTaskContent.editTitleLayout.error = null
        }
        if (newDescription.isBlank()) {
            binding.editTaskContent.editDescriptionLayout.error = "required task description"
            isValid = false
        } else {
            task?.content = newDescription
            binding.editTaskContent.editDescriptionLayout.error = null
        }
        if (binding.editTaskContent.editTime.text.isBlank()) {
            binding.editTaskContent.editTimeLayout.error = "required task time"
            isValid = false
        } else {
            task?.time = calendar.getTimeOnly()
        }
        if (binding.editTaskContent.editDate.text.isBlank()) {
            binding.editTaskContent.editDateLayout.error = "required task date"
            isValid = false
        } else {
            task?.date = calendar.getDateOnly()
        }
        return isValid
    }

    private fun updateTask() {
        if (!isValidTask())
            return
        myDataBase.getInstance(this)
            .getTasksDao()
            .updateTask(task!!)
        Toast.makeText(applicationContext, "Task edited successfully", Toast.LENGTH_LONG).show()
        finish()
    }

}