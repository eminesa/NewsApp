package com.example.recyclerviewapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.dto.DataDTO
import com.example.recyclerviewapp.ui.NewsDetailActivity

class NewsListViewHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(viewGroup.context)
        .inflate(R.layout.adapter_item_news_list, viewGroup, false)) {

    private val txtTitle by lazy { itemView.findViewById<TextView>(R.id.txtNewTitle) }
    private val txtSummary by lazy { itemView.findViewById<TextView>(R.id.txtNewSummary) }
    private val image by lazy { itemView.findViewById<ImageView>(R.id.imageNew) }

    fun bindTo(dataDTO: DataDTO, onItemClick: (view: View, newsDTO: DataDTO) -> Unit) {

        //databseden gelene veriyi kendi uygulamammızin içerisine burda yediriyoruz
        txtTitle.text = dataDTO.newtitle
        txtSummary.text = dataDTO.newdescrip
        //fotografı glide kütüphanesi yardimiyla cekip kendi imageimizin icerisine ata
        Glide.with(itemView.context).load(dataDTO.newimageurl).into(image)

        itemView.setOnClickListener {

            val intent = Intent(it.context, NewsDetailActivity::class.java)

            //Detail sayfasına gonderdigimiz verileri put extra kullanarak göndericez.
            intent.putExtra("newtitle", dataDTO.newtitle);
            intent.putExtra("newdescrip", dataDTO.newdescrip);
            intent.putExtra("newimageurl", dataDTO.newimageurl);

            it.context.startActivity(intent)
            onItemClick(it, DataDTO())
        }
    }

}
