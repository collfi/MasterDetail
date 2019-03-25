package sk.cll.masterdetail.data;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {

    private List<User> users;

    public List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

    public boolean isEmpty() {
        return users == null || users.isEmpty();
    }

    public void addUsers(List<User> list) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.addAll(list);
    }
}
