package ng.com.kpn.ui.screens.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ng.com.kpn.ui.theme.KpnPrimaryGreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    onRegistrationComplete: () -> Unit
) {
    var currentStep by remember { mutableStateOf(1) }
    val totalSteps = 5

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Join KPN", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (currentStep > 1) {
                            currentStep--
                        } else {
                            onNavigateBack()
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
        ) {
            // Progress Indicator
            LinearProgressIndicator(
                progress = currentStep.toFloat() / totalSteps,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = KpnPrimaryGreen,
                trackColor = Color(0xFFE5E7EB)
            )

            Text(
                text = "Step $currentStep of $totalSteps",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(16.dp)
            )

            // Step Content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                AnimatedContent(targetState = currentStep, label = "registration_steps") { step ->
                    when (step) {
                        1 -> StepIdentity()
                        2 -> StepPhoto()
                        3 -> StepLocation()
                        4 -> StepRole()
                        5 -> StepReview()
                    }
                }
            }

            // Bottom Navigation Bar
            Surface(
                color = Color.White,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (currentStep > 1) {
                        OutlinedButton(onClick = { currentStep-- }) {
                            Text("Back")
                        }
                    } else {
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Button(
                        onClick = {
                            if (currentStep < totalSteps) {
                                currentStep++
                            } else {
                                onRegistrationComplete()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = KpnPrimaryGreen)
                    ) {
                        Text(if (currentStep == totalSteps) "Submit Application" else "Next")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = if (currentStep == totalSteps) Icons.Default.Check else Icons.Default.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// SUB-COMPONENTS FOR EACH STEP
// ---------------------------------------------------------------------------

@Composable
fun StepIdentity() {
    Column {
        Text("Personal Information", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))
        Text("Tell us about yourself.", color = Color.Gray, modifier = Modifier.padding(bottom = 24.dp))
        
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("First Name") }, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Last Name") }, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Phone Number") }, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Email Address") }, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Password") }, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp))
    }
}

@Composable
fun StepPhoto() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text("Profile Photo", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937), modifier = Modifier.align(Alignment.Start))
        Text("Upload a clear, professional photo (Max 400KB).", color = Color.Gray, modifier = Modifier.align(Alignment.Start).padding(bottom = 40.dp))
        
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color(0xFFE5E7EB), shape = RoundedCornerShape(75.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("Tap to Select", color = Color.Gray)
        }
    }
}

@Composable
fun StepLocation() {
    Column {
        Text("Location Assignment", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))
        Text("Select your KPN jurisdiction.", color = Color.Gray, modifier = Modifier.padding(bottom = 24.dp))
        
        // TODO: Replace with proper Dropdowns populated via API in ViewModel
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Select Zone") }, enabled = false, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Select LGA") }, enabled = false, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Select Ward") }, enabled = false, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp))
    }
}

@Composable
fun StepRole() {
    Column {
        Text("Leadership Interest", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))
        Text("Which role are you applying for?", color = Color.Gray, modifier = Modifier.padding(bottom = 24.dp))
        
        // TODO: Populate dynamically from GET /api/v1/roles/?tier=
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Select Tier") }, enabled = false, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Select Role") }, enabled = false, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp))
    }
}

@Composable
fun StepReview() {
    Column {
        Text("Review Application", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))
        Text("Verify your details before submitting.", color = Color.Gray, modifier = Modifier.padding(bottom = 24.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Identity: Pending Input", color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
                Text("Location: Pending Input", color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
                Text("Role: Pending Input", color = Color.Gray)
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Surface(
            color = Color(0xFFEFF6FF),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Your application will be sent to your jurisdictional leader for verification. You will be notified via email when your status changes.",
                color = Color(0xFF1E3A8A),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
