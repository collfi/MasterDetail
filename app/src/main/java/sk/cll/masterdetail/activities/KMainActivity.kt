package sk.cll.masterdetail.activities

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sk.cll.masterdetail.R
import sk.cll.masterdetail.adapters.KUserAdapter
import sk.cll.masterdetail.data.KUserAndroidViewModel
import sk.cll.masterdetail.db.KUser
import sk.cll.masterdetail.utils.KPaginationScrollListener
import sk.cll.masterdetail.utils.KUtils

class KMainActivity: AppCompatActivity() {

    private var isLoading = false
    private var mUsers : List<KUser>? = null
    private var mModel: KUserAndroidViewModel? = null
    private var mCallback: Callback<List<KUser>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(mToolbar)
        mToolbar?.title = title

        mUsers = ArrayList()
        mRecyclerView.adapter = KUserAdapter(mUsers!!,this) //??
        mModel = ViewModelProviders.of(this).get(KUserAndroidViewModel::class.java)

        mProgressBar?.visibility = View.VISIBLE

        mCallback = object: Callback<List<KUser>> {
            override fun onFailure(call: Call<List<KUser>>, t: Throwable) {
                showError(t.localizedMessage)
            }

            override fun onResponse(call: Call<List<KUser>>, response: Response<List<KUser>>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        mProgressBar?.visibility = View.GONE
//                        mUsers?.let { addAll(response.body() as ArrayList) }
                        if (mUsers != null) {
                            (mUsers as ArrayList).addAll(response.body()!!)
                        }
                        mRecyclerView.adapter?.notifyDataSetChanged()
                        isLoading = false
                        mModel?.insert(response.body()!!)
                    }
                } else {
                    showError(response.message())
                }
            }

            private fun showError(error: String) {
                Log.e("KMainActivity", error)
                Toast.makeText(this@KMainActivity, R.string.error_loading, Toast.LENGTH_LONG).show()
                mProgressBar?.visibility = View.GONE
                if (mUsers?.isEmpty() == true) {
                    mEmptyView?.visibility = View.VISIBLE
                }
                isLoading = false
            }
        }

        mEmptyView?.setOnClickListener{
            mProgressBar?.visibility = View.VISIBLE
            mEmptyView?.visibility = View.GONE
            downloadUsers()
        }

        mRecyclerView.addOnScrollListener(object : KPaginationScrollListener() {
            override fun loadMoreItems() {
                downloadUsers()
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })

        mModel?.mAllUsers?.observe(this, Observer<List<KUser>> { users ->
            if (users != null) {
                if (users.isEmpty()) {
                    downloadUsers()
                    return@Observer
                }
                if ((mUsers as ArrayList).isEmpty()) { //??????
                    (mUsers as ArrayList).addAll(users)
                    mRecyclerView.adapter?.notifyDataSetChanged()
                    mProgressBar?.visibility = View.GONE
                    isLoading = false
                }
            }
        })

    }

    private fun downloadUsers() {
        if (KUtils.checkNetworkConnection(this)) {
            if (isLoading) return
            Toast.makeText(this, R.string.downloading, Toast.LENGTH_SHORT).show()
            isLoading = true
            mModel?.getAndSaveNewUsers()?.enqueue(mCallback)

        } else {
            Toast.makeText(this, R.string.error_internet, Toast.LENGTH_LONG).show()
            mEmptyView?.visibility = if (mUsers?.isEmpty() == true) View.VISIBLE else View.GONE
            mProgressBar?.visibility = View.GONE
        }
    }

    private fun removeDetailFragment() {
        val old = supportFragmentManager.findFragmentByTag("detail")
        if (old != null) {
            supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .remove(old)
                    .commit()
        }
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentByTag("detail") != null) {
            removeDetailFragment()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        removeDetailFragment()
        return super.onSupportNavigateUp()
    }


}