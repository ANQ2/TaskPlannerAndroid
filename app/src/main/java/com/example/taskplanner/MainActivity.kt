package com.example.taskplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.taskplanner.data.AppDatabase
import com.example.taskplanner.repository.TaskRepository
import com.example.taskplanner.viewmodel.TaskViewModel
import com.example.taskplanner.navigation.NavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация базы данных
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "task-planner-db"
        ).build()

        // Создаём DAO и репозиторий
        val userDao = db.userDao()
        val taskDao = db.taskDao()
        val taskRepository = TaskRepository(taskDao)

        // Создаём ViewModel
        val taskViewModel = TaskViewModel(taskRepository)

        setContent {
            val navController = rememberNavController()
            NavGraph(navController, userDao, taskViewModel)
        }
    }
}
