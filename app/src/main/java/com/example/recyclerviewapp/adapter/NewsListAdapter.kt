package com.example.recyclerviewapp.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.dto.DataDTO

class NewsListAdapter(private val newsList: List<DataDTO>) : RecyclerView.Adapter<NewsListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        return NewsListViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.bindTo(newsList[position])
    }
}
