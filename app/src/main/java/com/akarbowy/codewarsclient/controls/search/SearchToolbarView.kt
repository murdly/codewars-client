package com.akarbowy.codewarsclient.controls.search

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.akarbowy.codewarsclient.R
import com.akarbowy.codewarsclient.helpers.utils.KeyboardUtils
import kotlinx.android.synthetic.main.view_toolbar_search.view.*


class SearchToolbarView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {

    var mode: Mode = Mode.Normal
        set (value) {
            field = value
            onModeChange()
        }

    var query: String? = null
        set(value) {
            field = value
            field_query.setText(value)
        }

    var callback: Callback? = null

    init {
        View.inflate(context, R.layout.view_toolbar_search, this)

        initViews()
    }

    private fun initViews() {
        toolbar_layout_action.setOnClickListener { mode = Mode.Search }

        toolbar.setNavigationOnClickListener { mode = Mode.Normal }

        button_field_cancel.setOnClickListener {
            field_query.text.clear()
            button_field_cancel.visibility = GONE
        }

        field_query.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(query: Editable?) {
                callback?.onQueryTextChange(query.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                button_field_cancel.visibility = if (text.isNullOrEmpty()) GONE else VISIBLE
            }

        })

        toolbar.setOnMenuItemClickListener {
            callback?.onMenuItemClicked(it.itemId) ?: false
        }

        field_query.setOnEditorActionListener { textView, _, _ ->
            KeyboardUtils.hide(textView)
            true
        }

    }

    private fun onModeChange() {
        when (mode) {
            Mode.Search -> setSearchMode()
            Mode.Normal -> setNormalMode()
        }
    }

    private fun setNormalMode() {
        toolbar.navigationIcon = null
        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.menu_search)
        field_query.text.clear()
        toolbar_layout_action.visibility = View.VISIBLE
        toolbar_layout_searchable.visibility = View.GONE
        KeyboardUtils.hide(this)
    }

    private fun setSearchMode() {
        toolbar_layout_action.visibility = View.GONE
        toolbar_layout_searchable.visibility = View.VISIBLE
        field_query.text.clear()
        field_query.requestFocus()
        KeyboardUtils.show(this)
        toolbar.menu.clear()
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
    }

    enum class Mode {
        Normal, Search
    }

    interface Callback {
        fun onQueryTextChange(queryText: String)

        fun onMenuItemClicked(itemId: Int): Boolean
    }
}

