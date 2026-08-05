package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.GlassmorphicCard
import com.example.ui.components.InteractiveMinigame
import com.example.ui.components.NavPage
import com.example.ui.theme.CandyCyan
import com.example.ui.theme.CandyLime
import com.example.ui.theme.CandyPink
import com.example.ui.theme.CandyYellow
import com.example.ui.theme.DomainGold
import com.example.ui.theme.GlassBorderPurple
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.CandyViewModel

@Composable
fun HomeScreen(
    viewModel: CandyViewModel,
    onPlayClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onWatchTrailerClick: () -> Unit,
    onNavigate: (NavPage) -> Unit,
    modifier: Modifier = Modifier
) {
    var isMinigameOpen by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Hero Card Section
        GlassmorphicCard(
            cornerRadius = 24.dp,
            accentGlow = CandyPink
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Hero Banner Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(18.dp))
                        .border(1.dp, GlassBorderPurple, RoundedCornerShape(18.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_candy_hero_1785898379829),
                        contentDescription = "T.J. Candy Blast Hero",
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                    // Hero Badge Overlay
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Black.copy(alpha = 0.75f))
                            .border(1.dp, CandyYellow, RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("★ 60 FPS Engine", fontSize = 11.sp, color = CandyYellow, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Logo & Title
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.img_app_icon_1785898360575),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .border(2.dp, Brush.horizontalGradient(listOf(CandyPink, CandyCyan)), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "T.J. Candy Blast",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Play the Sweetest Puzzle Adventure!",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = CandyCyan
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Official Domain Highlight Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1B0733))
                        .border(1.dp, DomainGold.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Public, contentDescription = "Website", tint = DomainGold, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Official Website: ", fontSize = 12.sp, color = TextSecondary)
                        Text(
                            text = "https://tjcandyblast.com",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = DomainGold,
                            modifier = Modifier.testTag("home_domain_url")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Main CTA Buttons
                Button(
                    onClick = { isMinigameOpen = !isMinigameOpen },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("home_play_now_button")
                        .background(
                            Brush.horizontalGradient(listOf(CandyPink, CandyCyan)),
                            RoundedCornerShape(16.dp)
                        )
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isMinigameOpen) "Hide Live Game" else "Play Match-3 Preview", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onDownloadClick,
                        colors = ButtonDefaults.buttonColors(containerColor = CandyYellow),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("home_download_google_play")
                    ) {
                        Icon(imageVector = Icons.Default.Download, contentDescription = "Download", tint = Color.Black, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Google Play", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }

                    OutlinedButton(
                        onClick = onWatchTrailerClick,
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, CandyCyan),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("home_watch_trailer")
                    ) {
                        Icon(imageVector = Icons.Default.Videocam, contentDescription = "Trailer", tint = CandyCyan, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Watch Trailer", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CandyCyan)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Embedded Live Playable Match-3 Minigame Section
        AnimatedVisibility(visible = isMinigameOpen) {
            Column {
                InteractiveMinigame(
                    onClose = { isMinigameOpen = false },
                    onScoreUpdated = { score -> viewModel.updateHighScore(score) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Quick Game Highlights & Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GlassmorphicCard(
                modifier = Modifier.weight(1f),
                cornerRadius = 16.dp
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("1,200+", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = CandyPink)
                    Text("Levels", fontSize = 12.sp, color = TextSecondary)
                }
            }

            GlassmorphicCard(
                modifier = Modifier.weight(1f),
                cornerRadius = 16.dp
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("8", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = CandyCyan)
                    Text("Candy Worlds", fontSize = 12.sp, color = TextSecondary)
                }
            }

            GlassmorphicCard(
                modifier = Modifier.weight(1f),
                cornerRadius = 16.dp
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("100%", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = CandyLime)
                    Text("Offline Play", fontSize = 12.sp, color = TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Featured Candy Worlds Teaser
        GlassmorphicCard(
            cornerRadius = 20.dp,
            onClick = { onNavigate(NavPage.WORLDS) }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("🌟 Explore 8 Candy Worlds", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = CandyYellow)
                    Text(
                        "From Aurora Candy Valley to Dream Jelly Gardens",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = "Explore", tint = CandyCyan)
            }
        }
    }
}
