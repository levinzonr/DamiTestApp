package cz.levinzonr.damiapp.model.local

import android.content.Context
import android.content.pm.PackageManager
import cz.levinzonr.damiapp.model.entities.User

class SharedPreferencesManager(context: Context) {
    private val preferences = context.getSharedPreferences(PREF_NAME, 0)

    companion object {
        const val PREF_NAME = "cz.levinzonr.damiapp.userpreferences"
        const val USER_ID = "userId"
        const val USER_TOKEN = "userToken"

    }


    fun saveLoggedInUser(user: User) {
        preferences.edit()
                .putInt(USER_ID, user.id)
                .putString(USER_TOKEN, user.token)
                .apply()
    }

    fun isLoggedIn() : Boolean {
        val value = preferences.getString(USER_TOKEN, null)
        return value != null
    }

    fun getUserToken() : String {
        return preferences.getString(USER_TOKEN, null)
    }

    fun getUserId() : Int {
        return preferences.getInt(USER_ID, 0)
    }

    fun clear() {
        preferences.edit()
                .remove(USER_ID)
                .remove(USER_TOKEN)
                .apply()
    }


}