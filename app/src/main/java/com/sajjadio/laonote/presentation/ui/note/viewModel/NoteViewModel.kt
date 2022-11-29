package com.sajjadio.laonote.presentation.ui.note.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.domain.usecase.note.SetNoteUseCase
import com.sajjadio.laonote.domain.usecase.note.ValidateNoteTitleUseCase
import com.sajjadio.laonote.presentation.base.BaseViewModel
import com.sajjadio.laonote.utils.NetworkResponse
import com.sajjadio.laonote.utils.event.Event
import com.sajjadio.laonote.utils.extension.dateFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCase: SetNoteUseCase,
    private val validateNoteTitleUseCase: ValidateNoteTitleUseCase,
) : BaseViewModel() {

    private val _eventResponse = MutableLiveData<Event<NetworkResponse<Any>>>()
    val eventResponse: LiveData<Event<NetworkResponse<Any>>> = _eventResponse

    val note_title = MutableLiveData<String?>(null)
    val note_subTitle = MutableLiveData<String?>(null)
    val note_description = MutableLiveData<String?>(null)
    val note_image = MutableLiveData<String?>(null)
    val note_webUrl = MutableLiveData<String?>(null)
    val note_color = MutableLiveData<String>()
    val note_date_created = MutableLiveData(Calendar.getInstance().time.toString().dateFormat())

    val date = MutableLiveData<String>()

    fun setNote() {
        viewModelScope.launch {
            val validNoteTitle = validateNoteTitleUseCase(note_title.value.toString())
            if (!validNoteTitle.successful) {
                _eventResponse.postValue(Event(NetworkResponse.Error(validNoteTitle.errorMessage)))
                return@launch
            }
            val note = Note(
                note_id = UUID.randomUUID().toString(),
                note_title = note_title.value.toString(),
                note_subTitle = note_subTitle.value.toString(),
                note_description = note_description.value.toString(),
                note_image = note_image.value.toString(),
                note_webUrl = note_webUrl.value.toString(),
                note_color = note_color.value.toString(),
                note_date_created = note_date_created.value.toString(),
            )
            noteUseCase(note).collectLatest { response ->
                _eventResponse.postValue(Event(checkNetworkResponseStatus(response)))
            }

        }
    }

}