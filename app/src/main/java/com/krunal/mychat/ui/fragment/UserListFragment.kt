package com.krunal.mychat.ui.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.krunal.mychat.R
import com.krunal.mychat.databinding.FragmentUserListBinding
import com.krunal.mychat.ui.adapter.UserAdapter
import com.krunal.mychat.ui.base.BaseFragment
import com.krunal.mychat.ui.viewmodel.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class UserListFragment : BaseFragment<FragmentUserListBinding, UserListViewModel>() {

    private val userAdapter = UserAdapter()

    override val viewModel: UserListViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.fragment_user_list

    override fun initialize() {
        super.initialize()
        setupUI()
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.users.collectLatest { users ->
                    userAdapter.addAllItem(users)
                }
            }
        }
    }

    private fun setupUI() {
        binding.rvUsers.adapter = userAdapter
    }
}