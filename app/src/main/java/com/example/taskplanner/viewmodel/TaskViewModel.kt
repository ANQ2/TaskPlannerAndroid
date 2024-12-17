package com.example.taskplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskplanner.data.Task
import com.example.taskplanner.data.TaskFilter
import com.example.taskplanner.repository.TaskRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    // Состояние для хранения всех задач
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    // Состояние для хранения текущего фильтра задач
    private val _filter = MutableStateFlow(TaskFilter.ALL)
    val filter: StateFlow<TaskFilter> = _filter.asStateFlow()

    // Состояние для хранения отфильтрованных задач
    val filteredTasks: StateFlow<List<Task>> = combine(_tasks, _filter) { tasks, filter ->
        when (filter) {
            TaskFilter.ALL -> tasks
            TaskFilter.COMPLETED -> tasks.filter { it.isCompleted }
            TaskFilter.PENDING -> tasks.filter { !it.isCompleted }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // Инициализация: загрузка задач при создании ViewModel
    init {
        loadTasks()
    }

    // Загрузка всех задач из репозитория
    private fun loadTasks() {
        viewModelScope.launch {
            repository.allTasks.collect { taskList ->
                _tasks.value = taskList
            }
        }
    }

    // Установка фильтра для отображения задач
    fun setFilter(filter: TaskFilter) {
        _filter.value = filter
    }

    // Добавление новой задачи
    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.addTask(task)
            loadTasks() // Обновить задачи после добавления
        }
    }

    // Обновление существующей задачи
    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
            loadTasks() // Обновить задачи после обновления
        }
    }

    // Удаление задачи
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
            loadTasks() // Обновить задачи после удаления
        }
    }
}
