package sk.cll.masterdetail.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import sk.cll.masterdetail.R;
import sk.cll.masterdetail.adapters.UserAdapter;
import sk.cll.masterdetail.db.User;
import sk.cll.masterdetail.data.UserAndroidViewModel;
import sk.cll.masterdetail.utils.PaginationScrollListener;
import sk.cll.masterdetail.utils.Utils;

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
    private UserAndroidViewModel mModel;
    private Callback<List<User>> mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getTitle());

        mCompositeDisposable = new CompositeDisposable();
        mUsers = new ArrayList<>();
        mRecyclerView.setAdapter(new UserAdapter(mUsers, this));
        mModel = ViewModelProviders.of(this).get(UserAndroidViewModel.class);

        mProgressBar.setVisibility(View.VISIBLE);

        mCallback = new Callback<List<User>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mProgressBar.setVisibility(View.GONE);
                        mUsers.addAll(response.body());
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                        isLoading = false;
                        mModel.insert(response.body());
                    }
                } else {
                    showError(response.message());
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<List<User>> call, Throwable t) {
                showError(t.getLocalizedMessage());
            }

            private void showError(String error) {
                Log.e("MainActivity", error);
                Toast.makeText(MainActivity.this, R.string.error_loading, Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
                if (mUsers.isEmpty()) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
                isLoading = false;
            }
        };

        mEmptyView.setOnClickListener(v -> {
            mProgressBar.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
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

        mModel.getAllUsers().observe(this, users -> {
            if (users != null) {
                if (users.isEmpty()) {
                    downloadUsers();
                    return;
                }
                if (mUsers.isEmpty()) {
                    mUsers.addAll(users);
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                    mProgressBar.setVisibility(View.GONE);
                    isLoading = false;
                }
            }
        });
    }

    private void downloadUsers() {
        if (Utils.checkInternetConnection(this)) {
            if (isLoading) return;
            Toast.makeText(this, R.string.downloading, Toast.LENGTH_SHORT).show();
            isLoading = true;
            mModel.getAndSaveNewUsers().enqueue(mCallback);
        } else {
            Toast.makeText(this, R.string.error_internet, Toast.LENGTH_SHORT).show();
            mEmptyView.setVisibility(mUsers.isEmpty() ? View.VISIBLE : View.GONE);
            mProgressBar.setVisibility(View.GONE);
        }
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
        if (getSupportFragmentManager().findFragmentByTag("detail") != null) {
            removeDetailFragment();
        } else {
            super.onBackPressed();
        }
    }
}
