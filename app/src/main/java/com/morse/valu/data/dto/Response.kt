package com.morse.valu.data.dto

sealed class Response {
    data class Success<SuccessType>(val response: SuccessType) : Response()
    data class Error(val reason: String) : Response()
}
