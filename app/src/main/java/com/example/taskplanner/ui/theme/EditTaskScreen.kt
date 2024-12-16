package com.example.taskplanner.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskplanner.data.Task
import com.example.taskplanner.viewmodel.TaskViewModel

@Composable
fun EditTaskScreen(
    navController: NavController,
    taskId: String?,
    viewModel: TaskViewModel
) {
    val task = viewModel.tasks.collectAsState().value.find { it.id.toString() == taskId }

    if (task != null) {
        var title by remember { mutableStateOf(task.title) }
        var description by remember { mutableStateOf(task.description) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Edit Task", style = MaterialTheme.typography.headlineLarge)

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty()) {
                        viewModel.updateTask(task.copy(title = title, description = description))
                        navController.navigate("taskList")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }
        }
    } else {
        Text("Task not found", style = MaterialTheme.typography.headlineSmall)
    }
}
