package cz.levinzonr.damiapp.model.remote


class Response<out V>(
        val responseCode: Int,
        val responseCodeText: String,
        val response: V)
