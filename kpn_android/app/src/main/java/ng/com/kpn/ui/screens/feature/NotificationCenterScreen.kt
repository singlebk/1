package ng.com.kpn.ui.screens.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun NotificationCenterScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = KpnDarkGreen)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues).background(MaterialTheme.colorScheme.background)
        ) {
            items(5) { index ->
                val isUnread = index < 2
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (isUnread) KpnPrimaryGreen.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surface)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(48.dp).clip(CircleShape).background(KpnDarkGreen.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = KpnDarkGreen)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Ward Meeting Reminder", fontWeight = if (isUnread) FontWeight.Bold else FontWeight.Normal, fontSize = 16.sp)
                        Text("Your monthly ward meeting is scheduled for 4 PM today.", fontSize = 14.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("2 hours ago", fontSize = 12.sp, color = KpnPrimaryGreen)
                    }
                    if (isUnread) {
                        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Red))
                    }
                }
                Divider()
            }
        }
    }
}
