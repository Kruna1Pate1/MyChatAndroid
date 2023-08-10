package com.krunal.mychat.ui.activity

import androidx.activity.viewModels
import com.krunal.mychat.R
import com.krunal.mychat.databinding.ActivitySampleBinding
import com.krunal.mychat.ui.base.BaseAppCompatActivity
import com.krunal.mychat.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseAppCompatActivity<ActivitySampleBinding, AuthViewModel>() {
    override val viewModel: AuthViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_auth
}
