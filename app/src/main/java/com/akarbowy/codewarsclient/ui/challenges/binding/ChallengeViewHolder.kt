package com.akarbowy.codewarsclient.ui.challenges.binding

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.akarbowy.codewarsclient.R
import com.akarbowy.codewarsclient.data.network.model.Challenge


class ChallengeViewHolder(view: View)
    : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.name)
    private var challenge: Challenge? = null

    init {
        view.setOnClickListener {
//            challenge?.url?.let { url ->
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                view.context.startActivity(intent)
//            }
        }
    }

    fun bind(challenge: Challenge?) {
        this.challenge = challenge
        name.text = challenge?.name ?: "loading"
    }

    companion object {
        fun create(parent: ViewGroup): ChallengeViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.challenges_item_completed, parent, false)
            return ChallengeViewHolder(view)
        }
    }
}