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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ng.com.kpn.ui.theme.KpnDarkGreen
import ng.com.kpn.ui.theme.KpnPrimaryGreen

// ── Local stat card ───────────────────────────────────────────────────────────
@Composable
private fun AceStatCard(
    label: String,
    value: String,
    icon: ImageVector,
    accentColor: Color = KpnPrimaryGreen,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(26.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = value, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = accentColor)
                Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

// ── Local action card ─────────────────────────────────────────────────────────
@Composable
private fun AceActionCard(
    label: String,
    icon: ImageVector,
    accentColor: Color = KpnPrimaryGreen,
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
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(accentColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = label, tint = accentColor, modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// ── Main Screen ───────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssistantCommunityEngagementDashboardScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: GenericViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val actions = listOf(
        Triple("Field Report", Icons.Default.EditNote, KpnPrimaryGreen),
        Triple("Assign Program", Icons.Default.PlaylistAdd, KpnDarkGreen),
        Triple("Community Feedback", Icons.Default.Forum, KpnPrimaryGreen),
        Triple("Support Request", Icons.Default.SupportAgent, KpnDarkGreen)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Community Support", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(
                            "Assistant Director of Community Engagement",
                            fontSize = 12.sp,
                            color = KpnPrimaryGreen,
                            fontWeight = FontWeight.Medium
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = KpnPrimaryGreen)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Diversity3, contentDescription = null, tint = Color.White, modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text("Field Support Unit", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                        Text("Grassroots · Field Logistics · Feedback", fontSize = 12.sp, color = Color.White.copy(alpha = 0.85f))
                    }
                }
            }

            // Metrics
            Text("My Overview", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AceStatCard(
                    label = "Assigned Programs",
                    value = "8",
                    icon = Icons.Default.LibraryBooks,
                    accentColor = KpnPrimaryGreen,
                    modifier = Modifier.weight(1f)
                )
                AceStatCard(
                    label = "Field Reports Submitted",
                    value = "12",
                    icon = Icons.Default.AssignmentTurnedIn,
                    accentColor = KpnDarkGreen,
                    modifier = Modifier.weight(1f)
                )
            }

            // Field Activity Feed
            Text("Recent Field Activities", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
            listOf(
                Triple("Grassroots Sensitisation – Ward 5", "Today, 9:00 AM", Icons.Default.LocationOn),
                Triple("Feedback Collection – Mararaba", "Yesterday", Icons.Default.RecordVoiceOver),
                Triple("Program Assignment – Youth Skill", "2 days ago", Icons.Default.CheckCircle)
            ).forEach { (activity, time, icon) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(icon, contentDescription = null, tint = KpnPrimaryGreen, modifier = Modifier.size(22.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(activity, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            Text(time, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            // Action Grid
            Text("Quick Actions", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(260.dp)
            ) {
                items(actions) { (label, icon, color) ->
                    AceActionCard(label = label, icon = icon, accentColor = color)
                }
            }
        }
    }
}
