package com.krunal.mychat.data.repository

import com.google.firebase.auth.FirebaseUser
import com.krunal.mychat.data.remote.FirebaseService
import com.krunal.mychat.data.remote.apiresult.ApiError
import com.krunal.mychat.data.remote.apiresult.ApiResult
import com.krunal.mychat.data.remote.apiresult.ApiSuccess
import javax.inject.Inject
import javax.inject.Singleton

interface AuthRepository {

    suspend fun login(email: String, password: String): ApiResult<FirebaseUser>
}

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService
) : AuthRepository {

    override suspend fun login(email: String, password: String): ApiResult<FirebaseUser> {
        return try {
            val result = firebaseService.login(email, password)
            ApiSuccess(result.user)
        } catch (e: Exception) {
            ApiError(0, e.localizedMessage)
        }
    }
}
