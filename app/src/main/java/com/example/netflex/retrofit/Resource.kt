package com.example.netflex.retrofit

sealed class Resource<T>(val d: T?, val m: String?) {
    data class Success<T>(val data: T?, val msg: String? = null): Resource<T>(data, msg)
    data class Error<T>(val data: T? = null, val msg: String?): Resource<T>(data, msg)
}