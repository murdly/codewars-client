package com.akarbowy.codewarsclient.ui.challenges.binding

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.akarbowy.codewarsclient.R
import com.akarbowy.codewarsclient.data.network.model.Challenge


class ChallengeAdapter : PagedListAdapter<Challenge, RecyclerView.ViewHolder>(CHALLENGE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.challenges_item_completed -> ChallengeViewHolder.create(parent)
//            R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.challenges_item_completed -> (holder as ChallengeViewHolder).bind(getItem(position))
//            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bindTo(
//                    networkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.challenges_item_completed

//        return if (hasExtraRow() && position == itemCount - 1) {
//            R.layout.network_state_item
//        } else {
//            R.layout.challenges_item_completed
//        }
    }

//    override fun getItemCount(): Int {
//        return super.getItemCount() + if (hasExtraRow()) 1 else 0
//    }

    companion object {
        val CHALLENGE_COMPARATOR = object : DiffUtil.ItemCallback<Challenge>() {
            override fun areContentsTheSame(oldItem: Challenge, newItem: Challenge): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Challenge, newItem: Challenge): Boolean =
                    oldItem.name == newItem.name
        }

    }
}