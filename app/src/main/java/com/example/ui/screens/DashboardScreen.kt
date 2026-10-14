package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AstraScreen
import com.example.ui.AstraViewModel
import com.example.ui.components.TacticalBadge
import com.example.ui.theme.BudhadityaGold
import com.example.ui.theme.KetuPurple
import com.example.ui.theme.MarsRed
import com.example.ui.theme.MoonSilver
import com.example.ui.theme.SaturnBlue
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TacticalBorder
import com.example.ui.theme.TacticalObsidian
import com.example.ui.theme.TacticalSurfaceCard
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun DashboardScreen(
    viewModel: AstraViewModel,
    modifier: Modifier = Modifier
) {
    val weather by viewModel.tacticalWeather.collectAsState()
    val bootState by viewModel.bootState.collectAsState()
    val microTasks by viewModel.microTasks.collectAsState()
    val deployments by viewModel.deployments.collectAsState()
    val totalMinutes by viewModel.totalMinutes.collectAsState()
    val totalPyqs by viewModel.totalPyqs.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TacticalObsidian)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            // Dasha Header Banner
            Card(
                colors = CardDefaults.cardColors(containerColor = TacticalSurfaceCard),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MarsRed.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .testTag("dasha_banner_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TacticalBadge(
                            text = weather.currentDasha,
                            accentColor = MarsRed,
                            icon = Icons.Default.Bolt
                        )
                        TacticalBadge(
                            text = "OCT 2026 - MAY 2027",
                            accentColor = BudhadityaGold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "CAREER BREAKTHROUGH WINDOW",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = TextPrimary,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Transitioned out of Moon lethargy into active Moon-Mars Dasha. Mars execution is now dominant.",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Exam Target Countdowns
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF030712))
                                .border(1.dp, TacticalBorder, RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(
                                    text = "UPSC APFC",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MarsRed,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "${weather.apfcDaysRemaining}D ${weather.apfcHoursRemaining}H",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Black,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Exam: Dec 20, 2026",
                                    fontSize = 10.sp,
                                    color = TextMuted
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF030712))
                                .border(1.dp, TacticalBorder, RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(
                                    text = "HC RO/ARO",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SaturnBlue,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "~${weather.roAroDaysTarget} DAYS",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Black,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Allahabad High Court",
                                    fontSize = 10.sp,
                                    color = TextMuted
                                )
                            }
                        }
                    }
                }
            }
        }

        // Tactical Telemetry Meters (Mars, Saturn, Moon-Ketu)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = TacticalSurfaceCard),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, TacticalBorder, RoundedCornerShape(12.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "OPERATIONAL TELEMETRY",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = SaturnBlue,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Mars Stamina Meter
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Mars Stamina (Raw Execution)", fontSize = 12.sp, color = TextPrimary)
                        Text("${weather.marsStaminaScore}%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MarsRed)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { weather.marsStaminaScore / 100f },
                        modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                        color = MarsRed,
                        trackColor = Color(0xFF030712)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Saturn Discipline Meter
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Saturn Rigor (6th House Bastion)", fontSize = 12.sp, color = TextPrimary)
                        Text("${weather.saturnDisciplineIndex}%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SaturnBlue)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { weather.saturnDisciplineIndex / 100f },
                        modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                        color = SaturnBlue,
                        trackColor = Color(0xFF030712)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Moon-Ketu Fog Risk
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("8th-House Moon Fog Hazard", fontSize = 12.sp, color = TextPrimary)
                        Text("${weather.moonKetuFogRisk}%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = KetuPurple)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { weather.moonKetuFogRisk / 100f },
                        modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                        color = KetuPurple,
                        trackColor = Color(0xFF030712)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = weather.dayTacticalWeatherSummary,
                        fontSize = 11.sp,
                        color = TextSecondary,
                        lineHeight = 15.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }
        }

        // Morning "System Boot" Protocol
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = TacticalSurfaceCard),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BudhadityaGold.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .testTag("system_boot_protocol_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = BudhadityaGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "SYSTEM BOOT PROTOCOL",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = BudhadityaGold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        TacticalBadge(
                            text = "${bootState.cognitiveMomentumScore}% READY",
                            accentColor = SuccessGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 1. Silver Glass
                    BootCheckItem(
                        title = "Silver Glass Morning Hydration",
                        subtitle = "8th-House Moon Grounding (Stabilizes mental fog & overthinking)",
                        isChecked = bootState.silverGlassHydrated,
                        onClick = { viewModel.toggleSilverGlass() },
                        testTag = "boot_silver_glass_toggle"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // 2. Stray Dogs
                    BootCheckItem(
                        title = "Feed Stray Dogs (Ketu Harmonization)",
                        subtitle = "Neutralizes detachment & erratic anxiety vectors",
                        isChecked = bootState.strayDogsFed,
                        onClick = { viewModel.toggleStrayDogs() },
                        testTag = "boot_stray_dogs_toggle"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // 3. Digital Perimeter
                    BootCheckItem(
                        title = "Digital Perimeter Locked",
                        subtitle = "Zero distraction morning state. Notifications suppressed.",
                        isChecked = bootState.digitalPerimeterLocked,
                        onClick = { viewModel.toggleDigitalPerimeter() },
                        testTag = "boot_digital_perimeter_toggle"
                    )
                }
            }
        }

        // Primary Action: Engage Deployment Block
        item {
            Button(
                onClick = { viewModel.navigateTo(AstraScreen.DEPLOYMENT_TIMER) },
                colors = ButtonDefaults.buttonColors(containerColor = MarsRed),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("engage_deployment_button")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ENGAGE MECHANICAL DEPLOYMENT",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

        // High-Yield Directives / Micro-Tasks
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TACTICAL DIRECTIVES (MICRO-TASKS)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    fontFamily = FontFamily.Monospace
                )
                TacticalBadge(
                    text = "${microTasks.count { !it.isCompleted }} PENDING",
                    accentColor = MarsRed
                )
            }
        }

        items(microTasks.take(4)) { task ->
            Card(
                colors = CardDefaults.cardColors(containerColor = TacticalSurfaceCard),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        if (task.isCompleted) SuccessGreen.copy(alpha = 0.4f) else TacticalBorder,
                        RoundedCornerShape(8.dp)
                    )
                    .clickable { viewModel.toggleMicroTask(task) }
                    .testTag("micro_task_card_${task.id}")
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (task.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                        contentDescription = null,
                        tint = if (task.isCompleted) SuccessGreen else TextMuted,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            TacticalBadge(
                                text = task.examType,
                                accentColor = if (task.examType == "UPSC APFC") MarsRed else SaturnBlue
                            )
                            TacticalBadge(
                                text = task.directiveType,
                                accentColor = BudhadityaGold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = task.title,
                            fontSize = 13.sp,
                            color = if (task.isCompleted) TextMuted else TextPrimary,
                            fontWeight = if (task.isCompleted) FontWeight.Normal else FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Performance Statistics Telemetry
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = TacticalSurfaceCard),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, TacticalBorder, RoundedCornerShape(12.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "CUMULATIVE COMBAT METRICS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SaturnBlue,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        MetricItem(
                            label = "Focus Minutes",
                            value = "${totalMinutes ?: 0}m",
                            color = MarsRed
                        )
                        MetricItem(
                            label = "PYQs Mastered",
                            value = "${totalPyqs ?: 0}",
                            color = BudhadityaGold
                        )
                        MetricItem(
                            label = "Deployments",
                            value = "${deployments.size}",
                            color = SaturnBlue
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun BootCheckItem(
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (isChecked) SuccessGreen.copy(alpha = 0.08f) else Color(0xFF030712))
            .border(
                1.dp,
                if (isChecked) SuccessGreen.copy(alpha = 0.4f) else TacticalBorder,
                RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(10.dp)
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isChecked) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (isChecked) SuccessGreen else TextMuted,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (isChecked) TextPrimary else TextSecondary
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = TextMuted
            )
        }
    }
}

@Composable
private fun MetricItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            color = color,
            fontFamily = FontFamily.Monospace
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = TextSecondary
        )
    }
}
