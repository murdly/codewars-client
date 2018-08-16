package com.akarbowy.codewarsclient.ui.search.view

import com.akarbowy.codewarsclient.R
import com.akarbowy.codewarsclient.base.BaseBindingActivity
import com.akarbowy.codewarsclient.controls.search.SearchToolbarView
import com.akarbowy.codewarsclient.databinding.ActivitySearchBinding
import com.akarbowy.codewarsclient.ui.search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseBindingActivity<ActivitySearchBinding, SearchViewModel>() {

    override fun layout() = R.layout.activity_search

    override fun viewModelClass() = SearchViewModel::class.java

    override fun onBackPressed() {
        if (search_toolbar.mode == SearchToolbarView.Mode.Search) {
            search_toolbar.mode = SearchToolbarView.Mode.Normal
        } else {
            super.onBackPressed()
        }
    }

}
