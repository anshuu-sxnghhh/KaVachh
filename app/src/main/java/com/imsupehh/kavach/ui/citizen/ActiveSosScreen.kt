package com.imsupehh.kavach.ui.citizen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imsupehh.kavach.data.models.CameraLensType
import com.imsupehh.kavach.ui.components.WaveformVisualizer
import com.imsupehh.kavach.ui.theme.DarkBackground
import com.imsupehh.kavach.ui.theme.DarkCard
import com.imsupehh.kavach.ui.theme.KavachRedGlow
import com.imsupehh.kavach.ui.theme.KavachRedPrimary
import com.imsupehh.kavach.viewmodel.KavachViewModel

@Composable
fun ActiveSosScreen(
    viewModel: KavachViewModel,
    onCancelSos: () -> Unit,
    onViewReport: () -> Unit,
    onBack: () -> Unit
) {
    val cameraState by viewModel.cameraBurstState.collectAsState()
    val audioState by viewModel.audioState.collectAsState()
    val locationState by viewModel.locationState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "Live Evidence",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(KavachRedPrimary.copy(alpha = 0.2f))
                        .border(1.dp, KavachRedPrimary.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(KavachRedPrimary)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Recording... ${audioState.formattedTimer}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dual Camera Feeds / Sequence Indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Rear Camera Status Tile
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkCard),
                    border = BorderStroke(
                        width = if (cameraState.currentLensFacing == CameraLensType.REAR && cameraState.isCapturing) 2.dp else 1.dp,
                        color = if (cameraState.currentLensFacing == CameraLensType.REAR && cameraState.isCapturing) KavachRedPrimary else Color(0xFF2C2C38)
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Rear Camera", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("${cameraState.rearPhotosCaptured}/15", fontSize = 12.sp, color = KavachRedGlow)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { cameraState.rearPhotosCaptured / 15f },
                            modifier = Modifier.fillMaxWidth().height(6.dp),
                            color = KavachRedPrimary,
                            trackColor = Color.DarkGray,
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (cameraState.rearPhotosCaptured == 15) "Burst Complete" else "Capturing...",
                            fontSize = 11.sp,
                            color = Color.LightGray
                        )
                    }
                }

                // Front Camera Status Tile
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkCard),
                    border = BorderStroke(
                        width = if (cameraState.currentLensFacing == CameraLensType.FRONT && cameraState.isCapturing) 2.dp else 1.dp,
                        color = if (cameraState.currentLensFacing == CameraLensType.FRONT && cameraState.isCapturing) KavachRedPrimary else Color(0xFF2C2C38)
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Front Camera", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text("${cameraState.frontPhotosCaptured}/15", fontSize = 12.sp, color = KavachRedGlow)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { cameraState.frontPhotosCaptured / 15f },
                            modifier = Modifier.fillMaxWidth().height(6.dp),
                            color = KavachRedPrimary,
                            trackColor = Color.DarkGray,
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (cameraState.frontPhotosCaptured == 15) "Burst Complete" else "Capturing...",
                            fontSize = 11.sp,
                            color = Color.LightGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Captured Evidence Frames Gallery Preview (Horizontal scroll)
            Text(
                text = "Captured Frames (${cameraState.photos.size}/30)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(cameraState.photos) { photo ->
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF262632))
                            .border(1.dp, Color(0xFF3E3E4F), RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = null,
                                tint = KavachRedGlow,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${photo.lensType.name.take(1)}#${photo.sequenceNumber}",
                                fontSize = 10.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Audio Recording Waveform Visualizer
            WaveformVisualizer(
                isRecording = audioState.isRecording,
                amplitudes = audioState.waveformAmplitudes,
                timer = audioState.formattedTimer
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Live Location Breadcrumb
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCard)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(KavachRedPrimary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = KavachRedPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Live Location Sent", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(locationState.address, fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons
        Column(modifier = Modifier.fillMaxWidth()) {
            if (cameraState.isComplete || !audioState.isRecording) {
                Button(
                    onClick = onViewReport,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Text("View AI Incident Report", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = onCancelSos,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(containerColor = KavachRedPrimary)
            ) {
                Text(
                    text = "Cancel SOS",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
