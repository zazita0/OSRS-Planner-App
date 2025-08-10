package com.osrsplanner.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.TextView
import com.osrsplanner.app.R

class MoneyMakingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_money_making, container, false)
        view.findViewById<TextView>(R.id.text_title).text = "Money Making"
        view.findViewById<TextView>(R.id.text_message).text = "Money Making feature coming soon!"
        return view
    }
} 