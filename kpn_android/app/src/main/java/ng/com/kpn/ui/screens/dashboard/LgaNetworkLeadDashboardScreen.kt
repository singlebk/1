package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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

// ---------------------------------------------------------------------------
// Local composable helpers (scoped to this file)
// ---------------------------------------------------------------------------

@Composable
private fun LgaHeaderChip(lgaName: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = KpnPrimaryGreen.copy(alpha = 0.12f),
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        Text(
            text = "Your LGA: $lgaName",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium,
            color = KpnDarkGreen,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun LgaMetricCard(label: String, value: String, icon: ImageVector) {
    Card(
        modifier = Modifier
            .weight(1f)
            .height(90.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(icon, contentDescription = label, tint = KpnPrimaryGreen, modifier = Modifier.size(22.dp))
            Text(value, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = KpnDarkGreen)
            Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
    }
}

@Composable
private fun LgaActionCard(label: String, icon: ImageVector, onClick: () -> Unit = {}) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(14.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = label, tint = KpnPrimaryGreen, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = KpnDarkGreen
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Main screen
// ---------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LgaNetworkLeadDashboardScreen(lgaName: String = "Ikeja") {
    val actions = listOf(
        Pair("Pending Approvals", Icons.Default.HowToReg),
        Pair("Ward Reports", Icons.Default.Assessment),
        Pair("LGA Meetings", Icons.Default.Groups),
        Pair("Member List", Icons.Default.People)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "LGA Operations",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "LGA Network Lead",
                            style = MaterialTheme.typography.labelSmall,
                            color = KpnPrimaryGreen
                        )
                    }
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(38.dp)
                            .background(KpnPrimaryGreen, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Hub, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                LgaHeaderChip(lgaName = lgaName)
            }

            // Metrics Row
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    LgaMetricCard(label = "LGA Members", value = "1,240", icon = Icons.Default.People)
                    LgaMetricCard(label = "Wards Active", value = "12", icon = Icons.Default.LocationCity)
                }
            }

            // Section Header
            item {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = KpnDarkGreen
                )
            }

            // Action Grid
            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.heightIn(max = 400.dp)
                ) {
                    items(actions) { (label, icon) ->
                        LgaActionCard(label = label, icon = icon)
                    }
                }
            }

            // Pending Approvals Banner
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = KpnPrimaryGreen.copy(alpha = 0.08f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = KpnPrimaryGreen)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("5 member approvals pending", fontWeight = FontWeight.SemiBold, color = KpnDarkGreen)
                            Text("Review & approve to activate ward members", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}
