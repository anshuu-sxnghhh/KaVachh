package com.imsupehh.kavach.repository

import com.imsupehh.kavach.data.models.EmergencyContact
import com.imsupehh.kavach.data.models.Incident
import com.imsupehh.kavach.data.models.IncidentPriority
import com.imsupehh.kavach.data.models.IncidentStatus
import com.imsupehh.kavach.data.models.MedicalProfile
import com.imsupehh.kavach.data.models.SosShortcutSettings
import com.imsupehh.kavach.data.models.TeamActionItem
import com.imsupehh.kavach.data.models.UserProfile
import com.imsupehh.kavach.data.models.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class KavachRepository {

    // Current User Profile
    private val _currentUser = MutableStateFlow(UserProfile())
    val currentUser: StateFlow<UserProfile> = _currentUser.asStateFlow()

    // Emergency Contacts (Matching mockup Screen 6)
    private val _emergencyContacts = MutableStateFlow(
        listOf(
            EmergencyContact(name = "Riya Sharma", phone = "+91 98765 43210", relationship = "Friend", isPrimary = true),
            EmergencyContact(name = "Neha Singh", phone = "+91 87654 32109", relationship = "Sister"),
            EmergencyContact(name = "Papa", phone = "+91 95687 69012", relationship = "Father")
        )
    )
    val emergencyContacts: StateFlow<List<EmergencyContact>> = _emergencyContacts.asStateFlow()

    // Medical Profile (Matching mockup Screen 7)
    private val _medicalProfile = MutableStateFlow(MedicalProfile())
    val medicalProfile: StateFlow<MedicalProfile> = _medicalProfile.asStateFlow()

    // SOS Shortcut Settings (Matching mockup Screen 8)
    private val _shortcutSettings = MutableStateFlow(SosShortcutSettings())
    val shortcutSettings: StateFlow<SosShortcutSettings> = _shortcutSettings.asStateFlow()

    // Active Incident (null when idle)
    private val _activeIncident = MutableStateFlow<Incident?>(null)
    val activeIncident: StateFlow<Incident?> = _activeIncident.asStateFlow()

    // Authority Incident Feed (Matching mockup Screen 6 & 7)
    private val _incidents = MutableStateFlow(
        listOf(
            Incident(
                id = "inc-1024",
                code = "KAV-1024",
                category = "Physical Threat",
                priority = IncidentPriority.HIGH,
                status = IncidentStatus.ACTIVE,
                locationAddress = "Sector 62, Noida",
                distanceKm = 2.4,
                timeFormatted = "10:42 PM",
                dateFormatted = "12 Jan 2025",
                aiClassification = "Possible Physical Threat",
                aiConfidencePercent = 84,
                keyIndicators = listOf(
                    "Distress-related speech",
                    "Multiple voices detected",
                    "Sudden movement",
                    "Unusual interaction pattern"
                )
            ),
            Incident(
                id = "inc-1023",
                code = "KAV-1023",
                category = "Medical Emergency",
                priority = IncidentPriority.MEDIUM,
                status = IncidentStatus.ACTIVE,
                locationAddress = "Sector 18, Noida",
                distanceKm = 3.6,
                timeFormatted = "10:28 PM",
                dateFormatted = "12 Jan 2025",
                aiClassification = "Severe Medical Distress",
                aiConfidencePercent = 91,
                keyIndicators = listOf(
                    "Rapid shallow breathing",
                    "Loss of responsiveness",
                    "Sudden collapse detected"
                )
            ),
            Incident(
                id = "inc-1022",
                code = "KAV-1022",
                category = "Harassment Alert",
                priority = IncidentPriority.HIGH,
                status = IncidentStatus.ACTIVE,
                locationAddress = "Atta Market, Noida",
                distanceKm = 5.1,
                timeFormatted = "09:15 PM",
                dateFormatted = "12 Jan 2025",
                aiClassification = "Aggressive Verbal Threat",
                aiConfidencePercent = 79,
                keyIndicators = listOf(
                    "Aggressive stalking behavior",
                    "Raised confrontational voices",
                    "Crowded public perimeter"
                )
            )
        )
    )
    val incidents: StateFlow<List<Incident>> = _incidents.asStateFlow()

    // Team Actions (Matching mockup Screen 12)
    private val _teamActions = MutableStateFlow(
        listOf(
            TeamActionItem(
                title = "Dispatch Patrol",
                timestamp = "20 Dec 2024 - 08:30 PM",
                location = "Sector 62, Noida",
                isResolved = false,
                actionType = "PATROL"
            ),
            TeamActionItem(
                title = "Share with Control Room",
                timestamp = "20 Dec 2024 - 08:32 PM",
                location = "Headquarters Link",
                isResolved = true,
                actionType = "COMMUNICATION"
            ),
            TeamActionItem(
                title = "Notify Medical Team",
                timestamp = "20 Dec 2024 - 08:35 PM",
                location = "District Hospital Ambulance 108",
                isResolved = false,
                actionType = "AMBULANCE"
            ),
            TeamActionItem(
                title = "Notify Neighbor Alert",
                timestamp = "20 Dec 2024 - 08:36 PM",
                location = "Sector 62 Resident Beacon",
                isResolved = false,
                actionType = "COMMUNITY"
            )
        )
    )
    val teamActions: StateFlow<List<TeamActionItem>> = _teamActions.asStateFlow()

    fun setUserRole(role: UserRole) {
        _currentUser.value = _currentUser.value.copy(role = role)
    }

    fun updateProfile(profile: UserProfile) {
        _currentUser.value = profile
    }

    fun addEmergencyContact(contact: EmergencyContact) {
        _emergencyContacts.value = _emergencyContacts.value + contact
    }

    fun removeEmergencyContact(contactId: String) {
        _emergencyContacts.value = _emergencyContacts.value.filter { it.id != contactId }
    }

    fun updateMedicalProfile(profile: MedicalProfile) {
        _medicalProfile.value = profile
    }

    fun updateShortcutSettings(settings: SosShortcutSettings) {
        _shortcutSettings.value = settings
    }

    fun createSosIncident(): Incident {
        val newIncident = Incident(
            id = UUID.randomUUID().toString(),
            code = "KAV-${(1000..9999).random()}",
            citizenName = _currentUser.value.name,
            citizenPhone = _currentUser.value.phone,
            timeFormatted = "Just now",
            dateFormatted = "Today"
        )
        _activeIncident.value = newIncident
        _incidents.value = listOf(newIncident) + _incidents.value
        return newIncident
    }

    fun cancelSosIncident(incidentId: String) {
        _activeIncident.value?.let { active ->
            if (active.id == incidentId) {
                _activeIncident.value = null
            }
        }
        _incidents.value = _incidents.value.map {
            if (it.id == incidentId) it.copy(status = IncidentStatus.FALSE_ALARM) else it
        }
    }

    fun resolveIncident(incidentId: String) {
        _incidents.value = _incidents.value.map {
            if (it.id == incidentId) it.copy(status = IncidentStatus.RESOLVED) else it
        }
    }

    fun triggerTeamAction(actionId: String) {
        _teamActions.value = _teamActions.value.map {
            if (it.id == actionId) it.copy(isResolved = true) else it
        }
    }
}
