package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AstraScreen
import com.example.ui.AstraViewModel
import com.example.ui.components.TacticalBadge
import com.example.ui.screens.AstroBlueprintScreen
import com.example.ui.screens.CoachTerminalScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DeploymentTimerScreen
import com.example.ui.screens.NoGuessProtocolScreen
import com.example.ui.screens.SyllabusSyntaxScreen
import com.example.ui.theme.BudhadityaGold
import com.example.ui.theme.MarsRed
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SaturnBlue
import com.example.ui.theme.TacticalBorder
import com.example.ui.theme.TacticalObsidian
import com.example.ui.theme.TacticalSurfaceCard
import com.example.ui.theme.TacticalSurfaceDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

class MainActivity : ComponentActivity() {

    private val viewModel: AstraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AstraApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstraApp(viewModel: AstraViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = TacticalObsidian,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(MarsRed),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ASTRAOPS",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            color = TextPrimary,
                            letterSpacing = 1.5.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TacticalBadge(
                            text = "MOON-MARS",
                            accentColor = MarsRed
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.navigateTo(AstraScreen.ASTRO_BLUEPRINT) },
                        modifier = Modifier.testTag("top_bar_blueprint_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Fingerprint,
                            contentDescription = "Astrological Blueprint",
                            tint = if (currentScreen == AstraScreen.ASTRO_BLUEPRINT) BudhadityaGold else TextSecondary
                        )
                    }
                    IconButton(
                        onClick = { viewModel.navigateTo(AstraScreen.COACH_TERMINAL) },
                        modifier = Modifier.testTag("top_bar_coach_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Terminal,
                            contentDescription = "Tactical Coach Terminal",
                            tint = if (currentScreen == AstraScreen.COACH_TERMINAL) MarsRed else TextSecondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TacticalSurfaceDark,
                    titleContentColor = TextPrimary
                ),
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .border(0.5.dp, TacticalBorder)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = TacticalSurfaceDark,
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .border(0.5.dp, TacticalBorder)
                    .height(68.dp)
            ) {
                NavBarItem(
                    screen = AstraScreen.COMMAND_DASHBOARD,
                    label = "Ops Radar",
                    icon = Icons.Default.Dashboard,
                    isSelected = currentScreen == AstraScreen.COMMAND_DASHBOARD,
                    onClick = { viewModel.navigateTo(AstraScreen.COMMAND_DASHBOARD) },
                    testTag = "nav_dashboard"
                )
                NavBarItem(
                    screen = AstraScreen.DEPLOYMENT_TIMER,
                    label = "Execution",
                    icon = Icons.Default.Timer,
                    isSelected = currentScreen == AstraScreen.DEPLOYMENT_TIMER,
                    onClick = { viewModel.navigateTo(AstraScreen.DEPLOYMENT_TIMER) },
                    testTag = "nav_timer"
                )
                NavBarItem(
                    screen = AstraScreen.SYLLABUS_SYNTAX,
                    label = "IT Trees",
                    icon = Icons.Default.Code,
                    isSelected = currentScreen == AstraScreen.SYLLABUS_SYNTAX,
                    onClick = { viewModel.navigateTo(AstraScreen.SYLLABUS_SYNTAX) },
                    testTag = "nav_syntax"
                )
                NavBarItem(
                    screen = AstraScreen.NO_GUESS_PROTOCOL,
                    label = "No-Guess",
                    icon = Icons.Default.Shield,
                    isSelected = currentScreen == AstraScreen.NO_GUESS_PROTOCOL,
                    onClick = { viewModel.navigateTo(AstraScreen.NO_GUESS_PROTOCOL) },
                    testTag = "nav_noguess"
                )
                NavBarItem(
                    screen = AstraScreen.COACH_TERMINAL,
                    label = "Coach",
                    icon = Icons.Default.Terminal,
                    isSelected = currentScreen == AstraScreen.COACH_TERMINAL,
                    onClick = { viewModel.navigateTo(AstraScreen.COACH_TERMINAL) },
                    testTag = "nav_coach"
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ScreenTransition"
            ) { screen ->
                when (screen) {
                    AstraScreen.COMMAND_DASHBOARD -> DashboardScreen(viewModel = viewModel)
                    AstraScreen.DEPLOYMENT_TIMER -> DeploymentTimerScreen(viewModel = viewModel)
                    AstraScreen.SYLLABUS_SYNTAX -> SyllabusSyntaxScreen(viewModel = viewModel)
                    AstraScreen.NO_GUESS_PROTOCOL -> NoGuessProtocolScreen(viewModel = viewModel)
                    AstraScreen.COACH_TERMINAL -> CoachTerminalScreen(viewModel = viewModel)
                    AstraScreen.ASTRO_BLUEPRINT -> AstroBlueprintScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
private fun androidx.compose.foundation.layout.RowScope.NavBarItem(
    screen: AstraScreen,
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    NavigationBarItem(
        selected = isSelected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(20.dp)
            )
        },
        label = {
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontFamily = FontFamily.Monospace
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MarsRed,
            selectedTextColor = MarsRed,
            indicatorColor = MarsRed.copy(alpha = 0.15f),
            unselectedIconColor = TextMuted,
            unselectedTextColor = TextMuted
        ),
        modifier = Modifier.testTag(testTag)
    )
}
