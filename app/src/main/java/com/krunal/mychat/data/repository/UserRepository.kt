package com.krunal.mychat.data.repository

import com.krunal.mychat.data.remote.FirebaseService
import com.krunal.mychat.data.remote.apiresult.ApiResult
import com.krunal.mychat.data.remote.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface UserRepository {

    suspend fun getUsers(): Flow<ApiResult<List<User>>>
}

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService
) : UserRepository {

    override suspend fun getUsers(): Flow<ApiResult<List<User>>> =
        firebaseService.getUsers()
}
