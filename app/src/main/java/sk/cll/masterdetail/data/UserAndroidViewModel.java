package sk.cll.masterdetail.data;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import retrofit2.Call;
import sk.cll.masterdetail.db.User;
import sk.cll.masterdetail.service.Repository;

public class UserAndroidViewModel extends AndroidViewModel {

    private Repository mRepository;

    private LiveData<List<User>> mAllUsers;


    public UserAndroidViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllUsers = mRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public Call<List<User>> getAndSaveNewUsers() {
        return mRepository.executeApi();
    }

    public void insert(List<User> users) {
        mRepository.insert(users);
    }
}
