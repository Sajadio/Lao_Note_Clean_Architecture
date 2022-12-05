package com.sajjadio.laonote.presentation.ui.note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.domain.usecase.note.GetNotesUseCase
import com.sajjadio.laonote.domain.usecase.note.SetNoteUseCase
import com.sajjadio.laonote.domain.usecase.note.ValidateNoteTitleUseCase
import com.sajjadio.laonote.presentation.base.BaseViewModel
import com.sajjadio.laonote.utils.NetworkResponse
import com.sajjadio.laonote.utils.TAG
import com.sajjadio.laonote.utils.event.Event
import com.sajjadio.laonote.utils.extension.dateFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val setNoteUseCase: SetNoteUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val validateNoteTitleUseCase: ValidateNoteTitleUseCase,
) : BaseViewModel() {

    private val _eventResponse = MutableLiveData<Event<NetworkResponse<Any>>>()
    val eventResponse: LiveData<Event<NetworkResponse<Any>>> = _eventResponse
     val eventResponseNotes = MutableLiveData<Event<NetworkResponse<List<Note>>>>()

    val note_title = MutableLiveData<String?>(null)
    val note_subTitle = MutableLiveData<String?>(null)
    val note_description = MutableLiveData<String?>(null)
    val note_image = MutableLiveData<String?>(null)
    val note_webUrl = MutableLiveData<String?>(null)
    val note_color = MutableLiveData<Int>()
    val font_color = MutableLiveData<Int>()
    val note_date_created = MutableLiveData(Calendar.getInstance().time.toString().dateFormat())

    val date = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            getNotesUseCase().collectLatest {
                eventResponseNotes.postValue(Event(checkNetworkResponseStatus(it)))
            }
        }
    }

    fun setNote() {
        viewModelScope.launch {
            val validNoteTitle = validateNoteTitleUseCase(note_title.value.toString())
            if (!validNoteTitle.successful) {
                _eventResponse.postValue(Event(NetworkResponse.Error(validNoteTitle.errorMessage)))
                return@launch
            }
            Log.d(TAG, "launchView: ${note_color.value?.toInt()}")
            val note = Note(
                note_title = note_title.value.toString(),
                note_subTitle = note_subTitle.value.toString(),
                note_description = note_description.value.toString(),
                note_image = note_image.value.toString(),
                note_webUrl = note_webUrl.value.toString(),
                note_color = note_color.value?.toInt(),
                font_color = font_color.value?.toInt(),
            )
            setNoteUseCase(note).collectLatest { response ->
                _eventResponse.postValue(Event(checkNetworkResponseStatus(response)))
            }

        }
    }

}