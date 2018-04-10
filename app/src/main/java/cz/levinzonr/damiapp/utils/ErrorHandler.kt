package cz.levinzonr.damiapp.utils

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import cz.levinzonr.damiapp.model.entities.Response
import java.io.IOException
import java.net.SocketTimeoutException

class ErrorHandler {

    fun handleError(e: Throwable) : String {
       return when {
            e is HttpException -> handleApiError(e)
            e is SocketTimeoutException -> "Connection timeout"
            e is IOException -> "Network error"
            else -> "Unkown error"

        }
    }

    private fun handleApiError(e: HttpException) : String {
        val response = Gson().fromJson(e.response().errorBody().string(), Response::class.java)
        return response.responseCodeText
    }

}