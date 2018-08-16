package com.akarbowy.codewarsclient.controls.adapter

import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes

interface BindingItemTypeAdapter<B : ViewDataBinding, T> {

    /**
     * This method determines whether the current item is handled by this type adapter
     *
     * @param item data for row item
     * @return true if the passed item is handled by this type adapter; otherwise false
     */
    fun isItemHandled(item: T): Boolean

    /**
     * Bind variables to xml
     *
     * @param binding      current item binding object
     * @param item         item to bind to xml
     * @param eventHandler [BindingListEventHandler] to bind to xml
     */
    fun bind(binding: B, item: T, eventHandler: BindingListEventHandler<T>)

    /**
     * Layout id to use for the current item type
     *
     * @return layout id e.g.: R.layout.list_item_van or R.layout.list_item_car
     */
    @LayoutRes
    fun layout(): Int
}
