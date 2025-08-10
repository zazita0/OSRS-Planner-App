package com.osrsplanner.app.data.network

import com.osrsplanner.app.data.models.*
import retrofit2.http.*

interface ApiService {
    
    // OSRS Grand Exchange API
    @GET("item/{itemId}")
    suspend fun getItemPrice(@Path("itemId") itemId: Int): ItemPriceResponse
    
    @GET("items")
    suspend fun getItemPrices(@Query("items") itemIds: String): List<ItemPriceResponse>
    
    // OSRS Hiscores API
    @GET("m=hiscore_oldschool/index_lite.ws")
    suspend fun getPlayerStats(@Query("player") playerName: String): String
    
    // Custom API endpoints for app data
    @GET("gear")
    suspend fun getGearItems(): List<GearItem>
    
    @GET("gear/{slot}")
    suspend fun getGearBySlot(@Path("slot") slot: String): List<GearItem>
    
    @GET("bosses")
    suspend fun getBosses(): List<Boss>
    
    @GET("bosses/{bossId}")
    suspend fun getBoss(@Path("bossId") bossId: Int): Boss
    
    @GET("quests")
    suspend fun getQuests(): List<Quest>
    
    @GET("quests/{questId}")
    suspend fun getQuest(@Path("questId") questId: Int): Quest
    
    @GET("training-methods")
    suspend fun getTrainingMethods(): List<TrainingMethod>
    
    @GET("training-methods/{style}")
    suspend fun getTrainingMethodsByStyle(@Path("style") style: String): List<TrainingMethod>
    
    // Pro features API
    @GET("pro-features")
    suspend fun getProFeatures(): List<ProFeature>
    
    @POST("pro-features/{featureId}/unlock")
    suspend fun unlockProFeature(@Path("featureId") featureId: String): ApiResponse<Boolean>
    
    // User data sync
    @POST("user/sync")
    suspend fun syncUserData(@Body userProfile: UserProfile): ApiResponse<UserProfile>
    
    @GET("user/{userId}/stats")
    suspend fun getUserStats(@Path("userId") userId: String): ApiResponse<UserProfile>
}

// Mock API Service for development
class MockApiService : ApiService {
    
    override suspend fun getItemPrice(itemId: Int): ItemPriceResponse {
        return ItemPriceResponse(itemId, (1000..1000000).random().toLong(), System.currentTimeMillis())
    }
    
    override suspend fun getItemPrices(itemIds: String): List<ItemPriceResponse> {
        return itemIds.split(",").map { 
            ItemPriceResponse(it.toInt(), (1000..1000000).random().toLong(), System.currentTimeMillis())
        }
    }
    
    override suspend fun getPlayerStats(playerName: String): String {
        // Mock hiscores data
        return "1,99,13034431\n2,99,13034431\n3,99,13034431\n4,99,13034431\n5,99,13034431"
    }
    
    override suspend fun getGearItems(): List<GearItem> {
        return MockData.getMockGearItems()
    }
    
    override suspend fun getGearBySlot(slot: String): List<GearItem> {
        return MockData.getMockGearItems().filter { it.slot.name == slot.uppercase() }
    }
    
    override suspend fun getBosses(): List<Boss> {
        return MockData.getMockBosses()
    }
    
    override suspend fun getBoss(bossId: Int): Boss {
        return MockData.getMockBosses().find { it.id == bossId } 
            ?: throw IllegalArgumentException("Boss not found")
    }
    
    override suspend fun getQuests(): List<Quest> {
        return MockData.getMockQuests()
    }
    
    override suspend fun getQuest(questId: Int): Quest {
        return MockData.getMockQuests().find { it.id == questId }
            ?: throw IllegalArgumentException("Quest not found")
    }
    
    override suspend fun getTrainingMethods(): List<TrainingMethod> {
        return MockData.getMockTrainingMethods()
    }
    
    override suspend fun getTrainingMethodsByStyle(style: String): List<TrainingMethod> {
        return MockData.getMockTrainingMethods().filter { it.name.contains(style, ignoreCase = true) }
    }
    
    override suspend fun getProFeatures(): List<ProFeature> {
        return MockData.getMockProFeatures()
    }
    
    override suspend fun unlockProFeature(featureId: String): ApiResponse<Boolean> {
        return ApiResponse(true, true, "Feature unlocked successfully")
    }
    
    override suspend fun syncUserData(userProfile: UserProfile): ApiResponse<UserProfile> {
        return ApiResponse(true, userProfile, "Data synced successfully")
    }
    
    override suspend fun getUserStats(userId: String): ApiResponse<UserProfile> {
        return ApiResponse(true, MockData.getMockUserProfile(), "Stats retrieved successfully")
    }
} 