package com.krunal.mychat.ui.activity

import androidx.activity.viewModels
import com.krunal.mychat.R
import com.krunal.mychat.databinding.ActivityMainBinding
import com.krunal.mychat.databinding.ActivitySampleBinding
import com.krunal.mychat.ui.base.BaseAppCompatActivity
import com.krunal.mychat.ui.viewmodel.AuthViewModel
import com.krunal.mychat.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseAppCompatActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_main
}
