package com.sajjadio.laonote.presentation.ui.auth.viewModel

import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sajjadio.laonote.data.local.data_storage.SessionManager
import com.sajjadio.laonote.domain.model.Authentication
import com.sajjadio.laonote.domain.repository.AuthRepository
import com.sajjadio.laonote.domain.usecase.auth.LogInUserUseCase
import com.sajjadio.laonote.domain.usecase.auth.LogInWithGoogleUseCase
import com.sajjadio.laonote.domain.usecase.auth.ValidatePasswordUseCase
import com.sajjadio.laonote.domain.usecase.auth.ValidateEmailUseCase
import com.sajjadio.laonote.presentation.base.BaseViewModel
import com.sajjadio.laonote.utils.NetworkResponse
import com.sajjadio.laonote.utils.event.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val logInUserUseCase: LogInUserUseCase,
    private val logInWithGoogleUseCase: LogInWithGoogleUseCase,
    private val validateUsername: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val authRepo: AuthRepository,
    private val sessionManager: SessionManager
) : BaseViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _eventResponse = MutableLiveData<Event<NetworkResponse<Any?>>>()
    val eventResponse: LiveData<Event<NetworkResponse<Any?>>> = _eventResponse

    fun logIn() {
        viewModelScope.launch {
            val userLogIn = Authentication(
                email = email.value.toString(),
                password = password.value.toString()
            )
            val validUsername = validateUsername(userLogIn.email)
            val validPassword = validatePasswordUseCase(userLogIn.password)
            if (!validUsername.successful) {
                _eventResponse.postValue(Event(NetworkResponse.Error(validUsername.errorMessage)))
            } else if (!validPassword.successful) {
                _eventResponse.postValue(Event(NetworkResponse.Error(validPassword.errorMessage)))
            } else {
                logInUserUseCase(userLogIn).collect { response ->
                    _eventResponse.postValue(Event(checkNetworkResponseStatus(response)))
                    if (response is NetworkResponse.Success){
                        val token = response.data().toString()
                        sessionManager.updateSession(token)
                    }
                }
            }
        }
    }

    suspend fun requestSignInWithGoogle() = authRepo.requestSignInWithGoogle()

    fun checkSignInWithGoogle(account: ActivityResult) {
        viewModelScope.launch {
            logInWithGoogleUseCase(account).collectLatest { response ->
                _eventResponse.postValue(Event(checkNetworkResponseStatus(response)))
                if (response is NetworkResponse.Success){
                    val token = response.data().toString()
                    sessionManager.updateSession(token)
                }
            }
        }
    }
    fun logOut(){
        viewModelScope.launch {
            sessionManager.logout()
            authRepo.logOut()
        }
    }
}