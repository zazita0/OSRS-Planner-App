package com.osrsplanner.app.data.mock

import com.osrsplanner.app.data.models.*

object MockData {
    
    fun getMockGearItems(): List<GearItem> {
        return listOf(
            // Melee Gear
            GearItem(
                id = 1,
                name = "Abyssal Whip",
                slot = GearSlot.WEAPON,
                stats = CombatStats(attack = 82, strength = 82),
                requirements = Requirements(attack = 70),
                price = 2500000,
                isProFeature = false
            ),
            GearItem(
                id = 2,
                name = "Bandos Chestplate",
                slot = GearSlot.BODY,
                stats = CombatStats(attack = 4, defence = 4, strength = 4),
                requirements = Requirements(defence = 65),
                price = 15000000,
                isProFeature = false
            ),
            GearItem(
                id = 3,
                name = "Dragon Boots",
                slot = GearSlot.BOOTS,
                stats = CombatStats(attack = 4, defence = 4, strength = 4),
                requirements = Requirements(defence = 60),
                price = 250000,
                isProFeature = false
            ),
            
            // Ranged Gear
            GearItem(
                id = 4,
                name = "Toxic Blowpipe",
                slot = GearSlot.WEAPON,
                stats = CombatStats(ranged = 60),
                requirements = Requirements(ranged = 75),
                price = 5000000,
                isProFeature = true
            ),
            GearItem(
                id = 5,
                name = "Armadyl Chestplate",
                slot = GearSlot.BODY,
                stats = CombatStats(ranged = 4, defence = 4),
                requirements = Requirements(ranged = 70, defence = 70),
                price = 25000000,
                isProFeature = true
            ),
            
            // Magic Gear
            GearItem(
                id = 6,
                name = "Staff of the Dead",
                slot = GearSlot.WEAPON,
                stats = CombatStats(magic = 15, defence = 15),
                requirements = Requirements(magic = 75, attack = 75),
                price = 8000000,
                isProFeature = true
            ),
            GearItem(
                id = 7,
                name = "Ancestral Robe Top",
                slot = GearSlot.BODY,
                stats = CombatStats(magic = 8, defence = 8),
                requirements = Requirements(magic = 75, defence = 65),
                price = 45000000,
                isProFeature = true
            )
        )
    }
    
    fun getMockBosses(): List<Boss> {
        return listOf(
            Boss(
                id = 1,
                name = "Zulrah",
                description = "A powerful snake-like creature that requires quick gear switching",
                combatLevel = 725,
                maxHit = 41,
                attackStyle = listOf(AttackStyle.MIXED),
                weaknesses = listOf(Weakness.RANGED, Weakness.MAGIC),
                recommendedGear = getMockGearItems().filter { it.id in listOf(4, 5, 6, 7) },
                gpPerHour = 2500000,
                xpPerHour = 15000,
                requirements = Requirements(ranged = 75, magic = 75, defence = 70),
                isProFeature = true
            ),
            Boss(
                id = 2,
                name = "Vorkath",
                description = "A dragon boss that drops valuable items",
                combatLevel = 732,
                maxHit = 30,
                attackStyle = listOf(AttackStyle.RANGED, AttackStyle.MAGIC),
                weaknesses = listOf(Weakness.RANGED),
                recommendedGear = getMockGearItems().filter { it.id in listOf(4, 5) },
                gpPerHour = 3000000,
                xpPerHour = 12000,
                requirements = Requirements(ranged = 75, defence = 70),
                isProFeature = false
            ),
            Boss(
                id = 3,
                name = "Giant Mole",
                description = "A beginner-friendly boss in Falador",
                combatLevel = 230,
                maxHit = 10,
                attackStyle = listOf(AttackStyle.MELEE),
                weaknesses = listOf(Weakness.CRUSH),
                recommendedGear = getMockGearItems().filter { it.id in listOf(1, 2, 3) },
                gpPerHour = 800000,
                xpPerHour = 8000,
                requirements = Requirements(attack = 50, defence = 50),
                isProFeature = false
            )
        )
    }
    
