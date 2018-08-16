package com.akarbowy.codewarsclient.helpers.converters

import android.databinding.BindingConversion
import android.view.View

object VisibilityConverter {

    @BindingConversion
    @JvmStatic
    fun convertBooleanToVisibility(isVisible: Boolean) = if (isVisible) View.VISIBLE else View.GONE

}
