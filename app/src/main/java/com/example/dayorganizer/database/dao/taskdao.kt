package com.example.dayorganizer.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dayorganizer.database.model.Task

interface taskdao {
    @Insert
    fun insertTask(task:Task)
    @Update
    fun updateTask(task: Task)
    @Delete
    fun deleteTask(task: Task)
    @Query("select * from task")
    fun getAllTasks():(List<Task>)

}