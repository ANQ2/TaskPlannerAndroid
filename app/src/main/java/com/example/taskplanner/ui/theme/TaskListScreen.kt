package com.example.taskplanner.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskplanner.data.Task
import com.example.taskplanner.viewmodel.TaskViewModel

@Composable
fun TaskListScreen(navController: NavController, viewModel: TaskViewModel) {
    val tasks = viewModel.tasks.collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addTask") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)) {
            Text("Task Planner", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(tasks.value) { task ->
                    TaskItem(task = task, onComplete = {
                        viewModel.updateTask(task.copy(isCompleted = !task.isCompleted))
                    })
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onComplete: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(task.title, style = MaterialTheme.typography.titleMedium)
                Text(task.description, style = MaterialTheme.typography.bodyMedium)
            }
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onComplete() }
            )
        }
    }
}
