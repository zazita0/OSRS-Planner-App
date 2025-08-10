package com.osrsplanner.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.osrsplanner.app.databinding.FragmentPersonalizedPlannerBinding
import com.osrsplanner.app.ui.viewmodels.PersonalizedPlannerViewModel

class PersonalizedPlannerFragment : Fragment() {
    
    private var _binding: FragmentPersonalizedPlannerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PersonalizedPlannerViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalizedPlannerBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(this)[PersonalizedPlannerViewModel::class.java]
        setupUI()
        observeData()
    }
    
    private fun setupUI() {
        // Load saved profile if exists
        viewModel.loadSavedProfile()
        
        // Boss Recommendations
        binding.cardBossRecommendations.setOnClickListener {
            showBossRecommendations()
        }
        
        // Gear Optimization
        binding.cardGearOptimization.setOnClickListener {
            showGearOptimization()
        }
        
        // Slayer Task Setup
        binding.cardSlayerSetup.setOnClickListener {
            showSlayerSetup()
        }
        
        // Quest Recommendations
        binding.cardQuestRecommendations.setOnClickListener {
            showQuestRecommendations()
        }
        
        // Money Making for My Level
        binding.cardPersonalMoneyMaking.setOnClickListener {
            showPersonalMoneyMaking()
        }
        
        // XP Goals
        binding.cardXpGoals.setOnClickListener {
            showXpGoals()
        }
        
        // Edit Profile Button
        binding.buttonEditProfile.setOnClickListener {
            // Navigate to profile setup
            // findNavController().navigate(R.id.action_to_profileSetup)
        }
    }
    
    private fun showBossRecommendations() {
        val userProfile = viewModel.getCurrentProfile()
        if (userProfile == null) {
            showSetupProfileDialog()
            return
        }
        
        val recommendations = viewModel.getBossRecommendations(userProfile)
        
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Boss Recommendations for You")
            .setMessage(buildBossRecommendationsMessage(recommendations))
            .setPositiveButton("View Details") { _, _ ->
                // Navigate to detailed boss guide
            }
            .setNegativeButton("Close", null)
            .create()
        dialog.show()
    }
    
    private fun showGearOptimization() {
        val userProfile = viewModel.getCurrentProfile()
        if (userProfile == null) {
            showSetupProfileDialog()
            return
        }
        
        val budgetOptions = arrayOf(
            "Budget: 1M - 10M",
            "Mid-Range: 10M - 50M",
            "High-End: 50M - 200M",
            "Max Gear: 200M+"
        )
        
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Gear Optimization for Your Stats")
            .setItems(budgetOptions) { _, which ->
                val budget = when (which) {
                    0 -> 5000000L // 5M
                    1 -> 25000000L // 25M
                    2 -> 100000000L // 100M
                    3 -> 500000000L // 500M
                    else -> 5000000L
                }
                viewModel.getOptimizedGear(userProfile, budget)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showSlayerSetup() {
        val userProfile = viewModel.getCurrentProfile()
        if (userProfile == null) {
            showSetupProfileDialog()
            return
        }
        
        val slayerLevel = userProfile.skills["slayer"] ?: 1
        val recommendations = viewModel.getSlayerRecommendations(slayerLevel)
        
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Slayer Setup for Level $slayerLevel")
            .setMessage(buildSlayerRecommendationsMessage(recommendations))
            .setPositiveButton("Track Task") { _, _ ->
                // Start tracking current slayer task
            }
            .setNegativeButton("Close", null)
            .create()
        dialog.show()
    }
    
    private fun showQuestRecommendations() {
        val userProfile = viewModel.getCurrentProfile()
        if (userProfile == null) {
            showSetupProfileDialog()
            return
        }
        
        val recommendations = viewModel.getQuestRecommendations(userProfile)
        
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Quest Recommendations")
            .setMessage(buildQuestRecommendationsMessage(recommendations))
            .setPositiveButton("View Quest Guide") { _, _ ->
                // Navigate to quest guide
            }
            .setNegativeButton("Close", null)
            .create()
        dialog.show()
    }
    
    private fun showPersonalMoneyMaking() {
        val userProfile = viewModel.getCurrentProfile()
        if (userProfile == null) {
            showSetupProfileDialog()
            return
        }
        
        val methods = viewModel.getPersonalMoneyMakingMethods(userProfile)
        
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Money Making for Your Stats")
            .setMessage(buildMoneyMakingMessage(methods))
            .setPositiveButton("View Details") { _, _ ->
                // Navigate to detailed money making guide
            }
            .setNegativeButton("Close", null)
            .create()
        dialog.show()
    }
    
    private fun showXpGoals() {
        val userProfile = viewModel.getCurrentProfile()
        if (userProfile == null) {
            showSetupProfileDialog()
            return
        }
        
        val goals = viewModel.getXpGoals(userProfile)
        
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("XP Goals & Training")
            .setMessage(buildXpGoalsMessage(goals))
            .setPositiveButton("Set Goals") { _, _ ->
                // Navigate to XP calculator
            }
            .setNegativeButton("Close", null)
            .create()
        dialog.show()
    }
    
    private fun showSetupProfileDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Setup Your Profile")
            .setMessage("To get personalized recommendations, please set up your character profile with your skill levels and quest progress.")
            .setPositiveButton("Setup Profile") { _, _ ->
                // Navigate to profile setup
                // findNavController().navigate(R.id.action_to_profileSetup)
            }
            .setNegativeButton("Maybe Later", null)
            .show()
    }
    
    private fun buildBossRecommendationsMessage(recommendations: List<String>): String {
        return "Based on your stats, here are the best bosses for you:\n\n" +
                recommendations.joinToString("\n• ", "• ")
    }
    
    private fun buildSlayerRecommendationsMessage(recommendations: List<String>): String {
        return "Best slayer tasks for your level:\n\n" +
                recommendations.joinToString("\n• ", "• ")
    }
    
    private fun buildQuestRecommendationsMessage(recommendations: List<String>): String {
        return "Recommended quests to complete next:\n\n" +
                recommendations.joinToString("\n• ", "• ")
    }
    
    private fun buildMoneyMakingMessage(methods: List<String>): String {
        return "Best money making methods for your stats:\n\n" +
                methods.joinToString("\n• ", "• ")
    }
    
    private fun buildXpGoalsMessage(goals: List<String>): String {
        return "Recommended training goals:\n\n" +
                goals.joinToString("\n• ", "• ")
    }
    
    private fun observeData() {
        viewModel.currentProfile.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                binding.textWelcome.text = "Welcome back, ${it.username}!"
                binding.textCombatLevel.text = "Combat Level: ${it.combatLevel}"
                binding.textTotalLevel.text = "Total Level: ${it.totalLevel}"
                
                // Show personalized features
                binding.cardBossRecommendations.visibility = View.VISIBLE
                binding.cardGearOptimization.visibility = View.VISIBLE
                binding.cardSlayerSetup.visibility = View.VISIBLE
                binding.cardQuestRecommendations.visibility = View.VISIBLE
                binding.cardPersonalMoneyMaking.visibility = View.VISIBLE
                binding.cardXpGoals.visibility = View.VISIBLE
            } ?: run {
                // No profile set up
                binding.textWelcome.text = "Welcome to OSRS Planner!"
                binding.textCombatLevel.text = "Set up your profile for personalized recommendations"
                binding.textTotalLevel.text = "Or use Quick Start for general guides"
                
                // Hide personalized features
                binding.cardBossRecommendations.visibility = View.GONE
                binding.cardGearOptimization.visibility = View.GONE
                binding.cardSlayerSetup.visibility = View.GONE
                binding.cardQuestRecommendations.visibility = View.GONE
                binding.cardPersonalMoneyMaking.visibility = View.GONE
                binding.cardXpGoals.visibility = View.GONE
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 