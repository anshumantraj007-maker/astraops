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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.StudySessionLogEntity
import com.example.data.model.SyllabusCoverageEntity
import com.example.ui.AstraScreen
import com.example.ui.AstraViewModel
import com.example.ui.components.TacticalBadge
import com.example.ui.theme.BudhadityaGold
import com.example.ui.theme.KetuPurple
import com.example.ui.theme.MarsRed
import com.example.ui.theme.SaturnBlue
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TacticalBorder
import com.example.ui.theme.TacticalObsidian
import com.example.ui.theme.TacticalSurfaceCard
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SyllabusCoverageScreen(
    viewModel: AstraViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateTo(AstraScreen.COMMAND_DASHBOARD)
    }

    val selectedExam by viewModel.selectedCoverageExam.collectAsState()
    val apfcCoverage by viewModel.apfcCoverage.collectAsState()
    val roAroCoverage by viewModel.roAroCoverage.collectAsState()
    val sessionLogs by viewModel.studySessionLogs.collectAsState()

    val currentCoverageList = if (selectedExam == "UPSC APFC") apfcCoverage else roAroCoverage
    val filteredLogs = sessionLogs.filter { it.examType == selectedExam }

    var selectedTab by remember { mutableIntStateOf(0) } // 0: Coverage Modules, 1: Session Logs
    var showLogSessionDialog by remember { mutableStateOf(false) }
    var targetModuleForLog by remember { mutableStateOf<SyllabusCoverageEntity?>(null) }

    // Aggregate statistics
    val totalEstimatedHours = currentCoverageList.sumOf { it.totalEstimatedHours.toDouble() }.toFloat()
    val totalCompletedHours = currentCoverageList.sumOf { it.completedHours.toDouble() }.toFloat()
    val overallProgressPercent = if (totalEstimatedHours > 0) ((totalCompletedHours / totalEstimatedHours) * 100).toInt() else 0
    val masteredCount = currentCoverageList.count { it.status == "MASTERED" }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(TacticalObsidian)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Bar
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.navigateTo(AstraScreen.COMMAND_DASHBOARD) },
                    modifier = Modifier.testTag("coverage_back_button")
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
                        text = "SYLLABUS COVERAGE & SESSION LOGS",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = SaturnBlue,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Room Persistence Engine • UPSC APFC & HC RO/ARO",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        // Exam Selector Chips (UPSC APFC vs Allahabad HC RO/ARO)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FilterChip(
                    selected = selectedExam == "UPSC APFC",
                    onClick = { viewModel.setCoverageExamFilter("UPSC APFC") },
                    label = {
                        Text(
                            text = "UPSC APFC (DEC 20, 2026)",
                            fontWeight = if (selectedExam == "UPSC APFC") FontWeight.Bold else FontWeight.Normal,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MarsRed.copy(alpha = 0.2f),
                        selectedLabelColor = MarsRed,
                        containerColor = Color(0xFF030712),
                        labelColor = TextSecondary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f).testTag("filter_apfc")
                )

                FilterChip(
                    selected = selectedExam == "Allahabad HC RO/ARO",
                    onClick = { viewModel.setCoverageExamFilter("Allahabad HC RO/ARO") },
                    label = {
                        Text(
                            text = "ALLAHABAD HC RO/ARO",
                            fontWeight = if (selectedExam == "Allahabad HC RO/ARO") FontWeight.Bold else FontWeight.Normal,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SaturnBlue.copy(alpha = 0.2f),
                        selectedLabelColor = SaturnBlue,
                        containerColor = Color(0xFF030712),
                        labelColor = TextSecondary
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f).testTag("filter_roaro")
                )
            }
        }

        // Overall Progress Metrics Card
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
                        Text(
                            text = "$selectedExam TELEMETRY",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedExam == "UPSC APFC") MarsRed else SaturnBlue,
                            fontFamily = FontFamily.Monospace
                        )
                        TacticalBadge(
                            text = "$masteredCount / ${currentCoverageList.size} MASTERED",
                            accentColor = SuccessGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Syllabus Time Coverage", fontSize = 12.sp, color = TextPrimary)
                        Text(
                            String.format(Locale.US, "%.1fh / %.1fh (%d%%)", totalCompletedHours, totalEstimatedHours, overallProgressPercent),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = BudhadityaGold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { if (totalEstimatedHours > 0) (totalCompletedHours / totalEstimatedHours).coerceIn(0f, 1f) else 0f },
                        modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                        color = if (selectedExam == "UPSC APFC") MarsRed else SaturnBlue,
                        trackColor = Color(0xFF030712)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total Sessions Logged: ${filteredLogs.size}",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )

                        Button(
                            onClick = {
                                targetModuleForLog = currentCoverageList.firstOrNull()
                                showLogSessionDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MarsRed),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(36.dp).testTag("quick_log_session_button")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("LOG STUDY SESSION", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Section Tabs: 0 -> Syllabus Modules, 1 -> Session Logs Feed
        item {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color(0xFF030712),
                contentColor = TextPrimary,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = if (selectedExam == "UPSC APFC") MarsRed else SaturnBlue,
                        height = 3.dp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, TacticalBorder, RoundedCornerShape(8.dp))
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Text(
                            text = "SYLLABUS MODULES (${currentCoverageList.size})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = {
                        Text(
                            text = "SESSION LOGS (${filteredLogs.size})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                )
            }
        }

        // View Content based on Tab
        if (selectedTab == 0) {
            items(currentCoverageList) { module ->
                SyllabusModuleCard(
                    module = module,
                    onDeploy = {
                        viewModel.setDeploymentDetails(
                            exam = module.examType,
                            subject = module.subject,
                            topic = module.topicName,
                            moduleCode = module.moduleCode
                        )
                        viewModel.navigateTo(AstraScreen.DEPLOYMENT_TIMER)
                    },
                    onLogSession = {
                        targetModuleForLog = module
                        showLogSessionDialog = true
                    },
                    onIncrementRevision = {
                        viewModel.incrementModuleRevision(module.id)
                    }
                )
            }
        } else {
            if (filteredLogs.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No study sessions logged for $selectedExam yet. Click 'LOG STUDY SESSION' to record your first operational deployment.",
                            fontSize = 12.sp,
                            color = TextMuted,
                            lineHeight = 16.sp
                        )
                    }
                }
            } else {
                items(filteredLogs) { log ->
                    StudySessionLogCard(log = log)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    // Modal Dialog to Log Study Session Directly
    if (showLogSessionDialog && targetModuleForLog != null) {
        val module = targetModuleForLog!!
        var durationInput by remember { mutableStateOf("60") }
        var pyqAttemptedInput by remember { mutableStateOf("20") }
        var pyqCorrectInput by remember { mutableStateOf("18") }
        var notesInput by remember { mutableStateOf("") }
        var selectedCognitiveState by remember { mutableStateOf("SATURN_STEADY_GRIND") }

        AlertDialog(
            onDismissRequest = { showLogSessionDialog = false },
            containerColor = TacticalSurfaceCard,
            title = {
                Text(
                    text = "RECORD STUDY SESSION",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MarsRed,
                    fontFamily = FontFamily.Monospace
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Module: [${module.moduleCode}] ${module.topicName}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    OutlinedTextField(
                        value = durationInput,
                        onValueChange = { durationInput = it },
                        label = { Text("Duration (Minutes)") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MarsRed,
                            unfocusedBorderColor = TacticalBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("log_duration_input")
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = pyqAttemptedInput,
                            onValueChange = { pyqAttemptedInput = it },
                            label = { Text("PYQs Attempted") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MarsRed,
                                unfocusedBorderColor = TacticalBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            modifier = Modifier.weight(1f).testTag("log_pyq_attempted_input")
                        )

                        OutlinedTextField(
                            value = pyqCorrectInput,
                            onValueChange = { pyqCorrectInput = it },
                            label = { Text("PYQs Correct") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MarsRed,
                                unfocusedBorderColor = TacticalBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            modifier = Modifier.weight(1f).testTag("log_pyq_correct_input")
                        )
                    }

                    OutlinedTextField(
                        value = notesInput,
                        onValueChange = { notesInput = it },
                        label = { Text("Tactical Notes / Invariants Mastered") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MarsRed,
                            unfocusedBorderColor = TacticalBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("log_notes_input")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val duration = durationInput.toIntOrNull() ?: 45
                        val attempted = pyqAttemptedInput.toIntOrNull() ?: 15
                        val correct = pyqCorrectInput.toIntOrNull() ?: 13
                        viewModel.logStudySessionDirectly(
                            examType = module.examType,
                            subject = module.subject,
                            topic = module.topicName,
                            moduleCode = module.moduleCode,
                            durationMinutes = duration,
                            pyqsAttempted = attempted,
                            pyqsCorrect = correct,
                            cognitiveState = selectedCognitiveState,
                            notes = notesInput
                        )
                        showLogSessionDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MarsRed),
                    modifier = Modifier.testTag("confirm_log_session_button")
                ) {
                    Text("PERSIST TO ROOM", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showLogSessionDialog = false }) {
                    Text("CANCEL", color = TextSecondary)
                }
            }
        )
    }
}

@Composable
private fun SyllabusModuleCard(
    module: SyllabusCoverageEntity,
    onDeploy: () -> Unit,
    onLogSession: () -> Unit,
    onIncrementRevision: () -> Unit
) {
    val statusColor = when (module.status) {
        "MASTERED" -> SuccessGreen
        "PYQ_PRACTICE" -> BudhadityaGold
        "IN_PROGRESS" -> SaturnBlue
        else -> TextMuted
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = TacticalSurfaceCard),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, TacticalBorder, RoundedCornerShape(10.dp))
            .testTag("module_card_${module.moduleCode}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TacticalBadge(
                        text = module.moduleCode,
                        accentColor = if (module.examType == "UPSC APFC") MarsRed else SaturnBlue
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = module.subject,
                        fontSize = 11.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }
                TacticalBadge(
                    text = module.status,
                    accentColor = statusColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = module.topicName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Time & Mastery Progress Bars
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Time: ${module.completedHours}h / ${module.totalEstimatedHours}h",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "Mastery: ${module.masteryPercentage}%",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusColor,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { (module.masteryPercentage / 100f).coerceIn(0f, 1f) },
                modifier = Modifier.fillMaxWidth().height(5.dp).clip(CircleShape),
                color = statusColor,
                trackColor = Color(0xFF030712)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PYQs: ${module.pyqCompletedCount} / ${module.pyqTargetCount} | Rev: ${module.revisionCycleCount}",
                    fontSize = 11.sp,
                    color = BudhadityaGold,
                    fontFamily = FontFamily.Monospace
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    IconButton(
                        onClick = onIncrementRevision,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF030712))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Increment Revision",
                            tint = SaturnBlue,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Button(
                        onClick = onDeploy,
                        colors = ButtonDefaults.buttonColors(containerColor = MarsRed),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("DEPLOY", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun StudySessionLogCard(log: StudySessionLogEntity) {
    val dateStr = SimpleDateFormat("MMM dd, HH:mm", Locale.US).format(Date(log.timestamp))

    Card(
        colors = CardDefaults.cardColors(containerColor = TacticalSurfaceCard),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, TacticalBorder, RoundedCornerShape(10.dp))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TacticalBadge(
                        text = "${log.durationMinutes}m",
                        accentColor = MarsRed
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = log.subject,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SaturnBlue
                    )
                }
                Text(
                    text = dateStr,
                    fontSize = 10.sp,
                    color = TextMuted,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = log.topic,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "PYQs: ${log.pyqsCorrect} / ${log.pyqsAttempted} (${log.eliminationAccuracy.toInt()}% Acc)",
                    fontSize = 11.sp,
                    color = BudhadityaGold,
                    fontFamily = FontFamily.Monospace
                )
                TacticalBadge(
                    text = log.cognitiveState.replace("_", " "),
                    accentColor = when (log.cognitiveState) {
                        "PEAK_MOMENTUM" -> MarsRed
                        "MOON_FOG_BYPASS" -> KetuPurple
                        else -> SaturnBlue
                    }
                )
            }

            if (log.tacticalNotes.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Notes: ${log.tacticalNotes}",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    lineHeight = 15.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }
    }
}
