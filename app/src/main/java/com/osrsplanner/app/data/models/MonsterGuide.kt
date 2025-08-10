package com.osrsplanner.app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monster_guides")
data class MonsterGuide(
    @PrimaryKey val id: Int,
    val name: String,
    val combatLevel: Int,
    val maxHit: Int,
    val attackStyle: String,
    val weakness: String,
    val location: String,
    val slayerLevel: Int,
    val slayerTask: Boolean,
    val drops: List<String>,
    val expPerHour: Int,
    val gpPerHour: Int,
    val difficulty: String, // Easy, Medium, Hard, Very Hard
    val safeSpot: Boolean,
    val multiCombat: Boolean,
    val aggressive: Boolean,
    val respawnTime: Int,
    val recommendedStats: RecommendedStats,
    val setups: List<MonsterSetup>
)

data class RecommendedStats(
    val attack: Int,
    val strength: Int,
    val defence: Int,
    val ranged: Int,
    val magic: Int,
    val prayer: Int,
    val hitpoints: Int,
    val slayer: Int
)

data class MonsterSetup(
    val name: String,
    val type: SetupType, // BUDGET, MEDIUM, MAX, PREFERRED
    val description: String,
    val gear: List<GearItem>,
    val inventory: List<InventoryItem>,
    val requirements: List<String>,
    val notes: String,
    val estimatedCost: Long
)

enum class SetupType {
    BUDGET,
    MEDIUM,
    MAX,
    PREFERRED
}

data class InventoryItem(
    val name: String,
    val quantity: Int,
    val purpose: String // Food, Potion, Teleport, etc.
) 