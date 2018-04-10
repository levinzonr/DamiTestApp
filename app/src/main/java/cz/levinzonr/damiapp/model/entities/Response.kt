package cz.levinzonr.damiapp.model.entities

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