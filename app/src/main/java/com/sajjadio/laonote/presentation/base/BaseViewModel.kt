package com.sajjadio.laonote.presentation.base

import androidx.lifecycle.ViewModel
import com.sajjadio.laonote.utils.NetworkResponse

abstract class BaseViewModel: ViewModel() {

    fun <T> checkNetworkResponseStatus(status: NetworkResponse<T>) = when (status) {
        is NetworkResponse.Loading -> NetworkResponse.Loading
        is NetworkResponse.Success -> NetworkResponse.Success(status.data)
        is NetworkResponse.Error -> NetworkResponse.Error(status.errorMessage)
    }

}