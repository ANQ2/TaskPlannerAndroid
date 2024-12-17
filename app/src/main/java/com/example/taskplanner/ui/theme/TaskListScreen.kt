package com.example.taskplanner.ui.theme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskplanner.data.TaskFilter
import com.example.taskplanner.ui.components.TaskItem
import com.example.taskplanner.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(navController: NavController, viewModel: TaskViewModel) {
    val tasks by viewModel.filteredTasks.collectAsState() // Используем `by` для уменьшения boilerplate
    var filterMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Planner") },
                actions = {
                    FilterMenu(
                        expanded = filterMenuExpanded,
                        onExpandChange = { filterMenuExpanded = it },
                        viewModel = viewModel
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addTask") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (tasks.isEmpty()) {
                // Пустое состояние
                EmptyStateMessage()
            } else {
                // Список задач
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(tasks) { task ->
                        TaskItem(
                            task = task,
                            onEdit = { navController.navigate("editTask/${task.id}") },
                            onDelete = { viewModel.deleteTask(task) },
                            onToggleComplete = {
                                viewModel.updateTask(task.copy(isCompleted = !task.isCompleted))
                            }
                        )
                        Divider(color = Color.LightGray, thickness = 0.5.dp)
                    }
                }
            }
        }
    }
}

@Composable
fun FilterMenu(
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    viewModel: TaskViewModel
) {
    Box {
        IconButton(onClick = { onExpandChange(true) }) {
            Icon(Icons.Default.FilterList, contentDescription = "Filter")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandChange(false) }
        ) {
            DropdownMenuItem(
                text = { Text("All Tasks") },
                onClick = {
                    viewModel.setFilter(TaskFilter.ALL)
                    onExpandChange(false)
                }
            )
            DropdownMenuItem(
                text = { Text("Completed Tasks") },
                onClick = {
                    viewModel.setFilter(TaskFilter.COMPLETED)
                    onExpandChange(false)
                }
            )
            DropdownMenuItem(
                text = { Text("Pending Tasks") },
                onClick = {
                    viewModel.setFilter(TaskFilter.PENDING)
                    onExpandChange(false)
                }
            )
        }
    }
}

@Composable
fun EmptyStateMessage() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No tasks available.\nCreate your first task!",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}
