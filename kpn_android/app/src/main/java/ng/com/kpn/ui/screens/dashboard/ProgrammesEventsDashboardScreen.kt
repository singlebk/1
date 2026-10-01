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
private fun PeStatCard(
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
private fun PeActionCard(
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

// ── Calendar-style next event widget ─────────────────────────────────────────
@Composable
private fun NextEventWidget(
    eventName: String,
    date: String,
    venue: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = KpnDarkGreen),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Calendar tile
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("OCT", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.8f))
                    Text("05", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                    Text("2026", fontSize = 9.sp, color = Color.White.copy(alpha = 0.7f))
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Next Event", fontSize = 11.sp, color = Color.White.copy(alpha = 0.75f), fontWeight = FontWeight.Medium)
                Text(eventName, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(venue, fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
                }
                Text(date, fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
            }
        }
    }
}

// ── Main Screen ───────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgrammesEventsDashboardScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: GenericViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    val actions = listOf(
        Triple("Create Event", Icons.Default.AddBox, KpnPrimaryGreen),
        Triple("Event Schedule", Icons.Default.CalendarMonth, KpnDarkGreen),
        Triple("Attendance Log", Icons.Default.HowToReg, KpnPrimaryGreen),
        Triple("Program Reports", Icons.Default.Summarize, KpnDarkGreen)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Programmes & Events", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(
                            "Director of Programmes & Events",
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
            // Next Event Calendar Widget
            NextEventWidget(
                eventName = "KPN Annual Youth Summit",
                date = "Monday, 5 October 2026 · 10:00 AM",
                venue = "Abuja International Conference Centre"
            )

            // Metrics
            Text("Live Overview", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PeStatCard(
                    label = "Upcoming Events",
                    value = "7",
                    icon = Icons.Default.Event,
                    accentColor = KpnPrimaryGreen,
                    modifier = Modifier.weight(1f)
                )
                PeStatCard(
                    label = "Programs Active",
                    value = "11",
                    icon = Icons.Default.PlayCircle,
                    accentColor = KpnDarkGreen,
                    modifier = Modifier.weight(1f)
                )
            }

            // Upcoming Schedule
            Text("Upcoming Schedule", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground)
            listOf(
                Triple("Oct 05", "KPN Annual Youth Summit", "Abuja ICC"),
                Triple("Oct 12", "Women's Empowerment Seminar", "Lagos Hall"),
                Triple("Oct 18", "Community Outreach Day", "Multiple Venues")
            ).forEach { (date, name, venue) ->
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
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(KpnPrimaryGreen.copy(alpha = 0.10f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(date, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = KpnDarkGreen, textAlign = TextAlign.Center)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(name, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            Text(venue, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
                    PeActionCard(label = label, icon = icon, accentColor = color)
                }
            }
        }
    }
}
