package com.osrsplanner.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.osrsplanner.app.R

class BossPlannerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_boss_planner, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_boss)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = BossAdapter(listOf(
            "Barrows",
            "Zulrah",
            "Vorkath",
            "General Graardor"
        ))
        return view
    }

    class BossAdapter(private val items: List<String>) : RecyclerView.Adapter<BossAdapter.ViewHolder>() {
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val text: TextView = view.findViewById(R.id.text_boss_name)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_boss, parent, false)
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = items[position]
        }
        override fun getItemCount() = items.size
    }
} 