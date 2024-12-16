package com.example.taskplanner.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskplanner.data.UserDao
import com.example.taskplanner.viewmodel.TaskViewModel
import com.example.taskplanner.ui.*

@Composable
fun NavGraph(
    navController: NavHostController,
    userDao: UserDao,
    viewModel: TaskViewModel
) {
    NavHost(navController, startDestination = "register") {
        composable("register") { RegistrationScreen(navController, userDao) }
        composable("login") { LoginScreen(navController, userDao) }
        composable("taskList") { TaskListScreen(navController, viewModel) }
        composable("addTask") { AddTaskScreen(navController, viewModel) }
        composable("editTask/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            EditTaskScreen(navController, taskId, viewModel)
        }
    }
}
