package sk.cll.masterdetail.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sk.cll.masterdetail.R;
import sk.cll.masterdetail.adapters.UserAdapter;
import sk.cll.masterdetail.data.User;
import sk.cll.masterdetail.data.UserViewModel;
import sk.cll.masterdetail.data.utils.PaginationScrollListener;
import sk.cll.masterdetail.data.utils.Utils;
import sk.cll.masterdetail.service.Repository;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.item_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_empty_view)
    TextView mEmptyView;
    @BindView(R.id.progress_loading)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    CompositeDisposable mCompositeDisposable;
    private boolean isLoading;
    private List<User> mUsers;
    private UserViewModel mModel;
    private SharedPreferences mPreferences;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getTitle());


        mPreferences = getPreferences(Context.MODE_PRIVATE);
        mGson = new Gson();
        mCompositeDisposable = new CompositeDisposable();
        mUsers = new ArrayList<>();
        mRecyclerView.setAdapter(new UserAdapter(mUsers, this));
        mModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mUsers.addAll(mModel.getUsers());

        if (mUsers.isEmpty()) {
            loadSaved();
        }

        if (mUsers.isEmpty()) {
            if (Utils.checkInternetConnection(this)) {
                mProgressBar.setVisibility(View.VISIBLE);
                downloadUsers();
            } else {
                mEmptyView.setVisibility(View.VISIBLE);
            }
        } else {
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }

        mEmptyView.setOnClickListener(v -> {
            mProgressBar.setVisibility(View.VISIBLE);
            downloadUsers();
        });

        mRecyclerView.addOnScrollListener(new PaginationScrollListener() {
            @Override
            protected void loadMoreItems() {
                downloadUsers();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

    }

    private void loadSaved() {
        if (mPreferences.contains("users")) {
            Type listType = new TypeToken<ArrayList<User>>() {
            }.getType();
            List<User> users = mGson.fromJson(mPreferences.getString("users", ""), listType);
            mUsers.addAll(users);
        }
    }

    private void downloadUsers() {
        if (isLoading) return;
        Toast.makeText(this, R.string.downloading, Toast.LENGTH_SHORT).show();
        isLoading = true;
        mCompositeDisposable.add(new Repository().executeUsersApi()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void handleResponse(List<User> users) {
        mUsers.addAll(users);
        mEmptyView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mModel.addUsers(users);
        mPreferences.edit().putString("users", mGson.toJson(mUsers)).apply();
        isLoading = false;
    }

    private void handleError(Throwable error) {
        Log.e("MainActivity", error.getLocalizedMessage());
        isLoading = false;
        mProgressBar.setVisibility(View.GONE);
        if (mUsers.isEmpty()) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    private void removeDetailFragment() {
        Fragment old = getSupportFragmentManager().findFragmentByTag("detail");
        if (old != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_NONE)
                    .remove(old)
                    .commit();
        }
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        removeDetailFragment();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        removeDetailFragment();
    }
}
