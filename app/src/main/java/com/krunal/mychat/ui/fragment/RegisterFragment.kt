package com.krunal.mychat.ui.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.krunal.mychat.R
import com.krunal.mychat.databinding.FragmentLoginBinding
import com.krunal.mychat.databinding.FragmentRegisterBinding
import com.krunal.mychat.ui.base.BaseFragment
import com.krunal.mychat.ui.viewmodel.LoginViewModel
import com.krunal.mychat.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {

    override val viewModel: RegisterViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_register

    override fun initialize() {
        super.initialize()
        setupUI()
    }

    private fun setupUI() {
        binding.btnRegister.setOnClickListener {
            viewModel.register {
                Timber.d("Register success")
            }
        }
        binding.hintLogin.setOnClickListener {
            findNavController().popBackStack(R.id.registerFragment, true)
        }
    }
}