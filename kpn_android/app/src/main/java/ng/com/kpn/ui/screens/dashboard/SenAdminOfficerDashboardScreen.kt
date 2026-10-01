package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.NotificationsActive
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
 * ROLE 22: Zonal Administration Dashboard (ZONAL)
 * For the Senatorial Administrative Officer.
 * Covers zonal record-keeping, documentation, correspondence,
 * and administrative support for the Senatorial Director.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SenAdminOfficerDashboardScreen(
    viewModel: ZonalViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Zonal Administration",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            "Senatorial Administrative Officer",
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
                "Administrative Overview",
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
                    title = "Documents Filed",
                    value = "142",
                    subtitle = "In records system"
                )
                ExecutiveStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Correspondence Pending",
                    value = "6",
                    subtitle = "Awaiting dispatch"
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
                        title = "File Document",
                        count = "+",
                        icon = Icons.Default.CreateNewFolder,
                        color = KpnPrimaryGreen
                    )
                }
                item {
                    ActionCard(
                        title = "Draft Letter",
                        count = "6",
                        icon = Icons.Default.Description,
                        color = Color(0xFF3B82F6)
                    )
                }
                item {
                    ActionCard(
                        title = "Admin Records",
                        count = "142",
                        icon = Icons.Default.Folder,
                        color = Color(0xFFF59E0B)
                    )
                }
                item {
                    ActionCard(
                        title = "Zone Notices",
                        count = "4",
                        icon = Icons.Default.NotificationsActive,
                        color = Color(0xFFEF4444)
                    )
                }
            }
        }
    }
}
