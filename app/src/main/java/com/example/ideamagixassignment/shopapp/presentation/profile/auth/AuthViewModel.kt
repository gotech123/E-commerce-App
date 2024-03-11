package com.example.ideamagixassignment.shopapp.presentation.profile.auth

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import com.example.ideamagixassignment.shopapp.models.domain.Address
import com.example.ideamagixassignment.shopapp.models.mapper.UserMapper
import com.example.ideamagixassignment.shopapp.models.network.LoginResponse
import com.example.ideamagixassignment.shopapp.models.network.NetworkUser
import com.example.ideamagixassignment.shopapp.redux.ApplicationState
import com.example.ideamagixassignment.shopapp.redux.Store
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    private val authRepository: AuthRepository,
    private val userMapper: UserMapper
) : ViewModel() {

    private val _intentFlow = MutableStateFlow<Intent?>(null)
    val intentFlow: StateFlow<Intent?> = _intentFlow

    private fun ResponseBody?.parseError(): String? {
        return this?.byteStream()?.bufferedReader()?.readLine()?.capitalize()
    }

    fun login(username: String, password: String) = viewModelScope.launch {
        val response: Response<LoginResponse> = authRepository.login(username, password)
        if (response.isSuccessful) {
            val donUserResponse: Response<NetworkUser> = authRepository.fetchDon()
            store.update { applicationState ->
                val authState = donUserResponse.body()?.let { body ->
                    ApplicationState.AuthState.Auth(user = userMapper.buildFrom(body))
                } ?: ApplicationState.AuthState.UnAuth(
                    errorString = response.errorBody()?.parseError()
                )
                return@update applicationState.copy(authState = authState)
            }

        } else {
            store.update { applicationState ->
                applicationState.copy(
                    authState = ApplicationState.AuthState.UnAuth(
                        errorString = response.errorBody()?.parseError()
                    )
                )
            }
        }
    }

    fun logout() = viewModelScope.launch {
        store.update { applicationState ->
            applicationState.copy(authState = ApplicationState.AuthState.UnAuth())
        }
    }

    fun sendCallIntent() = viewModelScope.launch {
        val phoneNumber: String = store.read {
            (it.authState as ApplicationState.AuthState.Auth).user.phoneNumber
        }
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${phoneNumber}")
        _intentFlow.emit(intent)
    }

    fun sendLocation() = viewModelScope.launch {
        val address: Address = store.read {
            (it.authState as ApplicationState.AuthState.Auth).user.address
        }
        val intentUri = Uri.parse("geo:${address.lat},${address.long}")
        val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
        _intentFlow.emit(mapIntent)
    }

    private fun String.capitalize(): String {
        return this.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }
}