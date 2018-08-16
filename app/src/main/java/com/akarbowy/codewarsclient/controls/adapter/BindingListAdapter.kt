package com.akarbowy.codewarsclient.controls.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.akarbowy.codewarsclient.R
import com.akarbowy.codewarsclient.BR

class BindingListAdapter<T> : RecyclerView.Adapter<BindingViewHolder<ViewDataBinding>>() {

    private val typeAdapters = ArrayList<BindingItemTypeAdapter<ViewDataBinding, T>>()

    var list = ArrayList<T>()
        set(value) {
            this.list.clear()
            this.list.addAll(value)
        }

    var eventHandler: BindingListEventHandler<T>? = null

    /**
     * Add [BindingItemTypeAdapter] adapters that will handle the binding of different row types
     * You will add one [BindingItemTypeAdapter] per row type.
     *
     * @param adapter [BindingItemTypeAdapter] for each row type
     */
    fun addTypeAdapter(adapter: BindingItemTypeAdapter<ViewDataBinding, T>) {
        this.typeAdapters.add(adapter)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<ViewDataBinding> {

        val inflater = LayoutInflater.from(parent.context)
        val layout: Int

        if (viewType > -1) {
            val typeAdapter = typeAdapters[viewType]
            layout = typeAdapter.layout()
        } else {
            layout = R.layout.list_item_type_adapter_missing_error
        }

        val dataBinding = DataBindingUtil.inflate<ViewDataBinding>(inflater, layout, parent, false)
        return BindingViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ViewDataBinding>, position: Int) {
        val viewType = getItemViewType(position)
        val item = list[position]

        if (viewType > -1) {
            val typeAdapter = typeAdapters[viewType]
            typeAdapter.bind(holder.binding, item, eventHandler!!)
        } else {
            holder.binding.setVariable(BR.message, "No type adapter for item: " + item.toString())
        }

        holder.binding.executePendingBindings()

        notifyOnItemLoaded(item)

        handleOnItemClick(holder, item)
    }

    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        for (i in typeAdapters.indices) {
            if (typeAdapters[i].isItemHandled(item)) {
                return i
            }
        }

        return -1
    }

    override fun getItemId(position: Int): Long {
        return if (hasStableIds()) position.toLong() else super.getItemId(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    /**
     * Notify subscribers that a new item is loaded
     *
     * @param item loaded item
     */
    private fun notifyOnItemLoaded(item: T) {
        eventHandler?.onItemLoaded(item)

    }

    private fun handleOnItemClick(holder: BindingViewHolder<ViewDataBinding>, item: T) {
        holder.binding.root.setOnClickListener { _ ->
            eventHandler?.onClick(item)
        }
    }

}
