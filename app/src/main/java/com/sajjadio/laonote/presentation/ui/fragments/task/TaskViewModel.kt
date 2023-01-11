package com.sajjadio.laonote.presentation.ui.fragments.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sajjadio.laonote.domain.model.Task
import com.sajjadio.laonote.domain.usecase.ValidateTitleUseCase
import com.sajjadio.laonote.domain.usecase.ValidateWebURLUseCase
import com.sajjadio.laonote.domain.usecase.task.*
import com.sajjadio.laonote.presentation.base.BaseViewModel
import com.sajjadio.laonote.utils.NetworkResponse
import com.sajjadio.laonote.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val setTaskUseCase: SetTaskUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val getTasksByTitleUseCase: GetTasksByTitleUseCase,
    private val updateTaskByIDUseCase: UpdateTaskByIDUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val isTaskDoneUseCase: TaskDoneUseCase,
    private val validateTitleUseCase: ValidateTitleUseCase,
    private val validateWebURLUseCase: ValidateWebURLUseCase,
) : BaseViewModel() {

    private val _eventResponse = MutableLiveData<Event<NetworkResponse<Any>>>()
    val eventResponse: LiveData<Event<NetworkResponse<Any>>> = _eventResponse
    val eventResponseTask = MutableLiveData<Event<NetworkResponse<List<Task>>>>()
    val isLoading = MutableLiveData<Boolean>()

    val taskID = MutableLiveData<String>()
    val task_title = MutableLiveData("")
    val task_description = MutableLiveData("")
    val task_webUrl = MutableLiveData("")
    val date = MutableLiveData("")
    val isTaskDone = MutableLiveData<Boolean>()

    init {
        getTasks()
    }

    fun onRefresh() {
        getTasks()
    }

    fun setTask() {
        viewModelScope.launch {
            if (validateTaskFiled()) {
                val task = Task(
                    task_title = task_title.value.toString(),
                    task_description = task_description.value.toString(),
                    task_webUrl = task_webUrl.value.toString(),
                    task_date_created = date.value.toString(),
                    is_done = false
                )
                setTaskUseCase(task).collectLatest { response ->
                    _eventResponse.postValue(Event(checkNetworkResponseStatus(response)))
                }
            } else
                return@launch
        }
    }

    private fun getTasks() {
        viewModelScope.launch {
            getTasksUseCase().collectLatest {
                eventResponseTask.postValue(Event(checkNetworkResponseStatus(it)))
                println(it.data())
                isLoading.postValue(it is NetworkResponse.Loading)
            }
        }
    }

    fun getTasksByTitle(title: String) {
        if (title.isEmpty()) {
            getTasks()
        } else
            viewModelScope.launch {
                getTasksByTitleUseCase(title).collectLatest {
                    eventResponseTask.postValue(Event(checkNetworkResponseStatus(it)))
                    isLoading.postValue(it is NetworkResponse.Loading)
                }
            }
    }


    fun updateTaskByID() {
        viewModelScope.launch {
            if (validateTaskFiled()) {
                val task = Task(
                    task_id = taskID.value.toString(),
                    task_title = task_title.value.toString(),
                    task_description = task_description.value.toString(),
                    task_webUrl = task_webUrl.value.toString(),
                    task_date_created = date.value.toString(),
                    is_done = isTaskDone.value
                )
                updateTaskByIDUseCase(task).collectLatest { response ->
                    _eventResponse.postValue(Event(checkNetworkResponseStatus(response)))
                }
            } else
                return@launch
        }
    }

    fun isTaskDoneUseCase(taskId: String, isDone: Boolean) {
        viewModelScope.launch {
            val task = Task(
                task_id = taskId,
                is_done = isDone
            )
            println("$taskId $isDone")
            isTaskDoneUseCase(task).collectLatest { response ->
                _eventResponse.postValue(Event(checkNetworkResponseStatus(response)))
            }
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            deleteTaskUseCase(taskID.value.toString()).collectLatest {
                _eventResponse.postValue(Event(checkNetworkResponseStatus(it)))
                isLoading.postValue(it is NetworkResponse.Loading)
            }
        }
    }

    private fun validateTaskFiled(): Boolean {
        val validTaskTitle = validateTitleUseCase(task_title.value.toString())
        if (!validTaskTitle.successful) {
            _eventResponse.postValue(Event(NetworkResponse.Error(validTaskTitle.errorMessage)))
            return false
        }
        val validWeURL = validateWebURLUseCase(task_webUrl.value.toString())
        if (!validWeURL.successful) {
            _eventResponse.postValue(Event(NetworkResponse.Error(validWeURL.errorMessage)))
            return false
        }
        return true
    }

    fun showTaskDetails(task: Task) {
        taskID.postValue(task.task_id)
        task_title.postValue(task.task_title)
        task_description.postValue(task.task_description)
        task_webUrl.postValue(task.task_webUrl)
        date.postValue(task.task_date_created!!)
    }

}