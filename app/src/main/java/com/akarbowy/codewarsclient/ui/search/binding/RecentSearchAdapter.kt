package com.akarbowy.codewarsclient.ui.search.binding

import android.databinding.ViewDataBinding
import com.akarbowy.codewarsclient.BR
import com.akarbowy.codewarsclient.R
import com.akarbowy.codewarsclient.controls.adapter.BindingItemTypeAdapter
import com.akarbowy.codewarsclient.controls.adapter.BindingListEventHandler
import com.akarbowy.codewarsclient.ui.search.viewmodel.SearchViewModel

open class RecentSearchAdapter : BindingItemTypeAdapter<ViewDataBinding, SearchViewModel.SearchResult> {

    override fun isItemHandled(item: SearchViewModel.SearchResult): Boolean {
        return item is SearchViewModel.SearchResult.UserData
    }

    override fun bind(binding: ViewDataBinding, item: SearchViewModel.SearchResult, eventHandler: BindingListEventHandler<SearchViewModel.SearchResult>) {
        binding.setVariable(BR.userData, item as SearchViewModel.SearchResult.UserData)
    }

    override fun layout(): Int {
        return R.layout.search_item_user
    }
}
