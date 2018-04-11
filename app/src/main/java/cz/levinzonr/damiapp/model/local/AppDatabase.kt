package cz.levinzonr.damiapp.model.local

import android.app.Application
import android.arch.persistence.room.*
import android.content.Context
import cz.levinzonr.damiapp.model.entities.Contact
import cz.levinzonr.damiapp.model.entities.Converters
import cz.levinzonr.damiapp.model.entities.MapPoint
import cz.levinzonr.damiapp.model.entities.User

@Database(version = 1, entities = [User::class, MapPoint::class, Contact::class])
@TypeConverters(Converters::class)
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