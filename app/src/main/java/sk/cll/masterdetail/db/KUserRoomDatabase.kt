package sk.cll.masterdetail.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [KUser::class], version = 1)
abstract class KUserRoomDatabase: RoomDatabase() {
    abstract fun mUserDao(): KUserDao

    companion object {
        @Volatile
        private var INSTANCE: KUserRoomDatabase? = null

        fun getDatabase(context: Context): KUserRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        KUserRoomDatabase::class.java,
                        "users-database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }


}