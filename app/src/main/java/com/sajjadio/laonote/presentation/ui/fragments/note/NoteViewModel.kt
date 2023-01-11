package com.sajjadio.laonote.presentation.ui.fragments.note

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.domain.model.User
import com.sajjadio.laonote.domain.repository.NoteRepository
import com.sajjadio.laonote.domain.usecase.ValidateTitleUseCase
import com.sajjadio.laonote.domain.usecase.ValidateWebURLUseCase
import com.sajjadio.laonote.domain.usecase.note.*
import com.sajjadio.laonote.domain.usecase.profile.GetUserInfoUseCase
import com.sajjadio.laonote.presentation.base.BaseViewModel
import com.sajjadio.laonote.utils.FULL_DATE
import com.sajjadio.laonote.utils.NetworkResponse
import com.sajjadio.laonote.utils.STANDARD_DATE
import com.sajjadio.laonote.utils.TAG
import com.sajjadio.laonote.utils.event.Event
import com.sajjadio.laonote.utils.extension.formatDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val setNoteUseCase: SetNoteUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val getNotesByTitleUseCase: GetNotesByTitleUseCase,
    private val updateNoteByIDUseCase: UpdateNoteByIDUseCase,
    private val deleteNotesUseCase: DeleteNotesUseCase,
    private val validateTitleUseCase: ValidateTitleUseCase,
    private val validateWebURLUseCase: ValidateWebURLUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val noteRepo: NoteRepository
) : BaseViewModel() {

    private val _eventResponse = MutableLiveData<Event<NetworkResponse<Any>>>()
    val eventResponse: LiveData<Event<NetworkResponse<Any>>> = _eventResponse
    val eventManageImageStorage = MutableLiveData<Event<NetworkResponse<Any>>>()
    val eventResponseNotes = MutableLiveData<Event<NetworkResponse<List<Note>>>>()
    val isLoading = MutableLiveData<Boolean>()

    val notID = MutableLiveData<String>()
    val note_title = MutableLiveData("")
    val note_subTitle = MutableLiveData("")
    val note_description = MutableLiveData("")
    val note_image = MutableLiveData("")
    val note_webUrl = MutableLiveData("")
    val note_color = MutableLiveData<Int>()
    val font_color = MutableLiveData<Int>()
    val note_date_created = MutableLiveData(
        Calendar.getInstance().time.toString().formatDate(
            STANDARD_DATE, FULL_DATE
        )
    )
    val note_last_update = MutableLiveData<String>()


    val user = MutableLiveData<User>()

    init {
        viewModelScope.launch {
            getUserInfoUseCase().collect { info ->
                user.postValue(info.data())
            }
        }
        getNotes()
    }


    fun onRefresh() {
        getNotes()
    }

    fun manageImageStorageUseCase(imgUri: Uri) {
        viewModelScope.launch {
            _eventResponse.postValue(Event(checkNetworkResponseStatus(NetworkResponse.Loading)))
            noteRepo.manageImageStorage(imgUri).addOnCompleteListener {
                if (it.isSuccessful) {
                    eventManageImageStorage.postValue(
                        Event(
                            checkNetworkResponseStatus(
                                NetworkResponse.Success(null)
                            )
                        )
                    )
                    it.result.storage.downloadUrl.addOnSuccessListener { uri ->
                        note_image.postValue(uri.toString())
                    }
                } else
                    _eventResponse.postValue(
                        Event(
                            checkNetworkResponseStatus(
                                NetworkResponse.Error(
                                    it.exception.toString()
                                )
                            )
                        )
                    )
            }
        }
    }


    private fun getNotes() {
        viewModelScope.launch {
            getNotesUseCase().collectLatest {
                eventResponseNotes.postValue(Event(checkNetworkResponseStatus(it)))
                isLoading.postValue(it is NetworkResponse.Loading)
            }
        }
    }

    fun getNotesByTitle(title: String) {
        if (title.isEmpty()) {
            getNotes()
        } else
            viewModelScope.launch {
                getNotesByTitleUseCase(title).collectLatest {
                    eventResponseNotes.postValue(Event(checkNetworkResponseStatus(it)))
                    isLoading.postValue(it is NetworkResponse.Loading)
                }
            }
    }

    fun deleteNotes() {
        viewModelScope.launch {
            deleteNotesUseCase(notID.value.toString()).collectLatest {
                _eventResponse.postValue(Event(checkNetworkResponseStatus(it)))
                isLoading.postValue(it is NetworkResponse.Loading)
            }
        }
    }

    fun setNote() {
        viewModelScope.launch {
            if (validateNoteFiled()) {
                val note = Note(
                    note_title = note_title.value.toString(),
                    note_subTitle = note_subTitle.value.toString(),
                    note_description = note_description.value.toString(),
                    note_image = note_image.value.toString(),
                    note_webUrl = note_webUrl.value.toString(),
                    note_color = note_color.value?.toInt(),
                    font_color = font_color.value?.toInt(),
                    note_date_created = note_date_created.value.toString(),
                )
                setNoteUseCase(note).collectLatest { response ->
                    _eventResponse.postValue(Event(checkNetworkResponseStatus(response)))
                }
            } else
                return@launch
        }
    }

    fun updateNoteByID() {
        viewModelScope.launch {
            if (validateNoteFiled()) {
                val note = Note(
                    note_id = notID.value.toString(),
                    note_title = note_title.value.toString(),
                    note_subTitle = note_subTitle.value.toString(),
                    note_description = note_description.value.toString(),
                    note_image = note_image.value.toString(),
                    note_webUrl = note_webUrl.value.toString(),
                    note_color = note_color.value?.toInt(),
                    font_color = font_color.value?.toInt(),
                    note_last_update = Calendar.getInstance().time.toString().formatDate(
                        STANDARD_DATE, FULL_DATE
                    ).toString()
                )
                updateNoteByIDUseCase(note).collectLatest { response ->
                    _eventResponse.postValue(Event(checkNetworkResponseStatus(response)))
                }
            } else
                return@launch
        }
    }

    private fun validateNoteFiled(): Boolean {
        val validNoteTitle = validateTitleUseCase(note_title.value.toString())
        if (!validNoteTitle.successful) {
            _eventResponse.postValue(Event(NetworkResponse.Error(validNoteTitle.errorMessage)))
            return false
        }
        val validWeURL = validateWebURLUseCase(note_webUrl.value.toString())
        if (!validWeURL.successful) {
            _eventResponse.postValue(Event(NetworkResponse.Error(validWeURL.errorMessage)))
            return false
        }
        return true
    }

    fun showNoteDetails(note: Note) {
        Log.d(TAG, "showNoteDetails: ${note.note_image}")
        notID.postValue(note.note_id)
        note_title.postValue(note.note_title)
        note_subTitle.postValue(note.note_subTitle)
        note_description.postValue(note.note_description)
        note_image.postValue(note.note_image.toString())
        note_webUrl.postValue(note.note_webUrl)
        note_color.postValue(note.note_color!!)
        font_color.postValue(note.font_color!!)
        note_date_created.postValue(note.note_date_created)
        note_last_update.postValue(note.note_last_update)
    }

}