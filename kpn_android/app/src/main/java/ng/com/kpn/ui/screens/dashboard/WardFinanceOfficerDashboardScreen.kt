package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Receipt
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
fun WardFinanceOfficerDashboardScreen(
    viewModel: WardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Ward Finance", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text("Ward Finance Officer", fontSize = 12.sp, color = KpnPrimaryGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White, titleContentColor = Color(0xFF1F2937))
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF9FAFB)).padding(16.dp)) {
            WardJurisdictionChip("Your Ward")
            Spacer(modifier = Modifier.height(12.dp))
            
            // Financial Summary
            Surface(color = KpnPrimaryGreen, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                    Text("Ward Balance", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    Text("₦ 45,200", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Dues: ₦50k", color = Color.White, fontSize = 11.sp)
                        Text("Expenses: ₦4.8k", color = Color.White, fontSize = 11.sp)
                    }
                }
            }
            
            LazyVerticalGrid(columns = GridCells.Fixed(2), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item { WardFinActionCard("Collect Dues", "Log member dues", Icons.Default.Payments, KpnPrimaryGreen) }
                item { WardFinActionCard("Log Expense", "Ward petty cash", Icons.Default.AccountBalanceWallet, Color(0xFFEF4444)) }
                item { WardFinActionCard("Ward Receipts", "View history", Icons.Default.Receipt, Color(0xFF3B82F6)) }
                item { WardFinActionCard("Report to LGA", "Submit summary", Icons.Default.Assessment, Color(0xFF8B5CF6)) }
            }
        }
    }
}

@Composable
fun WardJurisdictionChip(label: String) {
    Surface(color = KpnPrimaryGreen.copy(alpha = 0.1f), shape = RoundedCornerShape(20.dp)) {
        Text("$label: [Ward Name]", color = KpnPrimaryGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WardFinActionCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
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
