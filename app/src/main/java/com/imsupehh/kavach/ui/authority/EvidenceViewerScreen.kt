package com.imsupehh.kavach.ui.authority

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imsupehh.kavach.ui.components.KavachButton
import com.imsupehh.kavach.ui.components.KavachTopBar
import com.imsupehh.kavach.ui.theme.AuthorityBluePrimary
import com.imsupehh.kavach.ui.theme.KavachRedGlow
import com.imsupehh.kavach.ui.theme.KavachRedLight
import com.imsupehh.kavach.ui.theme.KavachRedPrimary
import com.imsupehh.kavach.viewmodel.KavachViewModel

// Screen 12: Evidence Viewer (Photos 30, Audio 01, Location)
@Composable
fun EvidenceViewerScreen(
    viewModel: KavachViewModel,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Photos, 1: Audio, 2: Location

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            KavachTopBar(title = "Evidence Report", onBackClick = onBack)

            Spacer(modifier = Modifier.height(10.dp))

            // Evidence Tabs: [Photos (30)] [Audio (1)] [Location]
            val tabs = listOf("Photos (30)", "Audio (01)", "Location")
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color(0xFFF8FAFC),
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = AuthorityBluePrimary
                    )
                }
            ) {
                tabs.forEachIndexed { idx, label ->
                    Tab(
                        selected = selectedTab == idx,
                        onClick = { selectedTab = idx },
                        text = { Text(label, fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTab) {
                0 -> {
                    // 30 Photos Grid (Alternating 15 Rear + 15 Front)
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(30) { index ->
                            val isRear = (index % 2 == 0)
                            val lensText = if (isRear) "Rear" else "Front"
                            val num = (index / 2) + 1

                            Card(
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                                border = BorderStroke(1.dp, Color(0xFFCBD5E1))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp),
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CameraAlt,
                                        contentDescription = null,
                                        tint = if (isRear) AuthorityBluePrimary else KavachRedPrimary,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Color.White)
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "$lensText %02d".format(num),
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.DarkGray
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                1 -> {
                    // Audio Player Card
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text("Emergency Ambient Audio", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text("Recorded 12 Jan 2025 • High Bitrate AAC", color = Color.Gray, fontSize = 12.sp)

                                Spacer(modifier = Modifier.height(24.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(CircleShape)
                                            .background(AuthorityBluePrimary),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White, modifier = Modifier.size(28.dp))
                                    }

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(6.dp)
                                                .clip(RoundedCornerShape(3.dp))
                                                .background(Color(0xFF334155))
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth(0.35f)
                                                    .height(6.dp)
                                                    .background(AuthorityBluePrimary)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text("00:21", fontSize = 11.sp, color = Color.LightGray)
                                            Text("01:00", fontSize = 11.sp, color = Color.LightGray)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // Location Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = AuthorityBluePrimary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Sector 62, Noida", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Coordinates: 28.6280° N, 77.3649° E", fontSize = 13.sp, color = Color.DarkGray)
                            Text("Accuracy: 4.2 meters", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }

        KavachButton(
            text = "Download Forensic Dossier",
            onClick = onBack,
            containerColor = AuthorityBluePrimary
        )
    }
}

// Screen 11: AI Analysis Report
@Composable
fun AiAnalysisReportAuthorityScreen(
    viewModel: KavachViewModel,
    onBack: () -> Unit
) {
    val incident = viewModel.selectedIncident.collectAsState().value ?: viewModel.incidents.collectAsState().value.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            KavachTopBar(title = "AI Analysis Report", onBackClick = onBack)

            Spacer(modifier = Modifier.height(14.dp))

            // AI Badge
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F2)),
                border = BorderStroke(1.dp, KavachRedPrimary.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(KavachRedPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(incident.aiClassification, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text("${incident.aiConfidencePercent}% confidence", fontSize = 13.sp, color = KavachRedPrimary, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Key Indicators", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))

            incident.keyIndicators.forEach { item ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(item, fontSize = 14.sp, color = Color(0xFF334155))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Context Summary", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Location: ${incident.locationAddress}", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    Text("Time: ${incident.timeFormatted}", fontSize = 13.sp)
                    Text("Environment: Public corridor, low pedestrian density", fontSize = 13.sp, color = Color.Gray)
                }
            }
        }

        KavachButton(
            text = "Download Report",
            onClick = onBack,
            containerColor = AuthorityBluePrimary
        )
    }
}

// Screen 12: Team Actions Screen
@Composable
fun TeamActionsScreen(
    viewModel: KavachViewModel,
    onBack: () -> Unit
) {
    val actions by viewModel.teamActions.collectAsState()
    var selectedFilter by remember { mutableIntStateOf(0) } // 0: All, 1: Resolved, 2: Ongoing

    val filteredActions = when (selectedFilter) {
        1 -> actions.filter { it.isResolved }
        2 -> actions.filter { !it.isResolved }
        else -> actions
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            KavachTopBar(title = "Team Actions", onBackClick = onBack)

            Spacer(modifier = Modifier.height(10.dp))

            TabRow(
                selectedTabIndex = selectedFilter,
                containerColor = Color(0xFFF8FAFC),
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedFilter]),
                        color = AuthorityBluePrimary
                    )
                }
            ) {
                listOf("All", "Resolved", "Ongoing").forEachIndexed { idx, label ->
                    Tab(
                        selected = selectedFilter == idx,
                        onClick = { selectedFilter = idx },
                        text = { Text(label, fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredActions) { action ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(if (action.isResolved) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (action.isResolved) Icons.Default.CheckCircle else Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = if (action.isResolved) Color(0xFF2E7D32) else KavachRedPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(action.title, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                                Text("${action.timestamp} • ${action.location}", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }

        KavachButton(
            text = "Take Action",
            onClick = {
                // Execute next pending action
                actions.firstOrNull { !it.isResolved }?.let {
                    viewModel.executeTeamAction(it.id)
                }
            },
            containerColor = AuthorityBluePrimary
        )
    }
}

// Screen 14: Authority Profile Screen
@Composable
fun AuthorityProfileScreen(
    viewModel: KavachViewModel,
    onLogout: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            KavachTopBar(title = "Authority Profile", onBackClick = onBack)

            Spacer(modifier = Modifier.height(10.dp))

            // Officer Card
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
                        .background(Color(0xFFE3F2FD)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.LocalPolice, contentDescription = null, tint = AuthorityBluePrimary, modifier = Modifier.size(36.dp))
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Rajesh Kumar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1E24)
                    )
                    Text(
                        text = "Sub Inspector • UP Police",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .padding(vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF4B5563))
                Spacer(modifier = Modifier.width(16.dp))
                Text("My Profile", fontSize = 15.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .padding(vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Security, contentDescription = null, tint = Color(0xFF4B5563))
                Spacer(modifier = Modifier.width(16.dp))
                Text("Department Details", fontSize = 15.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .padding(vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Notifications, contentDescription = null, tint = Color(0xFF4B5563))
                Spacer(modifier = Modifier.width(16.dp))
                Text("Notification Settings", fontSize = 15.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .padding(vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Map, contentDescription = null, tint = Color(0xFF4B5563))
                Spacer(modifier = Modifier.width(16.dp))
                Text("Map Preferences", fontSize = 15.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .padding(vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF4B5563))
                Spacer(modifier = Modifier.width(16.dp))
                Text("Help & Support", fontSize = 15.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
            }
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
