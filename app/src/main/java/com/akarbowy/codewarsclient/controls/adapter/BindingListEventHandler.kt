package com.akarbowy.codewarsclient.controls.adapter

import io.reactivex.subjects.PublishSubject

class BindingListEventHandler<T> {

    /**
     * Subscribe to this Observer if you would like to be notified when an item is clicked
     *
     * @return PublishSubject to subscribe to
     */
    val clickObserver = PublishSubject.create<T>()

    /**
     * Subscribe to this Observer if you would like to be notified when a button on the item is clicked
     *
     * @return PublishSubject to subscribe to
     */
    val secondaryClickObserver = PublishSubject.create<T>()

    /**
     * Subscribe to this Observer if you would like to be notified when an item is created
     *
     * @return PublishSubject to subscribe to
     */
    val itemLoadedObserver = PublishSubject.create<T>()

    /**
     * Call it when an item is clicked.
     * Binding example: android:onClick="@{ () -> eventHandler.onClick(item) }"
     * where eventHandler is a variable bound to the row item
     *
     * @param itemData the data of the clicked item
     */
    fun onClick(itemData: T) {
        clickObserver.onNext(itemData)
    }

    /**
     * Call it when a button on the item is clicked.
     * Binding example: android:onClick="@{ () -> eventHandler.onSecondaryClick(item) }"
     * where eventHandler is a variable bound to the row item
     *
     * @param itemData the data of the clicked item
     */
    fun onSecondaryClick(itemData: T) {
        secondaryClickObserver.onNext(itemData)
    }

    /**
     * This is automatically called if an item is created
     *
     * @param itemData item data
     */
    fun onItemLoaded(itemData: T) {
        itemLoadedObserver.onNext(itemData)
    }
}
