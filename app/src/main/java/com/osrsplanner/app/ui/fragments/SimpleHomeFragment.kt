package com.osrsplanner.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.osrsplanner.app.databinding.FragmentSimpleHomeBinding
import com.osrsplanner.app.ui.viewmodels.SimpleHomeViewModel

class SimpleHomeFragment : Fragment() {
    
    private var _binding: FragmentSimpleHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SimpleHomeViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSimpleHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(this)[SimpleHomeViewModel::class.java]
        setupUI()
    }
    
    private fun setupUI() {
        // 1. MONEY MAKING - Most important for most players
        binding.cardMoneyMaking.setOnClickListener {
            showMoneyMakingOptions()
        }
        
        // 2. GEAR - Second most important
        binding.cardGear.setOnClickListener {
            showGearOptions()
        }
        
        // 3. BOSSING - Third most important
        binding.cardBossing.setOnClickListener {
            showBossOptions()
        }
        
        // Optional: Quick profile setup
        binding.buttonSetupProfile.setOnClickListener {
            // Quick profile setup for personalized recommendations
            showQuickProfileSetup()
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
            .setTitle("💰 Money Making Methods")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showBeginnerMoneyMaking()
                    1 -> showMidLevelMoneyMaking()
                    2 -> showHighLevelMoneyMaking()
                    3 -> showEndGameMoneyMaking()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showGearOptions() {
        val options = arrayOf(
            "Budget Setup (1M-10M)",
            "Mid-Range Setup (10M-50M)", 
            "High-End Setup (50M-200M)",
            "Max Setup (200M+)"
        )
        
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("⚔️ Gear Recommendations")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showBudgetGear()
                    1 -> showMidRangeGear()
                    2 -> showHighEndGear()
                    3 -> showMaxGear()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showBossOptions() {
        val options = arrayOf(
            "Beginner Bosses (Easy to learn)",
            "Mid-Level Bosses (Good profit)", 
            "High-Level Bosses (High profit)",
            "End-Game Bosses (Maximum profit)"
        )
        
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("👹 Boss Recommendations")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showBeginnerBosses()
                    1 -> showMidLevelBosses()
                    2 -> showHighLevelBosses()
                    3 -> showEndGameBosses()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun showQuickProfileSetup() {
        val levels = arrayOf(
            "Beginner (1-30 levels)",
            "Mid-Level (40-70 levels)", 
            "High-Level (70+ levels)",
            "Max Level (90+ levels)"
        )
        
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Quick Profile Setup")
            .setMessage("Set your approximate level range for personalized recommendations")
            .setItems(levels) { _, which ->
                when (which) {
                    0 -> viewModel.setQuickProfile("beginner")
                    1 -> viewModel.setQuickProfile("mid_level")
                    2 -> viewModel.setQuickProfile("high_level")
                    3 -> viewModel.setQuickProfile("max_level")
                }
                showSuccessMessage("Profile set! You'll now get personalized recommendations.")
            }
            .setNegativeButton("Skip", null)
            .show()
    }
    
    // Money Making Methods
    private fun showBeginnerMoneyMaking() {
        val methods = listOf(
            "• Woodcutting: 100K-200K/hour",
            "• Fishing: 150K-300K/hour", 
            "• Mining: 200K-400K/hour",
            "• Flipping items: 300K-500K/hour"
        )
        showDetailedGuide("Beginner Money Making", methods)
    }
    
    private fun showMidLevelMoneyMaking() {
        val methods = listOf(
            "• Slayer tasks: 500K-1M/hour",
            "• Barrows: 800K-1.5M/hour",
            "• Zulrah: 1M-2M/hour",
            "• Vorkath: 1.5M-3M/hour"
        )
        showDetailedGuide("Mid-Level Money Making", methods)
    }
    
    private fun showHighLevelMoneyMaking() {
        val methods = listOf(
            "• Vorkath: 2M-4M/hour",
            "• Zulrah: 1.5M-3M/hour",
            "• Raids (TOA): 3M-8M/hour",
            "• Nex: 5M-10M+/hour"
        )
        showDetailedGuide("High-Level Money Making", methods)
    }
    
    private fun showEndGameMoneyMaking() {
        val methods = listOf(
            "• Raids (TOA): 5M-15M/hour",
            "• Nex: 8M-15M+/hour",
            "• Corporeal Beast: 3M-8M/hour",
            "• High-level Slayer: 2M-5M/hour"
        )
        showDetailedGuide("End-Game Money Making", methods)
    }
    
    // Gear Recommendations
    private fun showBudgetGear() {
        val gear = listOf(
            "• Weapon: Dragon scimitar (100K)",
            "• Body: Rune platebody (200K)",
            "• Legs: Rune platelegs (150K)",
            "• Total: ~500K"
        )
        showDetailedGuide("Budget Melee Gear", gear)
    }
    
    private fun showMidRangeGear() {
        val gear = listOf(
            "• Weapon: Abyssal whip (2.5M)",
            "• Body: Bandos chestplate (15M)",
            "• Legs: Bandos tassets (15M)",
            "• Total: ~35M"
        )
        showDetailedGuide("Mid-Range Melee Gear", gear)
    }
    
    private fun showHighEndGear() {
        val gear = listOf(
            "• Weapon: Scythe of vitur (500M)",
            "• Body: Torva platebody (200M)",
            "• Legs: Torva platelegs (200M)",
            "• Total: ~1B+"
        )
        showDetailedGuide("High-End Melee Gear", gear)
    }
    
    private fun showMaxGear() {
        val gear = listOf(
            "• Weapon: Scythe of vitur (500M)",
            "• Body: Torva platebody (200M)",
            "• Legs: Torva platelegs (200M)",
            "• Ring: Ultor ring (100M)",
            "• Total: ~1.5B+"
        )
        showDetailedGuide("Max Melee Gear", gear)
    }
    
    // Boss Recommendations
    private fun showBeginnerBosses() {
        val bosses = listOf(
            "• Giant Mole (Easy, 800K/hour)",
            "• KBD (Easy, 1M/hour)",
            "• Barrows (Easy, 1.2M/hour)",
            "• Sarachnis (Easy, 1.5M/hour)"
        )
        showDetailedGuide("Beginner Bosses", bosses)
    }
    
    private fun showMidLevelBosses() {
        val bosses = listOf(
            "• Vorkath (Medium, 2M-4M/hour)",
            "• Zulrah (Medium, 1.5M-3M/hour)",
            "• Demonic Gorillas (Medium, 1M-2M/hour)",
            "• Slayer Bosses (Varies)"
        )
        showDetailedGuide("Mid-Level Bosses", bosses)
    }
    
    private fun showHighLevelBosses() {
        val bosses = listOf(
            "• Raids (TOA) (Hard, 3M-8M/hour)",
            "• Nex (Hard, 5M-10M+/hour)",
            "• Corporeal Beast (Hard, 3M-8M/hour)",
            "• High-level Slayer Bosses"
        )
        showDetailedGuide("High-Level Bosses", bosses)
    }
    
    private fun showEndGameBosses() {
        val bosses = listOf(
            "• Raids (TOA) (Expert, 5M-15M/hour)",
            "• Nex (Expert, 8M-15M+/hour)",
            "• Corporeal Beast (Expert, 3M-8M/hour)",
            "• All Slayer Bosses"
        )
        showDetailedGuide("End-Game Bosses", bosses)
    }
    
    private fun showDetailedGuide(title: String, items: List<String>) {
        val message = items.joinToString("\n")
        
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Save to Favorites") { _, _ ->
                viewModel.saveToFavorites(title, items)
                showSuccessMessage("Saved to favorites!")
            }
            .setNegativeButton("Close", null)
            .show()
    }
    
    private fun showSuccessMessage(message: String) {
        com.google.android.material.snackbar.Snackbar.make(
            binding.root,
            message,
            com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
        ).show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 