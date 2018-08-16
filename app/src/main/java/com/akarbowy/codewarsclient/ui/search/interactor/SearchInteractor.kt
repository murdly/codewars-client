package com.akarbowy.codewarsclient.ui.search.interactor

import com.akarbowy.codewarsclient.ui.search.viewmodel.SearchViewModel
import io.reactivex.Flowable
import io.reactivex.Single


interface SearchInteractor {

    fun searchUser(username: String): Single<SearchViewModel.SearchResult>

    fun getLastFiveSearches(): Flowable<List<SearchViewModel.SearchResult>>
}