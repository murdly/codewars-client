package com.akarbowy.codewarsclient.ui.search.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.akarbowy.codewarsclient.controls.adapter.BindingListAdapter
import com.akarbowy.codewarsclient.controls.adapter.BindingListEventHandler
import com.akarbowy.codewarsclient.controls.search.SearchToolbarView
import com.akarbowy.codewarsclient.controls.search.SearchToolbarView.Mode
import com.akarbowy.codewarsclient.helpers.utils.KeyboardUtils
import com.akarbowy.codewarsclient.ui.search.viewmodel.SearchViewModel


@BindingAdapter(value = ["searchesData", "searchesEventHandler"])
fun bindRecentSearches(recyclerView: RecyclerView,
                       data: MutableList<SearchViewModel.SearchResult>?,
                       eventHandler: BindingListEventHandler<SearchViewModel.SearchResult>) {

    var adapter: BindingListAdapter<SearchViewModel.SearchResult>? = recyclerView.adapter as? BindingListAdapter<SearchViewModel.SearchResult>
    if (adapter == null) {
        adapter = BindingListAdapter()

        adapter.eventHandler = eventHandler

        adapter.addTypeAdapter(RecentSearchAdapter())

        adapter.setHasStableIds(true)

        val layoutManager = LinearLayoutManager(recyclerView.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    KeyboardUtils.hide(recyclerView)
                }
            }
        })

    }

    adapter.list = data as ArrayList<SearchViewModel.SearchResult>
    adapter.notifyDataSetChanged()
}

@BindingAdapter(value = ["searchQuery", "searchCallback"])
fun bindSearchToolbar(toolbar: SearchToolbarView,
                      query: String?,
                      callback: SearchToolbarView.Callback) {

    toolbar.mode = if (query != null) Mode.Search else Mode.Normal

    toolbar.callback = callback
    toolbar.query = query


}
