package sk.cll.masterdetail.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_detail_content.*
import sk.cll.masterdetail.R
import sk.cll.masterdetail.activities.KMainActivity
import sk.cll.masterdetail.db.KUser
import sk.cll.masterdetail.utils.KPicassoCircleTransformation

class KItemDetailFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(arg: KUser): KItemDetailFragment {
            val itemDetailFragment = KItemDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable("user", arg)
            itemDetailFragment.arguments = bundle
            return itemDetailFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val rootView = inflater.inflate(R.layout.item_detail, container, false)
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (activity != null && (activity as KMainActivity).supportActionBar != null) {
                (activity as KMainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }

//        val u = arguments?.getParcelable<KUser>("user")
//        mName?.text = u?.getFullName()
//        mAge?.text = u?.age.toString()
//        mRegion?.text = u?.region
//        mGender?.text = u?.gender
//        mEmail?.text = u?.email
//        mPhone?.text = u?.phone
//
//        Picasso.get().load(u?.photo)
//                .transform(KPicassoCircleTransformation())
//                .placeholder(R.drawable.placeholder_large)
//                .into(mPhoto)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val u = arguments?.getParcelable<KUser>("user")
        mName?.text = u?.getFullName()
        mAge?.text = u?.age.toString()
        mRegion?.text = u?.region
        mGender?.text = u?.gender
        mEmail?.text = u?.email
        mPhone?.text = u?.phone

        Picasso.get().load(u?.photo)
                .transform(KPicassoCircleTransformation())
                .placeholder(R.drawable.placeholder_large)
                .into(mPhoto)
    }
}