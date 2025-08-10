package com.osrsplanner.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.osrsplanner.app.databinding.FragmentProfileSetupBinding
import com.osrsplanner.app.ui.viewmodels.ProfileSetupViewModel

class ProfileSetupFragment : Fragment() {
    
    private var _binding: FragmentProfileSetupBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileSetupViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileSetupBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(this)[ProfileSetupViewModel::class.java]
        setupUI()
        observeData()
    }
    
    private fun setupUI() {
        // Setup skill level spinners
        val skillLevels = (1..99).map { it.toString() }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, skillLevels)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        
        binding.spinnerAttack.adapter = adapter
        binding.spinnerDefence.adapter = adapter
        binding.spinnerStrength.adapter = adapter
        binding.spinnerRanged.adapter = adapter
        binding.spinnerMagic.adapter = adapter
        binding.spinnerPrayer.adapter = adapter
        binding.spinnerHitpoints.adapter = adapter
        
        // Setup quest completion checkboxes
        binding.checkboxDragonSlayer.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateQuestCompletion("Dragon Slayer", isChecked)
        }
        
        binding.checkboxRecipeForDisaster.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateQuestCompletion("Recipe for Disaster", isChecked)
        }
        
        binding.checkboxMonkeyMadness.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateQuestCompletion("Monkey Madness", isChecked)
        }
        
        // Setup save button
        binding.buttonSaveProfile.setOnClickListener {
            saveProfile()
        }
        
        // Setup quick level buttons
        binding.buttonSetCombatLevels.setOnClickListener {
            showCombatLevelDialog()
        }
        
        binding.buttonSetAllMax.setOnClickListener {
            setAllSkillsToMax()
        }
    }
    
    private fun saveProfile() {
        val username = binding.editTextUsername.text.toString()
        if (username.isBlank()) {
            binding.editTextUsername.error = "Please enter a username"
            return
        }
        
        val skills = mapOf(
            "attack" to binding.spinnerAttack.selectedItemPosition + 1,
            "defence" to binding.spinnerDefence.selectedItemPosition + 1,
            "strength" to binding.spinnerStrength.selectedItemPosition + 1,
            "ranged" to binding.spinnerRanged.selectedItemPosition + 1,
            "magic" to binding.spinnerMagic.selectedItemPosition + 1,
            "prayer" to binding.spinnerPrayer.selectedItemPosition + 1,
            "hitpoints" to binding.spinnerHitpoints.selectedItemPosition + 1
        )
        
        viewModel.saveProfile(username, skills)
    }
    
    private fun showCombatLevelDialog() {
        // Show dialog to quickly set combat levels
        val levels = listOf("1", "10", "20", "30", "40", "50", "60", "70", "80", "90", "99")
        val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Set Combat Levels")
            .setItems(levels.toTypedArray()) { _, which ->
                val level = levels[which].toInt()
                binding.spinnerAttack.setSelection(level - 1)
                binding.spinnerDefence.setSelection(level - 1)
                binding.spinnerStrength.setSelection(level - 1)
                binding.spinnerRanged.setSelection(level - 1)
                binding.spinnerMagic.setSelection(level - 1)
                binding.spinnerPrayer.setSelection(level - 1)
                binding.spinnerHitpoints.setSelection(level - 1)
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }
    
    private fun setAllSkillsToMax() {
        binding.spinnerAttack.setSelection(98) // 99
        binding.spinnerDefence.setSelection(98)
        binding.spinnerStrength.setSelection(98)
        binding.spinnerRanged.setSelection(98)
        binding.spinnerMagic.setSelection(98)
        binding.spinnerPrayer.setSelection(98)
        binding.spinnerHitpoints.setSelection(98)
    }
    
    private fun observeData() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.buttonSaveProfile.isEnabled = !isLoading
        }
        
        viewModel.saveSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                showSuccessMessage("Profile saved successfully!")
                // Navigate to home or main screen
            }
        }
        
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                showErrorMessage(it)
            }
        }
    }
    
    private fun showSuccessMessage(message: String) {
        com.google.android.material.snackbar.Snackbar.make(
            binding.root,
            message,
            com.google.android.material.snackbar.Snackbar.LENGTH_LONG
        ).show()
    }
    
    private fun showErrorMessage(message: String) {
        com.google.android.material.snackbar.Snackbar.make(
            binding.root,
            message,
            com.google.android.material.snackbar.Snackbar.LENGTH_LONG
        ).setBackgroundTint(requireContext().getColor(com.osrsplanner.app.R.color.error))
        .show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 