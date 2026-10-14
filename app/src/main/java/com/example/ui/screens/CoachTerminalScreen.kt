package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.coach.CoachMessage
import com.example.ui.AstraScreen
import com.example.ui.AstraViewModel
import com.example.ui.components.TacticalBadge
import com.example.ui.theme.BudhadityaGold
import com.example.ui.theme.MarsRed
import com.example.ui.theme.SaturnBlue
import com.example.ui.theme.TacticalBorder
import com.example.ui.theme.TacticalObsidian
import com.example.ui.theme.TacticalSurfaceCard
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun CoachTerminalScreen(
    viewModel: AstraViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateTo(AstraScreen.COMMAND_DASHBOARD)
    }

    val messages by viewModel.coachMessages.collectAsState()
    val isLoading by viewModel.coachLoading.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TacticalObsidian)
            .padding(16.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.navigateTo(AstraScreen.COMMAND_DASHBOARD) },
                modifier = Modifier.testTag("coach_back_button")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Terminal,
                        contentDescription = null,
                        tint = MarsRed,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "MARS-SATURN TACTICAL TERMINAL",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MarsRed,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )
                }
                Text(
                    text = "Vedic Psychological Engine • Zero Fluff • Mechanical Execution",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Quick Command Action Chips
        val hScroll = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(hScroll),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuickCommandChip(
                label = "Friction Phase Breaker",
                accentColor = MarsRed,
                onClick = { viewModel.sendCoachPrompt("I am feeling mental fog and friction. Execute 8th-house bypass protocol.") }
            )
            QuickCommandChip(
                label = "APFC 68-Day War Plan",
                accentColor = BudhadityaGold,
                onClick = { viewModel.sendCoachPrompt("Provide the UPSC APFC T-68 day high-yield operational schedule.") }
            )
            QuickCommandChip(
                label = "Analyze Planetary Weather",
                accentColor = SaturnBlue,
                onClick = { viewModel.sendCoachPrompt("Give me today's astrological weather report and tactical directive.") }
            )
            QuickCommandChip(
                label = "RO/ARO Hindi Strategy",
                accentColor = SaturnBlue,
                onClick = { viewModel.sendCoachPrompt("Explain the Allahabad HC RO/ARO Hindi grammar regex rules.") }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Message Feed
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(messages) { msg ->
                CoachMessageItem(
                    message = msg,
                    onExecuteAction = { action ->
                        if (action.contains("Friction") || action.contains("Deploy") || action.contains("Block")) {
                            viewModel.navigateTo(AstraScreen.DEPLOYMENT_TIMER)
                        } else if (action.contains("Drill") || action.contains("Hindi") || action.contains("Syntax")) {
                            viewModel.navigateTo(AstraScreen.NO_GUESS_PROTOCOL)
                        } else {
                            viewModel.navigateTo(AstraScreen.COMMAND_DASHBOARD)
                        }
                    }
                )
            }

            if (isLoading) {
                item {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = MarsRed,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "COMMAND ENGINE COMPUTING TACTICAL DIRECTIVE...",
                            fontSize = 11.sp,
                            color = MarsRed,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Prompt Input Field
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = {
                    Text(
                        "Transmit operational status or question...",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("coach_input_field"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MarsRed,
                    unfocusedBorderColor = TacticalBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedContainerColor = TacticalSurfaceCard,
                    unfocusedContainerColor = TacticalSurfaceCard
                ),
                shape = RoundedCornerShape(10.dp),
                maxLines = 3,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (inputText.isNotBlank()) {
                            viewModel.sendCoachPrompt(inputText)
                            inputText = ""
                        }
                    }
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {
                    if (inputText.isNotBlank()) {
                        viewModel.sendCoachPrompt(inputText)
                        inputText = ""
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MarsRed)
                    .testTag("coach_send_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun QuickCommandChip(
    label: String,
    accentColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFF030712))
            .border(1.dp, accentColor.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = label.uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = accentColor,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
private fun CoachMessageItem(
    message: CoachMessage,
    onExecuteAction: (String) -> Unit
) {
    val isUser = message.sender == "USER"

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TacticalBadge(
                text = if (isUser) "OPERATOR (YOU)" else "TACTICAL COMMAND",
                accentColor = if (isUser) SaturnBlue else MarsRed
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = if (isUser) Color(0xFF1E293B) else Color(0xFF0F172A)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(if (isUser) 0.88f else 1.0f)
                .border(
                    1.dp,
                    if (isUser) SaturnBlue.copy(alpha = 0.4f) else MarsRed.copy(alpha = 0.4f),
                    RoundedCornerShape(10.dp)
                )
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = message.content,
                    fontSize = 13.sp,
                    color = TextPrimary,
                    lineHeight = 18.sp
                )

                if (!message.microTaskAction.isNullOrBlank() && !isUser) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { onExecuteAction(message.microTaskAction) },
                        colors = ButtonDefaults.buttonColors(containerColor = MarsRed),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("execute_coach_directive_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = message.microTaskAction.uppercase(),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }
    }
}
