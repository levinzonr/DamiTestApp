package cz.levinzonr.damiapp.model.local.roomdb

import android.arch.persistence.room.*
import cz.levinzonr.damiapp.model.entities.User
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM user WHERE id LIKE :id  ")
    fun findById(id: Int) : Flowable<User>

    @Update
    fun update(user: User)
}