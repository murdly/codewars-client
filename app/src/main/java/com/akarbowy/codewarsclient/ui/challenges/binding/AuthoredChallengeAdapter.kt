package com.akarbowy.codewarsclient.ui.challenges.binding

import android.databinding.ViewDataBinding
import com.akarbowy.codewarsclient.BR
import com.akarbowy.codewarsclient.R
import com.akarbowy.codewarsclient.controls.adapter.BindingItemTypeAdapter
import com.akarbowy.codewarsclient.controls.adapter.BindingListEventHandler
import com.akarbowy.codewarsclient.data.network.model.Challenge

open class AuthoredChallengeAdapter : BindingItemTypeAdapter<ViewDataBinding, Challenge> {

    override fun isItemHandled(item: Challenge): Boolean {
        return true
    }

    override fun bind(binding: ViewDataBinding, item: Challenge, eventHandler: BindingListEventHandler<Challenge>) {
        binding.setVariable(BR.challenge, item)
    }

    override fun layout(): Int {
        return R.layout.challenges_item_authored
    }
}
