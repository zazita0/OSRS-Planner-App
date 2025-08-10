package com.osrsplanner.app.data.network

import com.osrsplanner.app.data.models.UserProfile
import retrofit2.http.GET
import retrofit2.http.Query

interface OSRSHiscoresService {
    
    @GET("m=hiscore_oldschool/index_lite.ws")
    suspend fun getPlayerStats(@Query("player") playerName: String): String
}

class OSRSHiscoresRepository @Inject constructor(
    private val hiscoresService: OSRSHiscoresService
) {
    
    suspend fun fetchPlayerProfile(username: String): Result<UserProfile> {
        return try {
            val hiscoresData = hiscoresService.getPlayerStats(username)
            val profile = parseHiscoresData(username, hiscoresData)
            Result.success(profile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun parseHiscoresData(username: String, hiscoresData: String): UserProfile {
        val lines = hiscoresData.trim().split("\n")
        
        // OSRS Hiscores format: rank,level,xp for each skill
        val skills = mutableMapOf<String, Int>()
        
        // Skill order in hiscores (0-23)
        val skillNames = listOf(
            "overall", "attack", "defence", "strength", "hitpoints", "ranged",
            "prayer", "magic", "cooking", "woodcutting", "fletching", "fishing",
            "firemaking", "crafting", "smithing", "mining", "herblore",
            "agility", "thieving", "slayer", "farming", "runecrafting",
            "hunter", "construction"
        )
        
        lines.forEachIndexed { index, line ->
            if (index < skillNames.size) {
                val parts = line.split(",")
                if (parts.size >= 2) {
                    val level = parts[1].toIntOrNull() ?: 1
                    skills[skillNames[index]] = level
                }
            }
        }
        
        // Calculate combat level
        val combatLevel = calculateCombatLevel(skills)
        
        // Calculate total level
        val totalLevel = skills.values.sum()
        
        return UserProfile(
            id = username.lowercase(),
            username = username,
            combatLevel = combatLevel,
            totalLevel = totalLevel,
            skills = skills,
            isProUser = false,
            lastUpdated = System.currentTimeMillis()
        )
    }
    
    private fun calculateCombatLevel(skills: Map<String, Int>): Int {
        val attack = skills["attack"] ?: 1
        val defence = skills["defence"] ?: 1
        val strength = skills["strength"] ?: 1
        val hitpoints = skills["hitpoints"] ?: 1
        val ranged = skills["ranged"] ?: 1
        val magic = skills["magic"] ?: 1
        val prayer = skills["prayer"] ?: 1
        
        val base = 0.25 * (defence + hitpoints + (prayer / 2))
        val melee = 0.325 * (attack + strength)
        val ranged = 0.325 * (ranged * 1.5)
        val magic = 0.325 * (magic * 1.5)
        
        val combatLevel = base + maxOf(melee, ranged, magic)
        return combatLevel.toInt()
    }
} 