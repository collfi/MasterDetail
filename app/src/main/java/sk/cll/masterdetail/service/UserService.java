package sk.cll.masterdetail.service;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sk.cll.masterdetail.data.User;

public interface UserService {
    @GET("api")
    Observable<List<User>> getUsers(@Query("ext") boolean ext, @Query("amount") int amount);

}

