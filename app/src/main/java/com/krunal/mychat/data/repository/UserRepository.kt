package com.krunal.mychat.data.repository

import com.krunal.mychat.data.remote.ApiService
import com.krunal.mychat.data.remote.apiresult.ApiResult
import com.krunal.mychat.data.remote.response.UserResponse
import javax.inject.Inject
import javax.inject.Singleton

interface UserRepository {
    /**
     * Loads [List] of [UserResponse]
     */
    suspend fun loadUsers(page: Int = 1): ApiResult<UserResponse>
}

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {
    override suspend fun loadUsers(page: Int): ApiResult<UserResponse> =
        apiService.loadUsers(page)
}
