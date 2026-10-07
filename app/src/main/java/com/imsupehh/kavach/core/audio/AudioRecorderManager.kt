package com.imsupehh.kavach.core.audio

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class AudioRecordingState(
    val isRecording: Boolean = false,
    val elapsedSeconds: Int = 0,
    val formattedTimer: String = "00:00:00",
    val waveformAmplitudes: List<Float> = List(24) { 0.2f },
    val audioFilePath: String = "",
    val isUploaded: Boolean = false
)

class AudioRecorderManager(
    private val context: Context,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private val _state = MutableStateFlow(AudioRecordingState())
    val state: StateFlow<AudioRecordingState> = _state.asStateFlow()

    private var recordingJob: Job? = null

    fun startRecording(incidentId: String) {
        if (_state.value.isRecording) return

        recordingJob?.cancel()
        _state.value = AudioRecordingState(
            isRecording = true,
            audioFilePath = "incident_${incidentId}/audio_ambient.m4a"
        )

        recordingJob = scope.launch {
            var seconds = 0
            while (_state.value.isRecording) {
                delay(1000)
                seconds++

                val hours = seconds / 3600
                val minutes = (seconds % 3600) / 60
                val secs = seconds % 60
                val timerFormatted = "%02d:%02d:%02d".format(hours, minutes, secs)

                // Generate simulated speech/ambient audio amplitude bars
                val newAmplitudes = List(24) {
                    Random.nextFloat().coerceIn(0.15f, 0.95f)
                }

                _state.value = _state.value.copy(
                    elapsedSeconds = seconds,
                    formattedTimer = timerFormatted,
                    waveformAmplitudes = newAmplitudes
                )
            }
        }
    }

    fun stopRecording() {
        recordingJob?.cancel()
        _state.value = _state.value.copy(
            isRecording = false,
            isUploaded = true
        )
    }

    fun reset() {
        recordingJob?.cancel()
        _state.value = AudioRecordingState()
    }
}
