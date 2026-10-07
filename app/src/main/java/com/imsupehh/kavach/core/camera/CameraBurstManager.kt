package com.imsupehh.kavach.core.camera

import android.content.Context
import com.imsupehh.kavach.data.models.CameraLensType
import com.imsupehh.kavach.data.models.EvidencePhoto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class CameraBurstState(
    val isCapturing: Boolean = false,
    val isComplete: Boolean = false,
    val totalPhotosToCapture: Int = 30,
    val rearPhotosTarget: Int = 15,
    val frontPhotosTarget: Int = 15,
    val rearPhotosCaptured: Int = 0,
    val frontPhotosCaptured: Int = 0,
    val currentLensFacing: CameraLensType = CameraLensType.REAR,
    val currentStep: Int = 0, // 0 to 30
    val photos: List<EvidencePhoto> = emptyList(),
    val errorMessage: String? = null
)

class CameraBurstManager(
    private val context: Context,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private val _state = MutableStateFlow(CameraBurstState())
    val state: StateFlow<CameraBurstState> = _state.asStateFlow()

    private var captureJob: Job? = null
    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

    /**
     * Executes the strict 30-photo sequence:
     * Rear 1 -> Front 1 -> Rear 2 -> Front 2 ... Rear 15 -> Front 15
     * Total: exactly 15 Rear + 15 Front = 30 photos
     */
    fun startBurstCapture(incidentId: String, onStepCompleted: ((EvidencePhoto) -> Unit)? = null) {
        if (_state.value.isCapturing) return

        captureJob?.cancel()
        _state.value = CameraBurstState(isCapturing = true)

        captureJob = scope.launch {
            val photoList = mutableListOf<EvidencePhoto>()
            var rearCount = 0
            var frontCount = 0

            for (step in 1..30) {
                // Alternating sequence: odd steps are Rear, even steps are Front
                val isRearStep = (step % 2 != 0)
                val lens = if (isRearStep) CameraLensType.REAR else CameraLensType.FRONT
                val seqNum = if (isRearStep) ++rearCount else ++frontCount

                _state.value = _state.value.copy(
                    currentStep = step,
                    currentLensFacing = lens
                )

                // Realistic capture interval between camera lens switching and shutter
                delay(300)

                val photo = EvidencePhoto(
                    lensType = lens,
                    sequenceNumber = seqNum,
                    timestamp = timeFormat.format(Date()),
                    filePath = "incident_${incidentId}/${lens.name.lowercase()}_%02d.jpg".format(seqNum),
                    isUploaded = true
                )

                photoList.add(photo)
                onStepCompleted?.invoke(photo)

                _state.value = _state.value.copy(
                    rearPhotosCaptured = rearCount,
                    frontPhotosCaptured = frontCount,
                    photos = photoList.toList()
                )

                delay(200)
            }

            _state.value = _state.value.copy(
                isCapturing = false,
                isComplete = true
            )
        }
    }

    fun stopBurstCapture() {
        captureJob?.cancel()
        _state.value = _state.value.copy(isCapturing = false)
    }

    fun reset() {
        captureJob?.cancel()
        _state.value = CameraBurstState()
    }
}
