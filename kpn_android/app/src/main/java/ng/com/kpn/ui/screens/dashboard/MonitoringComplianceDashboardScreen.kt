package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.WarningAmber
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonitoringComplianceDashboardScreen(
    viewModel: GenericViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Monitoring & Compliance", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text("Director of Monitoring & Compliance", fontSize = 12.sp, color = KpnPrimaryGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White, titleContentColor = Color(0xFF1F2937))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF9FAFB)).padding(16.dp)
        ) {
            Text("Network Compliance Overview", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ComplianceStatCard(Modifier.weight(1f), "Zones Compliant", "3/3", "All reporting")
                ComplianceStatCard(Modifier.weight(1f), "Wards Reporting", "189/225", "84% active")
            }
            Text("Compliance Actions", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            LazyVerticalGrid(columns = GridCells.Fixed(2), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item { ComplianceActionCard("Compliance Reports", "12 pending", Icons.Default.Report, Color(0xFFF59E0B)) }
                item { ComplianceActionCard("Performance Tracker", "View trends", Icons.Default.Policy, KpnPrimaryGreen) }
                item { ComplianceActionCard("Escalation Queue", "3 flagged", Icons.Default.WarningAmber, Color(0xFFEF4444)) }
                item { ComplianceActionCard("Field Reports", "Submit / Review", Icons.Default.Gavel, Color(0xFF3B82F6)) }
            }
        }
    }
}

@Composable
private fun ComplianceStatCard(modifier: Modifier, title: String, value: String, subtitle: String) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, color = Color.Gray, fontSize = 12.sp)
            Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937), modifier = Modifier.padding(vertical = 4.dp))
            Text(subtitle, color = KpnPrimaryGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ComplianceActionCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
    Card(
        onClick = {},
        modifier = Modifier.fillMaxWidth().height(120.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Surface(color = color.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.padding(8.dp).size(24.dp))
            }
            Column {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF1F2937))
                Text(subtitle, fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}
