package com.example.recyclerviewapp.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.dto.DataDTO
import java.util.*


class NewsListAdapter(var newsList: List<DataDTO>,
                      private val onItemClick: (view: View, newsDTO: DataDTO) -> Unit)
    : RecyclerView.Adapter<NewsListViewHolder>(), Filterable {
    var filter: CustomFilter? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        return NewsListViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.bindTo(newsList[position])
        { view, newsDTO -> onItemClick(view, newsDTO) }

    }

    //For filter
    override fun getFilter(): Filter {
        if (filter == null) {
            filter = CustomFilter(newsList as ArrayList<DataDTO>, this)
        }
        return filter as CustomFilter
    }
}
