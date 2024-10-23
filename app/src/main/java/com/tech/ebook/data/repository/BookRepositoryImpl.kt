package com.tech.ebook.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.tech.ebook.ResultState
import com.tech.ebook.data.response.BookCategoryModels
import com.tech.ebook.data.response.BookModels
import com.tech.ebook.presentation.repo.BookRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) : BookRepository {

    //callback - non stop data fetch from server when collector is available (this is cold flow but behave hot flow)

    override suspend fun getAllBooks(): Flow<ResultState<List<BookModels>>> = callbackFlow {

        trySend(ResultState.Loading)
        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items: List<BookModels> = snapshot.children.mapNotNull {
                    it.getValue<BookModels>()
                }
                trySend(ResultState.Success(items))
                Log.d("@realtime", "onDataChange: ${items.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(exception = error.toException()))
            }
        }
        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)


        // Clean up the listener when the flow is no longer active
        awaitClose {
            firebaseDatabase.reference.child("Books").removeEventListener(valueEvent)
            close()
        }
    }

    override suspend fun getAllCategoryBook(): Flow<ResultState<List<BookCategoryModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            val valueEvent = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val bookCategory: List<BookCategoryModels> = snapshot.children.mapNotNull {
                        it.getValue<BookCategoryModels>()
                    }
                    trySend(ResultState.Success(bookCategory))
                    Log.d("@realtime", "onDataChange: ${bookCategory.size}")
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(ResultState.Error(exception = error.toException()))
                }
            }
            firebaseDatabase.reference.child("BookCategory").addValueEventListener(valueEvent)


            // Clean up the listener when the flow is no longer active
            awaitClose {
                firebaseDatabase.reference.child("BookCategory").removeEventListener(valueEvent)
                close()
            }

        }

    override suspend fun getBookByCategory(category: String): Flow<ResultState<List<BookModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            val valueEvent = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val bookCategory: List<BookModels> = snapshot.children.mapNotNull {
                        it.getValue<BookModels>()
                    }.filter {
                        it.category == category
                    }
                    trySend(ResultState.Success(bookCategory))
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(ResultState.Error(exception = error.toException()))
                }
            }
            firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)
            awaitClose {
                firebaseDatabase.reference.child("Books").removeEventListener(valueEvent)
                close()
            }
        }
}

//with flow no update with realtime data
/*
        emit(ResultState.Loading)

       try {
               val snapshot = firebaseDatabase.reference.child("Books").get().await()
               val items: List<BookModels> = snapshot.children.mapNotNull {
                   it.getValue<BookModels>()
               }
               emit(ResultState.Success(items))
               Log.d("@realtime", "Fetched ${items.size} books")
           } catch (error: Exception) {
               emit(ResultState.Error(error))
           }

           // Wait for a specified time before polling again
//            delay(5000) // Poll every 5 seconds, adjust as needed
*/
