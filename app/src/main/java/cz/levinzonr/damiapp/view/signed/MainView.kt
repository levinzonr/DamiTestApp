package cz.levinzonr.damiapp.view.signed

import cz.levinzonr.damiapp.model.entities.User

interface MainView {

    fun onUserInfoLoaded(user: User)

    fun onLogoutComplete()
}