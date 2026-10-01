package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Report
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
import ng.com.kpn.ui.theme.KpnPrimaryGreen

/**
 * ROLE 3: Vice President
 * Dashboard explicitly designed for Zonal Oversight, Disciplinary Review, and Operations.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VicePresidentDashboardScreen(
    viewModel: GenericViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Operations & Oversight", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text("Office of the Vice President", fontSize = 12.sp, color = KpnPrimaryGreen)
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
            
            Text("Zonal Coordination", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            
            // Oversight Metrics
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ExecutiveStatCard(Modifier.weight(1f), "Zonal Directors", "3/3", "All reporting")
                ExecutiveStatCard(Modifier.weight(1f), "LGA Coordinators", "21/21", "Fully active")
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            Text("Action Queue", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            
            // VP Actions Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    ActionCard(
                        title = "Disciplinary Cases",
                        count = "2",
                        icon = Icons.Default.Gavel,
                        color = Color(0xFFEF4444) // Red
                    )
                }
                item {
                    ActionCard(
                        title = "Operational Reports",
                        count = "5",
                        icon = Icons.Default.Report,
                        color = Color(0xFFF59E0B) // Yellow
                    )
                }
                item {
                    ActionCard(
                        title = "Zonal Map",
                        count = "View",
                        icon = Icons.Default.Map,
                        color = KpnPrimaryGreen
                    )
                }
                item {
                    ActionCard(
                        title = "Staff Directory",
                        count = "Search",
                        icon = Icons.Default.People,
                        color = Color(0xFF3B82F6) // Blue
                    )
                }
            }
        }
    }
}
