package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GlassmorphicCard
import com.example.ui.theme.CandyBlue
import com.example.ui.theme.CandyCyan
import com.example.ui.theme.CandyLime
import com.example.ui.theme.CandyMagenta
import com.example.ui.theme.CandyPink
import com.example.ui.theme.CandyYellow
import com.example.ui.theme.DomainGold
import com.example.ui.theme.GlassBorderPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.CandyViewModel

@Composable
fun CommunityScreen(
    viewModel: CandyViewModel,
    modifier: Modifier = Modifier
) {
    var emailInput by remember { mutableStateOf("") }
    var joinedWaitlist by remember { mutableStateOf(false) }

    val socialChannels = listOf(
        SocialChannel("Discord", "Official T.J. Candy Blast Discord Guild", Icons.Default.Chat, CandyPink, "15,200 Members"),
        SocialChannel("YouTube", "Official Game Trailers & Devlogs", Icons.Default.Videocam, CandyYellow, "42,000 Subscribers"),
        SocialChannel("Instagram", "Daily Candy Art & Giveaways", Icons.Default.Share, CandyMagenta, "28,500 Followers"),
        SocialChannel("Facebook", "Official Fan Group & Guilds", Icons.Default.ThumbUp, CandyBlue, "19,000 Likes")
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "🌐 Official Community",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextPrimary
        )
        Text(
            text = "Join our global community channels and stay connected",
            fontSize = 13.sp,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Social Cards with "Coming Soon" Badges
        socialChannels.forEach { channel ->
            GlassmorphicCard(
                cornerRadius = 18.dp,
                accentGlow = channel.color,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .testTag("social_card_${channel.name.lowercase()}")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(channel.color.copy(alpha = 0.2f))
                            .border(1.dp, channel.color, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = channel.icon, contentDescription = channel.name, tint = channel.color, modifier = Modifier.size(24.dp))
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(channel.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(CandyYellow.copy(alpha = 0.2f))
                                    .border(1.dp, CandyYellow, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text("Coming Soon", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = CandyYellow)
                            }
                        }
                        Text(channel.desc, fontSize = 12.sp, color = TextSecondary)
                        Text(channel.stats, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = channel.color, modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Community Waitlist Form Card
        GlassmorphicCard(
            cornerRadius = 20.dp,
            accentGlow = CandyCyan
        ) {
            Column {
                Text("📩 Join Community Launch Waitlist", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Text("Get an exclusive Discord VIP Badge and 200 free gems on official launch!", fontSize = 12.sp, color = TextSecondary)

                Spacer(modifier = Modifier.height(12.dp))

                if (joinedWaitlist) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(CandyLime.copy(alpha = 0.2f))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Done", tint = CandyLime)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("You're on the VIP waitlist! Check your inbox soon.", fontSize = 13.sp, color = CandyLime, fontWeight = FontWeight.Bold)
                    }
                } else {
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        placeholder = { Text("Enter your email address", color = TextSecondary) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("community_waitlist_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CandyCyan,
                            unfocusedBorderColor = GlassBorderPurple,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (emailInput.isNotBlank()) {
                                joinedWaitlist = true
                                viewModel.addGems(200)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CandyCyan),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("join_waitlist_button")
                    ) {
                        Text("Claim VIP Badge & 200 Gems", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text("Official Portal: https://tjcandyblast.com", fontSize = 11.sp, color = DomainGold)
            }
        }
    }
}

private data class SocialChannel(
    val name: String,
    val desc: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color,
    val stats: String
)
