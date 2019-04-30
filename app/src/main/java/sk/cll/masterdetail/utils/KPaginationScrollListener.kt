package sk.cll.masterdetail.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class KPaginationScrollListener : RecyclerView.OnScrollListener() {

    protected abstract fun loadMoreItems()

    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = recyclerView.layoutManager?.childCount
        val totalItemCount = recyclerView.layoutManager?.itemCount
        val firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (totalItemCount == null || visibleItemCount == null) return

        if (!isLoading()) {
            if ((visibleItemCount.plus(firstVisibleItemPosition)) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= KUtils.LIMIT) {
                loadMoreItems()
            }
        }
    }
}