package com.example.dayorganizer.Ui.home.editTask

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.example.dayorganizer.R
import com.example.dayorganizer.databinding.ActivityEditTaskBinding
import com.example.dayorganizer.databinding.ActivityMainBinding

class EditTask : AppCompatActivity() {
    lateinit var binding: ActivityEditTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

}