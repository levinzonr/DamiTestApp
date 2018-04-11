package cz.levinzonr.damiapp.view.unsigned

import cz.levinzonr.damiapp.model.entities.User

interface SignInView {

    enum class Status {
        PASSWORD_MISMATCH, EMPTY_FIELD, EMAIL_FORMAT
    }

    fun onSingInStarted()

    fun onSignInFinished(user: User)

    fun onSignInError(error: String)

    fun allowSignIn(enable: Boolean)

    fun showHintMessage(status: Status)

}