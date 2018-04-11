package cz.levinzonr.damiapp

import android.app.Application
import android.content.Context
import android.widget.Toast

class MyApp : Application() {

    companion object {

        lateinit var instance: MyApp

        fun getContext() : Context {
            return instance.applicationContext
        }

    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

}