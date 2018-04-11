package cz.levinzonr.damiapp.model.local

import android.app.Application
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

abstract class AppDatabase : RoomDatabase() {

    companion object {

        var instance: AppDatabase? = null

        const val DB_NAME = "AppDatabase"

        fun getInstance(application: Context)  : AppDatabase{
            if (instance == null) {
                instance = Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME).build()
            }
            return instance as AppDatabase
        }

    }

    abstract fun userDao() : UserDao

    abstract fun mapPointDao() : MapPointDao

}