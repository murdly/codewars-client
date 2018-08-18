package com.akarbowy.codewarsclient.ui.challenges.binding

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.akarbowy.codewarsclient.R
import com.akarbowy.codewarsclient.controls.adapter.BindingListEventHandler
import com.akarbowy.codewarsclient.data.network.model.Challenge


class ChallengeViewHolder(view: View)
    : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.name)

    private var eventHandler: BindingListEventHandler<Challenge>? = null

    fun bind(challenge: Challenge?, eventHandler: BindingListEventHandler<Challenge>?) {
        this.eventHandler = eventHandler

        name.text = challenge?.name ?: "Unkown name"

        challenge?.let { handleOnItemClick(itemView, challenge) }
    }

    private fun handleOnItemClick(view: View, item: Challenge) {
        view.setOnClickListener { _ ->
            eventHandler?.onClick(item)
        }
    }

    companion object {
        fun create(parent: ViewGroup): ChallengeViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.challenges_item_completed, parent, false)
            return ChallengeViewHolder(view)
        }
    }
}