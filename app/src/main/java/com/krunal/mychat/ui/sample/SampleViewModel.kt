package com.krunal.mychat.ui.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.krunal.mychat.data.remote.response.User
import com.krunal.mychat.data.repository.UserRepository
import com.krunal.mychat.di.IoDispatcher
import com.krunal.mychat.di.MainDispatcher
import com.krunal.mychat.ui.base.BaseViewModel
import com.krunal.mychat.utils.extension.onError
import com.krunal.mychat.utils.extension.onException
import com.krunal.mychat.utils.extension.onSuccess
import com.krunal.mychat.utils.result.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel for [com.krunal.mychat.ui.sample.SampleActivity]
 */
@HiltViewModel
class SampleViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _onNewUserList = MutableLiveData<Event<List<User>>>()
    val onNewUserList: LiveData<Event<List<User>>>
        get() = _onNewUserList

    private val _isLoadingPage = MutableLiveData<Boolean>()
    val isLoadingPage: LiveData<Boolean>
        get() = _isLoadingPage

    private val _showShimmer = MutableLiveData<Boolean>()
    val showShimmer: LiveData<Boolean>
        get() = _showShimmer

    private var currentPage = 0

    init {
        loadMoreUsers()
    }

    fun loadMoreUsers() {
        _isLoadingPage.value = true
        currentPage += 1

        if (currentPage == 1) {
            _showShimmer.value = true
        }

        viewModelScope.launch(ioDispatcher) {
            userRepository
                .loadUsers(currentPage)
                .onSuccess {
                    withContext(mainDispatcher) {
                        _isLoadingPage.value = false
                        _onNewUserList.value = it?.let { it1 -> Event(it1.results) }
                        if (currentPage == 1) {
                            _showShimmer.value = false
                        }
                    }
                }.onError { code, message ->
                    Timber.e(code.toString() + message)
                    withContext(mainDispatcher) { handleApiFailure() }
                }.onException {
                    Timber.e(it.message)
                    withContext(mainDispatcher) { handleApiFailure() }
                }
        }
    }

    /**
     * Handle the failure occuring in API call
     */
    private fun handleApiFailure() {
        _isLoadingPage.value = false
        if (currentPage == 1) {
            _showShimmer.value = false
        }
        currentPage -= 1 // Revert increased count
    }
}
