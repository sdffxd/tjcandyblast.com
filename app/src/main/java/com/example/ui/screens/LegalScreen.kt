package com.example.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GlassmorphicCard
import com.example.ui.theme.CandyCyan
import com.example.ui.theme.CandyYellow
import com.example.ui.theme.DomainGold
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun LegalScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "📜 Privacy Policy & Terms",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextPrimary
        )
        Text(
            text = "Official legal guidelines for T.J. Candy Blast (https://tjcandyblast.com)",
            fontSize = 13.sp,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Privacy Policy Card
        GlassmorphicCard(
            cornerRadius = 18.dp,
            accentGlow = CandyCyan
        ) {
            Column {
                Text(
                    text = "1. PRIVACY POLICY",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = CandyCyan
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Effective Date: August 2026\nOfficial Website: https://tjcandyblast.com\n\n" +
                            "T.J. Candy Blast (\"we\", \"our\", or \"us\") respects your privacy. This Privacy Policy describes how your information is handled when you use our official mobile application and website at https://tjcandyblast.com.\n\n" +
                            "• Data Collection: We do not collect personal identifying information without your consent. Local game progress, scores, and gems are stored locally on your device.\n" +
                            "• Support & Bug Reports: Any information submitted via support or bug report forms is used exclusively to assist you and resolve technical issues.\n" +
                            "• Play Store Compliance: Our app complies strictly with Google Play Developer Program Policies regarding user privacy and child safety.\n" +
                            "• Contact Us: If you have questions regarding this privacy policy, email support@tjcandyblast.com.",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Terms of Service Card
        GlassmorphicCard(
            cornerRadius = 18.dp,
            accentGlow = CandyYellow
        ) {
            Column {
                Text(
                    text = "2. TERMS OF SERVICE",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = CandyYellow
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Official Domain: https://tjcandyblast.com\n\n" +
                            "• Acceptance of Terms: By downloading, accessing, or playing T.J. Candy Blast, you agree to these Terms of Service.\n" +
                            "• Intellectual Property: All original artwork, candy characters, audio tracks, and game mechanics are the exclusive property of T.J. Candy Blast.\n" +
                            "• Virtual Goods: In-game gems, boosters, and items are virtual goods for entertainment purposes only and have no real-world monetary value.\n" +
                            "• Fair Play: Cheating, reverse engineering, or exploiting bugs is strictly prohibited.\n" +
                            "• Copyright © 2026 T.J. Candy Blast. All Rights Reserved.",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 18.sp
                )
            }
        }
    }
}
