package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Announcement
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.MeetingRoom
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
 * ROLE 34: Ward Community Lead Dashboard
 * Scope: Ward-level member oversight, meetings, community reports, announcements.
 * Reports to LGA Network Lead.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WardCommunityLeadDashboardScreen(wardName: String = "Ikeja Ward 3") {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Ward Community Operations",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            "Ward Community Lead",
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
                "Ward Overview",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ExecutiveStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Ward Members",
                    value = "284",
                    subtitle = "+6 this week"
                )
                ExecutiveStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Meetings This Month",
                    value = "3",
                    subtitle = "Next: Friday"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Ward Actions",
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
                        title = "Ward Meetings",
                        count = "3",
                        icon = Icons.Default.MeetingRoom,
                        color = KpnPrimaryGreen
                    )
                }
                item {
                    ActionCard(
                        title = "Community Reports",
                        count = "7",
                        icon = Icons.Default.Description,
                        color = Color(0xFF3B82F6)
                    )
                }
                item {
                    ActionCard(
                        title = "Local Announcements",
                        count = "4",
                        icon = Icons.Default.Announcement,
                        color = Color(0xFFF59E0B)
                    )
                }
                item {
                    ActionCard(
                        title = "Member List",
                        count = "View",
                        icon = Icons.Default.Groups,
                        color = KpnDarkGreen
                    )
                }
            }
        }
    }
}
