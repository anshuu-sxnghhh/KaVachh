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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalPolice
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imsupehh.kavach.ui.components.KavachButton
import com.imsupehh.kavach.ui.components.KavachOutlinedButton
import com.imsupehh.kavach.ui.components.KavachTopBar
import com.imsupehh.kavach.ui.theme.AuthorityBlueAccent
import com.imsupehh.kavach.ui.theme.AuthorityBlueDark
import com.imsupehh.kavach.ui.theme.AuthorityBluePrimary
import com.imsupehh.kavach.ui.theme.AuthorityNavyBackground
import com.imsupehh.kavach.ui.theme.AuthorityNavyCard
import com.imsupehh.kavach.ui.theme.DarkBackground
import com.imsupehh.kavach.viewmodel.KavachViewModel

// Screen 1: Authority Splash Screen
@Composable
fun AuthoritySplashScreen(onContinue: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthorityNavyBackground)
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
                            listOf(AuthorityBlueAccent, AuthorityBluePrimary, AuthorityBlueDark)
                        )
                    )
                    .border(3.dp, Color.White.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocalPolice,
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

            Text(
                text = "Authority Portal",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AuthorityBlueAccent
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Real-Time Response. Safer Communities.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}

// Screen 2: Authority Get Started
@Composable
fun AuthorityGetStartedScreen(onGetStarted: () -> Unit, onLogin: () -> Unit) {
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
                    imageVector = Icons.Default.LocalPolice,
                    contentDescription = null,
                    tint = AuthorityBluePrimary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Kavach",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = AuthorityBluePrimary
                )
            }
            Text(
                text = "Authority Portal",
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier
                    .size(240.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE3F2FD)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    tint = AuthorityBluePrimary,
                    modifier = Modifier.size(110.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Empowering Authorities\nfor a Safer Tomorrow",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                lineHeight = 34.sp,
                color = Color(0xFF1E1E24)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Access live incidents, evidence\nand AI-powered insights.",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            KavachButton(
                text = "Get Started",
                onClick = onGetStarted,
                containerColor = AuthorityBluePrimary
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
                    color = AuthorityBluePrimary,
                    modifier = Modifier.clickable { onLogin() }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

// Screen 4: Department Login
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepartmentLoginScreen(
    onSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val departments = listOf(
        "Uttar Pradesh Police",
        "Delhi Police",
        "Emergency Medical Services (108)",
        "State Disaster Response Force (SDRF)",
        "Fire & Rescue Services"
    )
    var selectedDept by remember { mutableStateOf(departments.first()) }
    var isExpanded by remember { mutableStateOf(false) }

    var officialId by remember { mutableStateOf("UP123456") }
    var password by remember { mutableStateOf("••••••••") }
    var rememberMe by remember { mutableStateOf(true) }

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
                text = "Department Login",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E24)
            )
            Text(
                text = "Sign in with your official credentials.",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text("Department", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))

            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedDept,
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
                    departments.forEach { dept ->
                        DropdownMenuItem(
                            text = { Text(dept) },
                            onClick = {
                                selectedDept = dept
                                isExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text("Official ID", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = officialId,
                onValueChange = { officialId = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(checkedColor = AuthorityBluePrimary)
                    )
                    Text("Remember Me", fontSize = 13.sp, color = Color.DarkGray)
                }
                Text("Forgot password?", fontSize = 13.sp, color = AuthorityBluePrimary, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(28.dp))

            KavachButton(
                text = "Sign In",
                onClick = onSuccess,
                containerColor = AuthorityBluePrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            KavachOutlinedButton(
                text = "Sign in with Government SSO",
                onClick = onSuccess,
                borderColor = AuthorityBluePrimary,
                contentColor = AuthorityBluePrimary
            )
        }
    }
}

// Screen 5: Official Verification
@Composable
fun OfficialVerificationScreen(
    viewModel: KavachViewModel,
    onVerified: () -> Unit,
    onBack: () -> Unit
) {
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
                text = "Official Verification",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E1E24)
            )
            Text(
                text = "Verify your department details",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(28.dp))

            VerificationField(label = "Department", value = "Uttar Pradesh Police")
            Spacer(modifier = Modifier.height(14.dp))
            VerificationField(label = "Employee ID", value = "UP123456")
            Spacer(modifier = Modifier.height(14.dp))
            VerificationField(label = "Designation", value = "Sub Inspector")
            Spacer(modifier = Modifier.height(14.dp))
            VerificationField(label = "District", value = "Noida")
            Spacer(modifier = Modifier.height(14.dp))
            VerificationField(label = "Official Email", value = "official@upp.gov.in")
        }

        KavachButton(
            text = "Verify & Continue",
            onClick = onVerified,
            containerColor = AuthorityBluePrimary
        )
    }
}

@Composable
fun VerificationField(label: String, value: String) {
    Column {
        Text(text = label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
    }
}
