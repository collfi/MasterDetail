package sk.cll.masterdetail.service;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sk.cll.masterdetail.db.KUser;
import sk.cll.masterdetail.db.KUserDao;
import sk.cll.masterdetail.db.KUserRoomDatabase;
import sk.cll.masterdetail.utils.KUtils;

public class Repository {
    private KUserService apiCallInterface;
    private KUserDao mUserDao;
    private LiveData<List<KUser>> mAllUsers;

    public Repository(Application application) {
        KUserRoomDatabase db = KUserRoomDatabase.Companion.getDatabase(application);
        this.apiCallInterface = new Retrofit.Builder()
                .baseUrl(KUtils.Companion.getBASE_URL())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build().create(KUserService.class);
        mUserDao = db.mUserDao();
        mAllUsers = mUserDao.getAll();
    }

    public Call<List<KUser>> executeApi() {
        return apiCallInterface.getNewUsers(KUtils.getEXT(), KUtils.getLIMIT());
    }

    public LiveData<List<KUser>> getAllUsers() {
        return mAllUsers;
    }

    public void insert(List<KUser> users) {
        new InsertAsyncTask(mUserDao).execute(users);
    }

    private static class InsertAsyncTask extends AsyncTask<List<KUser>, Void, Void> {
        private KUserDao mUserDao;

        InsertAsyncTask(KUserDao userDao) {
            mUserDao = userDao;
        }

        @Override
        @SafeVarargs
        protected final Void doInBackground(List<KUser>... lists) {
            mUserDao.insertAll(lists[0]);
            return null;
        }
    }
}
