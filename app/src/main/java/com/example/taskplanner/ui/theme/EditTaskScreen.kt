package com.example.taskplanner.ui  // Убедитесь, что это соответствует пути файла

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskplanner.data.Task
import com.example.taskplanner.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)  // Убирает предупреждение об экспериментальном API
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

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Edit Task") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
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
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Changes")
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text("Task not found", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Back to Task List")
            }
        }
    }
}
