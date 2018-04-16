package cz.levinzonr.damiapp.model

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
            return@flatMap local.afterLogin(it.response).toSingleDefault(it).toFlowable()
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

    fun getPointsOnMap() : Flowable<Response<ArrayList<MapPoint>>> {
        return remote.getMapPoints().flatMap {
            return@flatMap local.saveMapPoints(it.response).toSingleDefault(it).toFlowable()
        }
    }

    fun getFavoritePoints() : Flowable<List<MapPoint>> {
        return local.getUserMapPoints()
    }

    fun updateContacts() : Flowable<Response<ArrayList<Contact>>> {
        return  remote.getContacts(local.getUserToken()).flatMap {
            return@flatMap local.saveContacts(it.response).toSingleDefault(it).toFlowable()
        }
    }


    fun getContacts() : Flowable<List<Contact>> {
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
        else remote.updateContact(local.getUserToken(), contact)
    }

    fun deleteContact(id: Int) : Flowable<Response<Any>> {
        return remote.deleteContact(local.getUserToken(), id).flatMap {
            return@flatMap local.deleteContactById(id).toSingleDefault(it).toFlowable()
        }
    }

}