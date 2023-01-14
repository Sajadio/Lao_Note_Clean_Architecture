package com.sajjadio.laonote.presentation.ui.fragments.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sajjadio.laonote.domain.model.EventModel
import com.sajjadio.laonote.domain.usecase.ValidateDateUseCase
import com.sajjadio.laonote.domain.usecase.ValidateTitleUseCase
import com.sajjadio.laonote.domain.usecase.event.*
import com.sajjadio.laonote.presentation.base.BaseViewModel
import com.sajjadio.laonote.utils.NetworkResponse
import com.sajjadio.laonote.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val setEventUseCase: SetEventUseCase,
    private val updateEventUseCase: UpdateEventUseCase,
    private val getEventsUseCase: GetEventsUseCase,
    private val getEventsByDateUseCase: GetEventsByDateUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val validateTitleUseCase: ValidateTitleUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    ) : BaseViewModel() {

    private val _eventResponse = MutableLiveData<Event<NetworkResponse<Any>>>()
    val eventResponse: LiveData<Event<NetworkResponse<Any>>> = _eventResponse
    val eventResponseEvent = MutableLiveData<Event<NetworkResponse<List<EventModel>>>>()
    val isLoading = MutableLiveData<Boolean>()

    val event_title = MutableLiveData("")
    val event_description = MutableLiveData("")
    val date = MutableLiveData("")
    val eventID = MutableLiveData<String>()

    init {
        getEvents()
    }

    fun onRefresh() {
        getEvents()
    }

    private fun getEvents() {
        viewModelScope.launch {
            getEventsUseCase().collectLatest {
                eventResponseEvent.postValue(Event(checkNetworkResponseStatus(it)))
                isLoading.postValue(it is NetworkResponse.Loading)
            }
        }
    }

    fun getEventsByDate(date: String) {
        viewModelScope.launch {
            getEventsByDateUseCase(date).collectLatest {
                eventResponseEvent.postValue(Event(checkNetworkResponseStatus(it)))
                isLoading.postValue(it is NetworkResponse.Loading)
            }
        }
    }

    fun deleteEvent() {
        viewModelScope.launch {
            deleteEventUseCase(eventID.value.toString()).collectLatest {
                _eventResponse.postValue(Event(checkNetworkResponseStatus(it)))
                isLoading.postValue(it is NetworkResponse.Loading)
            }
        }
    }

    fun setEvent() {
        viewModelScope.launch {
            if (validateEventFiled()) {
                val event = EventModel(
                    event_title = event_title.value.toString(),
                    event_description = event_description.value.toString(),
                    event_date_created = date.value.toString()
                )
                setEventUseCase(event).collectLatest { response ->
                    _eventResponse.postValue(Event(checkNetworkResponseStatus(response)))
                }
            } else
                return@launch
        }
    }

    fun updateEvent() {
        viewModelScope.launch {
            if (validateEventFiled()) {
                val event = EventModel(
                    event_id = eventID.value.toString(),
                    event_title = event_title.value.toString(),
                    event_description = event_description.value.toString(),
                    event_date_created = date.value.toString()
                )
                updateEventUseCase(event).collectLatest { response ->
                    _eventResponse.postValue(Event(checkNetworkResponseStatus(response)))
                }
            } else
                return@launch
        }
    }

    private fun validateEventFiled(): Boolean {
        val validEventTitle = validateTitleUseCase(event_title.value.toString())
        val validEventDate = validateDateUseCase(date.value.toString())
        if (!validEventTitle.successful) {
            _eventResponse.postValue(Event(NetworkResponse.Error(validEventTitle.errorMessage)))
            return false
        }
        if (!validEventDate.successful) {
            _eventResponse.postValue(Event(NetworkResponse.Error(validEventDate.errorMessage)))
            return false
        }
        return true
    }

    fun showEventDetails(event: EventModel) {
        eventID.postValue(event.event_id)
        event_title.postValue(event.event_title)
        event_description.postValue(event.event_description)
        date.postValue(event.event_date_created!!)
    }

}