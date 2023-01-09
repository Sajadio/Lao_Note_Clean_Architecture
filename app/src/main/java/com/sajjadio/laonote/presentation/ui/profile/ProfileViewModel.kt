package com.sajjadio.laonote.presentation.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sajjadio.laonote.data.local.data_storage.SessionManager
import com.sajjadio.laonote.domain.model.User
import com.sajjadio.laonote.domain.usecase.profile.GetUserInfoUseCase
import com.sajjadio.laonote.domain.usecase.profile.LogoutUserUseCase
import com.sajjadio.laonote.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class ProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val sessionManager: SessionManager,
) : BaseViewModel() {

    val user = MutableLiveData<User>()
    val isLogIn = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            getUserInfoUseCase().collect { info ->
                user.postValue(info.data())
            }
            sessionManager.accessToken.collect { token ->
                isLogIn.postValue(token?.isNotEmpty())
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            logoutUserUseCase()
        }
    }
}