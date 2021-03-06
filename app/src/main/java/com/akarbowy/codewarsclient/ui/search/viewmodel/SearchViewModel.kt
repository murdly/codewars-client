package com.akarbowy.codewarsclient.ui.search.viewmodel

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import com.akarbowy.codewarsclient.R
import com.akarbowy.codewarsclient.base.BaseViewModel
import com.akarbowy.codewarsclient.controls.adapter.BindingListEventHandler
import com.akarbowy.codewarsclient.controls.search.SearchToolbarView
import com.akarbowy.codewarsclient.ui.search.interactor.SearchInteractor
import com.akarbowy.codewarsclient.ui.search.router.SearchRouter
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class SearchViewModel(
        interactor: SearchInteractor,
        router: SearchRouter
) : BaseViewModel<SearchInteractor, SearchRouter>(interactor, router) {

    val historySearches = ObservableArrayList<SearchResult>()

    val userEventHandler = BindingListEventHandler<SearchResult>()

    val subject: PublishSubject<String> = PublishSubject.create()

    val isInSearchMode = ObservableBoolean(false)

    val isSearchingInProgress = ObservableBoolean(false)

    val noResultsFound = ObservableBoolean(false)

    var query: String? = null

    private val results = mutableListOf<SearchResult>()

    init {
        initUserFocusEventHandler()

        subscribeToSearchQuery()

        loadData()
    }

    private fun initUserFocusEventHandler() {
        disposables += userEventHandler.clickObserver
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribeBy(
                        onNext = { this.onUserClicked(it) }
                )
    }

    override fun loadData() {
        super.loadData()

        disposables += interactor.getLastFiveSearches()
                .subscribeBy(
                        onNext = { onSearchEntryLoaded(it) },
                        onError = { onErrorOccurred(it) }
                )
    }

    private fun onSearchEntryLoaded(data: List<SearchResult>) {

        results.clear()
        results.addAll(data)

        historySearches.clear()
        historySearches.addAll(results)

        onDataLoaded()
    }

    private fun subscribeToSearchQuery() {
        disposables += subject
                .skip(1)
                .doOnNext { filterCurrentResults(it) }
                .debounce(1000, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .doOnNext { isSearchingInProgress.set(true) }
                .switchMapSingle { interactor.searchUser(it) }
                .subscribeBy(
                        onNext = { onUserLoaded(it) },
                        onError = { onUserLoadingError(it) }
                )
    }

    private fun filterCurrentResults(query: String?) {
        val filtered = results
                .filter { it is SearchResult.UserData }
                .map { it as SearchResult.UserData }
                .filter { it.username.startsWith(query ?: "") }

        historySearches.clear()
        historySearches.addAll(filtered)

        noResultsFound.set(filtered.isEmpty())
    }

    private fun onUserLoadingError(error: Throwable) {
        onErrorOccurred(error)

        isSearchingInProgress.set(false)
    }

    private fun sortByRank() {
        val sorted = results
                .filter { it is SearchResult.UserData }
                .map { it as SearchResult.UserData }
                .sortedBy { it.position }

        historySearches.clear()
        historySearches.addAll(sorted)
    }

    private fun sortByRecent() {
        historySearches.clear()
        historySearches.addAll(results)
    }

    private fun onUserLoaded(user: SearchResult) {

        isSearchingInProgress.set(false)

        if (user == SearchResult.NoUser) {
            historySearches.clear()
            noResultsFound.set(true)
        } else {
            noResultsFound.set(false)
        }

    }

    private fun onUserClicked(result: SearchResult) {

        val userData = result as SearchResult.UserData

        router.loadChallengesScreen(userData.username)
    }


    private fun switchedToSearchMode() {
        isInSearchMode.set(true)
    }

    private fun switchedToNormalMode() {
        isInSearchMode.set(false)

        historySearches.clear()
        historySearches.addAll(results)

        noResultsFound.set(false)
    }

    val searchCallback = object : SearchToolbarView.Callback {
        override fun onModeChanged(mode: SearchToolbarView.Mode) {
            when (mode) {
                SearchToolbarView.Mode.Search -> switchedToSearchMode()
                SearchToolbarView.Mode.Normal -> switchedToNormalMode()
            }
        }

        override fun onMenuItemClicked(itemId: Int): Boolean {
            return when (itemId) {
                R.id.action_sort_recent -> {
                    sortByRecent()
                    true
                }
                R.id.action_sort_rank -> {
                    sortByRank()
                    true
                }
                else -> false
            }
        }

        override fun onQueryTextChange(queryText: String) {
            query = queryText

            subject.onNext(queryText)
        }

    }

    sealed class SearchResult {
        data class UserData(
                val username: String,
                val position: Long? = null,
                val bestLanguageText: String? = null
        ) : SearchResult()

        object NoUser : SearchResult()
    }

}