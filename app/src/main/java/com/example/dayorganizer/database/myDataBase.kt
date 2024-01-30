package com.example.dayorganizer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dayorganizer.database.dao.taskdao
import com.example.dayorganizer.database.model.Task

@Database(entities = [Task::class], version = 1)
abstract class myDataBase : RoomDatabase() {
    abstract fun getTasksDao(): taskdao

    companion object {
        val DATABBASE_NAME = "tasksdatabase"
        private var database: myDataBase? = null
        fun getInstance(context: Context): myDataBase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    myDataBase::class.java,
                    DATABBASE_NAME
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
            return database!!

        }
    }
}