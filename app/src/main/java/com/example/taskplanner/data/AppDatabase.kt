package com.example.taskplanner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskplanner.data.UserDao
import com.example.taskplanner.data.TaskDao
import com.example.taskplanner.data.User
import com.example.taskplanner.data.Task

@Database(entities = [User::class, Task::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "task-planner-db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
