package com.example.taskplanner.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskplanner.data.Task
import com.example.taskplanner.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddTaskScreen(navController: NavController, viewModel: TaskViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Work") }
    val categories = listOf("Work", "Personal", "Shopping", "Other")
    var isCategoryMenuExpanded by remember { mutableStateOf(false) }

    // Date management
    val calendar = Calendar.getInstance()
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var selectedDate by remember { mutableStateOf(dateFormatter.format(calendar.time)) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Add New Task", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Title input
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Description input
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Category Dropdown
        Text("Category", style = MaterialTheme.typography.titleMedium)
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { isCategoryMenuExpanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(category)
            }
            DropdownMenu(
                expanded = isCategoryMenuExpanded,
                onDismissRequest = { isCategoryMenuExpanded = false }
            ) {
                categories.forEach { cat ->
                    DropdownMenuItem(
                        text = { Text(cat) },
                        onClick = {
                            category = cat
                            isCategoryMenuExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Date Picker
        Text("Due Date", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = selectedDate,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    showDatePicker(context, calendar) { updatedDate ->
                        selectedDate = dateFormatter.format(updatedDate)
                    }
                }) {
                    Icon(Icons.Filled.CalendarMonth, contentDescription = "Select Date")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = {
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    viewModel.addTask(
                        Task(
                            title = title,
                            description = description,
                            category = category,
                            dueDate = selectedDate
                        )
                    )
                    navController.navigate("taskList")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Add Task")
        }
    }
}

fun showDatePicker(context: android.content.Context, calendar: Calendar, onDateSelected: (Date) -> Unit) {
    android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            onDateSelected(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}
