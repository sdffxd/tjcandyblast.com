package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.ui.theme.CandyCyan
import com.example.ui.theme.CandyLime
import com.example.ui.theme.CandyMagenta
import com.example.ui.theme.CandyPink
import com.example.ui.theme.CandyYellow
import com.example.ui.theme.DeepPurpleBg
import kotlin.random.Random

private data class Particle(
    val xRatio: Float,
    val yRatio: Float,
    val radius: Float,
    val speed: Float,
    val color: Color,
    val isStar: Boolean
)

@Composable
fun CandyBackgroundCanvas(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "BackgroundParticles")
    val animProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particleFloat"
    )

    val particles = remember {
        val colors = listOf(CandyPink, CandyCyan, CandyYellow, CandyMagenta, CandyLime)
        List(25) {
            Particle(
                xRatio = Random.nextFloat(),
                yRatio = Random.nextFloat(),
                radius = Random.nextFloat() * 12f + 6f,
                speed = Random.nextFloat() * 0.8f + 0.2f,
                color = colors[Random.nextInt(colors.size)],
                isStar = Random.nextBoolean()
            )
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Dark Purple Gradient Background
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    DeepPurpleBg,
                    Color(0xFF260D42),
                    Color(0xFF190632)
                )
            )
        )

        // Draw ambient glowing radial spots
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(CandyPink.copy(alpha = 0.15f), Color.Transparent),
                center = Offset(width * 0.2f, height * 0.25f),
                radius = width * 0.6f
            ),
            radius = width * 0.6f,
            center = Offset(width * 0.2f, height * 0.25f)
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(CandyCyan.copy(alpha = 0.12f), Color.Transparent),
                center = Offset(width * 0.8f, height * 0.7f),
                radius = width * 0.7f
            ),
            radius = width * 0.7f,
            center = Offset(width * 0.8f, height * 0.7f)
        )

        // Floating Candy Particles
        particles.forEach { p ->
            val currentY = ((p.yRatio - (animProgress * p.speed)) % 1f + 1f) % 1f * height
            val currentX = (p.xRatio * width + Math.sin((currentY * 0.01).toDouble()).toFloat() * 20f)
            val alpha = (0.3f + 0.4f * Math.sin((currentY * 0.02).toDouble()).toFloat()).coerceIn(0.2f, 0.8f)

            if (p.isStar) {
                // Draw star/cross sparkle
                val r = p.radius * 1.2f
                drawLine(
                    color = p.color.copy(alpha = alpha),
                    start = Offset(currentX - r, currentY),
                    end = Offset(currentX + r, currentY),
                    strokeWidth = 3f
                )
                drawLine(
                    color = p.color.copy(alpha = alpha),
                    start = Offset(currentX, currentY - r),
                    end = Offset(currentX, currentY + r),
                    strokeWidth = 3f
                )
            } else {
                // Draw candy jelly bubble
                drawCircle(
                    color = p.color.copy(alpha = alpha * 0.6f),
                    radius = p.radius,
                    center = Offset(currentX, currentY)
                )
                drawCircle(
                    color = p.color.copy(alpha = alpha),
                    radius = p.radius,
                    center = Offset(currentX, currentY),
                    style = Stroke(width = 2f)
                )
            }
        }
    }
}
