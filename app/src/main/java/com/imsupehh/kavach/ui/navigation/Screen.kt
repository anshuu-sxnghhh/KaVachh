package com.imsupehh.kavach.ui.navigation

sealed class Screen(val route: String) {
    // Shared / Entry
    data object RoleSelection : Screen("role_selection")

    // Citizen Flow
    data object CitizenSplash : Screen("citizen_splash")
    data object CitizenGetStarted : Screen("citizen_get_started")
    data object CitizenAuth : Screen("citizen_auth")
    data object CitizenPermissions : Screen("citizen_permissions")
    data object CitizenContacts : Screen("citizen_contacts")
    data object CitizenProfileSetup : Screen("citizen_profile_setup")
    data object CitizenShortcut : Screen("citizen_shortcut")
    data object CitizenHome : Screen("citizen_home")
    data object ActiveSos : Screen("active_sos")
    data object IncidentHistory : Screen("incident_history")
    data object AiReport : Screen("ai_report")
    data object CitizenProfile : Screen("citizen_profile")

    // Authority Flow
    data object AuthoritySplash : Screen("authority_splash")
    data object AuthorityGetStarted : Screen("authority_get_started")
    data object DepartmentLogin : Screen("department_login")
    data object OfficialVerification : Screen("official_verification")
    data object AuthorityDashboard : Screen("authority_dashboard")
    data object LiveIncidents : Screen("live_incidents")
    data object IncidentDetails : Screen("incident_details")
    data object EvidenceViewer : Screen("evidence_viewer")
    data object AiAnalysisReport : Screen("ai_analysis_report")
    data object TeamActions : Screen("team_actions")
    data object AuthorityProfile : Screen("authority_profile")
}
