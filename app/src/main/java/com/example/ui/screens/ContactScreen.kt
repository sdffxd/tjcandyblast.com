package com.example.ui.screens

import android.os.Build
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GlassmorphicCard
import com.example.ui.theme.CandyCyan
import com.example.ui.theme.CandyLime
import com.example.ui.theme.CandyPink
import com.example.ui.theme.CandyYellow
import com.example.ui.theme.DomainGold
import com.example.ui.theme.GlassBorderPurple
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.CandyViewModel

@Composable
fun ContactScreen(
    viewModel: CandyViewModel,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("General Inquiry") }
    var message by remember { mutableStateOf("") }
    var bugSubmitted by remember { mutableStateOf(false) }

    val submittedReports by viewModel.bugReports.collectAsState()
    val deviceInfo = "${Build.MANUFACTURER} ${Build.MODEL} (Android ${Build.VERSION.RELEASE})"

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "📬 Contact & Support",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextPrimary
        )
        Text(
            text = "Reach out to our support team or report bugs directly",
            fontSize = 13.sp,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Support Email Highlight Card
        GlassmorphicCard(
            cornerRadius = 18.dp,
            accentGlow = CandyYellow
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Email", tint = CandyYellow, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Official Support Email", fontSize = 12.sp, color = TextSecondary)
                    Text("support@tjcandyblast.com", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = CandyYellow)
                    Text("Official Portal: https://tjcandyblast.com", fontSize = 11.sp, color = DomainGold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Contact & Bug Report Form
        GlassmorphicCard(
            cornerRadius = 20.dp,
            accentGlow = CandyPink
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.BugReport, contentDescription = "Bug Report", tint = CandyPink, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Submit Support Request or Bug Report", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Your Name", color = TextSecondary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("contact_name_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CandyCyan,
                        unfocusedBorderColor = GlassBorderPurple,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Your Email Address", color = TextSecondary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("contact_email_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CandyCyan,
                        unfocusedBorderColor = GlassBorderPurple,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    placeholder = { Text("Describe your issue or feedback in detail...", color = TextSecondary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .testTag("contact_message_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CandyCyan,
                        unfocusedBorderColor = GlassBorderPurple,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF1B0733))
                        .padding(8.dp)
                ) {
                    Text("Auto-Detected Device: $deviceInfo", fontSize = 11.sp, color = TextSecondary)
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (bugSubmitted) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(CandyLime.copy(alpha = 0.2f))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Submitted", tint = CandyLime)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Submitted! Support will contact $email.", fontSize = 13.sp, color = CandyLime, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = {
                            if (name.isNotBlank() && email.isNotBlank() && message.isNotBlank()) {
                                viewModel.submitBugReport(name, email, category, message, deviceInfo)
                                bugSubmitted = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CandyPink),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("submit_contact_button")
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = "Send", tint = Color.White)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Send Support Request", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Submitted Tickets History
        if (submittedReports.isNotEmpty()) {
            Text("📋 Submitted Tickets History", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary, modifier = Modifier.padding(bottom = 8.dp))

            submittedReports.forEach { report ->
                GlassmorphicCard(
                    cornerRadius = 14.dp,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(report.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = CandyYellow)
                            Text(report.status, fontSize = 11.sp, color = CandyLime, fontWeight = FontWeight.Bold)
                        }
                        Text(report.message, fontSize = 12.sp, color = TextSecondary, modifier = Modifier.padding(top = 4.dp))
                    }
                }
            }
        }
    }
}
