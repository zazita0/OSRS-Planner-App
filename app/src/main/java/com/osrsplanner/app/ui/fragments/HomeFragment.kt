package com.osrsplanner.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.osrsplanner.app.databinding.FragmentHomeBinding
import com.osrsplanner.app.ui.viewmodels.HomeViewModel

class HomeFragment : Fragment() {
    
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        setupUI()
        observeData()
    }
    
    private fun setupUI() {
        binding.apply {
            // Setup click listeners for quick access cards
            cardGearPlanner.setOnClickListener {
                // Navigate to gear planner
            }
            
            cardBossPlanner.setOnClickListener {
                // Navigate to boss planner
            }
            
            cardQuestPlanner.setOnClickListener {
                // Navigate to quest planner
            }
            
            cardProFeatures.setOnClickListener {
                // Navigate to pro features
            }
        }
    }
    
    private fun observeData() {
        viewModel.userProfile.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                binding.textUsername.text = it.username
                binding.textCombatLevel.text = "Combat: ${it.combatLevel}"
                binding.textTotalLevel.text = "Total: ${it.totalLevel}"
                
                // Update skill levels
                binding.progressAttack.progress = it.skills["attack"] ?: 0
                binding.progressDefence.progress = it.skills["defence"] ?: 0
                binding.progressStrength.progress = it.skills["strength"] ?: 0
                binding.progressRanged.progress = it.skills["ranged"] ?: 0
                binding.progressMagic.progress = it.skills["magic"] ?: 0
            }
        }
        
        viewModel.recentActivity.observe(viewLifecycleOwner) { activities ->
            // Update recent activity list
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 