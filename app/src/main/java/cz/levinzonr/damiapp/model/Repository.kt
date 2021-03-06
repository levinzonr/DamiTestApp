package cz.levinzonr.damiapp.model

import cz.levinzonr.damiapp.MyApp
import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.model.entities.MapPoint
import cz.levinzonr.damiapp.model.entities.User
import cz.levinzonr.damiapp.model.local.DamiLocalDatasource
import cz.levinzonr.damiapp.model.remote.DamiRemoteDatasource
import cz.levinzonr.damiapp.model.remote.PostObject
import cz.levinzonr.damiapp.model.remote.Response
import cz.levinzonr.yoyofilms.androidutils.NetManager
import io.reactivex.Completable
import io.reactivex.Flowable

class Repository {
    private val local = DamiLocalDatasource(MyApp.getContext())
    private val remote = DamiRemoteDatasource()
    private val netManager = NetManager(MyApp.getContext())

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
            return@flatMap local.afterLogin(it.response).toSingleDefault(it).toFlowable()
        }
    }

    fun updateAccount(user: User, password: String?) : Flowable<Response<User>> {
        return remote.updateAccount(local.getUserToken(), user, password).flatMap {
            return@flatMap local.updateUser(it.response).toSingleDefault(it).toFlowable()
        }
    }

    fun register(user: PostObject.Register) : Flowable<Response<User>> {
        return remote.userRegister(user).flatMap {
            return@flatMap local.afterLogin(it.response).toSingleDefault(it).toFlowable()
        }
    }

    fun isLoggedIn() : Boolean = local.isLoggedIn()

    fun getCurrentUser() : Flowable<User> {
        return local.getCurrentUser()
    }

    fun getPointsOnMap() : Flowable<ArrayList<MapPoint>> {

        if (netManager.isConnected()) {
            return remote.getMapPoints().map { it.response }.flatMap {
                return@flatMap local.saveMapPoints(it).toSingleDefault(it).toFlowable()
            }
        }
        return local.getMapPoints().map { t: List<MapPoint> -> ArrayList(t) }
    }

    fun getFavoritePoints() : Flowable<List<MapPoint>> {
        return local.getUserMapPoints()
    }


    fun getContacts() : Flowable<List<Contact>> {
        if (netManager.isConnected()) {
            return remote.getContacts(local.getUserToken()).map { it.response }.flatMap {
                return@flatMap local.saveContacts(it).toSingleDefault(it).toFlowable()
            }
        }
        return local.getContacts()
    }

    fun getContactById(id: Int) : Flowable<Contact>{
        return local.getContactById(id)
    }

    fun logout() : Completable {
        return Completable.fromCallable { local.logout() }
    }

    fun updateContact(contact: Contact) : Flowable<Response<Contact>> {
        return if (contact.id == null) {
            remote.addContact(local.getUserToken(), contact).flatMap {
                return@flatMap local.saveContact(it.response).toSingleDefault(it).toFlowable()
            }
        }
        else remote.updateContact(local.getUserToken(), contact).flatMap {
            return@flatMap local.updateContact(contact).toSingleDefault(it).toFlowable()
        }
    }

    fun deleteContact(id: Int) : Flowable<Response<Any>> {
        return remote.deleteContact(local.getUserToken(), id).flatMap {
            return@flatMap local.deleteContactById(id).toSingleDefault(it).toFlowable()
        }
    }

}