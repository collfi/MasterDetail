package sk.cll.masterdetail.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sk.cll.masterdetail.db.User;

public interface UserService {
    @GET("api")
    Call<List<User>> getNewUsers(@Query("ext") boolean ext, @Query("amount") int amount);
}

