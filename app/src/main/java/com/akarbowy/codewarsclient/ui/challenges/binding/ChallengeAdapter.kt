package com.akarbowy.codewarsclient.ui.challenges.binding

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.akarbowy.codewarsclient.R
import com.akarbowy.codewarsclient.controls.adapter.BindingListEventHandler
import com.akarbowy.codewarsclient.data.network.model.Challenge
import com.akarbowy.codewarsclient.data.repository.challenges.NetworkState


class ChallengeAdapter : PagedListAdapter<Challenge, RecyclerView.ViewHolder>(CHALLENGE_COMPARATOR) {

    private var networkState: NetworkState? = null

    var eventHandler: BindingListEventHandler<Challenge>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.challenges_item_completed -> ChallengeViewHolder.create(parent)
            R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.challenges_item_completed -> (holder as ChallengeViewHolder).bind(getItem(position), eventHandler)
            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.challenges_item_completed
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        val CHALLENGE_COMPARATOR = object : DiffUtil.ItemCallback<Challenge>() {
            override fun areContentsTheSame(oldItem: Challenge, newItem: Challenge): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Challenge, newItem: Challenge): Boolean =
                    oldItem.name == newItem.name
        }

    }
}