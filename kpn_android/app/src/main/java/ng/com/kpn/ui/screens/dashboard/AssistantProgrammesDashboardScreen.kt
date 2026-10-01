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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ng.com.kpn.ui.theme.KpnDarkGreen
import ng.com.kpn.ui.theme.KpnPrimaryGreen

// ── Local stat card ───────────────────────────────────────────────────────────
@Composable
private fun ApeStatCard(
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
private fun ApeActionCard(
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
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}

// ── Main Screen ───────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssistantProgrammesDashboardScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: GenericViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val actions = listOf(
        Triple("Logistics Tasks", Icons.Default.LocalShipping, KpnPrimaryGreen),
        Triple("Vendor List", Icons.Default.StoreMallDirectory, KpnDarkGreen),
        Triple("Material Prep", Icons.Default.Inventory, KpnPrimaryGreen),
        Triple("Event Support", Icons.Default.EventAvailable, KpnDarkGreen)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Events Support", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(
                            "Assistant Director of Programmes & Events",
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
                colors = CardDefaults.cardColors(containerColor = KpnDarkGreen)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Build, contentDescription = null, tint = Color.White, modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text("Events Logistics Unit", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                        Text("Vendors · Materials · Participants", fontSize = 12.sp, color = Color.White.copy(alpha = 0.85f))
                    }
                }
            }

            // Metrics
            Text("My Tasks", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ApeStatCard(
                    label = "Events Assisted",
                    value = "5",
                    icon = Icons.Default.EventNote,
                    accentColor = KpnPrimaryGreen,
                    modifier = Modifier.weight(1f)
                )
                ApeStatCard(
                    label = "Pending Tasks",
                    value = "9",
                    icon = Icons.Default.PendingActions,
                    accentColor = KpnDarkGreen,
                    modifier = Modifier.weight(1f)
                )
            }

            // Pending Task List
            Text("Pending Tasks", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
            listOf(
                Triple("Confirm venue setup – Youth Summit", "High", Color(0xFFD32F2F)),
                Triple("Contact AV vendor – Lagos Hall", "Medium", Color(0xFFF57C00)),
                Triple("Print banners & roll-ups", "Medium", Color(0xFFF57C00)),
                Triple("Compile participant register", "Low", KpnPrimaryGreen)
            ).forEach { (task, priority, priorityColor) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Icon(Icons.Default.CheckBoxOutlineBlank, contentDescription = null, tint = KpnPrimaryGreen, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(task, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        }
                        Surface(shape = RoundedCornerShape(20.dp), color = priorityColor.copy(alpha = 0.12f)) {
                            Text(
                                text = priority,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = priorityColor,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
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
                    ApeActionCard(label = label, icon = icon, accentColor = color)
                }
            }
        }
    }
}
