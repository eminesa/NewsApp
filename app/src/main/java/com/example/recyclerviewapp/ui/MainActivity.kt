package com.example.recyclerviewapp.ui


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.adapter.NewsListAdapter
import com.example.recyclerviewapp.dto.DataDTO
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity)
        }
        //retrieve data
        retrieveData();
    }

    private fun retrieveData() {

        lateinit var database: DatabaseReference

        database = FirebaseDatabase.getInstance().reference.child("news")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val posts = ArrayList<DataDTO>()

                dataSnapshot.children.forEach {
                    val post = it.getValue(DataDTO::class.java)
                    posts.add(post!!)
                }

                val adapter = NewsListAdapter(posts) { view, newsDTO ->
                    //gonderilecek olan verileri burdan atiyoruz.

                }
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                //print error.message
            }
        })
    }


}
