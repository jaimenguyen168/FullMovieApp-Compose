package com.example.fullmovieapp_compose.main.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MediaDao {

    @Upsert
    suspend fun upsertMediaList(mediaEntities: List<MediaEntity>)

    @Upsert
    suspend fun upsertMediaItem(mediaEntity: MediaEntity)

    @Query("SELECT * FROM MediaEntity")
    suspend fun getAllMedia(): List<MediaEntity>

    @Query("SELECT * FROM MediaEntity WHERE mediaType = :mediaType AND category = :category")
    suspend fun getMediaListByTypeAndCategory(
        mediaType: String,
        category: String
    ): List<MediaEntity>

    @Query("SELECT * FROM MediaEntity WHERE category = :category")
    suspend fun getMediaListByCategory(
        category: String
    ): List<MediaEntity>

    @Query("SELECT * FROM MediaEntity WHERE mediaId = :mediaId")
    suspend fun getMediaItemById(mediaId: Int): MediaEntity

    @Query("SELECT COUNT(*) FROM MediaEntity WHERE mediaId = :mediaId")
    suspend fun doesMediaItemExist(mediaId: Int): Int

    // delete all media items
    @Query("DELETE FROM MediaEntity")
    suspend fun deleteAllMediaItems()

    // delete all media items by type and category
    @Query("DELETE FROM MediaEntity WHERE mediaType = :mediaType AND category = :category")
    suspend fun deleteMediaItemsByTypeAndCategory(
        mediaType: String,
        category: String
    )

    // delete all media items by category
    @Query("DELETE FROM MediaEntity WHERE category = :category")
    suspend fun deleteMediaItemsByCategory(category: String)

    // get media list by ids
    @Query("SELECT * FROM MediaEntity WHERE mediaId IN (:ids)")
    suspend fun getMediaListByIds(ids: List<Int>): List<MediaEntity>
}

















