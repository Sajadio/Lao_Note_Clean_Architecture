package com.sajjadio.laonote.presentation.ui.settings

import android.widget.CompoundButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sajjadio.laonote.data.local.data_storage.LocalDataStorage
import com.sajjadio.laonote.presentation.base.BaseViewModel
import com.sajjadio.laonote.utils.UiMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStorage: LocalDataStorage
) : BaseViewModel() {

    val selectedTheme = dataStorage.uiModeFlow.asLiveData()

    val isChecked: MutableLiveData<Boolean> = MutableLiveData()

    fun changeSelectedTheme(switch: CompoundButton, isChecked: Boolean) {
        viewModelScope.launch {
            when (isChecked) {
                false -> {
                    dataStorage.setUIMode(UiMode.LIGHT)
                }
                true -> {
                    dataStorage.setUIMode(UiMode.DARK)
                }
            }
        }
    }
}