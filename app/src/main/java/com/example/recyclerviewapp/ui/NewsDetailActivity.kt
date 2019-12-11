package com.example.recyclerviewapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.recyclerviewapp.R
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        txtNewTitleDetail.text = intent.extras.get("newtitle").toString()
        txtNewSummaryDetail.text = intent.extras.get("newdescrip").toString()
        //Veri tabanından gelen degerimi detailProjectImageUrl icerisine atiyorum.
        val detailProjectImageUrl = intent.extras.get("newimageurl").toString()
        Glide.with(this).load(detailProjectImageUrl).into(imageNewDetail);
    }
}
