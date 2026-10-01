package ng.com.kpn.ui.screens.public

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ng.com.kpn.ui.theme.KpnDarkGreen
import ng.com.kpn.ui.theme.KpnPrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeadershipScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Network Leadership", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = KpnDarkGreen)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).background(MaterialTheme.colorScheme.background)
        ) {
            // Filter Bar Native Implementation
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Filter by Jurisdiction", fontWeight = FontWeight.Medium)
                    Icon(Icons.Default.FilterList, contentDescription = "Filter", tint = KpnPrimaryGreen)
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Mock Leaders
                items(6) { index ->
                    LeaderCard(
                        name = if(index == 0) "Comr. Aliyu Hassan" else "Vacant",
                        role = if(index == 0) "State President" else "State Secretary",
                        location = "State Executive",
                        isVacant = index != 0
                    )
                }
            }
        }
    }
}

@Composable
fun LeaderCard(name: String, role: String, location: String, isVacant: Boolean) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().aspectRatio(0.85f),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(if (isVacant) Color.LightGray else KpnPrimaryGreen),
                contentAlignment = Alignment.Center
            ) {
                if (!isVacant) {
                    Text(name.first().toString(), color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = if(isVacant) Color.Gray else MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(4.dp))
            Text(role, fontSize = 12.sp, color = KpnDarkGreen, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(2.dp))
            Text(location, fontSize = 10.sp, color = Color.Gray)
        }
    }
}
