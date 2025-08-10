package com.osrsplanner.app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

// Gear and Equipment Models
@Parcelize
data class GearItem(
    val id: Int,
    val name: String,
    val slot: GearSlot,
    val stats: CombatStats,
    val requirements: Requirements,
    val price: Long,
    val imageUrl: String? = null,
    val isProFeature: Boolean = false
) : Parcelable

@Parcelize
data class CombatStats(
    val attack: Int = 0,
    val defence: Int = 0,
    val strength: Int = 0,
    val ranged: Int = 0,
    val magic: Int = 0,
    val prayer: Int = 0,
    val hitpoints: Int = 0
) : Parcelable

@Parcelize
data class Requirements(
    val attack: Int = 0,
    val defence: Int = 0,
    val strength: Int = 0,
    val ranged: Int = 0,
    val magic: Int = 0,
    val prayer: Int = 0,
    val quests: List<String> = emptyList()
) : Parcelable

enum class GearSlot {
    HEAD, NECK, CAPE, BODY, LEGS, BOOTS, GLOVES, SHIELD, WEAPON, RING, AMMO
}

// Boss Models
@Parcelize
data class Boss(
    val id: Int,
    val name: String,
    val description: String,
    val combatLevel: Int,
    val maxHit: Int,
    val attackStyle: List<AttackStyle>,
    val weaknesses: List<Weakness>,
    val recommendedGear: List<GearItem>,
    val gpPerHour: Long,
    val xpPerHour: Int,
    val requirements: Requirements,
    val imageUrl: String? = null,
    val isProFeature: Boolean = false
) : Parcelable

enum class AttackStyle {
    MELEE, RANGED, MAGIC, MIXED
}

enum class Weakness {
    CRUSH, SLASH, STAB, RANGED, MAGIC, WATER, EARTH, FIRE, AIR
}

// Quest Models
@Parcelize
data class Quest(
    val id: Int,
    val name: String,
    val description: String,
    val difficulty: QuestDifficulty,
    val requirements: Requirements,
    val rewards: QuestRewards,
    val isCompleted: Boolean = false,
    val isProFeature: Boolean = false
) : Parcelable

enum class QuestDifficulty {
    NOVICE, INTERMEDIATE, EXPERIENCED, MASTER, GRANDMASTER
}

@Parcelize
data class QuestRewards(
    val xp: Map<String, Int> = emptyMap(),
    val items: List<String> = emptyList(),
    val questPoints: Int = 0,
    val unlocks: List<String> = emptyList()
) : Parcelable

