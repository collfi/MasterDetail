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
import sk.cll.masterdetail.db.User;
import sk.cll.masterdetail.utils.Utils;
import sk.cll.masterdetail.db.UserDao;
import sk.cll.masterdetail.db.UserRoomDatabase;

public class Repository {
    private UserService apiCallInterface;
    private UserDao mUserDao;
    private LiveData<List<User>> mAllUsers;

    public Repository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        this.apiCallInterface = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build().create(UserService.class);
        mUserDao = db.mUserDao();
        mAllUsers = mUserDao.getAll();
    }

    public Call<List<User>> executeApi() {
        return apiCallInterface.getNewUsers(Utils.EXT, Utils.LIMIT);
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public void insert(List<User> users) {
        new InsertAsyncTask(mUserDao).execute(users);
    }

    private static class InsertAsyncTask extends AsyncTask<List<User>, Void, Void> {
        private UserDao mUserDao;

        InsertAsyncTask(UserDao userDao) {
            mUserDao = userDao;
        }

        @Override
        @SafeVarargs
        protected final Void doInBackground(List<User>... lists) {
            mUserDao.insertAll(lists[0]);
            return null;
        }
    }
}
