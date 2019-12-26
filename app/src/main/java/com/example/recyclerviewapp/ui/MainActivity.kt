package com.example.recyclerviewapp.ui


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.adapter.NewsListAdapter
import com.example.recyclerviewapp.dto.DataDTO
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val posts = ArrayList<DataDTO>()
    val adapter = NewsListAdapter(posts) { view, newsDTO ->
        //gonderilecek olan verileri burdan atiyoruz.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity)
        }
        //retrieve data
        retrieveData()

        //for search
        searchView()

        //go to add news activity
        goAddActivity()
    }


    private fun retrieveData() {

        lateinit var database: DatabaseReference

        database = FirebaseDatabase.getInstance().reference.child("news")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataSnapshot.children.forEach {
                    val post = it.getValue(DataDTO::class.java)
                    posts.add(post!!)

                }

                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                //print error.message
            }
        })
    }

    private fun searchView() {
        searchView.queryHint = "enter news title"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {

                //adapter nasıl cagrilir
                adapter.getFilter().filter(newText)

                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                return false
            }

        })

    }

    private fun goAddActivity() {

        fabButton.setOnClickListener {
            val intent = Intent(it.context, AddNewsActivity::class.java)
            it.context.startActivity(intent)
        }
    }

}
