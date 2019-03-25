package sk.cll.masterdetail.service;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sk.cll.masterdetail.data.User;
import sk.cll.masterdetail.data.utils.Utils;

public class Repository {
    private UserService apiCallInterface;

    public Repository() {
        this.apiCallInterface = new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build().create(UserService.class);
    }

    public Observable<List<User>> executeUsersApi() {
        return apiCallInterface.getUsers(true, Utils.LIMIT);
    }
}
