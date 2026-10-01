package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diversity1
import androidx.compose.material.icons.filled.ForwardToInbox
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.VolunteerActivism
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
fun WardWomensSupportDashboardScreen(
    viewModel: WardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val roseAccent = Color(0xFFEC4899)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Ward Women's Support", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text("Ward Women's Support Officer", fontSize = 12.sp, color = roseAccent)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White, titleContentColor = Color(0xFF1F2937))
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF9FAFB)).padding(16.dp)) {
            WardJurisdictionChip("Your Ward")
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                WardWomenStatCard(Modifier.weight(1f), "Women Supported", "45", roseAccent)
                WardWomenStatCard(Modifier.weight(1f), "Activities Held", "3", Color(0xFF8B5CF6))
            }
            LazyVerticalGrid(columns = GridCells.Fixed(2), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item { WardWomenActionCard("Support Case", "Log welfare issue", Icons.Default.LocalHospital, Color(0xFFEF4444)) }
                item { WardWomenActionCard("Women's Activity", "Local meetings", Icons.Default.Diversity1, roseAccent) }
                item { WardWomenActionCard("Welfare Log", "Past assistance", Icons.Default.VolunteerActivism, KpnPrimaryGreen) }
                item { WardWomenActionCard("Report to LGA", "Submit summary", Icons.Default.ForwardToInbox, Color(0xFF3B82F6)) }
            }
        }
    }
}

@Composable
private fun WardWomenStatCard(modifier: Modifier, title: String, value: String, color: Color) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, color = Color.Gray, fontSize = 11.sp)
            Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = color, modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WardWomenActionCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color) {
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
