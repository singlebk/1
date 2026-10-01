package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ng.com.kpn.ui.theme.KpnDarkGreen
import ng.com.kpn.ui.theme.KpnPrimaryGreen

// ---------------------------------------------------------------------------
// Local composable helpers
// ---------------------------------------------------------------------------

@Composable
private fun TreasurerStatCard(
    label: String,
    value: String,
    icon: ImageVector,
    accentColor: Color = KpnPrimaryGreen,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(90.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = accentColor.copy(alpha = 0.10f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(22.dp))
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = KpnDarkGreen)
            Text(text = label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun TreasurerActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(KpnPrimaryGreen.copy(alpha = 0.12f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = KpnPrimaryGreen, modifier = Modifier.size(24.dp))
            }
            Column {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                Text(text = subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Screen
// ---------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreasurerDashboardScreen(
    viewModel: FinanceViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()


    // Simulated financial figures
    val totalIncome = 4_850_000L
    val totalExpenses = 2_130_000L
    val balance = totalIncome - totalExpenses
    val balancePositive = balance >= 0

    data class ActionItem(val title: String, val subtitle: String, val icon: ImageVector)

    val actions = listOf(
        ActionItem("Record Income", "Log new income entry", Icons.Filled.AddCard),
        ActionItem("Record Expense", "Add an expense item", Icons.Filled.RemoveShoppingCart),
        ActionItem("Financial Report", "Generate & share report", Icons.Filled.Assessment),
        ActionItem("Audit Trail", "Review all transactions", Icons.Filled.ManageSearch)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Financial Operations",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "Treasurer",
                            style = MaterialTheme.typography.labelSmall,
                            color = KpnPrimaryGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = KpnDarkGreen,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // ── Hero Balance Card ────────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (balancePositive) KpnDarkGreen else Color(0xFFC62828)
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = if (balancePositive)
                                    listOf(KpnDarkGreen, KpnPrimaryGreen)
                                else
                                    listOf(Color(0xFFC62828), Color(0xFFEF5350))
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = if (balancePositive) Icons.Filled.TrendingUp else Icons.Filled.TrendingDown,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.85f),
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "CURRENT BALANCE",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White.copy(alpha = 0.85f),
                                letterSpacing = 1.5.sp
                            )
                        }
                        Text(
                            text = "₦${"%,.0f".format(balance.toDouble())}",
                            fontSize = 38.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = if (balancePositive) "Funds available" else "Deficit — review required",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.75f)
                        )
                    }
                }
            }

            // ── Income / Expense stats ───────────────────────────────────────
            Text(
                text = "Financial Summary",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = KpnDarkGreen
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TreasurerStatCard(
                    label = "Total Income",
                    value = "₦${"%,.0f".format(totalIncome.toDouble())}",
                    icon = Icons.Filled.ArrowCircleDown,
                    accentColor = KpnPrimaryGreen,
                    modifier = Modifier.weight(1f)
                )
                TreasurerStatCard(
                    label = "Total Expenses",
                    value = "₦${"%,.0f".format(totalExpenses.toDouble())}",
                    icon = Icons.Filled.ArrowCircleUp,
                    accentColor = Color(0xFFE53935),
                    modifier = Modifier.weight(1f)
                )
            }

            // ── Budget health indicator ──────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = KpnPrimaryGreen.copy(alpha = 0.08f)),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Budget Utilisation", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                        Text(
                            text = "${"%.1f".format((totalExpenses.toDouble() / totalIncome.toDouble()) * 100)}%",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = KpnDarkGreen
                        )
                    }
                    LinearProgressIndicator(
                        progress = { (totalExpenses.toDouble() / totalIncome.toDouble()).toFloat().coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = KpnPrimaryGreen,
                        trackColor = KpnPrimaryGreen.copy(alpha = 0.20f)
                    )
                    Text(
                        text = "Expenditure is within budget limits",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // ── Action grid ─────────────────────────────────────────────────
            Text(
                text = "Finance Actions",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = KpnDarkGreen
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp)
            ) {
                items(actions) { item ->
                    TreasurerActionCard(title = item.title, subtitle = item.subtitle, icon = item.icon)
                }
            }
        }
    }
}
