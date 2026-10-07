package com.imsupehh.kavach.ui.citizen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imsupehh.kavach.ui.components.KavachButton
import com.imsupehh.kavach.ui.components.KavachTopBar
import com.imsupehh.kavach.ui.theme.DarkBackground
import com.imsupehh.kavach.ui.theme.DarkCard
import com.imsupehh.kavach.ui.theme.KavachRedGlow
import com.imsupehh.kavach.ui.theme.KavachRedLight
import com.imsupehh.kavach.ui.theme.KavachRedPrimary
import com.imsupehh.kavach.viewmodel.KavachViewModel

// Screen 12: AI Incident Report & History
@Composable
fun AiIncidentReportScreen(
    viewModel: KavachViewModel,
    onBack: () -> Unit
) {
    val incident = viewModel.activeIncident.collectAsState().value ?: viewModel.incidents.collectAsState().value.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            KavachTopBar(title = "AI Incident Report", onBackClick = onBack)

            Spacer(modifier = Modifier.height(10.dp))

            // AI Classification Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F2)),
                border = BorderStroke(1.dp, KavachRedPrimary.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(KavachRedPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = incident.aiClassification,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E1E24)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${incident.aiConfidencePercent}% confidence",
                            fontSize = 13.sp,
                            color = KavachRedPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Key Indicators
            Text(
                text = "Key Indicators",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E24)
            )

            Spacer(modifier = Modifier.height(12.dp))

            incident.keyIndicators.forEach { indicator ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = indicator,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF374151)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Incident Timeline
            Text(
                text = "Incident Timeline",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E24)
            )

            Spacer(modifier = Modifier.height(12.dp))

            incident.timeline.forEach { event ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(KavachRedPrimary)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = event.timeFormatted,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = event.title,
                        fontSize = 14.sp,
                        color = Color(0xFF1E1E24)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Context details card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                border = BorderStroke(1.dp, Color(0xFFE5E7EB))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Recommended Emergency Response", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(incident.recommendedResponse, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = KavachRedPrimary)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Location: ${incident.locationAddress} • ${incident.dateFormatted}", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        KavachButton(
            text = "Back to Safety Dashboard",
            onClick = onBack,
            containerColor = KavachRedPrimary
        )
    }
}

// Screen 14: Citizen Profile / Settings
@Composable
fun CitizenProfileScreen(
    viewModel: KavachViewModel,
    onNavigateToContacts: () -> Unit,
    onNavigateToMedical: () -> Unit,
    onLogout: () -> Unit,
    onBack: () -> Unit
) {
    val user by viewModel.currentUser.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            KavachTopBar(title = "Profile", onBackClick = onBack)

            Spacer(modifier = Modifier.height(10.dp))

            // User Info Header Card
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(KavachRedLight),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user.name.take(1),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = KavachRedPrimary
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = user.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1E24)
                    )
                    Text(
                        text = user.phone,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            ProfileMenuItem(title = "My Profile", icon = Icons.Default.Person, onClick = {})
            ProfileMenuItem(title = "Emergency Contacts", icon = Icons.Default.Phone, onClick = onNavigateToContacts)
            ProfileMenuItem(title = "Medical Information", icon = Icons.Default.Security, onClick = onNavigateToMedical)
            ProfileMenuItem(title = "Safety Settings", icon = Icons.Default.Lock, onClick = {})
            ProfileMenuItem(title = "App Permissions", icon = Icons.Default.Notifications, onClick = {})
            ProfileMenuItem(title = "Help & Support", icon = Icons.Default.Info, onClick = {})
            ProfileMenuItem(title = "About Kavach", icon = Icons.Default.Shield, onClick = {})
        }

        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE))
        ) {
            Text("Log Out", color = KavachRedPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

@Composable
fun ProfileMenuItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF4B5563),
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF1F2937),
            modifier = Modifier.weight(1f)
        )
    }
}
