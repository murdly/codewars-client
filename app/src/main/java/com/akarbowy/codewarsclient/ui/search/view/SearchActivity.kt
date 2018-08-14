package com.akarbowy.codewarsclient.ui.search.view

import com.akarbowy.codewarsclient.R
import com.akarbowy.codewarsclient.base.BaseBindingActivity
import com.akarbowy.codewarsclient.databinding.ActivitySearchBinding
import com.akarbowy.codewarsclient.ui.search.viewmodel.SearchViewModel

class SearchActivity : BaseBindingActivity<ActivitySearchBinding, SearchViewModel>() {

    override fun layout() = R.layout.activity_search

    override fun viewModelClass() = SearchViewModel::class.java

}
