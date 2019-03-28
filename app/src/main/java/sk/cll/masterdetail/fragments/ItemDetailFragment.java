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
import sk.cll.masterdetail.activities.MainActivity;
import sk.cll.masterdetail.db.User;
import sk.cll.masterdetail.utils.PicassoCircleTransformation;

public class ItemDetailFragment extends Fragment {

    @BindView(R.id.tv_name)
    TextView mName;
    @BindView(R.id.tv_age)
    TextView mAge;
    @BindView(R.id.tv_region)
    TextView mRegion;
    @BindView(R.id.tv_gender)
    TextView mGender;
    @BindView(R.id.tv_phone)
    TextView mPhone;
    @BindView(R.id.tv_email)
    TextView mEmail;
    @BindView(R.id.img_photo_large)
    ImageView mPhoto;

    private Unbinder unbinder;

    public static ItemDetailFragment newInstance(User arg) {
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
            if (getActivity() != null && ((MainActivity) getActivity()).getSupportActionBar() != null)
                ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        User u = null;
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
                    .transform(new PicassoCircleTransformation())
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
