package com.example.e_book.repo

import com.example.e_book.ResultState
import com.example.e_book.response.BookCategoryModels
import com.example.e_book.response.BookModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose

class Repo @Inject constructor(private val firebaseDatabase: FirebaseDatabase) {

    // By the time we have used flow but now we are using callbackFlow because it is more effective cold flow than normal flow
    // But basically you have to know what is cold flow and what is hot flow

   fun getAllBooks() : Flow<ResultState<List<BookModels>>> = callbackFlow {

       trySend(ResultState.Loading) // trySend is like emit

       // snapshot means data of the latest current instance or snap
       val valueEvent = object : ValueEventListener {

           override fun onDataChange(snapshot: DataSnapshot) {
               var items : List<BookModels> = emptyList()

               items = snapshot.children.map {value ->
                   value.getValue<BookModels>()!!
               }
               trySend(ResultState.Success(items))
           }

           override fun onCancelled(error: DatabaseError) {
               trySend(ResultState.Error(error.toException()))
           }

       }
       // addValueEventListener is used to get the data from the firebase database
       firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)

       // awaitClose is used to close the flow otherwise app will be crashed
      awaitClose{
          firebaseDatabase.reference.child("Books").removeEventListener(valueEvent)
          close()
      }
    }

    fun getAllCategories() : Flow<ResultState<List<BookCategoryModels>>> = callbackFlow {

        trySend(ResultState.Loading) // trySend is like emit

        // snapshot means data of the latest current instance or snap
        val valueEvent = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var items : List<BookCategoryModels> = emptyList()

                items = snapshot.children.map {value ->
                    value.getValue<BookCategoryModels>()!!
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }

        }
        // addValueEventListener is used to get the data from the firebase database
        firebaseDatabase.reference.child("BookCategory").addValueEventListener(valueEvent)

        // awaitClose is used to close the flow otherwise app will be crashed
        awaitClose{
            firebaseDatabase.reference.child("BookCategory").removeEventListener(valueEvent)
            close()
        }
    }

    fun getBooksByCategory(category : String): Flow<ResultState<List<BookModels>>> = callbackFlow {

        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var items : List<BookModels> = emptyList()
                items = snapshot.children.filter {
                    it.getValue<BookModels>()?.category == category
                }.map {
                        it.getValue<BookModels>()!!
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }

        }

        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)

        awaitClose{
            firebaseDatabase.reference.child("Books").removeEventListener(valueEvent)
            close()
        }

    }


}