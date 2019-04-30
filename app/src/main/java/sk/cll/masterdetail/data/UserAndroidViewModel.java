package sk.cll.masterdetail.data;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import retrofit2.Call;
import sk.cll.masterdetail.db.KUser;
import sk.cll.masterdetail.service.KRepository;

public class UserAndroidViewModel extends AndroidViewModel {

    private KRepository mRepository;

    private LiveData<List<KUser>> mAllUsers;


    public UserAndroidViewModel(Application application) {
        super(application);
        mRepository = new KRepository(application);
        mAllUsers = mRepository.getAllUsers();
    }

    public LiveData<List<KUser>> getAllUsers() {
        return mAllUsers;
    }

    public Call<List<KUser>> getAndSaveNewUsers() {
        return mRepository.executeApi();
    }

    public void insert(List<KUser> users) {
        mRepository.insert(users);
    }
}
