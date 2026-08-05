package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CandyBlue
import com.example.ui.theme.CandyCyan
import com.example.ui.theme.CandyLime
import com.example.ui.theme.CandyMagenta
import com.example.ui.theme.CandyOrange
import com.example.ui.theme.CandyPink
import com.example.ui.theme.CandyYellow
import com.example.ui.theme.DarkPurpleCard
import com.example.ui.theme.DomainGold
import com.example.ui.theme.GlassBorderPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlin.random.Random

enum class CandyType(val symbol: String, val color: Color) {
    PINK_STAR("⭐", CandyPink),
    CYAN_JELLY("💎", CandyCyan),
    YELLOW_GEM("🌟", CandyYellow),
    LIME_CANDY("🍬", CandyLime),
    MAGENTA_HEART("💖", CandyMagenta),
    ORANGE_BLAST("🔥", CandyOrange)
}

data class GridPos(val row: Int, val col: Int)

@Composable
fun InteractiveMinigame(
    onClose: () -> Unit,
    onScoreUpdated: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val gridSize = 6
    var grid by remember {
        mutableStateOf(List(gridSize) { List(gridSize) { CandyType.values()[Random.nextInt(CandyType.values().size)] } })
    }
    var selectedPos by remember { mutableStateOf<GridPos?>(null) }
    var score by remember { mutableIntStateOf(0) }
    var combos by remember { mutableIntStateOf(0) }
    var movesLeft by remember { mutableIntStateOf(15) }
    var blastMessage by remember { mutableStateOf<String?>(null) }

    fun checkAndClearMatches(): Boolean {
        val toRemove = mutableSetOf<GridPos>()

        // Check rows
        for (r in 0 until gridSize) {
            var matchLen = 1
            for (c in 1 until gridSize) {
                if (grid[r][c] == grid[r][c - 1]) {
                    matchLen++
                } else {
                    if (matchLen >= 3) {
                        for (i in 0 until matchLen) toRemove.add(GridPos(r, c - 1 - i))
                    }
                    matchLen = 1
                }
            }
            if (matchLen >= 3) {
                for (i in 0 until matchLen) toRemove.add(GridPos(r, gridSize - 1 - i))
            }
        }

        // Check cols
        for (c in 0 until gridSize) {
            var matchLen = 1
            for (r in 1 until gridSize) {
                if (grid[r][c] == grid[r - 1][c]) {
                    matchLen++
                } else {
                    if (matchLen >= 3) {
                        for (i in 0 until matchLen) toRemove.add(GridPos(r - 1 - i, c))
                    }
                    matchLen = 1
                }
            }
            if (matchLen >= 3) {
                for (i in 0 until matchLen) toRemove.add(GridPos(gridSize - 1 - i, c))
            }
        }

        if (toRemove.isNotEmpty()) {
            val pts = toRemove.size * 100 + combos * 50
            score += pts
            combos++
            onScoreUpdated(score)

            blastMessage = when {
                toRemove.size >= 5 -> "🎉 SUPER CANDY BLAST! +$pts"
                combos > 2 -> "🔥 COMBO x$combos! +$pts"
                else -> "💥 CANDY MATCH! +$pts"
            }

            // Generate new candies to replace cleared
            val newGrid = grid.map { it.toMutableList() }
            for (pos in toRemove) {
                newGrid[pos.row][pos.col] = CandyType.values()[Random.nextInt(CandyType.values().size)]
            }
            grid = newGrid
            return true
        }
        return false
    }

    fun handleSwap(p1: GridPos, p2: GridPos) {
        val rowDiff = Math.abs(p1.row - p2.row)
        val colDiff = Math.abs(p1.col - p2.col)

        if ((rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1)) {
            val mutableGrid = grid.map { it.toMutableList() }.toMutableList()
            val temp = mutableGrid[p1.row][p1.col]
            mutableGrid[p1.row][p1.col] = mutableGrid[p2.row][p2.col]
            mutableGrid[p2.row][p2.col] = temp
            grid = mutableGrid
            movesLeft--

            val matched = checkAndClearMatches()
            if (!matched) {
                combos = 0
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(DarkPurpleCard)
            .border(2.dp, Brush.horizontalGradient(listOf(CandyPink, CandyCyan)), RoundedCornerShape(24.dp))
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Minigame Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Candy Blast",
                        tint = CandyYellow,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Match-3 Live Playable Preview",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(GlassBorderPurple)
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Score & Moves HUD
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF1B0733))
                    .border(1.dp, GlassBorderPurple, RoundedCornerShape(16.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("SCORE", fontSize = 10.sp, color = TextSecondary, fontWeight = FontWeight.Bold)
                    Text("$score", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = CandyYellow)
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(28.dp)
                        .background(GlassBorderPurple)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("MOVES LEFT", fontSize = 10.sp, color = TextSecondary, fontWeight = FontWeight.Bold)
                    Text("$movesLeft", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = CandyCyan)
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(28.dp)
                        .background(GlassBorderPurple)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("DOMAIN", fontSize = 9.sp, color = DomainGold, fontWeight = FontWeight.Bold)
                    Text("tjcandyblast.com", fontSize = 10.sp, color = DomainGold)
                }
            }

            // Blast Feedback Toast
            blastMessage?.let { msg ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + scaleIn()
                ) {
                    Text(
                        text = msg,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = CandyPink,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Match-3 Grid
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFF130526))
                    .border(1.5.dp, GlassBorderPurple, RoundedCornerShape(18.dp))
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (r in 0 until gridSize) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            for (c in 0 until gridSize) {
                                val pos = GridPos(r, c)
                                val isSelected = selectedPos == pos
                                val candy = grid[r][c]
                                val scale by animateFloatAsState(
                                    targetValue = if (isSelected) 1.15f else 1f,
                                    animationSpec = spring(stiffness = Spring.StiffnessMedium),
                                    label = "candyScale"
                                )

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .padding(3.dp)
                                        .scale(scale)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (isSelected) CandyPink.copy(alpha = 0.4f)
                                            else candy.color.copy(alpha = 0.25f)
                                        )
                                        .border(
                                            width = if (isSelected) 2.dp else 1.dp,
                                            color = if (isSelected) CandyYellow else candy.color.copy(alpha = 0.6f),
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .clickable {
                                            if (movesLeft > 0) {
                                                if (selectedPos == null) {
                                                    selectedPos = pos
                                                } else {
                                                    handleSwap(selectedPos!!, pos)
                                                    selectedPos = null
                                                }
                                            }
                                        }
                                        .testTag("candy_tile_${r}_${c}"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = candy.symbol,
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Restart / Reset Game Button
            if (movesLeft <= 0) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "🏆 Game Over! Final Score: $score",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = CandyYellow
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Button(
                        onClick = {
                            grid = List(gridSize) { List(gridSize) { CandyType.values()[Random.nextInt(CandyType.values().size)] } }
                            movesLeft = 15
                            score = 0
                            combos = 0
                            blastMessage = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CandyPink),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.testTag("restart_minigame")
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset", tint = Color.White)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Play Again", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            } else {
                Text(
                    text = "Tap two adjacent candies to swap and match 3 in a row!",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }
        }
    }
}
