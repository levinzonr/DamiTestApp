package cz.levinzonr.damiapp.model.remote

import cz.levinzonr.damiapp.model.entities.User

class Response{

        class OK(
        val responseCode: Int,
        val responseCodeText: String,
        val response: User
        )

        class BAD(
                val responseCode: Int,
                val responseCodeText: String
        )

}