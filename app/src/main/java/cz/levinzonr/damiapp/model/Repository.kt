package cz.levinzonr.damiapp.model

import android.content.Context
import cz.levinzonr.damiapp.MyApp
import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.model.entities.MapPoint
import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.model.local.DamiLocalDatasource
import cz.levinzonr.damiapp.model.remote.DamiRemoteDatasource
import cz.levinzonr.damiapp.model.remote.PostObject
import cz.levinzonr.damiapp.model.remote.Response
import io.reactivex.Completable
import io.reactivex.Flowable
import org.intellij.lang.annotations.Flow
import java.util.concurrent.TimeUnit

class Repository {
    private val local = DamiLocalDatasource(MyApp.getContext())
    private val remote = DamiRemoteDatasource()

    companion object {
        fun MockContacts() : ArrayList<Contact> {
            val list = ArrayList<Contact>()
            for (i in 0..10) {
                val contact = Contact(i,"emial$i@com", "John $i", "Doe", "Descpr", null)
                list.add(contact)
            }
            return list
        }
    }

    fun login(user: PostObject.Login) : Flowable<Response<User>> {
        return remote.userLogin(user).flatMap {
            return@flatMap local.saveUser(it.response).toSingleDefault(it).toFlowable()
        }
    }

    fun register(user: PostObject.Register) : Flowable<Response<User>> {
        return remote.userRegister(user).flatMap {
            return@flatMap local.saveUser(it.response).toSingleDefault(it).toFlowable()
        }
    }

    fun getCurrentUser() : Flowable<User> {
        return local.getCurrentUser()
    }

    fun getPointsOnMap() : Flowable<Response<ArrayList<MapPoint>>> {
        return remote.getMapPoints()
    }

    fun getContacts() : Flowable<Response<ArrayList<Contact>>> {
        //return remote.getContacts(local.getUserToken())
        val response = Response(1, "ok", MockContacts())
        return Flowable.just(response).delay(3, TimeUnit.SECONDS)
    }

    fun getContactById(id: Int) : Flowable<Response<Contact>>{
        val response = Response(1, "ok", MockContacts()[id])
        return Flowable.just(response)
    }

    fun logout() : Completable {
        return Completable.fromCallable { local.logout() }
    }

}