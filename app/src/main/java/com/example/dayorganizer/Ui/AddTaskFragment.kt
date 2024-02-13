package com.example.todo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dayorganizer.databinding.FragmentAddTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTaskFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddTaskBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun onSelectTimeClick() {
        binding.selectTimeTil.setOnClickListener {

            val calendar = Calendar.getInstance()

          /*  showTimePickerDialog(
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                title = "Select task time",
                childFragmentManager
            ) { hour, minutes ->
                val minuteString = if (minutes == 0) "00" else minutes.toString()
                binding.selectTimeTv.text =
                    "${getHourIn12(hour)}:${minuteString} ${getTimeAmPm(hour)}"
                this.timeCalendar.set(Calendar.YEAR, 0)
                this.timeCalendar.set(Calendar.MONTH, 0)
                this.timeCalendar.set(Calendar.DAY_OF_MONTH, 0)
                this.timeCalendar.set(Calendar.HOUR_OF_DAY, hour)
                this.timeCalendar.set(Calendar.MINUTE, minutes)
                this.timeCalendar.set(Calendar.SECOND, 0)
                this.timeCalendar.set(Calendar.MILLISECOND, 0)
            }*/

        }
    }





}