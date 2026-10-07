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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imsupehh.kavach.data.models.UserRole
import com.imsupehh.kavach.ui.components.KavachButton
import com.imsupehh.kavach.ui.components.KavachOutlinedButton
import com.imsupehh.kavach.ui.components.KavachTopBar
import com.imsupehh.kavach.ui.components.RoleSelectionCard
import com.imsupehh.kavach.ui.components.SosPulsingButton
import com.imsupehh.kavach.ui.theme.AuthorityBluePrimary
import com.imsupehh.kavach.ui.theme.DarkBackground
import com.imsupehh.kavach.ui.theme.DarkBorder
import com.imsupehh.kavach.ui.theme.DarkCard
import com.imsupehh.kavach.ui.theme.KavachRedDark
import com.imsupehh.kavach.ui.theme.KavachRedGlow
import com.imsupehh.kavach.ui.theme.KavachRedLight
import com.imsupehh.kavach.ui.theme.KavachRedPrimary
import com.imsupehh.kavach.viewmodel.KavachViewModel

// Screen 1: Splash Screen
@Composable
fun CitizenSplashScreen(onContinue: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .clickable { onContinue() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(KavachRedGlow, KavachRedPrimary, KavachRedDark)
                        )
                    )
                    .border(3.dp, Color.White.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(70.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Kavach",
                fontSize = 38.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                letterSpacing = 1.5.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your Safety, Our Priority.",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}

// Screen 2: Get Started
@Composable
fun CitizenGetStartedScreen(onGetStarted: () -> Unit, onLogin: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = KavachRedPrimary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Kavach",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = KavachRedPrimary
                )
            }
            Text(
                text = "Your Safety. Our Priority.",
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Graphic Illustration Box
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .clip(CircleShape)
                    .background(KavachRedLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    tint = KavachRedPrimary,
                    modifier = Modifier.size(110.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Be Prepared.\nStay Safe.\nStay Empowered.",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                lineHeight = 36.sp,
                color = Color(0xFF1E1E24)
            )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            KavachButton(
                text = "Get Started",
                onClick = onGetStarted,
                containerColor = KavachRedPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account? ",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Login",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = KavachRedPrimary,
                    modifier = Modifier.clickable { onLogin() }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

// Screen 3: Choose Your Role
@Composable
fun RoleSelectionScreen(
    viewModel: KavachViewModel,
    onRoleConfirmed: (UserRole) -> Unit
) {
    var selectedRole by remember { mutableStateOf(UserRole.CITIZEN) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Choose Your Role",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E24)
            )

            Spacer(modifier = Modifier.height(30.dp))

            RoleSelectionCard(
                title = "I am a User",
                subtitle = "Personal safety and emergency help.",
                icon = Icons.Default.Person,
                isSelected = selectedRole == UserRole.CITIZEN,
                accentColor = KavachRedPrimary,
                onClick = { selectedRole = UserRole.CITIZEN }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoleSelectionCard(
                title = "I am an Authority",
                subtitle = "Government or emergency responder.",
                icon = Icons.Default.Security,
                isSelected = selectedRole == UserRole.AUTHORITY,
                accentColor = AuthorityBluePrimary,
                onClick = { selectedRole = UserRole.AUTHORITY }
            )
        }

        KavachButton(
            text = "Continue",
            onClick = {
                viewModel.selectRole(selectedRole)
                onRoleConfirmed(selectedRole)
            },
            containerColor = if (selectedRole == UserRole.CITIZEN) KavachRedPrimary else AuthorityBluePrimary
        )
    }
}

// Screen 4: Sign Up / Login (Citizen)
@Composable
fun CitizenAuthScreen(
    onSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Login, 1: Sign Up
    var emailOrPhone by remember { mutableStateOf("muskan@example.com") }
    var password by remember { mutableStateOf("••••••••") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            KavachTopBar(title = "", onBackClick = onBack)

            Text(
                text = "Welcome Back",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E24)
            )
            Text(
                text = "Login to your Kavach account",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color(0xFFF5F5F7),
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = KavachRedPrimary
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Login", fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Sign Up", fontWeight = FontWeight.Bold) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Email / Phone Number", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = emailOrPhone,
                onValueChange = { emailOrPhone = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Password", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Forgot Password?",
                fontSize = 13.sp,
                color = KavachRedPrimary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(28.dp))

            KavachButton(
                text = if (selectedTab == 0) "Login" else "Sign Up",
                onClick = onSuccess,
                containerColor = KavachRedPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Or continue with",
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                KavachOutlinedButton(
                    text = "Google",
                    onClick = onSuccess,
                    modifier = Modifier.weight(1f),
                    borderColor = Color.LightGray,
                    contentColor = Color.DarkGray
                )
                KavachOutlinedButton(
                    text = "Apple",
                    onClick = onSuccess,
                    modifier = Modifier.weight(1f),
                    borderColor = Color.LightGray,
                    contentColor = Color.DarkGray
                )
            }
        }
    }
}

// Screen 5: Permissions
@Composable
fun CitizenPermissionsScreen(onContinue: () -> Unit, onBack: () -> Unit) {
    var locationGranted by remember { mutableStateOf(true) }
    var micGranted by remember { mutableStateOf(true) }
    var cameraGranted by remember { mutableStateOf(true) }
    var notifGranted by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            KavachTopBar(title = "", onBackClick = onBack)

            Text(
                text = "Permissions",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E24)
            )
            Text(
                text = "We need a few permissions to keep you safe.",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(30.dp))

            PermissionRow(
                icon = Icons.Default.LocationOn,
                title = "Location",
                subtitle = "For live location tracking",
                isChecked = locationGranted,
                onCheckedChange = { locationGranted = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PermissionRow(
                icon = Icons.Default.Mic,
                title = "Microphone",
                subtitle = "For audio evidence",
                isChecked = micGranted,
                onCheckedChange = { micGranted = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PermissionRow(
                icon = Icons.Default.CameraAlt,
                title = "Camera",
                subtitle = "For video/photo evidence",
                isChecked = cameraGranted,
                onCheckedChange = { cameraGranted = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PermissionRow(
                icon = Icons.Default.Notifications,
                title = "Notifications",
                subtitle = "For emergency alerts",
                isChecked = notifGranted,
                onCheckedChange = { notifGranted = it }
            )
        }

        KavachButton(
            text = "Continue",
            onClick = onContinue,
            containerColor = KavachRedPrimary
        )
    }
}

@Composable
fun PermissionRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(KavachRedLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = KavachRedPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = subtitle, fontSize = 13.sp, color = Color.Gray)
            }

            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = KavachRedPrimary
                )
            )
        }
    }
}

