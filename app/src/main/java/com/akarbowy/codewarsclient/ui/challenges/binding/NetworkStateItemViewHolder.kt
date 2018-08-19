package com.akarbowy.codewarsclient.ui.challenges.binding

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.akarbowy.codewarsclient.R
import com.akarbowy.codewarsclient.data.repository.challenges.NetworkState
import com.akarbowy.codewarsclient.data.repository.challenges.Status
import com.akarbowy.codewarsclient.helpers.converters.VisibilityConverter


class NetworkStateItemViewHolder(view: View)
    : RecyclerView.ViewHolder(view) {

    private val loadingIndicator: ProgressBar = view.findViewById(R.id.loading_indicator)

    private val errorView: TextView = view.findViewById(R.id.error_message)

    fun bind(networkState: NetworkState?) {

        loadingIndicator.visibility = VisibilityConverter.toVisibility(networkState?.status == Status.RUNNING)
        errorView.visibility = VisibilityConverter.toVisibility(networkState?.msg != null)
    }

    companion object {
        fun create(parent: ViewGroup): NetworkStateItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view)
        }
    }
}