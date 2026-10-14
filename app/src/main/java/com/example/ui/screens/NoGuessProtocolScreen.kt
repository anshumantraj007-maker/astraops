package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.noguess.NoGuessDrillRepository
import com.example.ui.AstraScreen
import com.example.ui.AstraViewModel
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

@Composable
fun NoGuessProtocolScreen(
    viewModel: AstraViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateTo(AstraScreen.COMMAND_DASHBOARD)
    }

    val questionIndex by viewModel.activeQuestionIndex.collectAsState()
    val eliminatedOptions by viewModel.eliminatedOptions.collectAsState()
    val selectedAnswer by viewModel.selectedAnswerIndex.collectAsState()
    val isSubmitted by viewModel.drillSubmitted.collectAsState()

    val drillList = NoGuessDrillRepository.SAMPLE_DRILLS
    val question = drillList[questionIndex]
    val isCorrect = selectedAnswer == question.correctIndex

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TacticalObsidian)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.navigateTo(AstraScreen.COMMAND_DASHBOARD) },
                    modifier = Modifier.testTag("noguess_back_button")
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
                        text = "THE NO-GUESS PROTOCOL",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MarsRed,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Zero Speculation • Binary Elimination • Algorithmic Deduction",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        // Protocol Doctrine Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = TacticalSurfaceCard),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MarsRed.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = MarsRed,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ANTI-PENALTY DOCTRINE",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MarsRed,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Negative marking (-0.33) is designed to exploit emotional guesswork. Strike out traps with the [X ELIMINATE] button before committing to a selection.",
                        fontSize = 11.sp,
                        color = TextSecondary,
                        lineHeight = 15.sp
                    )
                }
            }
        }

        // Question Details Card
        item {
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
                            text = question.exam,
                            accentColor = if (question.exam == "UPSC APFC") MarsRed else SaturnBlue
                        )
                        TacticalBadge(
                            text = "DRILL ${questionIndex + 1} OF ${drillList.size}",
                            accentColor = BudhadityaGold
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = question.questionText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Subject: ${question.subject}",
                        fontSize = 11.sp,
                        color = TextMuted,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        // Interactive Options
        item {
            Text(
                text = "OPTIONS (TAP 'X' TO ELIMINATE, OR TAP CARD TO SELECT):",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = SaturnBlue,
                fontFamily = FontFamily.Monospace
            )
        }

        itemsIndexed(question.options) { index, optionText ->
            val isEliminated = eliminatedOptions.contains(index)
            val isSelected = selectedAnswer == index
            val isCorrectOption = isSubmitted && index == question.correctIndex
            val isWrongSelected = isSubmitted && isSelected && !isCorrect

            val borderColor = when {
                isCorrectOption -> SuccessGreen
                isWrongSelected -> MarsRed
                isSelected -> SaturnBlue
                isEliminated -> TextMuted.copy(alpha = 0.3f)
                else -> TacticalBorder
            }

            val bgColor = when {
                isCorrectOption -> SuccessGreen.copy(alpha = 0.15f)
                isWrongSelected -> MarsRed.copy(alpha = 0.15f)
                isSelected -> SaturnBlue.copy(alpha = 0.15f)
                isEliminated -> Color(0xFF030712)
                else -> TacticalSurfaceCard
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = bgColor),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, borderColor, RoundedCornerShape(10.dp))
                    .clickable(enabled = !isSubmitted) {
                        viewModel.selectFinalAnswer(index)
                    }
                    .testTag("option_card_$index")
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = optionText,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = when {
                                isCorrectOption -> SuccessGreen
                                isWrongSelected -> MarsRed
                                isEliminated -> TextMuted
                                else -> TextPrimary
                            },
                            textDecoration = if (isEliminated) TextDecoration.LineThrough else TextDecoration.None
                        )
                        if (isEliminated) {
                            Text(
                                text = "[STRUCK OUT - LOGICALLY INVALID]",
                                fontSize = 10.sp,
                                color = MarsRed,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    if (!isSubmitted) {
                        // Quick Elimination Toggle Button
                        OutlinedButton(
                            onClick = { viewModel.toggleEliminateOption(index) },
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = if (isEliminated) SuccessGreen else MarsRed
                            ),
                            modifier = Modifier
                                .height(36.dp)
                                .testTag("eliminate_btn_$index")
                        ) {
                            Icon(
                                imageVector = if (isEliminated) Icons.Default.CheckCircle else Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isEliminated) "RESTORE" else "ELIMINATE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    } else if (isCorrectOption) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Correct",
                            tint = SuccessGreen,
                            modifier = Modifier.size(24.dp)
                        )
                    } else if (isWrongSelected) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = "Wrong",
                            tint = MarsRed,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        // Submit & Next Controls
        item {
            if (!isSubmitted) {
                Button(
                    onClick = { viewModel.submitDrillAnswer() },
                    enabled = selectedAnswer != null,
                    colors = ButtonDefaults.buttonColors(containerColor = MarsRed),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("submit_deduction_button")
                ) {
                    Text(
                        text = "SUBMIT DEDUCTION (ZERO GUESS)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            } else {
                Button(
                    onClick = { viewModel.nextDrillQuestion() },
                    colors = ButtonDefaults.buttonColors(containerColor = SaturnBlue),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("next_drill_button")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "ADVANCE TO NEXT DRILL",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TacticalObsidian
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = TacticalObsidian
                        )
                    }
                }
            }
        }

        // Post-Submission Detailed Algorithmic Deduction Breakdown
        if (isSubmitted) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = TacticalSurfaceCard),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            if (isCorrect) SuccessGreen else MarsRed,
                            RoundedCornerShape(12.dp)
                        )
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Warning,
                                contentDescription = null,
                                tint = if (isCorrect) SuccessGreen else MarsRed,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isCorrect) "DEDUCTION VERIFIED (+2.66 / +1.00 NET GAIN)" else "TACTICAL MISFIRE (-0.33 PENALTY DETECTED)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isCorrect) SuccessGreen else MarsRed,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Trap Analysis
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF030712))
                                .border(1.dp, TacticalBorder, RoundedCornerShape(6.dp))
                                .padding(10.dp)
                        ) {
                            Column {
                                Text(
                                    text = "UPSC / ARO EXAM TRAP IDENTIFIED:",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = BudhadityaGold,
                                    fontFamily = FontFamily.Monospace
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = question.trapReason,
                                    fontSize = 12.sp,
                                    color = TextSecondary,
                                    lineHeight = 16.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "STEP-BY-STEP IT DEDUCTION TRACE:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SaturnBlue,
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        question.deductionSteps.forEach { step ->
                            Text(
                                text = "• $step",
                                fontSize = 12.sp,
                                color = TextPrimary,
                                lineHeight = 16.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Statutory Rule Applied: ${question.ruleApplied}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = BudhadityaGold,
                            fontFamily = FontFamily.Monospace
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
