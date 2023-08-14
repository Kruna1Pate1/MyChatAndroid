package com.krunal.mychat.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.krunal.mychat.data.remote.model.User
import com.krunal.mychat.data.repository.UserRepository
import com.krunal.mychat.di.IoDispatcher
import com.krunal.mychat.di.MainDispatcher
import com.krunal.mychat.ui.base.BaseViewModel
import com.krunal.mychat.utils.extension.onError
import com.krunal.mychat.utils.extension.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
): BaseViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch(ioDispatcher) {
            _isLoading.emit(true)
            userRepository.getUsers().collectLatest { result ->
                result.onSuccess { users ->
                    users?.let { _users.emit(it) }
                    _isLoading.emit(false)
                }
                result.onError { code, message ->
                    _isLoading.emit(false)
                }
            }
        }
    }
}