package sk.cll.masterdetail.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_content.view.*
import sk.cll.masterdetail.R
import sk.cll.masterdetail.activities.KMainActivity
import sk.cll.masterdetail.db.KUser
import sk.cll.masterdetail.fragments.KItemDetailFragment
import sk.cll.masterdetail.utils.KPicassoCircleTransformation

class KUserAdapter(val data: List<KUser>, val context: Context) : RecyclerView.Adapter<KUserAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: KUser) {
            with(itemView) {
                name.text = item.getFullName()
                Picasso.get().load(item.photo)
                        .transform(KPicassoCircleTransformation())
                        .placeholder(R.drawable.placeholder_small)
                        .into(photo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_content, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.tag = data[position]
        holder.itemView.setOnClickListener {
            val fm = (context as KMainActivity).supportFragmentManager
            val fragment = KItemDetailFragment.newInstance(data[position])
            fm.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.item_detail_container, fragment, "detail")
                    .commit()
        }
    }
}