// Screen 6: Emergency Contacts
@Composable
fun CitizenContactsScreen(
    viewModel: KavachViewModel,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    val contacts by viewModel.emergencyContacts.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            KavachTopBar(title = "", onBackClick = onBack)

            Text(
                text = "Emergency Contacts",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E24)
            )
            Text(
                text = "Add trusted contacts who will be notified during an emergency.",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f, fill = false)
            ) {
                items(contacts) { contact ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
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
                                    .background(KavachRedLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = contact.name.take(1),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = KavachRedPrimary
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = contact.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = contact.phone,
                                    fontSize = 13.sp,
                                    color = Color.Gray
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = null,
                                tint = KavachRedPrimary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            KavachOutlinedButton(
                text = "+ Add Contact",
                onClick = { showAddDialog = true },
                borderColor = KavachRedPrimary,
                contentColor = KavachRedPrimary
            )
        }

        KavachButton(
            text = "Continue",
            onClick = onContinue,
            containerColor = KavachRedPrimary
        )
    }

    if (showAddDialog) {
        var name by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var relation by remember { mutableStateOf("Friend") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Emergency Contact") },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone Number") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (name.isNotBlank() && phone.isNotBlank()) {
                            viewModel.addContact(name, phone, relation)
                            showAddDialog = false
                        }
                    }
                ) {
                    Text("Add", color = KavachRedPrimary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

// Screen 7: Emergency Profile Setup
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitizenProfileSetupScreen(
    viewModel: KavachViewModel,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    val bloodGroups = listOf("O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-")
    var selectedBlood by remember { mutableStateOf("O+") }
    var isExpanded by remember { mutableStateOf(false) }

    var medicalInfo by remember { mutableStateOf("Asthma, Mild Hypertension") }
    var allergies by remember { mutableStateOf("Peanuts, Dust, Penicillin") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            KavachTopBar(title = "", onBackClick = onBack)

            Text(
                text = "Emergency Profile",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E24)
            )
            Text(
                text = "Add your personal and medical details (optional)",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text("Blood Group", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))

            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedBlood,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    bloodGroups.forEach { bg ->
                        DropdownMenuItem(
                            text = { Text(bg) },
                            onClick = {
                                selectedBlood = bg
                                isExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text("Medical Information", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = medicalInfo,
                onValueChange = { medicalInfo = it },
                placeholder = { Text("e.g. Asthma, Diabetes etc.") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text("Allergies", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = allergies,
                onValueChange = { allergies = it },
                placeholder = { Text("e.g. Peanuts, Dust etc.") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        }

        KavachButton(
            text = "Continue",
            onClick = {
                viewModel.updateMedicalProfile(selectedBlood, medicalInfo, allergies)
                onContinue()
            },
            containerColor = KavachRedPrimary
        )
    }
}

// Screen 8: SOS Shortcut Setup
@Composable
fun CitizenShortcutScreen(
    viewModel: KavachViewModel,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    var homeScreen by remember { mutableStateOf(true) }
    var quickSettings by remember { mutableStateOf(true) }
    var sideButton by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            KavachTopBar(title = "", onBackClick = onBack)

            Text(
                text = "SOS Shortcut",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E24)
            )
            Text(
                text = "Add a quick access button to your home screen.",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Shortcut Preview Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF2F4F7)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(KavachRedPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Kavach", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            ShortcutOptionRow(
                title = "Add to Home Screen",
                isChecked = homeScreen,
                onCheckedChange = { homeScreen = it }
            )

            Spacer(modifier = Modifier.height(14.dp))

            ShortcutOptionRow(
                title = "Use Quick Settings",
                isChecked = quickSettings,
                onCheckedChange = { quickSettings = it }
            )

            Spacer(modifier = Modifier.height(14.dp))

            ShortcutOptionRow(
                title = "Use Side Button (Optional)",
                isChecked = sideButton,
                onCheckedChange = { sideButton = it }
            )
        }

        KavachButton(
            text = "Continue",
            onClick = {
                viewModel.updateShortcutSettings(homeScreen, quickSettings, sideButton)
                onContinue()
            },
            containerColor = KavachRedPrimary
        )
    }
}

@Composable
fun ShortcutOptionRow(
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF9FAFB))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = KavachRedPrimary
            )
        )
    }
}

