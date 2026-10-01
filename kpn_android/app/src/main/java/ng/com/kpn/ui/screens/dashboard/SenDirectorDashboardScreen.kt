package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Announcement
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Map
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
 * ROLE 21: Senatorial Zone Operations Dashboard (ZONAL)
 * For the Senatorial Director.
 * Covers all LGAs in zone, zonal activity coordination,
 * reporting to state leadership, and zonal member management.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SenDirectorDashboardScreen(
    viewModel: ZonalViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Senatorial Zone Operations",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            "Senatorial Director",
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

            // Zone badge chip
            Surface(
                shape = RoundedCornerShape(50),
                color = KpnPrimaryGreen.copy(alpha = 0.12f),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Map,
                        contentDescription = null,
                        tint = KpnDarkGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        "Your Zone: Central Senatorial Zone",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = KpnDarkGreen
                    )
                }
            }

            Text(
                "Zonal Overview",
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
                    title = "LGAs in Zone",
                    value = "9",
                    subtitle = "All active"
                )
                ExecutiveStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Zonal Members",
                    value = "4,230",
                    subtitle = "+78 this month"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Zonal Actions",
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
                        title = "Zone LGAs",
                        count = "9",
                        icon = Icons.Default.Map,
                        color = KpnPrimaryGreen
                    )
                }
                item {
                    ActionCard(
                        title = "Member Overview",
                        count = "4,230",
                        icon = Icons.Default.Groups,
                        color = Color(0xFF3B82F6)
                    )
                }
                item {
                    ActionCard(
                        title = "Zonal Reports",
                        count = "7",
                        icon = Icons.Default.Assessment,
                        color = Color(0xFFF59E0B)
                    )
                }
                item {
                    ActionCard(
                        title = "Zone Announcements",
                        count = "3",
                        icon = Icons.Default.Announcement,
                        color = Color(0xFF8B5CF6)
                    )
                }
            }
        }
    }
}
