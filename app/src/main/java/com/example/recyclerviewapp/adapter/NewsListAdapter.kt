package com.example.recyclerviewapp.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.dto.DataDTO
import com.example.recyclerviewapp.ui.MainActivity
import com.example.recyclerviewapp.ui.NewsDetailActivity

class NewsListAdapter(private val newsList: List<DataDTO>,
                      private val onItemClick: (view: View, newsDTO: DataDTO) -> Unit)  : RecyclerView.Adapter<NewsListViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        return NewsListViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

        override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.bindTo(newsList[position])
        { view, newsDTO -> onItemClick(view, newsDTO)}

        }
}
