package com.osrsplanner.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.TextView
import com.osrsplanner.app.R

class XP_CalculatorFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_xp_calculator, container, false)
        view.findViewById<TextView>(R.id.text_title).text = "XP Calculator"
        view.findViewById<TextView>(R.id.text_message).text = "XP Calculator feature coming soon!"
        return view
    }
} 