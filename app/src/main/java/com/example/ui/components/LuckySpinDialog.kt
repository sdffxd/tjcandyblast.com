package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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

@Composable
fun LuckySpinDialog(
    onDismiss: () -> Unit,
    onRewardClaimed: (String, Int) -> Unit
) {
    val prizes = remember {
        listOf(
            "50 Gems" to 50,
            "Color Bomb" to 1,
            "100 Gems" to 100,
            "Candy Hammer" to 2,
            "200 Gems" to 200,
            "Jelly Switch" to 3
        )
    }

    var targetRotation by remember { mutableFloatStateOf(0f) }
    var isSpinning by remember { mutableStateOf(false) }
    var wonPrize by remember { mutableStateOf<Pair<String, Int>?>(null) }

    val animatedRotation by animateFloatAsState(
        targetValue = targetRotation,
        animationSpec = tween(durationMillis = 3500, easing = FastOutSlowInEasing),
        finishedListener = {
            isSpinning = false
            val prizeIndex = ((targetRotation % 360f) / (360f / prizes.size)).toInt() % prizes.size
            val prize = prizes[prizes.size - 1 - prizeIndex]
            wonPrize = prize
            onRewardClaimed(prize.first, prize.second)
        },
        label = "wheelRotation"
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .border(2.dp, Brush.horizontalGradient(listOf(CandyPink, CandyCyan)), RoundedCornerShape(24.dp)),
            color = DarkPurpleCard
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Casino,
                            contentDescription = "Lucky Spin",
                            tint = CandyYellow,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Daily Lucky Spin",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(GlassBorderPurple)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Wheel Canvas
                Box(
                    modifier = Modifier.size(220.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Wheel Canvas
                    Canvas(
                        modifier = Modifier
                            .size(200.dp)
                            .rotate(animatedRotation)
                    ) {
                        val sliceAngle = 360f / prizes.size
                        val sliceColors = listOf(CandyPink, CandyCyan, CandyYellow, CandyMagenta, CandyLime, CandyOrange)

                        prizes.forEachIndexed { i, _ ->
                            drawArc(
                                color = sliceColors[i % sliceColors.size],
                                startAngle = i * sliceAngle,
                                sweepAngle = sliceAngle,
                                useCenter = true,
                                size = Size(size.width, size.height)
                            )
                        }
                    }

                    // Pointer Indicator at top
                    Canvas(modifier = Modifier.size(200.dp)) {
                        drawCircle(
                            color = Color.White,
                            radius = 24f,
                            center = Offset(size.width / 2, size.height / 2)
                        )
                    }

                    Text(
                        text = "⭐",
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                wonPrize?.let { prize ->
                    Text(
                        text = "🎉 YOU WON: ${prize.first}!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = CandyYellow,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        if (!isSpinning) {
                            isSpinning = true
                            wonPrize = null
                            val randomRounds = Random.nextInt(5, 10)
                            val randomDegree = Random.nextFloat() * 360f
                            targetRotation += randomRounds * 360f + randomDegree
                        }
                    },
                    enabled = !isSpinning,
                    colors = ButtonDefaults.buttonColors(containerColor = CandyPink),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("spin_wheel_button")
                ) {
                    Text(
                        text = if (isSpinning) "Spinning..." else "SPIN FOR REWARDS",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Official Website: tjcandyblast.com",
                    fontSize = 11.sp,
                    color = DomainGold
                )
            }
        }
    }
}
