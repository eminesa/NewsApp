package com.example.recyclerviewapp.adapter


import android.widget.Filter
import com.example.recyclerviewapp.dto.DataDTO

import java.util.ArrayList

//this class is for filter.
class CustomFilter(internal var filterList: ArrayList<DataDTO>, internal var adapter: NewsListAdapter) : Filter() {

    //FILTERING OCURS
    override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
        var charSequence = charSequence

        //mantik ile sorgulama yaparak ver filtrelemeyi saglar
        charSequence = charSequence!!.toString().toLowerCase()
        //kontrol etmek icin
        val results = Filter.FilterResults()
        //CHECK CONSTRAINT VALIDITY
        if (charSequence != null && charSequence.length > 0) {

            //STORE OUR FILTERED PLAYERS
            val filteredPlayers = ArrayList<DataDTO>()
           // for (index in filterList) best practis olarak asagidaki foreach yapisi kullanilir. Diger turlu java ya benzer.
            filterList.forEach {
                //CHECK
                if (it.newtitle.toLowerCase().contains(charSequence) || it.newdescrip.toLowerCase().contains(charSequence)) {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(it)
                }
            }
            results.values = filteredPlayers
        } else {
            results.values = filterList
        }
        return results
    }

    override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
        //sort list
        //Filter result ile search ettiigimiz listeyi kendi listemizin icine atiyoruz.
        //bir degisiklik olmadiginda direk listeyi icine atiyoruz.
        //filtrelenen verileri listeler
        adapter.newsList = results.values as ArrayList<DataDTO>
        adapter.notifyDataSetChanged()

    }
}
