package com.example.coroutineflow.crypto_app

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class CryptoViewModel : ViewModel() {

    private val repository = CryptoRepository

    val state: Flow<State> = repository.getCurrencyList()
        .filter { it.isNotEmpty() }
        .map { State.Content(currencyList = it) as State }
        .onStart {
            Log.d("CryptoViewModel", "Started")
            emit(State.Loading)
        }
        .onEach {
            Log.d("CryptoViewModel", "OnEach")
        }
        .onCompletion {
            Log.d("CryptoViewModel", "Complete")
        }

}