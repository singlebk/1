package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Announcement
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ng.com.kpn.ui.screens.dashboard.viewmodels.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ng.com.kpn.ui.theme.KpnDarkGreen
import ng.com.kpn.ui.theme.KpnPrimaryGreen

/**
 * ROLE 23: Zonal Communications Dashboard (ZONAL)
 * For the Senatorial Communications Officer.
 * Covers zone-level communications, announcements to LGAs,
 * social media coordination, and zonal press releases.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SenCommsOfficerDashboardScreen(
    viewModel: ZonalViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Zonal Communications",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            "Senatorial Communications Officer",
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

            Text(
                "Communications Overview",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Metrics Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ExecutiveStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Zone Announcements",
                    value = "15",
                    subtitle = "Sent this month"
                )
                ExecutiveStatCard(
                    modifier = Modifier.weight(1f),
                    title = "LGAs Reached",
                    value = "9/9",
                    subtitle = "100% coverage"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Communications Actions",
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
                        title = "Zone Announcement",
                        count = "+",
                        icon = Icons.Default.Announcement,
                        color = KpnPrimaryGreen
                    )
                }
                item {
                    ActionCard(
                        title = "LGA Broadcast",
                        count = "9",
                        icon = Icons.Default.Campaign,
                        color = Color(0xFF3B82F6)
                    )
                }
                item {
                    ActionCard(
                        title = "Social Post",
                        count = "22",
                        icon = Icons.Default.Share,
                        color = Color(0xFFF59E0B)
                    )
                }
                item {
                    ActionCard(
                        title = "Media Report",
                        count = "4",
                        icon = Icons.Default.BarChart,
                        color = Color(0xFF8B5CF6)
                    )
                }
            }
        }
    }
}
