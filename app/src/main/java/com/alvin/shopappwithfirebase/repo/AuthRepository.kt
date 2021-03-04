package com.alvin.shopappwithfirebase.repo

import com.alvin.shopappwithfirebase.data.model.User
import com.alvin.shopappwithfirebase.data.network.Resource
import com.alvin.shopappwithfirebase.data.network.Status
import com.alvin.shopappwithfirebase.utils.constants.FirebaseConstants
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val userCollectionRef: CollectionReference
) {

    suspend fun login(emailId: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            val result = auth.signInWithEmailAndPassword(
                emailId, password
            ).await()
            emit(Resource(Status.SUCCESS, result, null))
        }.catch {
            emit(Resource(Status.ERROR, null, it.message))
        }
    }

    suspend fun register(emailId: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            val result = auth.createUserWithEmailAndPassword(
                emailId, password
            ).await()
            emit(Resource(Status.SUCCESS, result, null))
        }.catch {
            emit(Resource(Status.ERROR, null, it.message))
        }
    }

    suspend fun forgot(emailId: String): Flow<Resource<Void>> {
        return flow {
            val result = auth.sendPasswordResetEmail(
                emailId
            ).await()
            emit(Resource(Status.SUCCESS, result, null))
        }.catch {
            emit(Resource(Status.ERROR, null, it.message))
        }
    }

    suspend fun addUserCollection(user: User): Flow<Resource<Void>> {
        return flow {

            val result = userCollectionRef.document(
                user.id
            ).set(user, SetOptions.merge()).await()

            emit(Resource(Status.SUCCESS, result, null))
        }.catch {
            emit(Resource(Status.ERROR, null, it.message))
        }
    }

}