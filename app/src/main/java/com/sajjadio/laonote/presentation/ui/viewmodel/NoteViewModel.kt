package com.sajjadio.laonote.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor():ViewModel() {

    val pickerColor = MutableLiveData<String>()

    val date = MutableLiveData<String>()

    init {
        Log.d("TestingApp", "onItemClick: ${pickerColor.value}")
    }
}