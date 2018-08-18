package com.akarbowy.codewarsclient.ui.detail.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayoutManager
import ru.noties.markwon.Markwon


@BindingAdapter(value = ["setMarkdown"])
fun bindCompletedChallenges(textView: TextView,
                            challenge: Challenge?) {

    textView.visibility = if (challenge?.description.isNullOrBlank()) GONE else VISIBLE

    challenge?.description?.let { markdown ->
        Markwon.setMarkdown(textView, markdown)
    }
}

@BindingAdapter(value = ["setLanguages"])
fun bindCompletedChallenges(recyclerView: RecyclerView,
                            languages: List<String>?) {

    var adapter = recyclerView.adapter as? LanguagesAdapter
    if (adapter == null) {
        adapter = LanguagesAdapter()

        val flexbox = FlexboxLayoutManager(recyclerView.context)
        flexbox.alignItems = AlignItems.STRETCH

        recyclerView.layoutManager = flexbox

        recyclerView.adapter = adapter
    }

    languages?.let {
        adapter.setLanguages(it)
        adapter.notifyDataSetChanged()
    }
}