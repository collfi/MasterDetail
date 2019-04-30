package sk.cll.masterdetail.service

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import sk.cll.masterdetail.db.KUser
import sk.cll.masterdetail.db.KUserDao
import sk.cll.masterdetail.db.KUserRoomDatabase
import sk.cll.masterdetail.utils.KUtils

class KRepository(application: Application) {
    var apiCallInterface: KUserService
    var mUserDao: KUserDao
    var mAllUsers: LiveData<List<KUser>>

    init {
        val db = KUserRoomDatabase.getDatabase(application)
        apiCallInterface = Retrofit.Builder()
                .baseUrl(KUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClient.Builder().build())
                .build().create(KUserService::class.java)
        mUserDao = db.mUserDao()
        mAllUsers = mUserDao.getAll()
    }

    fun executeApi(): Call<List<KUser>> {
        return apiCallInterface.getNewUsers(KUtils.EXT, KUtils.LIMIT)
    }

    fun getAllUsers(): LiveData<List<KUser>> {
        return mAllUsers
    }


//    @WorkerThread
//    suspend fun insert(users: List<KUser>) {
//        mUserDao.insertAll(users)
//    }

    fun insert(users: List<KUser>) {
        InsertAsyncTask(mUserDao).execute(users)
    }

    companion object {
        class InsertAsyncTask(private val userDao: KUserDao) : AsyncTask<List<KUser>, Unit, Unit>() {
            override fun doInBackground(vararg params: List<KUser>) {
                userDao.insertAll(params[0])
            }
        }
    }
}