    fun getMockQuests(): List<Quest> {
        return listOf(
            Quest(
                id = 1,
                name = "Dragon Slayer",
                description = "Prove yourself worthy to wear the legendary rune platebody",
                difficulty = QuestDifficulty.EXPERIENCED,
                requirements = Requirements(
                    attack = 32, defence = 32, strength = 32,
                    quests = listOf("Merlin's Crystal", "The Family Crest")
                ),
                rewards = QuestRewards(
                    xp = mapOf("defence" to 18650, "strength" to 18650),
                    items = listOf("Rune platebody"),
                    questPoints = 2,
                    unlocks = listOf("Ability to wear rune platebody")
                ),
                isProFeature = false
            ),
            Quest(
                id = 2,
                name = "Recipe for Disaster",
                description = "Help the Lumbridge Cook save the banquet",
                difficulty = QuestDifficulty.GRANDMASTER,
                requirements = Requirements(
                    attack = 70, defence = 70, strength = 70, cooking = 70,
                    quests = listOf("Dragon Slayer", "Desert Treasure")
                ),
                rewards = QuestRewards(
                    xp = mapOf("attack" to 20000, "defence" to 20000, "strength" to 20000),
                    items = listOf("Barrows gloves"),
                    questPoints = 10,
                    unlocks = listOf("Access to Culinaromancer's Chest")
                ),
                isProFeature = true
            ),
            Quest(
                id = 3,
                name = "Monkey Madness",
                description = "Help the monkey child find his way home",
                difficulty = QuestDifficulty.EXPERIENCED,
                requirements = Requirements(
                    attack = 30, defence = 30, strength = 30,
                    quests = listOf("The Grand Tree")
                ),
                rewards = QuestRewards(
                    xp = mapOf("attack" to 20000, "defence" to 20000, "strength" to 20000),
                    items = listOf("Dragon scimitar"),
                    questPoints = 3,
                    unlocks = listOf("Access to Ape Atoll")
                ),
                isProFeature = false
            )
        )
    }
    
    fun getMockTrainingMethods(): List<TrainingMethod> {
        return listOf(
            TrainingMethod(
                name = "Ammonite Crabs",
                description = "High XP rates with no food required",
                xpPerHour = 65000,
                gpPerHour = -50000, // Negative for costs
                requirements = Requirements(attack = 30, defence = 30, strength = 30),
                location = "Fossil Island",
                isProFeature = false
            ),
            TrainingMethod(
                name = "Nightmare Zone",
                description = "AFK training with good XP rates",
                xpPerHour = 85000,
                gpPerHour = -100000,
                requirements = Requirements(attack = 70, defence = 70, strength = 70),
                location = "Yanille",
                isProFeature = true
            ),
            TrainingMethod(
                name = "Slayer",
                description = "Train combat while earning money",
                xpPerHour = 45000,
                gpPerHour = 500000,
                requirements = Requirements(attack = 40, defence = 40, strength = 40),
                location = "Various",
                isProFeature = false
            )
        )
    }
    
    fun getMockProFeatures(): List<ProFeature> {
        return listOf(
            ProFeature(
                id = "advanced_gear_planner",
                name = "Advanced Gear Planner",
                description = "Get optimal gear recommendations based on your budget and stats",
                isUnlocked = false,
                price = 4.99
            ),
            ProFeature(
                id = "boss_tracker",
                name = "Boss Kill Tracker",
                description = "Track your boss kills, times, and earnings",
                isUnlocked = false,
                price = 2.99
            ),
            ProFeature(
                id = "quest_optimizer",
                name = "Quest Optimizer",
                description = "Get the optimal quest order for your account",
                isUnlocked = false,
                price = 3.99
            ),
            ProFeature(
                id = "xp_calculator",
                name = "XP Calculator",
                description = "Calculate time to level and optimal training methods",
                isUnlocked = false,
                price = 1.99
            )
        )
    }
    
    fun getMockUserProfile(): UserProfile {
        return UserProfile(
            id = "mock_user_123",
            username = "MockPlayer",
            combatLevel = 85,
            totalLevel = 1500,
            skills = mapOf(
                "attack" to 80,
                "defence" to 75,
                "strength" to 82,
                "ranged" to 70,
                "magic" to 65,
                "prayer" to 60,
                "hitpoints" to 85
            ),
            isProUser = false
        )
    }
} 