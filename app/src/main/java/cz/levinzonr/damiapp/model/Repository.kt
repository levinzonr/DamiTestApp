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

class Repository {
    private val local = DamiLocalDatasource(MyApp.getContext())
    private val remote = DamiRemoteDatasource()


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
        return remote.getContacts(local.getUserToken())
    }

    fun logout() : Completable {
        return Completable.fromCallable { local.logout() }
    }

}