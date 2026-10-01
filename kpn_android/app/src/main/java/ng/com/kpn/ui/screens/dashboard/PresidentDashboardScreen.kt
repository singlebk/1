package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Approval
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ng.com.kpn.ui.screens.dashboard.viewmodels.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ng.com.kpn.ui.theme.KpnDarkGreen
import ng.com.kpn.ui.theme.KpnPrimaryGreen

/**
 * PHASE 6: ROLE DASHBOARD (President)
 * Designed explicitly for Executive Oversight. 
 * Replaces the incorrect visual mockup.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PresidentDashboardScreen(
    viewModel: GenericViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Executive Dashboard", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text("Office of the President", fontSize = 12.sp, color = KpnPrimaryGreen)
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
            
            Text("Network Health", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            
            // Executive Metrics
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ExecutiveStatCard(Modifier.weight(1f), "Total Members", "12,450", "+45 this week")
                ExecutiveStatCard(Modifier.weight(1f), "Active Zones", "3/3", "100% operational")
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            Text("Executive Actions", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            
            // Action Grid (No long lists!)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    ActionCard(
                        title = "Pending Approvals",
                        count = "12",
                        icon = Icons.Default.Approval,
                        color = Color(0xFFF59E0B)
                    )
                }
                item {
                    ActionCard(
                        title = "Audit Reports",
                        count = "3",
                        icon = Icons.Default.Assessment,
                        color = Color(0xFF3B82F6)
                    )
                }
                item {
                    ActionCard(
                        title = "Disciplinary Cases",
                        count = "1",
                        icon = Icons.Default.Warning,
                        color = Color(0xFFEF4444)
                    )
                }
                item {
                    ActionCard(
                        title = "Leadership Directory",
                        count = "View",
                        icon = Icons.Default.Group,
                        color = KpnPrimaryGreen
                    )
                }
            }
        }
    }
}

@Composable
fun ExecutiveStatCard(modifier: Modifier = Modifier, title: String, value: String, subtitle: String) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, color = Color.Gray, fontSize = 12.sp)
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = KpnDarkGreen, modifier = Modifier.padding(vertical = 4.dp))
            Text(subtitle, color = KpnPrimaryGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionCard(title: String, count: String, icon: ImageVector, color: Color) {
    Card(
        onClick = { /* TODO: Navigate to respective queue */ },
        modifier = Modifier.fillMaxWidth().height(120.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Surface(
                    color = color.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(icon, contentDescription = null, tint = color, modifier = Modifier.padding(8.dp).size(24.dp))
                }
                Text(count, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
            }
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1F2937))
        }
    }
}
