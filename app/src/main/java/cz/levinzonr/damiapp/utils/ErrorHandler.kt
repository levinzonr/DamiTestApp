package cz.levinzonr.damiapp.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import cz.levinzonr.damiapp.model.remote.Response
import java.io.IOException
import java.net.SocketTimeoutException

class ErrorHandler {

    inner class Error

    fun handleError(e: Throwable) : String {
       return when {
            e is HttpException -> handleApiError(e)
            e is SocketTimeoutException -> "Connection timeout"
            e is IOException -> "Network error"
            else -> "Unkown error: $e"

        }
    }

    private fun handleApiError(e: HttpException) : String {
        val type = object : TypeToken<Response<Error>>() {}.javaClass
        val response = Gson().fromJson<Response<Error>>(e.response().errorBody().string(), type)
        return response.responseCodeText
    }

}
