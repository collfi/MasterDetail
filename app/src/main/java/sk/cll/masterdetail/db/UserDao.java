package sk.cll.masterdetail.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import sk.cll.masterdetail.data.User;

@Dao
public interface UserDao {

    @Insert
    void insertAll(List<User> users);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAll();

    @Delete
    void delete(User user);

    @Query("DELETE FROM users")
    void deleteAll();
}
