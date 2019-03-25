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

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import sk.cll.masterdetail.R;
import sk.cll.masterdetail.activities.MainActivity;
import sk.cll.masterdetail.data.User;
import sk.cll.masterdetail.data.utils.PicassoCircleTransformation;

public class ItemDetailFragment extends Fragment {

    private Toolbar mToolbar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
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
            getFragmentManager().popBackStack();
        } else {
            TextView name = rootView.findViewById(R.id.tv_name);
            TextView age = rootView.findViewById(R.id.tv_age);
            TextView gender = rootView.findViewById(R.id.tv_gender);
            TextView phone = rootView.findViewById(R.id.tv_phone);
            TextView email = rootView.findViewById(R.id.tv_email);
            TextView region = rootView.findViewById(R.id.tv_region);

            name.setText(u.getFullName());
            age.setText(String.valueOf(u.getAge()));
            gender.setText(u.getGender());
            phone.setText(u.getPhone());
            email.setText(u.getEmail());
            region.setText(u.getRegion());

            ImageView photo = rootView.findViewById(R.id.img_photo_large);
            Picasso.get().load(u.getPhoto())
                    .transform(new PicassoCircleTransformation())
                    .placeholder(R.drawable.placeholder_large)
                    .into(photo);

//            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.baseline_arrow_back_white_24));
//            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //What to do on back clicked
//                }
//            });
        }
        return rootView;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(null);

    }
}
