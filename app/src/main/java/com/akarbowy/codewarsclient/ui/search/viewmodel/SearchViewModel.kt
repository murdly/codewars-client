package com.akarbowy.codewarsclient.ui.search.viewmodel

import android.databinding.ObservableArrayList
import com.akarbowy.codewarsclient.base.BaseViewModel
import com.akarbowy.codewarsclient.data.users.model.User
import com.akarbowy.codewarsclient.ui.search.interactor.SearchInteractor
import com.akarbowy.codewarsclient.ui.search.router.SearchRouter


class SearchViewModel(
        interactor: SearchInteractor,
        router: SearchRouter
) : BaseViewModel<SearchInteractor, SearchRouter>(interactor, router) {

    val recentSearches = ObservableArrayList<User>()

    val query: String? = null
}