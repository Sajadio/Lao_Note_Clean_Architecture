package com.sajjadio.laonote.presentation.ui.fragments.task

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sajjadio.laonote.data.local.data_storage.SessionManager
import com.sajjadio.laonote.domain.model.Task
import com.sajjadio.laonote.domain.usecase.ValidateDateUseCase
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
    private val searchAboutTaskUseCase: SearchAboutTaskUseCase,
    private val getTasksOrderUseCase: GetTasksOrderUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val isTaskDoneUseCase: TaskDoneUseCase,
    private val validateTitleUseCase: ValidateTitleUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val validateWebURLUseCase: ValidateWebURLUseCase,
    private val sessionManager: SessionManager
) : BaseViewModel() {

    private val _eventResponse = MutableLiveData<Event<NetworkResponse<Any>>>()
    val eventResponse: LiveData<Event<NetworkResponse<Any>>> = _eventResponse
    val eventResponseTask = MutableLiveData<Event<NetworkResponse<List<Task>>>>()
    val isLoading = MutableLiveData<Boolean>()

    val userID = MutableLiveData<String>()
    private val taskID = MutableLiveData<String>()
    val task_title = MutableLiveData("")
    val task_description = MutableLiveData("")
    val task_webUrl = MutableLiveData("")
    val date = MutableLiveData("")

    init {
        viewModelScope.launch {
            sessionManager.accessToken.collectLatest {
                getTasks(it.toString())
                userID.postValue(it.toString())
            }
        }
    }

    fun onRefresh() {
        getTasks(userID.value.toString())
    }

    fun setTask() {
        viewModelScope.launch {
            if (validateTaskFiled()) {
                val task = Task(
                    user_id = userID.value.toString(),
                    task_title = task_title.value.toString(),
                    task_description = task_description.value.toString(),
                    task_webUrl = task_webUrl.value.toString(),
                    task_date_created = date.value.toString(),
                )
                setTaskUseCase(task).collectLatest { response ->
                    _eventResponse.postValue(Event(checkNetworkResponseStatus(response)))
                }
            } else
                return@launch
        }
    }

    fun getTasks(userID: String) {
        viewModelScope.launch {
            getTasksUseCase(userID).collectLatest {
                eventResponseTask.postValue(Event(checkNetworkResponseStatus(it)))
                isLoading.postValue(it is NetworkResponse.Loading)
            }
        }
    }

    fun getTasksByTitle(title: String) {
        viewModelScope.launch {
            val task = Task(
                user_id = userID.value.toString(),
                task_title = title
            )
            searchAboutTaskUseCase(task).collectLatest { response ->
                eventResponseTask.postValue(Event(checkNetworkResponseStatus(response)))
                isLoading.postValue(response is NetworkResponse.Loading)
            }
        }
    }

    fun getCompleteTasks() {
        viewModelScope.launch {
            val task = Task(
                user_id = userID.value.toString(),
                is_done = true
            )
            getTasksOrderUseCase(task).collectLatest { response ->
                eventResponseTask.postValue(Event(checkNetworkResponseStatus(response)))
                isLoading.postValue(response is NetworkResponse.Loading)
            }
        }
    }

    fun getInCompleteTasks() {
        viewModelScope.launch {
            val task = Task(
                user_id = userID.value.toString(),
                is_done = false
            )
            getTasksOrderUseCase(task).collectLatest { response ->
                eventResponseTask.postValue(Event(checkNetworkResponseStatus(response)))
                isLoading.postValue(response is NetworkResponse.Loading)
            }
        }
    }

    fun updateTask() {
        viewModelScope.launch {
            if (validateTaskFiled()) {
                val task = Task(
                    user_id = userID.value.toString(),
                    task_id = taskID.value.toString(),
                    task_title = task_title.value.toString(),
                    task_description = task_description.value.toString(),
                    task_webUrl = task_webUrl.value.toString(),
                    task_date_created = date.value.toString(),
                )
                updateTaskUseCase(task).collectLatest { response ->
                    _eventResponse.postValue(Event(checkNetworkResponseStatus(response)))
                }
            } else
                return@launch
        }
    }

    fun isTaskDoneUseCase(taskId: String, isDone: Boolean) {
        viewModelScope.launch {
            val task = Task(
                user_id = userID.value.toString(),
                task_id = taskId,
                is_done = isDone
            )
            isTaskDoneUseCase(task).collectLatest { response ->
                _eventResponse.postValue(Event(checkNetworkResponseStatus(response)))
            }
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            val task = Task(
                user_id = userID.value.toString(),
                task_id = taskID.value.toString()
            )
            deleteTaskUseCase(task).collectLatest {
                _eventResponse.postValue(Event(checkNetworkResponseStatus(it)))
                isLoading.postValue(it is NetworkResponse.Loading)
            }
        }
    }

    private fun validateTaskFiled(): Boolean {
        val validTaskTitle = validateTitleUseCase(task_title.value.toString())
        val validTaskDate = validateDateUseCase(date.value.toString())
        if (!validTaskTitle.successful) {
            _eventResponse.postValue(Event(NetworkResponse.Error(validTaskTitle.errorMessage)))
            return false
        }
        if (!validTaskDate.successful) {
            _eventResponse.postValue(Event(NetworkResponse.Error(validTaskDate.errorMessage)))
            return false
        }
        val validWeURL =
            validateWebURLUseCase(
                task_webUrl.value.toString(),
                Patterns.WEB_URL.matcher(task_webUrl.value.toString()).matches()
            )
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