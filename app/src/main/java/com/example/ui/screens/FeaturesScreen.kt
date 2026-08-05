package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DailyQuest
import com.example.model.GameFeature
import com.example.ui.components.GlassmorphicCard
import com.example.ui.components.LuckySpinDialog
import com.example.ui.theme.CandyBlue
import com.example.ui.theme.CandyCyan
import com.example.ui.theme.CandyLime
import com.example.ui.theme.CandyMagenta
import com.example.ui.theme.CandyOrange
import com.example.ui.theme.CandyPink
import com.example.ui.theme.CandyPurple
import com.example.ui.theme.CandyYellow
import com.example.ui.theme.DarkPurpleCard
import com.example.ui.theme.GlassBorderPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.CandyViewModel

@Composable
fun FeaturesScreen(
    viewModel: CandyViewModel,
    modifier: Modifier = Modifier
) {
    var showSpinDialog by remember { mutableStateOf(false) }
    val userProgress by viewModel.userProgress.collectAsState()

    if (showSpinDialog) {
        LuckySpinDialog(
            onDismiss = { showSpinDialog = false },
            onRewardClaimed = { rewardName, amount ->
                if (rewardName.contains("Gems")) {
                    viewModel.addGems(amount)
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Title Banner
        Text(
            text = "✨ Game Features",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextPrimary
        )
        Text(
            text = "Discover all 12 original features in T.J. Candy Blast",
            fontSize = 13.sp,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Gems & Lucky Spin Quick Banner
        GlassmorphicCard(
            cornerRadius = 20.dp,
            accentGlow = CandyYellow
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Your Candy Gems", fontSize = 12.sp, color = TextSecondary)
                    Text("💎 ${userProgress.gems}", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = CandyYellow)
                }

                Button(
                    onClick = { showSpinDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = CandyPink),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.testTag("open_lucky_spin")
                ) {
                    Icon(imageVector = Icons.Default.Casino, contentDescription = "Spin", tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Lucky Spin", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 12 Feature Cards List
        viewModel.gameFeatures.forEachIndexed { index, feature ->
            val icon = getFeatureIcon(feature.iconName)
            val accentColor = getFeatureColor(index)

            GlassmorphicCard(
                cornerRadius = 18.dp,
                accentGlow = accentColor,
                onClick = {
                    if (feature.id == "f3") {
                        showSpinDialog = true
                    }
                },
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .testTag("feature_card_${feature.id}")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(accentColor.copy(alpha = 0.2f))
                            .border(1.dp, accentColor, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = feature.title,
                            tint = accentColor,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = feature.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )

                            feature.badge?.let { badge ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(accentColor.copy(alpha = 0.2f))
                                        .border(1.dp, accentColor.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(badge, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = accentColor)
                                }
                            }
                        }

                        Text(
                            text = feature.description,
                            fontSize = 12.sp,
                            color = TextSecondary,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Daily Quests Interactive Section
        Text(
            text = "📜 Daily Quests",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        viewModel.dailyQuests.forEach { quest ->
            GlassmorphicCard(
                cornerRadius = 16.dp,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(quest.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { quest.currentProgress.toFloat() / quest.target.toFloat() },
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = CandyCyan,
                            trackColor = GlassBorderPurple
                        )
                        Text("${quest.currentProgress}/${quest.target}", fontSize = 11.sp, color = TextSecondary)
                    }

                    if (quest.isClaimed) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Claimed", tint = CandyLime, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Claimed", fontSize = 12.sp, color = CandyLime, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Button(
                            onClick = { viewModel.addGems(quest.rewardGems) },
                            colors = ButtonDefaults.buttonColors(containerColor = CandyYellow),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("+${quest.rewardGems} 💎", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

private fun getFeatureIcon(iconName: String): ImageVector {
    return when (iconName) {
        "Extension" -> Icons.Default.Extension
        "CardGiftcard" -> Icons.Default.CardGiftcard
        "Casino" -> Icons.Default.Casino
        "MilitaryTech" -> Icons.Default.MilitaryTech
        "AssignmentTurnedIn" -> Icons.Default.AssignmentTurnedIn
        "Leaderboard" -> Icons.Default.Leaderboard
        "Groups" -> Icons.Default.Groups
        "EmojiEvents" -> Icons.Default.EmojiEvents
        "AutoAwesome" -> Icons.Default.AutoAwesome
        "Public" -> Icons.Default.Public
        "WifiOff" -> Icons.Default.WifiOff
        "Speed" -> Icons.Default.Speed
        else -> Icons.Default.AutoAwesome
    }
}

private fun getFeatureColor(index: Int): Color {
    val colors = listOf(CandyPink, CandyCyan, CandyYellow, CandyMagenta, CandyLime, CandyOrange, CandyPurple, CandyBlue)
    return colors[index % colors.size]
}
