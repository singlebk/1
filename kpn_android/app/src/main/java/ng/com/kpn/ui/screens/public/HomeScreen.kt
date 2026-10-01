package ng.com.kpn.ui.screens.public

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ng.com.kpn.ui.theme.KpnDarkGreen
import ng.com.kpn.ui.theme.KpnPrimaryGreen

@Composable
fun HomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToNewsroom: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
            .verticalScroll(scrollState)
    ) {
        // ----------------------------------------------------
        // HERO SECTION
        // ----------------------------------------------------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(KpnPrimaryGreen, KpnDarkGreen)))
                .padding(vertical = 48.dp, horizontal = 24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "One Voice, One Change",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Empowering the youth of Kebbi State for progressive development.",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 16.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                Button(
                    onClick = onNavigateToLogin,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = KpnDarkGreen),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(48.dp).padding(horizontal = 16.dp)
                ) {
                    Text("Member Portal", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                }
            }
        }

        // ----------------------------------------------------
        // IMPACT METRICS (Live from API)
        // ----------------------------------------------------
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxWidth().height(120.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = KpnPrimaryGreen)
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ImpactMetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Verified Members",
                    value = "${uiState.verifiedMembers}",
                    icon = Icons.Default.Group
                )
                ImpactMetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Active Wards",
                    value = "${uiState.activeWards}",
                    icon = Icons.Default.Map
                )
                ImpactMetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Communities",
                    value = "${uiState.impactCommunities}",
                    icon = Icons.Default.Star
                )
            }
        }

        // ----------------------------------------------------
        // QUICK NAVIGATION CARDS
        // ----------------------------------------------------
        Text(
            text = "Explore KPN",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            item { ExploreCard("Newsroom", "Latest updates & campaigns", KpnPrimaryGreen, onNavigateToNewsroom) }
            item { ExploreCard("Opportunities", "Grants, scholarships & jobs", Color(0xFF3B82F6), {}) }
            item { ExploreCard("Leadership", "Our network structure", Color(0xFF8B5CF6), {}) }
        }
    }
}

@Composable
fun ImpactMetricCard(modifier: Modifier = Modifier, title: String, value: String, icon: ImageVector) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = KpnPrimaryGreen, modifier = Modifier.size(24.dp).padding(bottom = 8.dp))
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))
            Text(title, fontSize = 12.sp, color = Color.Gray, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreCard(title: String, subtitle: String, color: Color, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.width(200.dp).height(100.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
            Text(subtitle, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
        }
    }
}
