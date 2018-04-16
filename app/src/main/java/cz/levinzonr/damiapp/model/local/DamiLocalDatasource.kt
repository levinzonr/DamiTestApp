package cz.levinzonr.damiapp.model.local

import android.app.Application
import android.content.Context
import android.util.Log
import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.model.entities.MapPoint
import cz.levinzonr.damiapp.model.entities.User
import io.reactivex.Completable
import io.reactivex.Flowable
import org.intellij.lang.annotations.Flow

class DamiLocalDatasource(application: Context) {
    private val db = AppDatabase.getInstance(application)
    private val prefs = SharedPreferencesManager(application)


    fun afterLogin(user: User) : Completable {
        return  Completable.fromCallable {
            prefs.saveLoggedInUser(user)
            db.userDao().insert(user)
            user.favorites.map {
                it.userId = user.id
            }
            db.mapPointDao().insertAll(user.favorites)
        }
    }

    fun saveMapPoints(list: List<MapPoint>) : Completable {
        return Completable.fromCallable {
            db.mapPointDao().insertAll(list)
        }
    }

    fun getUserMapPoints() : Flowable<List<MapPoint>> {
        return db.mapPointDao().findPointsOfUser(prefs.getUserId())
    }

    fun saveContacts(arrayList: ArrayList<Contact>) : Completable {
        return Completable.fromCallable {
            db.contactsDao().insertAll(arrayList)
        }
    }

    fun saveContact(contact: Contact) : Completable{
        return Completable.fromCallable {
            db.contactsDao().insert(contact)
        }
    }

    fun deleteContactById(id: Int) : Completable{
        return Completable.fromCallable {
            db.contactsDao().deleteById(id)
        }
    }

    fun getContactById(id: Int) : Flowable<Contact> {
        return db.contactsDao().findById(id)
    }

    fun getContacts() : Flowable<List<Contact>> {
        return db.contactsDao().findAll()
    }

    fun isLoggedIn() : Boolean {
        return prefs.isLoggedIn()
    }

    fun getUserToken() : String {
        return prefs.getUserToken()
    }

    fun getCurrentUser() : Flowable<User> {
        val id = prefs.getUserId()
        return db.userDao().findById(id)
    }

    fun logout() {
        prefs.clear()
    }



}