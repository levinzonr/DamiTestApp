package cz.levinzonr.damiapp.model

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import cz.levinzonr.damiapp.model.entities.PostObject
import cz.levinzonr.damiapp.model.entities.User
import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

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
        const val BASE_URL = "https://androidtest.damidev.com/api"
    }


    fun userLogin(user : PostObject.Login) : Flowable<User> {
        return service.login(user)
    }

    fun userRegister(user: PostObject.Login) : Flowable<User> {
        return service.register(user)
    }


    interface DamiService {

        @POST("/login")
        fun login(@Body body: PostObject.Login) : Flowable<User>

        @POST("/register0")
        fun register(@Body body: PostObject.Login) : Flowable<User>

    }

}