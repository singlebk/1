package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.ManageSearch
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
 * ROLE 35: Ward Administrative Officer Dashboard
 * Scope: Ward records management, meeting attendance, notice preparation, and filing.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WardAdminOfficerDashboardScreen(wardName: String = "Ikeja Ward 3") {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Ward Administration",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            "Ward Administrative Officer",
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
                "Records Summary",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ExecutiveStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Filed Records",
                    value = "41",
                    subtitle = "Up to date"
                )
                ExecutiveStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Pending Notices",
                    value = "5",
                    subtitle = "Needs action"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Admin Actions",
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
                        title = "Meeting Attendance",
                        count = "Log",
                        icon = Icons.Default.AssignmentTurnedIn,
                        color = KpnPrimaryGreen
                    )
                }
                item {
                    ActionCard(
                        title = "File Record",
                        count = "41",
                        icon = Icons.Default.CreateNewFolder,
                        color = Color(0xFF3B82F6)
                    )
                }
                item {
                    ActionCard(
                        title = "Draft Notice",
                        count = "5",
                        icon = Icons.Default.EditNote,
                        color = Color(0xFFF59E0B)
                    )
                }
                item {
                    ActionCard(
                        title = "Admin Log",
                        count = "View",
                        icon = Icons.Default.ManageSearch,
                        color = KpnDarkGreen
                    )
                }
            }
        }
    }
}
