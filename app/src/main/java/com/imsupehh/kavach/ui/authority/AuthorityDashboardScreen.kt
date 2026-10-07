package com.imsupehh.kavach.ui.authority

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
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
import com.imsupehh.kavach.data.models.Incident
import com.imsupehh.kavach.data.models.IncidentPriority
import com.imsupehh.kavach.ui.theme.AuthorityBlueAccent
import com.imsupehh.kavach.ui.theme.AuthorityBlueDark
import com.imsupehh.kavach.ui.theme.AuthorityBluePrimary
import com.imsupehh.kavach.ui.theme.AuthorityNavyBackground
import com.imsupehh.kavach.ui.theme.AuthorityNavyCard
import com.imsupehh.kavach.ui.theme.AuthorityNavySurface
import com.imsupehh.kavach.ui.theme.KavachRedPrimary
import com.imsupehh.kavach.ui.theme.SeverityHigh
import com.imsupehh.kavach.ui.theme.SeverityMedium
import com.imsupehh.kavach.viewmodel.KavachViewModel

// Screen 6: Authority Dashboard
@Composable
fun AuthorityDashboardScreen(
    viewModel: KavachViewModel,
    onSelectIncident: (Incident) -> Unit,
    onViewAllIncidents: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToActions: () -> Unit
) {
    val incidents by viewModel.incidents.collectAsState()
    var selectedBottomTab by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = Color(0xFFF4F6F9),
        bottomBar = {
            NavigationBar(
                containerColor = AuthorityNavySurface,
                contentColor = Color.White
            ) {
                NavigationBarItem(
                    selected = selectedBottomTab == 0,
                    onClick = { selectedBottomTab = 0 },
                    icon = { Icon(Icons.Default.Warning, contentDescription = "Incidents") },
                    label = { Text("Incidents") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AuthorityBlueAccent,
                        selectedTextColor = AuthorityBlueAccent,
                        indicatorColor = AuthorityNavyCard
                    )
                )
                NavigationBarItem(
                    selected = selectedBottomTab == 1,
                    onClick = {
                        selectedBottomTab = 1
                        onViewAllIncidents()
                    },
                    icon = { Icon(Icons.Default.Map, contentDescription = "Map") },
                    label = { Text("Map") }
                )
                NavigationBarItem(
                    selected = selectedBottomTab == 2,
                    onClick = {
                        selectedBottomTab = 2
                        onNavigateToActions()
                    },
                    icon = { Icon(Icons.Default.Assessment, contentDescription = "Actions") },
                    label = { Text("Actions") }
                )
                NavigationBarItem(
                    selected = selectedBottomTab == 3,
                    onClick = {
                        selectedBottomTab = 3
                        onNavigateToProfile()
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(AuthorityBluePrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalPolice,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Kavach Authority Portal",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E1E24)
                        )
                        Text(
                            text = "Welcome, Inspector Singh • UP Police",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Stats Metrics Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DashboardMetricCard(
                    count = "12",
                    label = "Live Incidents",
                    accentColor = AuthorityBluePrimary,
                    modifier = Modifier.weight(1f)
                )
                DashboardMetricCard(
                    count = "48",
                    label = "Interventions",
                    accentColor = Color(0xFF3949AB),
                    modifier = Modifier.weight(1f)
                )
                DashboardMetricCard(
                    count = "36",
                    label = "Resolved",
                    accentColor = Color(0xFF2E7D32),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Schematic Map View Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clickable { onViewAllIncidents() },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EEF5)),
                border = BorderStroke(1.dp, Color(0xFFCBD5E1))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Map Grid lines simulation
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Noida Sector 62 Grid View",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF334155)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(AuthorityBluePrimary)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("Live GPS", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Simulated pins
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            MapPinBeacon(title = "KAV-1024", isHigh = true)
                            MapPinBeacon(title = "KAV-1023", isHigh = false)
                            MapPinBeacon(title = "KAV-1022", isHigh = true)
                        }

                        Text(
                            text = "Tap to open interactive live tactical map",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Live Incidents Section Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Live Incidents",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E1E24)
                )
                Text(
                    text = "View All",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = AuthorityBluePrimary,
                    modifier = Modifier.clickable { onViewAllIncidents() }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Incident List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(incidents) { incident ->
                    IncidentCardItem(
                        incident = incident,
                        onClick = {
                            viewModel.selectIncident(incident)
                            onSelectIncident(incident)
                        }
                    )
                }
            }
        }
    }
}

// Screen 7: Live Incidents Queue
@Composable
fun LiveIncidentsScreen(
    viewModel: KavachViewModel,
    onSelectIncident: (Incident) -> Unit,
    onBack: () -> Unit
) {
    val incidents by viewModel.incidents.collectAsState()
    val activeFilter by viewModel.incidentFilter.collectAsState()

    val filteredIncidents = when (activeFilter) {
        "HIGH" -> incidents.filter { it.priority == IncidentPriority.HIGH || it.priority == IncidentPriority.CRITICAL }
        "MEDIUM" -> incidents.filter { it.priority == IncidentPriority.MEDIUM }
        "LOW" -> incidents.filter { it.priority == IncidentPriority.LOW }
        else -> incidents
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F9))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "< Live Incidents",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E24),
                modifier = Modifier.clickable { onBack() }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Filter Tabs: [All] [High] [Medium] [Low]
        val tabs = listOf("ALL", "HIGH", "MEDIUM", "LOW")
        val selectedIndex = tabs.indexOf(activeFilter).coerceAtLeast(0)

        TabRow(
            selectedTabIndex = selectedIndex,
            containerColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                    color = AuthorityBluePrimary
                )
            }
        ) {
            tabs.forEach { tab ->
                Tab(
                    selected = activeFilter == tab,
                    onClick = { viewModel.setFilter(tab) },
                    text = { Text(tab, fontWeight = FontWeight.Bold, fontSize = 13.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredIncidents) { incident ->
                IncidentCardItem(
                    incident = incident,
                    onClick = {
                        viewModel.selectIncident(incident)
                        onSelectIncident(incident)
                    }
                )
            }
        }
    }
}

@Composable
fun DashboardMetricCard(
    count: String,
    label: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(86.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = count,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = accentColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun IncidentCardItem(
    incident: Incident,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        if (incident.priority == IncidentPriority.HIGH) KavachRedPrimary.copy(alpha = 0.15f)
                        else AuthorityBluePrimary.copy(alpha = 0.15f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = if (incident.priority == IncidentPriority.HIGH) KavachRedPrimary else AuthorityBluePrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${incident.code} • ${incident.category}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E1E24)
                    )
                    Text(
                        text = "${incident.distanceKm} km",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = AuthorityBluePrimary
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${incident.locationAddress} • ${incident.timeFormatted}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun MapPinBeacon(title: String, isHigh: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (isHigh) KavachRedPrimary else AuthorityBluePrimary),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
        }
        Text(title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF334155))
    }
}
