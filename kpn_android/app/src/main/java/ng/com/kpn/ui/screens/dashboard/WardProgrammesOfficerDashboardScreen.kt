package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ng.com.kpn.ui.screens.dashboard.viewmodels.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ng.com.kpn.ui.theme.KpnDarkGreen
import ng.com.kpn.ui.theme.KpnPrimaryGreen

/**
 * ROLE 36: Ward Programmes Officer Dashboard
 * Scope: Ward-level programs, local events, participation tracking.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WardProgrammesOfficerDashboardScreen(wardName: String = "Ikeja Ward 3") {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Ward Programmes",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            "Ward Programmes Officer",
                            fontSize = 12.sp,
                            color = KpnPrimaryGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF1F2937)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF9FAFB))
                .padding(16.dp)
        ) {

            // Ward scope chip
            SuggestionChip(
                onClick = {},
                label = {
                    Text(
                        "Your Ward: $wardName",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = KpnDarkGreen
                    )
                },
                modifier = Modifier.padding(bottom = 16.dp),
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = KpnPrimaryGreen.copy(alpha = 0.12f)
                )
            )

            // Metrics Row
            Text(
                "Programme Overview",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ExecutiveStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Ward Programs",
                    value = "6",
                    subtitle = "2 active now"
                )
                ExecutiveStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Events Held",
                    value = "14",
                    subtitle = "This quarter"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Programme Actions",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    ActionCard(
                        title = "Create Program",
                        count = "New",
                        icon = Icons.Default.AddTask,
                        color = KpnPrimaryGreen
                    )
                }
                item {
                    ActionCard(
                        title = "Event Log",
                        count = "14",
                        icon = Icons.Default.Event,
                        color = Color(0xFF3B82F6)
                    )
                }
                item {
                    ActionCard(
                        title = "Participation",
                        count = "Track",
                        icon = Icons.Default.BarChart,
                        color = Color(0xFFF59E0B)
                    )
                }
                item {
                    ActionCard(
                        title = "Report Program",
                        count = "Submit",
                        icon = Icons.Default.Summarize,
                        color = KpnDarkGreen
                    )
                }
            }
        }
    }
}
