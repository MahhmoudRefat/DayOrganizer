package com.example.dayorganizer.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dayorganizer.database.model.Task
@Dao
interface taskdao {
    @Insert
    fun insertTask(task:Task)
    @Update
    fun updateTask(task: Task)
    @Delete
    fun deleteTask(task: Task)
    //the second datetime is the one in function parameter
    @Query("select * from Task where date = :date order by time DESC")
    fun getAllTasksByDate(date: Long): List<Task>
    @Query("select * from task")
    fun getAllTasks():(List<Task>)
}