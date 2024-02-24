package com.example.dayorganizer.Ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dayorganizer.R
import com.example.dayorganizer.database.myDataBase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myDataBase.getInstance(this).getTasksDao().getAllTasks()
    }
}