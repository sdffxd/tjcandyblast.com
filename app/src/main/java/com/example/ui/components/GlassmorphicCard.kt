package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.CandyCyan
import com.example.ui.theme.CandyPink
import com.example.ui.theme.DarkPurpleCard
import com.example.ui.theme.GlassBorderPurple

@Composable
fun GlassmorphicCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 20.dp,
    borderColor: Color = GlassBorderPurple,
    accentGlow: Color? = null,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)
    val borderBrush = if (accentGlow != null) {
        Brush.horizontalGradient(
            colors = listOf(
                accentGlow.copy(alpha = 0.8f),
                CandyPink.copy(alpha = 0.6f),
                CandyCyan.copy(alpha = 0.8f)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                GlassBorderPurple.copy(alpha = 0.8f),
                Color(0xFF381B5E).copy(alpha = 0.4f)
            )
        )
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = shape,
                spotColor = accentGlow ?: CandyPink.copy(alpha = 0.3f),
                ambientColor = Color.Black
            )
            .clip(shape)
            .border(
                border = BorderStroke(1.5.dp, borderBrush),
                shape = shape
            )
            .then(
                if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
            ),
        color = DarkPurpleCard.copy(alpha = 0.85f),
        shape = shape
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2E134D).copy(alpha = 0.85f),
                            DarkPurpleCard.copy(alpha = 0.92f)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            content()
        }
    }
}
