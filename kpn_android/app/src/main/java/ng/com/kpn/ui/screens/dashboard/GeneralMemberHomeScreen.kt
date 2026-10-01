package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ng.com.kpn.ui.theme.KpnDarkGreen
import ng.com.kpn.ui.theme.KpnPrimaryGreen

@Composable
fun GeneralMemberHomeScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF9FAFB))) {
        // Welcome Header
        Box(modifier = Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(KpnPrimaryGreen, KpnDarkGreen))).padding(24.dp)) {
            Column {
                Spacer(modifier = Modifier.height(24.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text("Welcome back,", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                        Text("Abubakar", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    }
                    Surface(color = Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(16.dp)) {
                        Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("VERIFIED", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
            
            // Telegram Status
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2FE)), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Surface(color = Color(0xFF0284C7), shape = RoundedCornerShape(8.dp), modifier = Modifier.size(40.dp)) {
                            Icon(Icons.Default.Message, contentDescription = null, tint = Color.White, modifier = Modifier.padding(8.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Official Telegram", fontWeight = FontWeight.Bold, color = Color(0xFF0C4A6E))
                            Text("Join the community channel for live updates.", fontSize = 12.sp, color = Color(0xFF0C4A6E).copy(alpha = 0.8f))
                        }
                        Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))) {
                            Text("Join")
                        }
                    }
                }
            }
            
            // Latest News
            item {
                Column {
                    Text("Latest Updates", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        item { MemberNewsCard("Townhall Meeting Scheduled", "CIVIC") }
                        item { MemberNewsCard("KPN Empowers 500 Youth", "YOUTH") }
                        item { MemberNewsCard("Statewide Outreach", "COMMUNITY") }
                    }
                }
            }
            
            // Opportunities
            item {
                Column {
                    Text("Featured Opportunities", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(bottom = 12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        MemberOppCard(Modifier.weight(1f), "State Youth Grant", Icons.Default.BusinessCenter)
                        MemberOppCard(Modifier.weight(1f), "Tech Scholarship", Icons.Default.School)
                    }
                }
            }
            
            // Network Info
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(2.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Your Network", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))
                        NetworkRow("Zone", "Central Senatorial Zone")
                        NetworkRow("LGA", "Birnin Kebbi")
                        NetworkRow("Ward", "Nassarawa I")
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun MemberNewsCard(title: String, category: String) {
    Card(modifier = Modifier.width(240.dp).height(120.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(modifier = Modifier.padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Surface(color = KpnPrimaryGreen.copy(alpha = 0.1f), shape = RoundedCornerShape(4.dp)) {
                Text(category, color = KpnPrimaryGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
            }
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

@Composable
private fun MemberOppCard(modifier: Modifier, title: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Card(modifier = modifier.height(100.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(modifier = Modifier.padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null, tint = Color(0xFF3B82F6), modifier = Modifier.size(24.dp).padding(bottom = 8.dp))
            Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}

@Composable
private fun NetworkRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Color.Gray, fontSize = 14.sp)
        Text(value, fontWeight = FontWeight.Medium, fontSize = 14.sp)
    }
}
