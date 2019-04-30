package sk.cll.masterdetail.db;

import androidx.room.RoomDatabase;

//@Database(entities = {KUser.class}, version = 1)
public abstract class UserRoomDatabase extends RoomDatabase {
    /*public abstract UserDao mUserDao();

    //SINGLETON
    private static UserRoomDatabase INSTANCE;

    public static UserRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserRoomDatabase.class, "users-database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }*/
}
