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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.domain.syllabus.SyllabusRepository
import com.example.ui.AstraScreen
import com.example.ui.AstraViewModel
import com.example.ui.components.CodeSyntaxBlock
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
fun SyllabusSyntaxScreen(
    viewModel: AstraViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateTo(AstraScreen.COMMAND_DASHBOARD)
    }

    val selectedModule by viewModel.selectedModule.collectAsState()
    val modules = SyllabusRepository.MODULES

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
                    modifier = Modifier.testTag("syntax_back_button")
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
                        text = "IT-SYNTAX & LOGIC TREES",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = SaturnBlue,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "BCA Cognitive Framework: Law & Accounting as Code",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            }
        }

        // Module Tabs Horizontal Scroll
        item {
            val hScroll = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(hScroll),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                modules.forEach { module ->
                    val isSelected = module.id == selectedModule.id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isSelected) SaturnBlue.copy(alpha = 0.2f) else TacticalSurfaceCard
                            )
                            .border(
                                1.dp,
                                if (isSelected) SaturnBlue else TacticalBorder,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { viewModel.selectModule(module) }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .testTag("module_tab_${module.id}")
                    ) {
                        Column {
                            Text(
                                text = module.exam,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (module.exam == "UPSC APFC") MarsRed else SaturnBlue,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                text = module.subject,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) TextPrimary else TextSecondary
                            )
                        }
                    }
                }
            }
        }

        // Active Module Title Card
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
                            text = selectedModule.exam,
                            accentColor = if (selectedModule.exam == "UPSC APFC") MarsRed else SaturnBlue
                        )
                        TacticalBadge(
                            text = "HIGH YIELD SYNTAX",
                            accentColor = BudhadityaGold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = selectedModule.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Formula: ${selectedModule.executionFormula}",
                        fontSize = 11.sp,
                        color = BudhadityaGold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        // Code Syntax Container
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ALGORITHMIC SPECIFICATION (KOTLIN/PSEUDO-CODE)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SaturnBlue,
                        fontFamily = FontFamily.Monospace
                    )
                    Icon(
                        imageVector = Icons.Default.Code,
                        contentDescription = null,
                        tint = SaturnBlue,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                CodeSyntaxBlock(
                    codeText = selectedModule.pseudoCode,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Logic Trees Flowchart Nodes
        item {
            Text(
                text = "CONDITIONAL LOGIC GATES & DECISION TREE",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                fontFamily = FontFamily.Monospace
            )
        }

        items(selectedModule.logicFlowchart) { node ->
            Card(
                colors = CardDefaults.cardColors(containerColor = TacticalSurfaceCard),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, TacticalBorder, RoundedCornerShape(10.dp))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TacticalBadge(
                            text = "IF CONDITION",
                            accentColor = SaturnBlue
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = node.condition,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.Top) {
                        TacticalBadge(
                            text = "THEN",
                            accentColor = BudhadityaGold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = node.result,
                            fontSize = 12.sp,
                            color = TextSecondary,
                            lineHeight = 16.sp
                        )
                    }

                    if (node.exception != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Exception: ${node.exception}",
                            fontSize = 11.sp,
                            color = TextMuted,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    }

                    if (node.pyqTrap != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(MarsRed.copy(alpha = 0.1f))
                                .border(1.dp, MarsRed.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = node.pyqTrap,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MarsRed
                            )
                        }
                    }
                }
            }
        }

        // Action: Load into Deployment Timer
        item {
            Button(
                onClick = {
                    viewModel.setDeploymentDetails(
                        exam = selectedModule.exam,
                        subject = selectedModule.subject,
                        topic = selectedModule.title
                    )
                    viewModel.navigateTo(AstraScreen.DEPLOYMENT_TIMER)
                },
                colors = ButtonDefaults.buttonColors(containerColor = MarsRed),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("deploy_module_button")
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "DEPLOY THIS MODULE IN EXECUTION TIMER",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
