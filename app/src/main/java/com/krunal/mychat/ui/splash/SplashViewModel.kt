package com.krunal.mychat.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.krunal.mychat.ui.base.BaseViewModel
import com.krunal.mychat.utils.result.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

/**
 * ViewModel for [com.krunal.mychat.ui.splash.SplashActivity]
 */
@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    companion object {
        private const val LAUNCH_DELAY = 3000L
    }

    private val _goToScreen = MutableLiveData<Event<Destination>>()
    val goToScreen: LiveData<Event<Destination>>
        get() = _goToScreen

    init {
        navigateTo()
    }

    /**
     * Decides where to navigate user.
     */
    private fun navigateTo() {
        viewModelScope.launch {
            delay(LAUNCH_DELAY)
            _goToScreen.value = Event(Destination.Login)
        }
    }

    sealed class Destination {
        object Home : Destination()
        object Login : Destination()
        object Sample : Destination()
    }
}
