package com.osrsplanner.app.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.osrsplanner.app.data.models.ProFeature
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FreemiumManager @Inject constructor() : ViewModel() {
    
    private val _isProUser = MutableLiveData(false)
    val isProUser: LiveData<Boolean> = _isProUser
    
    private val _showUpgradePrompt = MutableLiveData<UpgradePrompt?>()
    val showUpgradePrompt: LiveData<UpgradePrompt?> = _showUpgradePrompt
    
    private var featureUsageCount = mutableMapOf<String, Int>()
    
    // Free vs Premium Feature Definitions
    companion object {
        // FREE FEATURES - Basic but useful
        val FREE_FEATURES = setOf(
            "basic_money_making",
            "simple_gear_guide", 
            "boss_basics",
            "quest_list",
            "basic_xp_calculator"
        )
        
        // PREMIUM FEATURES - Advanced and personalized
        val PREMIUM_FEATURES = setOf(
            "advanced_money_making",
            "gear_optimizer",
            "boss_master",
            "quest_optimizer", 
            "advanced_xp_planner",
            "slayer_tracker",
            "combat_calculator",
            "achievement_diary",
            "ad_free"
        )
        
        // UPGRADE TRIGGERS - Smart prompts
        val UPGRADE_TRIGGERS = mapOf(
            "basic_money_making" to UpgradeTrigger(3, "Unlock 50+ money making methods with live GP tracking"),
            "simple_gear_guide" to UpgradeTrigger(2, "Get personalized gear recommendations for your exact stats"),
            "boss_basics" to UpgradeTrigger(2, "Access detailed boss strategies and profit tracking"),
            "basic_xp_calculator" to UpgradeTrigger(3, "Compare training methods and set XP goals")
        )
    }
    
    fun isFeatureAvailable(featureId: String): Boolean {
        return if (isProUser.value == true) {
            true // Pro users get everything
        } else {
            FREE_FEATURES.contains(featureId)
        }
    }
    
    fun useFeature(featureId: String) {
        if (isProUser.value == true) return // Pro users don't need tracking
        
        val currentCount = featureUsageCount[featureId] ?: 0
        featureUsageCount[featureId] = currentCount + 1
        
        // Check if we should show upgrade prompt
        val trigger = UPGRADE_TRIGGERS[featureId]
        if (trigger != null && currentCount + 1 >= trigger.usageCount) {
            showUpgradePrompt(trigger.message, featureId)
        }
    }
    
    fun showUpgradePrompt(message: String, featureId: String) {
        _showUpgradePrompt.value = UpgradePrompt(message, featureId)
    }
    
    fun clearUpgradePrompt() {
        _showUpgradePrompt.value = null
    }
    
    fun upgradeToPro() {
        _isProUser.value = true
        clearUpgradePrompt()
    }
    
    fun getFreeFeatures(): List<ProFeature> {
        return FREE_FEATURES.map { featureId ->
            ProFeature(
                id = featureId,
                name = getFeatureName(featureId),
                description = getFeatureDescription(featureId),
                isUnlocked = true,
                price = null
            )
        }
    }
    
    fun getPremiumFeatures(): List<ProFeature> {
        return PREMIUM_FEATURES.map { featureId ->
            ProFeature(
                id = featureId,
                name = getFeatureName(featureId),
                description = getFeatureDescription(featureId),
                isUnlocked = isProUser.value == true,
                price = getFeaturePrice(featureId)
            )
        }
    }
    
    private fun getFeatureName(featureId: String): String {
        return when (featureId) {
            "basic_money_making" -> "Basic Money Making"
            "advanced_money_making" -> "Advanced Money Making"
            "simple_gear_guide" -> "Simple Gear Guide"
            "gear_optimizer" -> "Gear Optimizer"
            "boss_basics" -> "Boss Basics"
            "boss_master" -> "Boss Master"
            "quest_list" -> "Quest List"
            "quest_optimizer" -> "Quest Optimizer"
            "basic_xp_calculator" -> "Basic XP Calculator"
            "advanced_xp_planner" -> "Advanced XP Planner"
            "slayer_tracker" -> "Slayer Task Tracker"
            "combat_calculator" -> "Combat Calculator"
            "achievement_diary" -> "Achievement Diary Helper"
            "ad_free" -> "Ad-Free Experience"
            else -> "Unknown Feature"
        }
    }
    
    private fun getFeatureDescription(featureId: String): String {
        return when (featureId) {
            "basic_money_making" -> "Top 3 money making methods for each level"
            "advanced_money_making" -> "50+ methods with live GP/hour tracking and profit optimization"
            "simple_gear_guide" -> "Basic gear sets for different budgets"
            "gear_optimizer" -> "Personalized gear recommendations for your exact stats and budget"
            "boss_basics" -> "Entry-level bosses with basic strategies"
            "boss_master" -> "Detailed strategies, gear setups, profit tracking, and kill counters"
            "quest_list" -> "List of quests to complete"
            "quest_optimizer" -> "Optimal quest order for your account progression"
            "basic_xp_calculator" -> "Time to level for main skills"
            "advanced_xp_planner" -> "Training method comparisons, goal tracking, and efficiency analysis"
            "slayer_tracker" -> "Track slayer tasks, get recommendations, and optimize task lists"
            "combat_calculator" -> "DPS calculations, gear comparisons, and max hit calculations"
            "achievement_diary" -> "Track diary progress, requirements, and optimal completion order"
            "ad_free" -> "Remove all advertisements"
            else -> "Unknown feature description"
        }
    }
    
    private fun getFeaturePrice(featureId: String): Double? {
        return when (featureId) {
            "ad_free" -> 0.99
            else -> null // Part of pro subscription
        }
    }
    
    data class UpgradeTrigger(
        val usageCount: Int,
        val message: String
    )
    
    data class UpgradePrompt(
        val message: String,
        val featureId: String
    )
} 