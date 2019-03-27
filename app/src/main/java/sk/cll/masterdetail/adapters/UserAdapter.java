package sk.cll.masterdetail.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import sk.cll.masterdetail.R;
import sk.cll.masterdetail.activities.MainActivity;
import sk.cll.masterdetail.data.User;
import sk.cll.masterdetail.data.utils.PicassoCircleTransformation;
import sk.cll.masterdetail.fragments.ItemDetailFragment;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private List<User> data;
    private Context context;

    public UserAdapter(List<User> data, Context context) {
        this.data = data;
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_photo)
        ImageView photo;
        @BindView(R.id.tv_name)
        TextView name;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (data != null) {
            holder.name.setText(data.get(position).getFullName());
            Picasso.get().load(data.get(position).getPhoto())
                    .transform(new PicassoCircleTransformation())
                    .placeholder(R.drawable.placeholder_small)
                    .into(holder.photo);
            holder.itemView.setTag(data.get(position));
            holder.itemView.setOnClickListener(v -> {

                FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
                Fragment old = fm.findFragmentByTag("detail");
                if (old != null) {
                    fm.beginTransaction().remove(old).commit();
                }
                Fragment fragment = ItemDetailFragment.newInstance(data.get(position));
                fm.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.item_detail_container, fragment, "detail")
                        .commit();
            });
        } else {
            holder.name.setText(R.string.loading);
        }
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }
}
