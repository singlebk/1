package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ng.com.kpn.ui.screens.dashboard.viewmodels.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ng.com.kpn.ui.theme.KpnDarkGreen
import ng.com.kpn.ui.theme.KpnPrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceOperationsOfficerDashboardScreen(
    viewModel: FinanceViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Finance Operations", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text("Finance Operations Officer", fontSize = 12.sp, color = KpnPrimaryGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White, titleContentColor = Color(0xFF1F2937))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF9FAFB)).padding(16.dp)
        ) {
            // Operations summary
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FinOpsStatCard(Modifier.weight(1f), "Pending Receipts", "7", Color(0xFFF59E0B))
                FinOpsStatCard(Modifier.weight(1f), "Processed Today", "14", KpnPrimaryGreen)
            }
            Text("Daily Operations", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            LazyVerticalGrid(columns = GridCells.Fixed(2), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item { FinOpsActionCard("Log Receipt", "Record income", Icons.Default.Receipt, KpnPrimaryGreen) }
                item { FinOpsActionCard("Process Payment", "Outgoing funds", Icons.Default.RemoveShoppingCart, Color(0xFFEF4444)) }
                item { FinOpsActionCard("Petty Cash", "Manage float", Icons.Default.AccountBalance, Color(0xFF3B82F6)) }
                item { FinOpsActionCard("Expense Claim", "Submit claim", Icons.Default.AddCard, Color(0xFF8B5CF6)) }
            }
        }
    }
}

@Composable
private fun FinOpsStatCard(modifier: Modifier, title: String, value: String, color: Color) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, color = Color.Gray, fontSize = 12.sp)
            Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = color, modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FinOpsActionCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
    Card(onClick = {}, modifier = Modifier.fillMaxWidth().height(120.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp), shape = RoundedCornerShape(12.dp)) {
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
