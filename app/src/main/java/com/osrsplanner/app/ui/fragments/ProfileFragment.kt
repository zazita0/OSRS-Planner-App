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

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        view.findViewById<TextView>(R.id.text_username).text = "Player123"
        view.findViewById<TextView>(R.id.text_total_level).text = "Total Level: 1500"
        view.findViewById<TextView>(R.id.text_combat_level).text = "Combat Level: 85"
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_achievements)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = AchievementAdapter(listOf(
            "Completed all F2P quests",
            "Defeated Barrows Brothers",
            "Achieved 99 Strength"
        ))
        return view
    }

    class AchievementAdapter(private val items: List<String>) : RecyclerView.Adapter<AchievementAdapter.ViewHolder>() {
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val text: TextView = view.findViewById(R.id.text_achievement)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_achievement, parent, false)
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = items[position]
        }
        override fun getItemCount() = items.size
    }
} 