package com.example.e_book.user_pref

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "pager_state_preferences")
class UserPreferenceManager (private val context : Context){

    companion object{
        private val PAGER_STATE = stringPreferencesKey("pager_state")
    }

    suspend fun savePagerState(pagerState : String){
        if (pagerState.isNotEmpty()) {
            context.dataStore.edit {
                it[PAGER_STATE] = pagerState
            }
        }
        else{
            throw Exception("Pager state cannot be empty")
        }
    }

    val pagerState = context.dataStore.data.map {
        it[PAGER_STATE] ?: ""
    }
}