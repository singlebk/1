package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ng.com.kpn.ui.theme.KpnPrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaDirectorDashboardScreen(
    viewModel: MediaDirectorViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Communications Hub", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text("Director of Media & Communications", fontSize = 12.sp, color = KpnPrimaryGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White, titleContentColor = Color(0xFF1F2937))
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF9FAFB))) {
            
            if (state.isLoading && state.data == null) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = KpnPrimaryGreen)
            } else if (state.error != null && state.data == null) {
                Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error loading data: ${state.error}", color = Color.Red, modifier = Modifier.padding(16.dp))
                    Button(onClick = { viewModel.fetchMetrics() }, colors = ButtonDefaults.buttonColors(containerColor = KpnPrimaryGreen)) {
                        Text("Retry")
                    }
                }
            } else {
                // Main Content
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    Text("Editorial Pipeline", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                    
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        ExecutiveStatCard(
                            Modifier.weight(1f), 
                            "Pending Review", 
                            state.data?.pendingReview?.toString() ?: "0", 
                            "Action required"
                        )
                        ExecutiveStatCard(
                            Modifier.weight(1f), 
                            "Published News", 
                            state.data?.publishedNews?.toString() ?: "0", 
                            "Live"
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Management Actions", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                    
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            ActionCard(
                                title = "Review Queue",
                                count = state.data?.pendingReview?.toString() ?: "0",
                                icon = Icons.Default.RateReview,
                                color = Color(0xFFF59E0B) // Yellow
                            )
                        }
                        item {
                            ActionCard(
                                title = "Active Campaigns",
                                count = state.data?.activeCampaigns?.toString() ?: "0",
                                icon = Icons.Default.Campaign,
                                color = KpnPrimaryGreen
                            )
                        }
                        item {
                            ActionCard(
                                title = "Newsroom Editor",
                                count = "Manage",
                                icon = Icons.Default.Newspaper,
                                color = Color(0xFF3B82F6) // Blue
                            )
                        }
                        item {
                            ActionCard(
                                title = "Trusted Reporters",
                                count = state.data?.trustedReporters?.toString() ?: "0",
                                icon = Icons.Default.People,
                                color = Color(0xFF8B5CF6) // Purple
                            )
                        }
                    }
                }
            }
        }
    }
}
