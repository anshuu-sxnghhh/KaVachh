package com.imsupehh.kavach.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.imsupehh.kavach.core.audio.AudioRecorderManager
import com.imsupehh.kavach.core.camera.CameraBurstManager
import com.imsupehh.kavach.core.location.LocationTrackerManager
import com.imsupehh.kavach.data.models.EmergencyContact
import com.imsupehh.kavach.data.models.Incident
import com.imsupehh.kavach.data.models.MedicalProfile
import com.imsupehh.kavach.data.models.SosShortcutSettings
import com.imsupehh.kavach.data.models.UserProfile
import com.imsupehh.kavach.data.models.UserRole
import com.imsupehh.kavach.repository.KavachRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class KavachViewModel(application: Application) : AndroidViewModel(application) {

    val repository = KavachRepository()
    val cameraManager = CameraBurstManager(application, viewModelScope)
    val audioManager = AudioRecorderManager(application, viewModelScope)
    val locationTracker = LocationTrackerManager(application)

    val currentUser = repository.currentUser
    val emergencyContacts = repository.emergencyContacts
    val medicalProfile = repository.medicalProfile
    val shortcutSettings = repository.shortcutSettings
    val activeIncident = repository.activeIncident
    val incidents = repository.incidents
    val teamActions = repository.teamActions

    val cameraBurstState = cameraManager.state
    val audioState = audioManager.state
    val locationState = locationTracker.state

    // Selected Incident for Authority Detail View
    private val _selectedIncident = MutableStateFlow<Incident?>(null)
    val selectedIncident: StateFlow<Incident?> = _selectedIncident.asStateFlow()

    // Authority Incident Filter
    private val _incidentFilter = MutableStateFlow("ALL")
    val incidentFilter: StateFlow<String> = _incidentFilter.asStateFlow()

    fun selectRole(role: UserRole) {
        repository.setUserRole(role)
    }

    fun selectIncident(incident: Incident) {
        _selectedIncident.value = incident
    }

    fun setFilter(filter: String) {
        _incidentFilter.value = filter
    }

    fun addContact(name: String, phone: String, relationship: String) {
        repository.addEmergencyContact(
            EmergencyContact(
                name = name,
                phone = phone,
                relationship = relationship
            )
        )
    }

    fun removeContact(contactId: String) {
        repository.removeEmergencyContact(contactId)
    }

    fun updateMedicalProfile(bloodGroup: String, conditions: String, allergies: String) {
        repository.updateMedicalProfile(
            MedicalProfile(
                bloodGroup = bloodGroup,
                medicalConditions = conditions,
                allergies = allergies
            )
        )
    }

    fun updateShortcutSettings(addToHomeScreen: Boolean, useQuickSettings: Boolean, useSideButton: Boolean) {
        repository.updateShortcutSettings(
            SosShortcutSettings(
                addToHomeScreen = addToHomeScreen,
                useQuickSettings = useQuickSettings,
                useSideButton = useSideButton
            )
        )
    }

    /**
     * Activates Full SOS Emergency Response Pipeline:
     * 1. Create unique incident record
     * 2. Start GPS Location tracking
     * 3. Start 30-photo burst (15 Rear + 15 Front alternating)
     * 4. Start Synchronized ambient audio recording
     */
    fun triggerSos() {
        val incident = repository.createSosIncident()
        locationTracker.startTracking()
        cameraManager.startBurstCapture(incident.id)
        audioManager.startRecording(incident.id)
    }

    /**
     * Cancels / Deactivates active emergency:
     * Stops camera and audio capture, logs cancellation in incident audit trail.
     */
    fun cancelSos() {
        val current = activeIncident.value
        if (current != null) {
            cameraManager.stopBurstCapture()
            audioManager.stopRecording()
            locationTracker.stopTracking()
            repository.cancelSosIncident(current.id)
        }
    }

    fun executeTeamAction(actionId: String) {
        repository.triggerTeamAction(actionId)
    }
}