// Screen 9: Home Screen (User)
@Composable
fun CitizenHomeScreen(
    viewModel: KavachViewModel,
    onTriggerSos: () -> Unit,
    onNavigateToEvidence: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToMedical: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToContacts: () -> Unit
) {
    var selectedBottomTab by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = DarkBackground,
        bottomBar = {
            NavigationBar(
                containerColor = DarkCard,
                contentColor = Color.White
            ) {
                NavigationBarItem(
                    selected = selectedBottomTab == 0,
                    onClick = { selectedBottomTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = KavachRedPrimary,
                        selectedTextColor = KavachRedPrimary,
                        indicatorColor = DarkCard
                    )
                )
                NavigationBarItem(
                    selected = selectedBottomTab == 1,
                    onClick = {
                        selectedBottomTab = 1
                        onNavigateToContacts()
                    },
                    icon = { Icon(Icons.Default.Phone, contentDescription = "Contacts") },
                    label = { Text("Contacts") }
                )
                NavigationBarItem(
                    selected = selectedBottomTab == 2,
                    onClick = {
                        selectedBottomTab = 2
                        onNavigateToSettings()
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
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(KavachRedPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Kavach",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                        Text(
                            text = "Your Safety, Our Priority",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                IconButton(onClick = onNavigateToSettings) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Central Massive Pulsing Glowing SOS Button
            SosPulsingButton(
                onClick = onTriggerSos,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 4 Quick Action Cards Grid
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionCard(
                        title = "Live Evidence",
                        icon = Icons.Default.CameraAlt,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToEvidence
                    )
                    QuickActionCard(
                        title = "Incident History",
                        icon = Icons.Default.History,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToHistory
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionCard(
                        title = "Medical Info",
                        icon = Icons.Default.Security,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToMedical
                    )
                    QuickActionCard(
                        title = "Settings",
                        icon = Icons.Default.Settings,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToSettings
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(78.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard),
        border = BorderStroke(1.dp, DarkBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(KavachRedPrimary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = KavachRedPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}
