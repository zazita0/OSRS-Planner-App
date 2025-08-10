package com.osrsplanner.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.osrsplanner.app.databinding.FragmentPvmSetupBuilderBinding
import com.osrsplanner.app.ui.viewmodels.PvMSetupBuilderViewModel

class PvMSetupBuilderFragment : Fragment() {
    
    private var _binding: FragmentPvmSetupBuilderBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PvMSetupBuilderViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPvmSetupBuilderBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(this)[PvMSetupBuilderViewModel::class.java]
        setupUI()
        observeData()
    }
    
    private fun setupUI() {
        // Boss selection
        binding.spinnerBoss.setOnItemSelectedListener { position ->
            val boss = viewModel.getAvailableBosses()[position]
            viewModel.selectBoss(boss)
        }
        
        // Budget input
        binding.editTextBudget.addTextChangedListener { text ->
            val budget = text.toString().toLongOrNull() ?: 0L
            viewModel.setBudget(budget)
        }
        
        // Combat style selection
        binding.radioGroupCombatStyle.setOnCheckedChangeListener { _, checkedId ->
            val style = when (checkedId) {
                binding.radioMelee.id -> "MELEE"
                binding.radioRanged.id -> "RANGED"
                binding.radioMagic.id -> "MAGIC"
                binding.radioHybrid.id -> "HYBRID"
                else -> "MELEE"
            }
            viewModel.setCombatStyle(style)
        }
        
        // Build setup button
        binding.buttonBuildSetup.setOnClickListener {
            buildPvMSetup()
        }
        
        // Save setup button
        binding.buttonSaveSetup.setOnClickListener {
            saveCurrentSetup()
        }
        
        // Load saved setups
        binding.buttonLoadSetups.setOnClickListener {
            showSavedSetups()
        }
    }
    
    private fun buildPvMSetup() {
        val budget = binding.editTextBudget.text.toString().toLongOrNull() ?: 0L
        if (budget <= 0) {
            binding.editTextBudget.error = "Please enter a valid budget"
            return
        }
        
        viewModel.buildOptimalSetup()
    }
    
    private fun saveCurrentSetup() {
        val setupName = binding.editTextSetupName.text.toString()
        if (setupName.isBlank()) {
            binding.editTextSetupName.error = "Please enter a setup name"
            return
        }
        
        viewModel.saveCurrentSetup(setupName)
    }
    
    private fun showSavedSetups() {
        val setups = viewModel.getSavedSetups()
        if (setups.isEmpty()) {
            showMessage("No saved setups found")
            return
        }
        
        val setupNames = setups.map { it.name }.toTypedArray()
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Saved Setups")
            .setItems(setupNames) { _, which ->
                viewModel.loadSetup(setups[which])
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun observeData() {
        viewModel.currentSetup.observe(viewLifecycleOwner) { setup ->
            setup?.let {
                displaySetup(it)
            }
        }
        
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.buttonBuildSetup.isEnabled = !isLoading
        }
        
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                showErrorMessage(it)
            }
        }
        
        viewModel.saveSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                showSuccessMessage("Setup saved successfully!")
                binding.editTextSetupName.text?.clear()
            }
        }
    }
    
    private fun displaySetup(setup: PvMSetup) {
        binding.apply {
            // Display gear setup
            textViewGearSetup.text = setup.gearSetup.joinToString("\n") { 
                "• ${it.name} (${formatGP(it.price)})" 
            }
            
            // Display inventory
            textViewInventory.text = setup.inventory.joinToString("\n") { "• $it" }
            
            // Display stats
            textViewExpectedKills.text = "${setup.expectedKillsPerHour} kills/hour"
            textViewExpectedProfit.text = "${formatGP(setup.expectedProfitPerHour)}/hour"
            textViewDifficulty.text = setup.difficulty
            textViewTotalCost.text = "Total Cost: ${formatGP(setup.gearSetup.sumOf { it.price })}"
            
            // Show setup details
            cardViewSetupDetails.visibility = View.VISIBLE
            buttonSaveSetup.visibility = View.VISIBLE
        }
    }
    
    private fun formatGP(amount: Long): String {
        return when {
            amount >= 1_000_000_000 -> "${amount / 1_000_000_000}B"
            amount >= 1_000_000 -> "${amount / 1_000_000}M"
            amount >= 1_000 -> "${amount / 1_000}K"
            else -> amount.toString()
        }
    }
    
    private fun showMessage(message: String) {
        com.google.android.material.snackbar.Snackbar.make(
            binding.root,
            message,
            com.google.android.material.snackbar.Snackbar.LENGTH_LONG
        ).show()
    }
    
    private fun showSuccessMessage(message: String) {
        com.google.android.material.snackbar.Snackbar.make(
            binding.root,
            message,
            com.google.android.material.snackbar.Snackbar.LENGTH_LONG
        ).setBackgroundTint(requireContext().getColor(com.osrsplanner.app.R.color.success))
        .show()
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