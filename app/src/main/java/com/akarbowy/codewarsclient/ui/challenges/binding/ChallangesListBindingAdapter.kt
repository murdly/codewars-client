package com.akarbowy.codewarsclient.ui.challenges.binding

import android.arch.paging.PagedList
import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.akarbowy.codewarsclient.data.network.model.Challenge


@BindingAdapter(value = ["challengesList"])
fun bindChallenges(recyclerView: RecyclerView,
                   pagedList: PagedList<Challenge>?) {

    val adapter = recyclerView.adapter as? ChallengeAdapter
    if (adapter == null) {
        recyclerView.adapter = ChallengeAdapter()
    }

    adapter?.submitList(pagedList)

}