// NEW: XP Calculator Models
@Parcelize
data class SkillGoal(
    val id: Int,
    val skillName: String,
    val currentLevel: Int,
    val targetLevel: Int,
    val currentXp: Long,
    val targetXp: Long,
    val estimatedTime: Long, // in hours
    val recommendedMethods: List<TrainingMethod>,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

@Parcelize
data class TrainingMethod(
    val name: String,
    val description: String,
    val xpPerHour: Int,
    val gpPerHour: Long,
    val requirements: Requirements,
    val location: String,
    val isProFeature: Boolean = false,
    val difficulty: TrainingDifficulty = TrainingDifficulty.EASY
) : Parcelable

enum class TrainingDifficulty {
    EASY, MEDIUM, HARD, EXPERT
}

// NEW: Money Making Models
@Parcelize
data class MoneyMakingMethod(
    val id: Int,
    val name: String,
    val description: String,
    val gpPerHour: Long,
    val requirements: Requirements,
    val riskLevel: RiskLevel,
    val timeInvestment: TimeInvestment,
    val location: String,
    val tips: List<String>,
    val isProFeature: Boolean = false
) : Parcelable

enum class RiskLevel {
    NONE, LOW, MEDIUM, HIGH, VERY_HIGH
}

enum class TimeInvestment {
    QUICK, SHORT, MEDIUM, LONG, VERY_LONG
}

// NEW: Clue Scroll Models
@Parcelize
data class ClueScroll(
    val id: Int,
    val type: ClueType,
    val step: Int,
    val description: String,
    val solution: String,
    val requiredItems: List<String>,
    val teleportLocation: String?,
    val coordinates: String?,
    val imageUrl: String?,
    val isProFeature: Boolean = false
) : Parcelable

enum class ClueType {
    EASY, MEDIUM, HARD, ELITE, MASTER
}

// NEW: Slayer Task Models
@Parcelize
data class SlayerTask(
    val id: Int,
    val monsterName: String,
    val taskAmount: Int,
    val currentKills: Int,
    val location: String,
    val bestGear: List<GearItem>,
    val strategy: String,
    val xpPerHour: Int,
    val gpPerHour: Long,
    val isProFeature: Boolean = false
) : Parcelable

@Entity(tableName = "slayer_progress")
data class SlayerProgress(
    @PrimaryKey val id: Int = 0,
    val currentTask: String? = null,
    val taskAmount: Int = 0,
    val currentKills: Int = 0,
    val streak: Int = 0,
    val totalTasks: Int = 0,
    val lastUpdated: Long = System.currentTimeMillis()
)

// NEW: Achievement Diary Models
@Parcelize
data class AchievementDiary(
    val id: Int,
    val name: String,
    val region: String,
    val difficulty: DiaryDifficulty,
    val tasks: List<DiaryTask>,
    val rewards: DiaryRewards,
    val isProFeature: Boolean = false
) : Parcelable

@Parcelize
data class DiaryTask(
    val id: Int,
    val description: String,
    val requirements: Requirements,
    val isCompleted: Boolean = false,
    val difficulty: DiaryDifficulty
) : Parcelable

enum class DiaryDifficulty {
    EASY, MEDIUM, HARD, ELITE
}

@Parcelize
data class DiaryRewards(
    val xp: Map<String, Int> = emptyMap(),
    val items: List<String> = emptyList(),
    val teleports: List<String> = emptyList(),
    val otherBenefits: List<String> = emptyList()
) : Parcelable

// NEW: Item Price Tracking
@Parcelize
data class ItemPrice(
    val itemId: Int,
    val itemName: String,
    val currentPrice: Long,
    val highAlchValue: Long,
    val lowAlchValue: Long,
    val geLimit: Int,
    val lastUpdated: Long,
    val priceHistory: List<PricePoint> = emptyList()
) : Parcelable

@Parcelize
data class PricePoint(
    val timestamp: Long,
    val price: Long
) : Parcelable

// NEW: Combat Calculator
@Parcelize
data class CombatCalculator(
    val maxHit: Int,
    val accuracy: Double,
    val dps: Double,
    val gearSetup: List<GearItem>,
    val combatStyle: CombatStyle,
    val target: CombatTarget
) : Parcelable

@Parcelize
data class CombatTarget(
    val name: String,
    val defenceLevel: Int,
    val defenceBonus: Int,
    val weakness: Weakness
) : Parcelable

// User Data Models
@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey val id: String,
    val username: String,
    val combatLevel: Int,
    val totalLevel: Int,
    val skills: Map<String, Int>,
    val isProUser: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val lastUpdated: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_gear_sets")
data class SavedGearSet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val gearItems: List<Int>, // List of gear item IDs
    val totalCost: Long,
    val totalStats: CombatStats,
    val createdAt: Long = System.currentTimeMillis(),
    val isProFeature: Boolean = false
)

@Entity(tableName = "boss_kills")
data class BossKill(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bossId: Int,
    val killCount: Int,
    val bestTime: Long? = null, // in milliseconds
    val totalGpEarned: Long = 0,
    val lastKillTime: Long = System.currentTimeMillis()
)

@Entity(tableName = "quest_progress")
data class QuestProgress(
    @PrimaryKey val questId: Int,
    val isCompleted: Boolean = false,
    val completedAt: Long? = null,
    val notes: String? = null
)

// Build Planner Models
@Parcelize
data class Build(
    val id: Int,
    val name: String,
    val description: String,
    val combatStyle: CombatStyle,
    val targetLevel: Int,
    val budget: Long,
    val gearSet: List<GearItem>,
    val trainingMethods: List<TrainingMethod>,
    val isProFeature: Boolean = false
) : Parcelable

enum class CombatStyle {
    MELEE, RANGED, MAGIC, HYBRID
}

