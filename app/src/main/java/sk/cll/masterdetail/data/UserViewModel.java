package sk.cll.masterdetail.data;

import java.util.List;

import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {

    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
