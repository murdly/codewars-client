package com.akarbowy.codewarsclient.controls.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

class BindingViewHolder<B : ViewDataBinding> internal constructor(val binding: B) :
        RecyclerView.ViewHolder(binding.root)
