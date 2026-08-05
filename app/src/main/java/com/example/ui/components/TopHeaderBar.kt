package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.CandyCyan
import com.example.ui.theme.CandyPink
import com.example.ui.theme.CandyYellow
import com.example.ui.theme.DeepPurpleBg
import com.example.ui.theme.DomainGold
import com.example.ui.theme.GlassBorderPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

enum class NavPage(val title: String) {
    HOME("Home"),
    FEATURES("Features"),
    WORLDS("Candy Worlds"),
    GALLERY("Gallery"),
    NEWS("News"),
    COMMUNITY("Community"),
    FAQ("FAQ"),
    CONTACT("Contact"),
    LEGAL("Legal")
}

@Composable
fun TopHeaderBar(
    currentPage: NavPage,
    onPageSelected: (NavPage) -> Unit,
    onPlayClick: () -> Unit,
    onDownloadClick: () -> Unit,
    isMenuOpen: Boolean,
    onToggleMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = DeepPurpleBg.copy(alpha = 0.95f),
        tonalElevation = 6.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Main Branding Header Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Logo + Title + Domain Badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onPageSelected(NavPage.HOME) }
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, Brush.linearGradient(listOf(CandyPink, CandyCyan)), CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_app_icon_1785898360575),
                            contentDescription = "T.J. Candy Blast Logo",
                            modifier = Modifier.size(42.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = "T.J. Candy Blast",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextPrimary,
                            letterSpacing = 0.5.sp
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Public,
                                contentDescription = "Official Domain",
                                tint = DomainGold,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "https://tjcandyblast.com",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = DomainGold
                            )
                        }
                    }
                }

                // Header Action Buttons
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = onPlayClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .testTag("nav_play_button")
                            .background(
                                Brush.horizontalGradient(listOf(CandyPink, CandyCyan)),
                                RoundedCornerShape(20.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Play", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    IconButton(
                        onClick = onToggleMenu,
                        modifier = Modifier
                            .testTag("toggle_nav_menu")
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(GlassBorderPurple.copy(alpha = 0.3f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = CandyCyan
                        )
                    }
                }
            }

            // Quick Scrollable Nav Bar Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                NavPage.values().forEach { page ->
                    val isSelected = currentPage == page
                    val chipBg = if (isSelected) {
                        Brush.horizontalGradient(listOf(CandyPink, CandyCyan))
                    } else {
                        Brush.horizontalGradient(
                            listOf(
                                GlassBorderPurple.copy(alpha = 0.3f),
                                GlassBorderPurple.copy(alpha = 0.2f)
                            )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .testTag("nav_chip_${page.name.lowercase()}")
                            .clip(RoundedCornerShape(16.dp))
                            .background(chipBg)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) CandyYellow else GlassBorderPurple.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { onPageSelected(page) }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = page.title,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else TextSecondary
                        )
                    }
                }
            }

            // Expanded Drawer Menu
            AnimatedVisibility(visible = isMenuOpen) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1E0A36))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "🌐 Official Domain: https://tjcandyblast.com",
                        color = DomainGold,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    NavPage.values().forEach { page ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    onPageSelected(page)
                                    onToggleMenu()
                                }
                                .padding(vertical = 10.dp, horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = page.title,
                                fontSize = 15.sp,
                                color = if (currentPage == page) CandyCyan else TextPrimary,
                                fontWeight = if (currentPage == page) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            onDownloadClick()
                            onToggleMenu()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CandyYellow),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = "Download",
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Get T.J. Candy Blast APK", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
