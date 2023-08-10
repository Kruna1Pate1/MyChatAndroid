package com.krunal.mychat.ui.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.krunal.mychat.data.local.model.LoginDetail
import com.krunal.mychat.data.repository.AuthRepository
import com.krunal.mychat.di.IoDispatcher
import com.krunal.mychat.di.MainDispatcher
import com.krunal.mychat.ui.base.BaseViewModel
import com.krunal.mychat.utils.extension.onError
import com.krunal.mychat.utils.extension.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val loginDetail = MutableStateFlow(LoginDetail())

    fun login(onSuccess: () -> Unit) {
        val email = loginDetail.value.email
        val password = loginDetail.value.password

        if (!validate(email, password)) return
        viewModelScope.launch(ioDispatcher) {
            authRepository.login(email, password)
                .onSuccess {
                    withContext(mainDispatcher) { onSuccess() }
                }
                .onError { code, message ->
                    Timber.d("login: $code $message")
                }
        }
    }

    private fun validate(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            Timber.d("Email is empty")
            return false
        }

        if (password.isEmpty()) {
            Timber.d("Password is empty")
            return false
        }
        return true
    }
}