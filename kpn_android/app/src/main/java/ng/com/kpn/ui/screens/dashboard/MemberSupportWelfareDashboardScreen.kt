package ng.com.kpn.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
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
import ng.com.kpn.ui.theme.KpnDarkGreen
import ng.com.kpn.ui.theme.KpnPrimaryGreen

/**
 * ROLE 18: Member Support & Welfare Dashboard
 * For the Director of Member Support & Welfare.
 * Covers welfare cases, hardship support, medical referrals,
 * condolence coordination, and welfare fund management.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberSupportWelfareDashboardScreen(
    viewModel: GenericViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Member Support & Welfare",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            "Director of Member Support & Welfare",
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
                "Welfare Overview",
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
                    title = "Open Welfare Cases",
                    value = "34",
                    subtitle = "Awaiting resolution"
                )
                ExecutiveStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Resolved This Month",
                    value = "21",
                    subtitle = "Cases closed"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Welfare Actions",
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
                        title = "Welfare Cases",
                        count = "34",
                        icon = Icons.Default.Favorite,
                        color = Color(0xFFEF4444)
                    )
                }
                item {
                    ActionCard(
                        title = "New Case",
                        count = "+",
                        icon = Icons.Default.AddCircle,
                        color = KpnPrimaryGreen
                    )
                }
                item {
                    ActionCard(
                        title = "Medical Referral",
                        count = "8",
                        icon = Icons.Default.LocalHospital,
                        color = Color(0xFF3B82F6)
                    )
                }
                item {
                    ActionCard(
                        title = "Condolence Log",
                        count = "5",
                        icon = Icons.Default.VolunteerActivism,
                        color = Color(0xFF8B5CF6)
                    )
                }
            }
        }
    }
}
