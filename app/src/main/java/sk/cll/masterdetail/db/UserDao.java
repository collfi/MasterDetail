package sk.cll.masterdetail.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

//@Dao
public interface UserDao {

    @Insert
    void insertAll(List<KUser> users);

    @Query("SELECT * FROM users")
    LiveData<List<KUser>> getAll();

    @Delete
    void delete(KUser user);

    @Query("DELETE FROM users")
    void deleteAll();
}
