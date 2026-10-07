package com.imsupehh.kavach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imsupehh.kavach.data.models.UserRole
import com.imsupehh.kavach.ui.authority.AiAnalysisReportAuthorityScreen
import com.imsupehh.kavach.ui.authority.AuthorityDashboardScreen
import com.imsupehh.kavach.ui.authority.AuthorityGetStartedScreen
import com.imsupehh.kavach.ui.authority.AuthorityProfileScreen
import com.imsupehh.kavach.ui.authority.AuthoritySplashScreen
import com.imsupehh.kavach.ui.authority.DepartmentLoginScreen
import com.imsupehh.kavach.ui.authority.EvidenceViewerScreen
import com.imsupehh.kavach.ui.authority.IncidentDetailsScreen
import com.imsupehh.kavach.ui.authority.LiveIncidentsScreen
import com.imsupehh.kavach.ui.authority.OfficialVerificationScreen
import com.imsupehh.kavach.ui.authority.TeamActionsScreen
import com.imsupehh.kavach.ui.citizen.ActiveSosScreen
import com.imsupehh.kavach.ui.citizen.AiIncidentReportScreen
import com.imsupehh.kavach.ui.citizen.CitizenAuthScreen
import com.imsupehh.kavach.ui.citizen.CitizenContactsScreen
import com.imsupehh.kavach.ui.citizen.CitizenGetStartedScreen
import com.imsupehh.kavach.ui.citizen.CitizenHomeScreen
import com.imsupehh.kavach.ui.citizen.CitizenPermissionsScreen
import com.imsupehh.kavach.ui.citizen.CitizenProfileScreen
import com.imsupehh.kavach.ui.citizen.CitizenProfileSetupScreen
import com.imsupehh.kavach.ui.citizen.CitizenShortcutScreen
import com.imsupehh.kavach.ui.citizen.CitizenSplashScreen
import com.imsupehh.kavach.ui.citizen.RoleSelectionScreen
import com.imsupehh.kavach.ui.navigation.Screen
import com.imsupehh.kavach.ui.theme.KaVachTheme
import com.imsupehh.kavach.viewmodel.KavachViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: KavachViewModel = viewModel()
            val user by viewModel.currentUser.collectAsState()
            val isAuthority = user.role == UserRole.AUTHORITY

            KaVachTheme(isAuthorityTheme = isAuthority) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    KavachNavHost(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun KavachNavHost(viewModel: KavachViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.CitizenSplash.route
    ) {
        // --- Shared & Onboarding Routes ---
        composable(Screen.CitizenSplash.route) {
            CitizenSplashScreen(
                onContinue = { navController.navigate(Screen.CitizenGetStarted.route) }
            )
        }

        composable(Screen.CitizenGetStarted.route) {
            CitizenGetStartedScreen(
                onGetStarted = { navController.navigate(Screen.RoleSelection.route) },
                onLogin = { navController.navigate(Screen.CitizenAuth.route) }
            )
        }

        composable(Screen.RoleSelection.route) {
            RoleSelectionScreen(
                viewModel = viewModel,
                onRoleConfirmed = { role ->
                    if (role == UserRole.CITIZEN) {
                        navController.navigate(Screen.CitizenAuth.route)
                    } else {
                        navController.navigate(Screen.AuthoritySplash.route)
                    }
                }
            )
        }

        // --- Citizen Flow Routes ---
        composable(Screen.CitizenAuth.route) {
            CitizenAuthScreen(
                onSuccess = { navController.navigate(Screen.CitizenPermissions.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.CitizenPermissions.route) {
            CitizenPermissionsScreen(
                onContinue = { navController.navigate(Screen.CitizenContacts.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.CitizenContacts.route) {
            CitizenContactsScreen(
                viewModel = viewModel,
                onContinue = { navController.navigate(Screen.CitizenProfileSetup.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.CitizenProfileSetup.route) {
            CitizenProfileSetupScreen(
                viewModel = viewModel,
                onContinue = { navController.navigate(Screen.CitizenShortcut.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.CitizenShortcut.route) {
            CitizenShortcutScreen(
                viewModel = viewModel,
                onContinue = {
                    navController.navigate(Screen.CitizenHome.route) {
                        popUpTo(Screen.CitizenSplash.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.CitizenHome.route) {
            CitizenHomeScreen(
                viewModel = viewModel,
                onTriggerSos = {
                    viewModel.triggerSos()
                    navController.navigate(Screen.ActiveSos.route)
                },
                onNavigateToEvidence = { navController.navigate(Screen.ActiveSos.route) },
                onNavigateToHistory = { navController.navigate(Screen.IncidentHistory.route) },
                onNavigateToMedical = { navController.navigate(Screen.CitizenProfileSetup.route) },
                onNavigateToSettings = { navController.navigate(Screen.CitizenProfile.route) },
                onNavigateToContacts = { navController.navigate(Screen.CitizenContacts.route) }
            )
        }

        composable(Screen.ActiveSos.route) {
            ActiveSosScreen(
                viewModel = viewModel,
                onCancelSos = {
                    viewModel.cancelSos()
                    navController.popBackStack()
                },
                onViewReport = { navController.navigate(Screen.AiReport.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.IncidentHistory.route) {
            AiIncidentReportScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AiReport.route) {
            AiIncidentReportScreen(
                viewModel = viewModel,
                onBack = { navController.navigate(Screen.CitizenHome.route) }
            )
        }

        composable(Screen.CitizenProfile.route) {
            CitizenProfileScreen(
                viewModel = viewModel,
                onNavigateToContacts = { navController.navigate(Screen.CitizenContacts.route) },
                onNavigateToMedical = { navController.navigate(Screen.CitizenProfileSetup.route) },
                onLogout = {
                    navController.navigate(Screen.RoleSelection.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        // --- Authority Flow Routes ---
        composable(Screen.AuthoritySplash.route) {
            AuthoritySplashScreen(
                onContinue = { navController.navigate(Screen.AuthorityGetStarted.route) }
            )
        }

        composable(Screen.AuthorityGetStarted.route) {
            AuthorityGetStartedScreen(
                onGetStarted = { navController.navigate(Screen.DepartmentLogin.route) },
                onLogin = { navController.navigate(Screen.DepartmentLogin.route) }
            )
        }

        composable(Screen.DepartmentLogin.route) {
            DepartmentLoginScreen(
                onSuccess = { navController.navigate(Screen.OfficialVerification.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.OfficialVerification.route) {
            OfficialVerificationScreen(
                viewModel = viewModel,
                onVerified = {
                    navController.navigate(Screen.AuthorityDashboard.route) {
                        popUpTo(Screen.AuthoritySplash.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AuthorityDashboard.route) {
            AuthorityDashboardScreen(
                viewModel = viewModel,
                onSelectIncident = { navController.navigate(Screen.IncidentDetails.route) },
                onViewAllIncidents = { navController.navigate(Screen.LiveIncidents.route) },
                onNavigateToProfile = { navController.navigate(Screen.AuthorityProfile.route) },
                onNavigateToActions = { navController.navigate(Screen.TeamActions.route) }
            )
        }

        composable(Screen.LiveIncidents.route) {
            LiveIncidentsScreen(
                viewModel = viewModel,
                onSelectIncident = { navController.navigate(Screen.IncidentDetails.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.IncidentDetails.route) {
            IncidentDetailsScreen(
                viewModel = viewModel,
                onViewEvidence = { navController.navigate(Screen.EvidenceViewer.route) },
                onViewAiReport = { navController.navigate(Screen.AiAnalysisReport.route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.EvidenceViewer.route) {
            EvidenceViewerScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AiAnalysisReport.route) {
            AiAnalysisReportAuthorityScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.TeamActions.route) {
            TeamActionsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AuthorityProfile.route) {
            AuthorityProfileScreen(
                viewModel = viewModel,
                onLogout = {
                    navController.navigate(Screen.RoleSelection.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}