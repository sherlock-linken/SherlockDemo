package com.tanzy.sherlockdemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tanzy.sherlockdemo.entity.DemoEntity
import com.tanzy.sherlockdemo.radar.RadarActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    private val demoList: ArrayList<DemoEntity> = arrayListOf()
    private val adapter by lazy { DemoAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rcy_demos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcy_demos.adapter = adapter

        demoList.add(DemoEntity("hunterRadar", Intent(this,RadarActivity::class.java)))

    }

    inner class DemoAdapter() : RecyclerView.Adapter<DemoViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoViewHolder {
            return DemoViewHolder(LayoutInflater.from(this@MainActivity).inflate(R.layout.item_main_demo_list, parent, false))
        }

        override fun getItemCount(): Int {
            return demoList.size
        }

        override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
            holder.name.text = demoList[position].name
            holder.itemView.setOnClickListener {
                startActivity(demoList[position].intent)
            }
        }

    }


    class DemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var name: TextView

        init {
            name = itemView.findViewById<TextView>(R.id.tv_demo_name)
        }
    }
}
