package cz.levinzonr.damiapp.model.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import cz.levinzonr.damiapp.R
import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.model.entities.MapPoint
import cz.levinzonr.damiapp.model.entities.User
import io.reactivex.Flowable
import org.intellij.lang.annotations.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class DamiRemoteDatasource {

    private val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create()
    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    val service = retrofit.create(DamiService::class.java)


    companion object {
        const val BASE_URL = "https://androidtest.damidev.com/api/"
    }


    fun userLogin(user : PostObject.Login) : Flowable<Response<User>> {
        return service.login(user.email, user.password)
    }

    fun userRegister(user: PostObject.Register) : Flowable<Response<User>> {
        return service.register(user.email, user.password)
    }

    fun getMapPoints() : Flowable<Response<ArrayList<MapPoint>>> {
        return service.getMapPoints()
    }

    fun getContacts(token: String) : Flowable<Response<ArrayList<Contact>>> {
        return service.getContacts(token)
    }

    fun addContact(token: String, contact: Contact) : Flowable<Response<Contact>> {
        return service.addContact(token,
                contact.name, contact.email,"", contact.lastname, contact.description)
    }

    fun updateContact(token: String, contact: Contact) : Flowable<Response<Contact>> {
        return service.updateContact(token, contact.id!!, contact.name, contact.email,"",
                contact.lastname, contact.description)
    }

    fun deleteContact(token: String, id: Int) : Flowable<Response<Any>> {
        return service.deleteContact(token, id)
    }

    interface DamiService {
        @FormUrlEncoded
        @POST("login")
        fun login(@Field("email" ) email:String, @Field("password") password: String) : Flowable<Response<User>>

        @FormUrlEncoded
        @POST("register")
        fun register(@Field("email") email:String, @Field("password") password: String) : Flowable<Response<User>>

        @GET("getPointsOnMap")
        fun getMapPoints() : Flowable<Response<ArrayList<MapPoint>>>

        @FormUrlEncoded
        @POST("getContacts")
        fun getContacts(@Field("token") token : String) : Flowable<Response<ArrayList<Contact>>>

        @FormUrlEncoded
        @POST("addContact")
        fun addContact(
                @Field("token") token: String,
                @Field("name") name: String?,
                @Field("email") email: String?,
                @Field("phone") phone: String?,
                @Field("lastname") lastname: String?,
                @Field("description") desc: String?) : Flowable<Response<Contact>>

        @FormUrlEncoded
        @POST("updateContact")
        fun updateContact(
                @Field("token") token: String,
                @Field("id") id: Int,
                @Field("name") name: String?,
                @Field("email") email: String?,
                @Field("phone") phone: String?,
                @Field("lastname") lastname: String?,
                @Field("description") desc: String?) : Flowable<Response<Contact>>


        @FormUrlEncoded
        @POST("deleteContact")
        fun deleteContact(@Field("token") token: String,
                          @Field("id") contactId: Int) : Flowable<Response<Any>>


    }

}