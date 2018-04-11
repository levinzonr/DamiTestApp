package cz.levinzonr.damiapp.model.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import cz.levinzonr.damiapp.model.entities.User
import io.reactivex.Flowable
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


    interface DamiService {
        @FormUrlEncoded
        @POST("login")
        fun login(@Field("email" ) email:String, @Field("password") password: String) : Flowable<Response<User>>

        @FormUrlEncoded
        @POST("register")
        fun register(@Field("email") email:String, @Field("password") password: String) : Flowable<Response<User>>
    }

}