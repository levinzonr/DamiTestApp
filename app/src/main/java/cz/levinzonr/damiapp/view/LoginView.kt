package cz.levinzonr.damiapp.view

interface LoginView {

    fun onLoginStart()

    fun onLoginFinished()

    fun onLoginError(error: String)

}