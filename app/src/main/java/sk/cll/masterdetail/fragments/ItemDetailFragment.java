package sk.cll.masterdetail.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import sk.cll.masterdetail.R;
import sk.cll.masterdetail.activities.KMainActivity;
import sk.cll.masterdetail.db.KUser;
import sk.cll.masterdetail.utils.KPicassoCircleTransformation;

public class ItemDetailFragment extends Fragment {

    @BindView(R.id.mName)
    TextView mName;
    @BindView(R.id.mAge)
    TextView mAge;
    @BindView(R.id.mRegion)
    TextView mRegion;
    @BindView(R.id.mGender)
    TextView mGender;
    @BindView(R.id.mPhone)
    TextView mPhone;
    @BindView(R.id.mEmail)
    TextView mEmail;
    @BindView(R.id.mPhoto)
    ImageView mPhoto;

    private Unbinder unbinder;

    public static ItemDetailFragment newInstance(KUser arg) {
        ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", arg);
        itemDetailFragment.setArguments(bundle);
        return itemDetailFragment;
    }

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (getActivity() != null && ((KMainActivity) getActivity()).getSupportActionBar() != null)
                ((KMainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        KUser u = null;
        if (getArguments() != null) {
            if (getArguments().containsKey("user")) {
                u = getArguments().getParcelable("user");
            }
        }
        if (u == null) {
            Toast.makeText(getActivity(), R.string.error_user_detail, Toast.LENGTH_LONG).show();
        } else {
            mName.setText(u.getFullName());
            mAge.setText(String.valueOf(u.getAge()));
            mGender.setText(u.getGender());
            mPhone.setText(u.getPhone());
            mEmail.setText(u.getEmail());
            mRegion.setText(u.getRegion());

            Picasso.get().load(u.getPhoto())
                    .transform(new KPicassoCircleTransformation())
                    .placeholder(R.drawable.placeholder_large)
                    .into(mPhoto);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
