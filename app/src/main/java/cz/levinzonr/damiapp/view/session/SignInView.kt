package cz.levinzonr.damiapp.view.session

import cz.levinzonr.damiapp.model.entities.User

interface SignInView {

    fun onSingInStarted()

    fun onSignInFinished(user: User)

    fun onSignInError(error: String)

    fun allowSignIn(enable: Boolean)

}