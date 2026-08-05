package com.example.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.R

data class CandyWorld(
    val id: String,
    val title: String,
    val description: String,
    val specialCandy: String,
    val totalLevels: Int,
    @DrawableRes val imageRes: Int,
    val accentColor: Color
)

data class GameFeature(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val badge: String? = null
)

enum class GalleryCategory {
    ALL, SCREENSHOTS, CHARACTERS, CANDY_ART, ANIMATIONS
}

data class GalleryItem(
    val id: String,
    val title: String,
    val category: GalleryCategory,
    @DrawableRes val imageRes: Int,
    val description: String
)

data class NewsItem(
    val id: String,
    val title: String,
    val date: String,
    val category: String, // Update, Event, Patch Notes, Seasonal
    val summary: String,
    val content: String,
    val isFeatured: Boolean = false
)

data class FaqItem(
    val id: String,
    val question: String,
    val answer: String,
    val category: String = "General"
)

data class DailyQuest(
    val id: String,
    val title: String,
    val target: Int,
    val currentProgress: Int,
    val rewardGems: Int,
    val isClaimed: Boolean = false
)

data class LeaderboardUser(
    val rank: Int,
    val name: String,
    val score: Long,
    val worldTitle: String,
    val isUser: Boolean = false
)

data class BoosterItem(
    val name: String,
    val description: String,
    val iconSymbol: String,
    val accentColor: Color
)
