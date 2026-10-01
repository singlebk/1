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
// Local composable helpers
// ---------------------------------------------------------------------------

@Composable
private fun FinanceLgaChip(lgaName: String) {
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
private fun FinanceActionCard(label: String, icon: ImageVector, onClick: () -> Unit = {}) {
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

@Composable
private fun FinanceBalanceRow(label: String, amount: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        Text(amount, fontWeight = FontWeight.Bold, color = color, fontSize = 16.sp)
    }
}

// ---------------------------------------------------------------------------
// Main screen
// ---------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LgaFinanceOfficerDashboardScreen(lgaName: String = "Ikeja") {
    val actions = listOf(
        Pair("Log Income", Icons.Default.TrendingUp),
        Pair("Log Expense", Icons.Default.TrendingDown),
        Pair("LGA Receipts", Icons.Default.Receipt),
        Pair("Financial Report", Icons.Default.BarChart)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "LGA Finance",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "LGA Finance Officer",
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
                        Icon(Icons.Default.AccountBalance, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
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
                FinanceLgaChip(lgaName = lgaName)
            }

            // Balance Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = KpnDarkGreen),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("LGA Financial Summary", color = Color.White.copy(alpha = 0.75f), style = MaterialTheme.typography.labelMedium)
                        Text("₦ 482,500", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 30.sp)
                        Text("Current LGA Balance", color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.labelSmall)
                        Divider(color = Color.White.copy(alpha = 0.2f))
                        FinanceBalanceRow(label = "Total Income", amount = "₦ 750,000", color = Color(0xFF66BB6A))
                        FinanceBalanceRow(label = "Total Expenses", amount = "₦ 267,500", color = Color(0xFFEF5350))
                    }
                }
            }

            // Section Header
            item {
                Text(
                    text = "Finance Actions",
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
                        FinanceActionCard(label = label, icon = icon)
                    }
                }
            }

            // Recent Transactions
            item {
                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = KpnDarkGreen
                )
            }

            item {
                val transactions = listOf(
                    Triple("Membership Dues - Ward 5", "+₦ 45,000", true),
                    Triple("Office Supplies", "-₦ 8,200", false),
                    Triple("LGA Meeting Logistics", "-₦ 12,500", false),
                    Triple("Ward 7 Contribution", "+₦ 30,000", true)
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    transactions.forEach { (desc, amount, isIncome) ->
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    if (isIncome) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                                    contentDescription = null,
                                    tint = if (isIncome) Color(0xFF388E3C) else Color(0xFFD32F2F),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(desc, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                                Text(
                                    amount,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isIncome) Color(0xFF388E3C) else Color(0xFFD32F2F),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}
