package com.krunal.mychat.ui.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.krunal.mychat.R
import com.krunal.mychat.databinding.FragmentLoginBinding
import com.krunal.mychat.ui.base.BaseFragment
import com.krunal.mychat.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_login

    override fun initialize() {
        super.initialize()
        setupUI()
    }

    private fun setupUI() {
        binding.btnLogin.setOnClickListener {
            viewModel.login {
                Timber.d("Login success")
            }
        }
        binding.hintRegister.setOnClickListener {
            val destination = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(destination)
        }
    }
}