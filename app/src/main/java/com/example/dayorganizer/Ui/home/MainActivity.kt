package com.example.dayorganizer.Ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.dayorganizer.R
import com.example.dayorganizer.Ui.home.settings.SettingsFragment
import com.example.dayorganizer.Ui.home.tasksLis.TasksListFragment
import com.example.dayorganizer.database.myDataBase
import com.example.dayorganizer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
        myDataBase.getInstance(this).getTasksDao().getAllTasks()
    }

    private fun setUpViews() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.nav_tasks) {
                ShowFragment(TasksListFragment())
            }else{
                ShowFragment(SettingsFragment())
            }
            return@setOnItemSelectedListener  true
        }
        binding.bottomNavigation.selectedItemId = R.id.nav_tasks
    }

    private fun ShowFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()

    }
}