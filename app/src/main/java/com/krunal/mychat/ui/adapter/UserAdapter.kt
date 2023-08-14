package com.krunal.mychat.ui.adapter

import android.view.View
import com.krunal.mychat.R
import com.krunal.mychat.data.remote.model.User
import com.krunal.mychat.ui.base.BaseRecyclerAdapter

class UserAdapter: BaseRecyclerAdapter<User>() {

    override fun getLayoutIdForType(viewType: Int): Int = R.layout.item_user

    override fun onItemClick(view: View?, position: Int) { }

    override fun areItemsSame(firstItem: User, secondItem: User): Boolean = firstItem == secondItem
}