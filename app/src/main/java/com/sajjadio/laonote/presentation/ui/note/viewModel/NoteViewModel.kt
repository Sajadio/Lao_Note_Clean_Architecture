package com.sajjadio.laonote.presentation.ui.note.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.presentation.base.BaseViewModel
import com.sajjadio.laonote.utils.NetworkResponse
import com.sajjadio.laonote.utils.event.Event
import com.sajjadio.laonote.utils.extension.dateFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
) : BaseViewModel() {

    private val _eventResponse = MutableLiveData<Event<NetworkResponse<Int>>>()
    val eventResponse: LiveData<Event<NetworkResponse<Int>>> = _eventResponse

    val note_title = MutableLiveData<String>()
    val note_subTitle = MutableLiveData<String>()
    val note_description = MutableLiveData<String>()
    val note_image = MutableLiveData<String>()
    val note_webUrl = MutableLiveData<String>()
    val note_color = MutableLiveData<String>()
    val note_date_created = MutableLiveData(Calendar.getInstance().time.toString().dateFormat())

    val date = MutableLiveData<String>()


}