package com.example.firemap

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView

class SelectMap : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_map)

        viewManager = LinearLayoutManager(this)
        //viewAdapter = ChangeMapAdapter(mapDataset) TODO: create dataset of maps

        recyclerView = findViewById<RecyclerView>(R.id.map_list_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            //setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify a viewAdapter
            adapter = viewAdapter
        }

    }
}

class ChangeMapAdapter(private val mapDataset: Array<String>) {

    class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)


}