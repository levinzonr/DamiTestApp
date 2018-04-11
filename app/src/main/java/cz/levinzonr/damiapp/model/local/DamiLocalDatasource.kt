package cz.levinzonr.damiapp.model.local

import android.app.Application
import android.content.Context
import cz.levinzonr.damiapp.model.entities.User
import io.reactivex.Completable

class DamiLocalDatasource(application: Context) {
    private val db = AppDatabase.getInstance(application)
    private val prefs = SharedPreferencesManager(application)


    fun saveUser(user: User) : Completable {
        return  Completable.fromCallable {
            prefs.saveLoggedInUser(user)
            db.userDao().insert(user) }
    }

    fun isLoggedIn() : Boolean {
        return prefs.isLoggedIn()
    }

    fun getUserToken() : String {
        return prefs.getUserToken()
    }



}