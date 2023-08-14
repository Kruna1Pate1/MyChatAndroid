package com.krunal.mychat.data.remote

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.krunal.mychat.data.remote.apiresult.ApiError
import com.krunal.mychat.data.remote.apiresult.ApiResult
import com.krunal.mychat.data.remote.apiresult.ApiSuccess
import com.krunal.mychat.data.remote.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseService @Inject constructor(
    private val auth: FirebaseAuth,
    private val databaseReference: DatabaseReference
) {

    suspend fun login(email: String, password: String): AuthResult =
        auth.signInWithEmailAndPassword(email, password).await()

    suspend fun register(name: String, email: String, password: String): AuthResult {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val changeRequest = userProfileChangeRequest {
            displayName = name
        }
        result.user?.let { user ->
            val userDetail = User(user.uid, name, email, password)
            databaseReference.child(user.uid).setValue(userDetail)
            user.updateProfile(changeRequest)
        }
        return result
    }

    suspend fun getUsers(): Flow<ApiResult<List<User>>> = callbackFlow {
        val listener = databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.children
                    .filterNot { it.key == auth.uid }
                    .mapNotNull { it.getValue(User::class.java) }
                trySend(ApiSuccess(users))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ApiError(error.code, error.message))
            }
        })

        awaitClose {
            databaseReference.removeEventListener(listener)
        }
    }
}
