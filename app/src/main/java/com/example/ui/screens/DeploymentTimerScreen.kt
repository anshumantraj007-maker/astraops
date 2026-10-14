package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
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
import com.example.ui.TimerState
import com.example.ui.components.TacticalBadge
import com.example.ui.theme.BudhadityaGold
import com.example.ui.theme.MarsRed
import com.example.ui.theme.SaturnBlue
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TacticalBorder
import com.example.ui.theme.TacticalObsidian
import com.example.ui.theme.TacticalSurfaceCard
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.util.Locale

@Composable
fun DeploymentTimerScreen(
    viewModel: AstraViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateTo(AstraScreen.COMMAND_DASHBOARD)
    }

    val timerState by viewModel.timerState.collectAsState()
    val targetMinutes by viewModel.targetDurationMinutes.collectAsState()
    val remainingSeconds by viewModel.remainingSeconds.collectAsState()
    val activeExam by viewModel.activeExam.collectAsState()
    val activeSubject by viewModel.activeSubject.collectAsState()
    val activeTopic by viewModel.activeTopic.collectAsState()
    val sessionPyqs by viewModel.sessionPyqs.collectAsState()

    val minutesLeft = remainingSeconds / 60
    val secondsLeft = remainingSeconds % 60
    val totalSeconds = targetMinutes * 60
    val progress = if (totalSeconds > 0) (totalSeconds - remainingSeconds).toFloat() / totalSeconds.toFloat() else 0f

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TacticalObsidian)
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Navigation & Title Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateTo(AstraScreen.COMMAND_DASHBOARD) },
                modifier = Modifier.testTag("timer_back_button")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "MECHANICAL EXECUTION ENGINE",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MarsRed,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Bypassing 8th-House Moon-Ketu Fog through Action",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Target Preset Selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PresetChip(
                label = "90m Deep",
                minutes = 90,
                selected = targetMinutes == 90,
                onClick = { viewModel.setDuration(90) },
                modifier = Modifier.weight(1f)
            )
            PresetChip(
                label = "60m Sprint",
                minutes = 60,
                selected = targetMinutes == 60,
                onClick = { viewModel.setDuration(60) },
                modifier = Modifier.weight(1f)
            )
            PresetChip(
                label = "45m Drill",
                minutes = 45,
                selected = targetMinutes == 45,
                onClick = { viewModel.setDuration(45) },
                modifier = Modifier.weight(1f)
            )
            PresetChip(
                label = "5m Friction",
                minutes = 5,
                selected = targetMinutes == 5,
                onClick = { viewModel.setDuration(5) },
                accentColor = MarsRed,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Active Subject Card
        Card(
            colors = CardDefaults.cardColors(containerColor = TacticalSurfaceCard),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, TacticalBorder, RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TacticalBadge(
                        text = activeExam,
                        accentColor = if (activeExam == "UPSC APFC") MarsRed else SaturnBlue
                    )
                    TacticalBadge(
                        text = when (timerState) {
                            TimerState.IDLE -> "STANDBY"
                            TimerState.RUNNING -> "DEPLOYED / RUNNING"
                            TimerState.PAUSED -> "PAUSED"
                            TimerState.COMPLETED -> "MISSION CONCLUDED"
                        },
                        accentColor = when (timerState) {
                            TimerState.IDLE -> TextMuted
                            TimerState.RUNNING -> MarsRed
                            TimerState.PAUSED -> BudhadityaGold
                            TimerState.COMPLETED -> SuccessGreen
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = activeSubject,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = activeTopic,
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Tactical HUD Circular/Block Display
        Box(
            modifier = Modifier
                .size(240.dp)
                .clip(CircleShape)
                .background(Color(0xFF030712))
                .border(
                    3.dp,
                    if (timerState == TimerState.RUNNING) MarsRed else TacticalBorder,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = String.format(Locale.US, "%02d:%02d", minutesLeft, secondsLeft),
                    fontSize = 54.sp,
                    fontWeight = FontWeight.Black,
                    color = if (timerState == TimerState.RUNNING) TextPrimary else TextSecondary,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 2.sp
                )
                Text(
                    text = when (timerState) {
                        TimerState.RUNNING -> "COGNITIVE MOMENTUM"
                        TimerState.PAUSED -> "SUSPENDED"
                        TimerState.COMPLETED -> "DEPLOYMENT COMPLETED"
                        TimerState.IDLE -> "READY TO DEPLOY"
                    },
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (timerState == TimerState.RUNNING) MarsRed else TextMuted,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .width(140.dp)
                        .height(6.dp)
                        .clip(CircleShape),
                    color = MarsRed,
                    trackColor = TacticalBorder
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // PYQ Tracking Counter during Block
        Card(
            colors = CardDefaults.cardColors(containerColor = TacticalSurfaceCard),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, TacticalBorder, RoundedCornerShape(10.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "OBJECTIVE PYQS SOLVED",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BudhadityaGold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "Count raw output, 0 guessing allowed",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { viewModel.decrementPyq() },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF030712))
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = TextPrimary)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "$sessionPyqs",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = TextPrimary,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    IconButton(
                        onClick = { viewModel.incrementPyq() },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF030712))
                            .testTag("increment_pyq_button")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Increase", tint = TextPrimary)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Timer Control Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (timerState) {
                TimerState.IDLE, TimerState.PAUSED -> {
                    Button(
                        onClick = { viewModel.startTimer() },
                        colors = ButtonDefaults.buttonColors(containerColor = MarsRed),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("start_timer_button")
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (timerState == TimerState.PAUSED) "RESUME DEPLOYMENT" else "START DEPLOYMENT",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
                TimerState.RUNNING -> {
                    Button(
                        onClick = { viewModel.pauseTimer() },
                        colors = ButtonDefaults.buttonColors(containerColor = BudhadityaGold),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("pause_timer_button")
                    ) {
                        Icon(Icons.Default.Pause, contentDescription = null, tint = Color.Black)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "PAUSE DEPLOYMENT",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 13.sp
                        )
                    }
                }
                TimerState.COMPLETED -> {
                    Button(
                        onClick = { viewModel.resetTimer() },
                        colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("MISSION LOGGED", fontWeight = FontWeight.Bold)
                    }
                }
            }

            if (timerState == TimerState.RUNNING || timerState == TimerState.PAUSED) {
                OutlinedButton(
                    onClick = { viewModel.completeDeployment(isFull = false) },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                    modifier = Modifier.height(52.dp).testTag("finish_deployment_button")
                ) {
                    Text("LOG NOW", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Iron Rule Directive
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF030712))
                .border(1.dp, MarsRed.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = MarsRed,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "OPERATIONAL MANDATE: Never negotiate with mood or fatigue. Feelings are subjective 8th-house noise; execution is objective Mars reality.",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    lineHeight = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun PresetChip(
    label: String,
    minutes: Int,
    selected: Boolean,
    onClick: () -> Unit,
    accentColor: Color = SaturnBlue,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                fontFamily = FontFamily.Monospace
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = accentColor.copy(alpha = 0.2f),
            selectedLabelColor = accentColor,
            containerColor = Color(0xFF030712),
            labelColor = TextSecondary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.testTag("preset_${minutes}m")
    )
}
