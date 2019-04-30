package sk.cll.masterdetail.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import retrofit2.Call
import sk.cll.masterdetail.db.KUser
import sk.cll.masterdetail.service.KRepository

class KUserAndroidViewModel(application: Application) : AndroidViewModel(application) {

    val mRepository: KRepository = KRepository(application)
    var mAllUsers: LiveData<List<KUser>>

    init {
        mAllUsers = mRepository.getAllUsers()
    }

    fun getAndSaveNewUsers(): Call<List<KUser>> {
        return mRepository.executeApi()
    }

    fun insert(users: List<KUser>) {
        mRepository.insert(users)
    }
}