package com.akarbowy.codewarsclient.ui.detail.binding

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.akarbowy.codewarsclient.R


class LanguagesAdapter : RecyclerView.Adapter<LanguageViewHolder>() {

    private val languages = mutableListOf<String>()

    fun setLanguages(languages: List<String>) {
        this.languages.clear()
        this.languages.addAll(languages)
    }

    override fun getItemCount() = languages.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.detail_item_language, parent, false)

        return LanguageViewHolder(view)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val adapterPosition = holder.adapterPosition

        holder.bindTo(languages[position])
    }
}