package com.imsupehh.kavach.core.location

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class LocationState(
    val latitude: Double = 28.6280,
    val longitude: Double = 77.3649,
    val accuracyMeters: Float = 4.2f,
    val address: String = "Sector 62, Noida, Uttar Pradesh",
    val timestamp: String = "",
    val isTracking: Boolean = false
)

class LocationTrackerManager(private val context: Context) {
    private val _state = MutableStateFlow(
        LocationState(
            timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        )
    )
    val state: StateFlow<LocationState> = _state.asStateFlow()

    fun startTracking() {
        _state.value = _state.value.copy(
            isTracking = true,
            timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        )
    }

    fun stopTracking() {
        _state.value = _state.value.copy(isTracking = false)
    }

    fun updateCoordinates(lat: Double, lng: Double, address: String) {
        _state.value = _state.value.copy(
            latitude = lat,
            longitude = lng,
            address = address,
            timestamp = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        )
    }
}
