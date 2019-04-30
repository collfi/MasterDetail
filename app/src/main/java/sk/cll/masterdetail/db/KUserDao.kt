package sk.cll.masterdetail.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface KUserDao {

    @Insert
    fun insertAll(users: List<KUser>)

    @Query("SELECT * FROM users")
    fun getAll(): LiveData<List<KUser>>

    @Delete
    fun delete(user: KUser)

    @Query("DELETE FROM users")
    fun deleteAll()
}