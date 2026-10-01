package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Share
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
import ng.com.kpn.ui.theme.KpnDarkGreen
import ng.com.kpn.ui.theme.KpnPrimaryGreen

/**
 * ROLE 20: Communications Support Dashboard
 * For the Assistant Director of Media & Communications.
 * Covers content support, social media, reporting assistance,
 * trusted reporter coordination, and media asset management.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssistantMediaDashboardScreen(
    viewModel: GenericViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Communications Support",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            "Assistant Director of Media & Communications",
                            fontSize = 12.sp,
                            color = KpnPrimaryGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF1F2937)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF9FAFB))
                .padding(16.dp)
        ) {

            Text(
                "Media & Content Overview",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Metrics Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ExecutiveStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Content Drafts",
                    value = "11",
                    subtitle = "Awaiting review"
                )
                ExecutiveStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Assets Uploaded",
                    value = "63",
                    subtitle = "In media library"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Media Actions",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    ActionCard(
                        title = "Create Draft",
                        count = "+",
                        icon = Icons.Default.EditNote,
                        color = KpnPrimaryGreen
                    )
                }
                item {
                    ActionCard(
                        title = "Upload Asset",
                        count = "63",
                        icon = Icons.Default.CloudUpload,
                        color = Color(0xFF3B82F6)
                    )
                }
                item {
                    ActionCard(
                        title = "Reporters List",
                        count = "View",
                        icon = Icons.Default.Contacts,
                        color = Color(0xFFF59E0B)
                    )
                }
                item {
                    ActionCard(
                        title = "Social Media Log",
                        count = "28",
                        icon = Icons.Default.Share,
                        color = Color(0xFF8B5CF6)
                    )
                }
            }
        }
    }
}
