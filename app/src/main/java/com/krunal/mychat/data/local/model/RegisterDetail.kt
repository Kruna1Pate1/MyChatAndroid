package com.krunal.mychat.data.local.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.krunal.mychat.BR

data class RegisterDetail(
    private var _name: String = "",
    private var _email: String = "",
    private var _password: String = "",
) : BaseObservable() {


    @get:Bindable
    var name: String = _name
        set(value) {
            _name = value
            field = value
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var email: String = _email
        set(value) {
            _email = value
            field = value
            notifyPropertyChanged(BR.email)
        }

    @get:Bindable
    var password: String = _password
        set(value) {
            _password = value
            field = value
            notifyPropertyChanged(BR.password)
        }
}