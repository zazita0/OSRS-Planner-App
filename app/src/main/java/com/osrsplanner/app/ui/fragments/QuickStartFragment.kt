package com.osrsplanner.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.osrsplanner.app.databinding.FragmentQuickStartBinding
import com.osrsplanner.app.ui.viewmodels.QuickStartViewModel

class QuickStartFragment : Fragment() {
    
    private var _binding: FragmentQuickStartBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: QuickStartViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuickStartBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(this)[QuickStartViewModel::class.java]
        setupUI()
    }
    
    private fun setupUI() {
        // Money Making - No account data needed!
        binding.cardMoneyMaking.setOnClickListener {
            // Show money making methods for different levels
            showMoneyMakingOptions()
        }
        
        // Quest Guide - No account data needed!
        binding.cardQuestGuide.setOnClickListener {
            // Show quest recommendations
            showQuestGuide()
        }
        
        // Gear Calculator - Budget-based
        binding.cardGearCalculator.setOnClickListener {
            showGearCalculator()
        }
        
        // Boss Guide - No account data needed!
        binding.cardBossGuide.setOnClickListener {
            showBossGuide()
        }
        
        // XP Calculator - Quick presets
        binding.cardXpCalculator.setOnClickListener {
            showXpCalculator()
        }
        
        // Clue Scroll Helper - No account data needed!
        binding.cardClueHelper.setOnClickListener {
            showClueHelper()
        }
    }
    
    private fun showMoneyMakingOptions() {
        val options = arrayOf(
            "Beginner (1-30 levels) - 100K-500K/hour",
            "Mid-Level (40-70 levels) - 500K-2M/hour", 
            "High-Level (70+ levels) - 1M-5M/hour",
            "End-Game (90+ levels) - 3M-10M+/hour"
        )
        
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Money Making Methods")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> viewModel.showBeginnerMoneyMaking()
                    1 -> viewModel.showMidLevelMoneyMaking()
                    2 -> viewModel.showHighLevelMoneyMaking()
                    3 -> viewModel.showEndGameMoneyMaking()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showQuestGuide() {
        val questCategories = arrayOf(
            "Essential Quests (Unlock important content)",
            "Combat Quests (For PvM progression)",
            "Skilling Quests (For skill unlocks)",
            "Quest Point Cape (Completionist goals)"
        )
        
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Quest Guide")
            .setItems(questCategories) { _, which ->
                when (which) {
                    0 -> viewModel.showEssentialQuests()
                    1 -> viewModel.showCombatQuests()
                    2 -> viewModel.showSkillingQuests()
                    3 -> viewModel.showQuestPointCape()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showGearCalculator() {
        val budgetOptions = arrayOf(
            "Budget: 1M - 10M",
            "Mid-Range: 10M - 50M", 
            "High-End: 50M - 200M",
            "Max Gear: 200M+"
        )
        
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Gear Calculator")
            .setItems(budgetOptions) { _, which ->
                when (which) {
                    0 -> viewModel.showBudgetGear()
                    1 -> viewModel.showMidRangeGear()
                    2 -> viewModel.showHighEndGear()
                    3 -> viewModel.showMaxGear()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showBossGuide() {
        val bossCategories = arrayOf(
            "Beginner Bosses (Easy to learn)",
            "Mid-Level Bosses (Good profit)",
            "High-Level Bosses (High profit)",
            "End-Game Bosses (Maximum profit)"
        )
        
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Boss Guide")
            .setItems(bossCategories) { _, which ->
                when (which) {
                    0 -> viewModel.showBeginnerBosses()
                    1 -> viewModel.showMidLevelBosses()
                    2 -> viewModel.showHighLevelBosses()
                    3 -> viewModel.showEndGameBosses()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showXpCalculator() {
        val skillCategories = arrayOf(
            "Combat Skills (Attack, Strength, Defence)",
            "Ranged & Magic",
            "Gathering Skills (Mining, Woodcutting, Fishing)",
            "Production Skills (Cooking, Smithing, Crafting)",
            "Support Skills (Prayer, Agility, Slayer)"
        )
        
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("XP Calculator")
            .setItems(skillCategories) { _, which ->
                when (which) {
                    0 -> viewModel.showCombatXpCalculator()
                    1 -> viewModel.showRangedMagicXpCalculator()
                    2 -> viewModel.showGatheringXpCalculator()
                    3 -> viewModel.showProductionXpCalculator()
                    4 -> viewModel.showSupportXpCalculator()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showClueHelper() {
        val clueTypes = arrayOf(
            "Easy Clues (Beginner friendly)",
            "Medium Clues (Good rewards)",
            "Hard Clues (Valuable rewards)",
            "Elite Clues (Rare rewards)",
            "Master Clues (Best rewards)"
        )
        
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Clue Scroll Helper")
            .setItems(clueTypes) { _, which ->
                when (which) {
                    0 -> viewModel.showEasyClues()
                    1 -> viewModel.showMediumClues()
                    2 -> viewModel.showHardClues()
                    3 -> viewModel.showEliteClues()
                    4 -> viewModel.showMasterClues()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 