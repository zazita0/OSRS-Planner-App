package com.osrsplanner.app.data.database

import androidx.room.*
import com.osrsplanner.app.data.models.*
import kotlinx.coroutines.flow.Flow

@Database(
    entities = [
        UserProfile::class,
        SavedGearSet::class,
        BossKill::class,
        QuestProgress::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun gearSetDao(): GearSetDao
    abstract fun bossKillDao(): BossKillDao
    abstract fun questProgressDao(): QuestProgressDao
}

// Type Converters for Room
class Converters {
    @TypeConverter
    fun fromMap(value: Map<String, Int>): String {
        return value.entries.joinToString(",") { "${it.key}:${it.value}" }
    }

    @TypeConverter
    fun toMap(value: String): Map<String, Int> {
        if (value.isEmpty()) return emptyMap()
        return value.split(",").associate {
            val (key, intValue) = it.split(":")
            key to intValue.toInt()
        }
    }

    @TypeConverter
    fun fromList(value: List<Int>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toList(value: String): List<Int> {
        if (value.isEmpty()) return emptyList()
        return value.split(",").map { it.toInt() }
    }
}

// DAOs
@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profiles WHERE id = :userId")
    fun getUserProfile(userId: String): Flow<UserProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(profile: UserProfile)

    @Update
    suspend fun updateUserProfile(profile: UserProfile)

    @Delete
    suspend fun deleteUserProfile(profile: UserProfile)
}

@Dao
interface GearSetDao {
    @Query("SELECT * FROM saved_gear_sets ORDER BY createdAt DESC")
    fun getAllGearSets(): Flow<List<SavedGearSet>>

    @Query("SELECT * FROM saved_gear_sets WHERE id = :gearSetId")
    fun getGearSet(gearSetId: Int): Flow<SavedGearSet?>

    @Insert
    suspend fun insertGearSet(gearSet: SavedGearSet): Long

    @Update
    suspend fun updateGearSet(gearSet: SavedGearSet)

    @Delete
    suspend fun deleteGearSet(gearSet: SavedGearSet)
}

@Dao
interface BossKillDao {
    @Query("SELECT * FROM boss_kills WHERE bossId = :bossId")
    fun getBossKills(bossId: Int): Flow<BossKill?>

    @Query("SELECT * FROM boss_kills ORDER BY lastKillTime DESC")
    fun getAllBossKills(): Flow<List<BossKill>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBossKill(bossKill: BossKill)

    @Update
    suspend fun updateBossKill(bossKill: BossKill)

    @Delete
    suspend fun deleteBossKill(bossKill: BossKill)
}

@Dao
interface QuestProgressDao {
    @Query("SELECT * FROM quest_progress WHERE questId = :questId")
    fun getQuestProgress(questId: Int): Flow<QuestProgress?>

    @Query("SELECT * FROM quest_progress WHERE isCompleted = 1")
    fun getCompletedQuests(): Flow<List<QuestProgress>>

    @Query("SELECT * FROM quest_progress WHERE isCompleted = 0")
    fun getIncompleteQuests(): Flow<List<QuestProgress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestProgress(questProgress: QuestProgress)

    @Update
    suspend fun updateQuestProgress(questProgress: QuestProgress)

    @Delete
    suspend fun deleteQuestProgress(questProgress: QuestProgress)
} 