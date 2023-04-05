package com.mohammedmorse.utils.extensions

import android.app.Activity
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.morse.valu.data.dto.Response
import com.morse.valu.utils.base.Parser
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

fun Activity.navigateDelay(time: Long = 2000L, action: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({
        action.invoke()
    }, time)
}

inline fun <T> AppCompatActivity.collect(flow: Flow<T>, crossinline block: (T) -> Unit) =
    lifecycleScope.launch {
        flow
            .flowWithLifecycle(this@collect.lifecycle)
            .collect { action ->
                block.invoke(action)
            }
    }

inline fun <T> ViewModel.collect(flow: Flow<T>, crossinline block: (T) -> Unit) =
    viewModelScope.launch {
        flow
            .onEach { block.invoke(it) }
            .launchIn(viewModelScope)
    }


inline fun <reified Result> consume(crossinline apiCall: suspend () -> retrofit2.Response<ArrayList<Result>>): Flow<Response> {
    return flow {
        val result = apiCall.invoke()
        if (result.isSuccessful) {
            emit(Response.Success((result.body() as ArrayList<Result>)))
        } else {
            emit(
                Response.Error(
                    result.errorBody()?.string() ?: ""
                )
            )
        }
    }.catch { exception: Throwable ->
        emit(
            Response.Error(
                exception.message ?: ""
            )
        )
    }
}