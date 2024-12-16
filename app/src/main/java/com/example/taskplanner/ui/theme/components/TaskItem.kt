package com.example.taskplanner.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taskplanner.data.Task

@Composable
fun TaskItem(
    task: Task,
    onClick: (Task) -> Unit,
    onToggleComplete: (Task) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(task) }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(task.title, style = MaterialTheme.typography.titleMedium)
                Text(task.description, style = MaterialTheme.typography.bodyMedium)
            }
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onToggleComplete(task) }
            )
        }
    }
}
