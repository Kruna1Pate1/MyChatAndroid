package com.krunal.mychat.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.krunal.mychat.data.local.model.RegisterDetail
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
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val registerDetail = MutableStateFlow(RegisterDetail())

    fun register(onSuccess: () -> Unit) {
        val name = registerDetail.value.name
        val email = registerDetail.value.email
        val password = registerDetail.value.password

        if (!validate(name, email, password)) return
        viewModelScope.launch(ioDispatcher) {
            authRepository.register(name, email, password)
                .onSuccess {
                    withContext(mainDispatcher) { onSuccess() }
                }
                .onError { code, message ->
                    Timber.d("register: $code $message")
                }
        }
    }

    private fun validate(name: String, email: String, password: String): Boolean {
        if (name.isEmpty()) {
            Timber.d("Name is empty")
            return false
        }

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