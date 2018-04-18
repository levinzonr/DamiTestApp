package cz.levinzonr.damiapp.model.local.roomdb

import android.arch.persistence.room.*
import cz.levinzonr.damiapp.model.entities.Contact
import io.reactivex.Flowable

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(arrayList: ArrayList<Contact>)

    @Query("SELECT * FROM contact WHERE id LIKE :id")
    fun findById(id: Int) : Flowable<Contact>

    @Query("SELECT * FROM contact")
    fun findAll() : Flowable<List<Contact>>

    @Update
    fun update(contact:Contact)

    @Query("DELETE from contact where id LIKE :id")
    fun deleteById(id: Int)

}