// Pro Features Models
@Parcelize
data class ProFeature(
    val id: String,
    val name: String,
    val description: String,
    val isUnlocked: Boolean = false,
    val price: Double? = null
) : Parcelable

// Notification Models
@Parcelize
data class Notification(
    val id: Int,
    val title: String,
    val message: String,
    val type: NotificationType,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
) : Parcelable

enum class NotificationType {
    BOSS_REMINDER, QUEST_REMINDER, LEVEL_UP, PRO_FEATURE, GENERAL, SLAYER_TASK, CLUE_SCROLL
}

// API Response Models
data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val message: String? = null
)

data class ItemPriceResponse(
    val itemId: Int,
    val price: Long,
    val lastUpdated: Long
)

// Settings Models
@Parcelize
data class AppSettings(
    val isDarkMode: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val autoSaveEnabled: Boolean = true,
    val dataUsageOptimized: Boolean = false
) : Parcelable

// NEW: Goal Tracking Models
@Parcelize
data class GoalProgress(
    val goalId: Int,
    val currentProgress: Double, // 0.0 to 1.0
    val timeRemaining: Long, // in hours
    val xpRemaining: Long,
    val isOnTrack: Boolean
) : Parcelize

// NEW: Profit Tracking Models
@Parcelize
data class ProfitSession(
    val id: Int,
    val activity: String, // "Vorkath", "Zulrah", etc.
    val startTime: Long,
    val endTime: Long,
    val kills: Int,
    val totalProfit: Long,
    val profitPerHour: Long,
    val suppliesCost: Long,
    val netProfit: Long
) : Parcelize

@Parcelize
data class ProfitGoal(
    val id: Int,
    val targetAmount: Long,
    val currentAmount: Long,
    val deadline: Long?,
    val recommendedMethods: List<MoneyMakingMethod>
) : Parcelize

// NEW: PvM Setup Models
@Parcelize
data class PvMSetup(
    val id: Int,
    val bossName: String,
    val budget: Long,
    val gearSetup: List<GearItem>,
    val inventory: List<String>,
    val strategy: String,
    val expectedKillsPerHour: Int,
    val expectedProfitPerHour: Long,
    val difficulty: String,
    val requirements: Requirements
) : Parcelize

// NEW: Account Analytics Models
@Parcelize
data class AccountAnalytics(
    val totalWorth: Long,
    val skillEfficiency: Double, // 0.0 to 1.0
    val percentile: Int, // Top X% of players
    val timePlayed: Long, // in hours
    val achievements: List<String>,
    val recommendations: List<String>
) : Parcelize

// NEW: Daily Task Models
@Parcelize
data class DailyTask(
    val id: Int,
    val name: String,
    val description: String,
    val profitPerDay: Long,
    val timeRequired: Int, // in minutes
    val requirements: Requirements,
    val isCompleted: Boolean = false,
    val lastCompleted: Long? = null
) : Parcelize

@Parcelize
data class DailyTaskOptimizer(
    val recommendedTasks: List<DailyTask>,
    val totalProfitPerDay: Long,
    val totalTimeRequired: Int,
    val priorityOrder: List<Int> // task IDs in priority order
) : Parcelize

// NEW: Skill Training Path Models
@Parcelize
data class TrainingPath(
    val skillName: String,
    val currentLevel: Int,
    val targetLevel: Int,
    val steps: List<TrainingStep>,
    val totalTime: Long,
    val totalCost: Long,
    val efficiency: Double
) : Parcelize

@Parcelize
data class TrainingStep(
    val level: Int,
    val method: String,
    val xpPerHour: Int,
    val costPerHour: Long,
    val timeToLevel: Long,
    val requirements: Requirements
) : Parcelize

// NEW: Smart Notification Models
@Parcelize
data class SmartNotification(
    val id: Int,
    val type: NotificationType,
    val title: String,
    val message: String,
    val action: String?,
    val scheduledTime: Long,
    val isRepeating: Boolean = false,
    val repeatInterval: Long? = null
) : Parcelize

enum class NotificationType {
    HERB_PATCH, BIRDHOUSE, TREE_RUN, LEVEL_UP, GOAL_REMINDER, DAILY_TASK, BOSS_RESPAWN, PROFIT_GOAL
} 