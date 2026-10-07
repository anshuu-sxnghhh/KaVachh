package com.imsupehh.kavach.data.models

import java.util.UUID

enum class UserRole {
    CITIZEN,
    AUTHORITY
}

data class UserProfile(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "Muskan Rawat",
    val email: String = "muskan.rawat@kavach.org",
    val phone: String = "+91 98765 43210",
    val role: UserRole = UserRole.CITIZEN,
    val avatarUrl: String = "",
    // Authority specific fields
    val department: String = "Uttar Pradesh Police",
    val employeeId: String = "UP123456",
    val designation: String = "Sub Inspector",
    val district: String = "Noida",
    val officialEmail: String = "official@upp.gov.in"
)

data class EmergencyContact(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val phone: String,
    val relationship: String = "Family",
    val isPrimary: Boolean = false
)

data class MedicalProfile(
    val bloodGroup: String = "O+",
    val medicalConditions: String = "Asthma, Mild Hypertension",
    val allergies: String = "Peanuts, Dust, Penicillin",
    val emergencyMedications: String = "Inhaler (Albuterol)"
)

enum class IncidentPriority {
    CRITICAL,
    HIGH,
    MEDIUM,
    LOW
}

enum class IncidentStatus {
    ACTIVE,
    RESOLVED,
    FALSE_ALARM
}

enum class CameraLensType {
    REAR,
    FRONT
}

data class EvidencePhoto(
    val id: String = UUID.randomUUID().toString(),
    val lensType: CameraLensType,
    val sequenceNumber: Int, // 1 to 15
    val timestamp: String,
    val filePath: String = "",
    val isUploaded: Boolean = true
)

data class EvidenceAudio(
    val id: String = UUID.randomUUID().toString(),
    val durationSeconds: Int = 60,
    val timestamp: String,
    val filePath: String = "",
    val isUploaded: Boolean = true
)

data class TimelineEvent(
    val id: String = UUID.randomUUID().toString(),
    val timeFormatted: String,
    val title: String,
    val description: String = ""
)

data class Incident(
    val id: String = UUID.randomUUID().toString(),
    val code: String = "KAV-1024",
    val citizenName: String = "Muskan Rawat",
    val citizenPhone: String = "+91 98765 43210",
    val category: String = "Physical Threat",
    val priority: IncidentPriority = IncidentPriority.HIGH,
    val status: IncidentStatus = IncidentStatus.ACTIVE,
    val locationAddress: String = "Sector 62, Noida",
    val latitude: Double = 28.6280,
    val longitude: Double = 77.3649,
    val distanceKm: Double = 2.4,
    val timeFormatted: String = "10:42 PM",
    val dateFormatted: String = "12 Jan 2025",
    val aiClassification: String = "Possible Physical Threat",
    val aiConfidencePercent: Int = 84,
    val keyIndicators: List<String> = listOf(
        "Distress-related speech",
        "Multiple voices detected",
        "Sudden movement",
        "Unusual interaction pattern"
    ),
    val contextSummary: String = "High-stress ambient noise, late night hours, isolated pedestrian corridor.",
    val recommendedResponse: String = "Police Patrol & Emergency Intercept",
    val rearPhotosCount: Int = 15,
    val frontPhotosCount: Int = 15,
    val timeline: List<TimelineEvent> = listOf(
        TimelineEvent(timeFormatted = "10:42:11", title = "SOS Activated"),
        TimelineEvent(timeFormatted = "10:42:17", title = "Audio Upload Started"),
        TimelineEvent(timeFormatted = "10:42:24", title = "Multiple Voices Detected"),
        TimelineEvent(timeFormatted = "10:42:29", title = "Sudden Movement")
    )
)

data class TeamActionItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val timestamp: String,
    val location: String,
    val isResolved: Boolean = false,
    val actionType: String = "DISPATCH"
)

data class SosShortcutSettings(
    val addToHomeScreen: Boolean = true,
    val useQuickSettings: Boolean = true,
    val useSideButton: Boolean = false
)
