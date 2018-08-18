package com.akarbowy.codewarsclient.ui.challenges.binding

import android.arch.paging.PagedList
import android.databinding.BindingAdapter
import android.support.design.widget.TabLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ViewSwitcher
import com.akarbowy.codewarsclient.R
import com.akarbowy.codewarsclient.controls.adapter.BindingListAdapter
import com.akarbowy.codewarsclient.controls.adapter.BindingListEventHandler
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.repository.challenges.NetworkState
import com.akarbowy.codewarsclient.ui.challenges.viewmodel.ChallengesViewModel
import com.akarbowy.codewarsclient.ui.challenges.viewmodel.ChallengesViewModel.ChallengeTab


@BindingAdapter(value = ["completedChallengesList", "completedNetworkState", "completedEventHandler"])
fun bindCompletedChallenges(recyclerView: RecyclerView,
                            data: PagedList<Challenge>?,
                            networkState: NetworkState?,
                            eventHandler: BindingListEventHandler<Challenge>) {

    var adapter = recyclerView.adapter as? ChallengeAdapter
    if (adapter == null) {
        adapter = ChallengeAdapter()

        adapter.eventHandler = eventHandler

        recyclerView.adapter = adapter

        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL))
    }

    adapter.setNetworkState(networkState)
    
    adapter.submitList(data)
}

@BindingAdapter(value = ["authoredChallengesList", "authoredEventHandler"])
fun bindAuthoredChallenges(recyclerView: RecyclerView,
                           data: ArrayList<Challenge>?,
                           eventHandler: BindingListEventHandler<Challenge>) {

    var adapter = recyclerView.adapter as? BindingListAdapter<Challenge>
    if (adapter == null) {
        adapter = BindingListAdapter()

        adapter.eventHandler = eventHandler

        adapter.addTypeAdapter(AuthoredChallengeAdapter())

        adapter.setHasStableIds(true)

        recyclerView.adapter = adapter

        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL))
    }

    data?.let {
        adapter.list = data
        adapter.notifyDataSetChanged()
    }

}


@BindingAdapter(value = ["setTabView"])
fun bindTabView(switcher: ViewSwitcher,
                selectedTab: ChallengeTab) {

    switcher.displayedChild = selectedTab.ordinal
}

@BindingAdapter(value = ["tabChangedListener"])
fun bindTabs(tabLayout: TabLayout,
             tabListener: (ChallengesViewModel.ChallengeTab) -> Unit) {

    tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.let {
                when (it.text) {
                    tabLayout.resources.getString(R.string.challenge_tab_completed) -> tabListener(ChallengeTab.COMPLETED)
                    tabLayout.resources.getString(R.string.challenge_tab_authored) -> tabListener(ChallengeTab.AUTHORED)
                }
            }
        }

    })

}