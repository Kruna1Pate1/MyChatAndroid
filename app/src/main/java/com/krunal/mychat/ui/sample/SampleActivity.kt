package com.krunal.mychat.ui.sample

import androidx.activity.viewModels
import com.krunal.mychat.R
import com.krunal.mychat.ui.base.BaseAppCompatActivity
import com.krunal.mychat.databinding.ActivitySampleBinding
import com.krunal.mychat.utils.RecyclerPaginationListener
import com.krunal.mychat.utils.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleActivity : BaseAppCompatActivity<ActivitySampleBinding, SampleViewModel>() {
    override val viewModel: SampleViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_sample

    private val userAdapter = UserAdapter()
    private val paginationListener = RecyclerPaginationListener {
        viewModel.loadMoreUsers()
    }

    override fun initialize() {
        super.initialize()
        binding.rvUsers.adapter = userAdapter
        binding.rvUsers.addOnScrollListener(paginationListener)
    }

    override fun setupViewModel() {
        super.setupViewModel()

        binding.shimmer.startShimmer()

        viewModel.showShimmer.observe(this) {
            if (it) {
                binding.shimmer.startShimmer()
            } else {
                binding.shimmer.stopShimmer()
            }
        }

        viewModel.isLoadingPage.observe(this) {
            if (it) {
                userAdapter.addLoader()
            } else {
                userAdapter.removeLoader()
            }
        }

        viewModel.onNewUserList.observeEvent(this) {
            userAdapter.addAllItem(ArrayList(it))
        }
    }
}
