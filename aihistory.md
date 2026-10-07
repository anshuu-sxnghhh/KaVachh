# KAVACH AI HISTORY

## 1. Project Overview
**KAVACH** is an AI-Powered SOS Emergency Response & Evidence Platform designed for high-stress personal safety emergencies and rapid responder intervention. Unlike conventional SOS applications that merely ping emergency contacts with GPS coordinates, KAVACH establishes a forensically sound, end-to-end emergency pipeline:
$$\text{SOS} \longrightarrow \text{Location} \longrightarrow \text{Evidence Capture} \longrightarrow \text{AI Analysis} \longrightarrow \text{Incident Timeline} \longrightarrow \text{Structured Report} \longrightarrow \text{Emergency Routing} \longrightarrow \text{Human Response}$$

The platform operates across two distinct interfaces:
1. **Citizen (User) Mobile App**: Fast, high-contrast, zero-hesitation emergency trigger, automated 30-photo burst capture (15 rear + 15 front), secure audio recording, live status, and personal safety profile.
2. **Department / Authority Dashboard**: Real-time responder portal for police, medical, fire, and disaster teams featuring live map tracking, triaged incident queues, structured AI assessments with confidence scoring, evidence player/viewer, and dispatch action routing.

---

## 2. Product Vision
To empower citizens in distress with immediate, resilient assistance while equipping emergency authorities with actionable, AI-assisted decision intelligence—dramatically reducing responder response latency and ensuring uncompromised evidence preservation.
The AI is strictly a **decision-support tool** to assist human authorities; it never supersedes human responders, and raw evidence is preserved separately and immutably from AI interpretations.

---

## 3. Core User Flow
1. **Pre-Emergency Onboarding**: Role Selection $\rightarrow$ Authentication $\rightarrow$ Permissions Setup (Location, Camera, Audio, Notifications) $\rightarrow$ Trusted Contacts $\rightarrow$ Medical/Emergency Profile $\rightarrow$ SOS Shortcut/Widget Config.
2. **Emergency Trigger (SOS)**: Single tap on the glowing SOS button triggers instant incident creation with a unique incident ID.
3. **Immediate Notification**: Contacts and authorities receive immediate notification with initial location coordinates.
4. **Automated Evidence Burst**: Device executes automated capture: 15 rear photos alternating with 15 front photos (30 photos total) + synchronized audio capture.
5. **Secure Transmission & AI Processing**: Evidence streams securely to Supabase. Cloud/edge AI performs multimodal classification (speech patterns, stress indicators, visual context, sudden movement).
6. **Timeline & Report Generation**: Chronological incident timeline and structured report generated.
7. **Responder Triaging**: Authorities view prioritized alerts on the dashboard, review raw evidence and AI insights, and execute dispatch or team notifications.
8. **Resolution / False SOS**: Incident deactivated with logged resolution status or authorized deactivation PIN/audit record.

---

## 4. Current Feature Set
- **Role-Based Authentication**: Seamless dual-role support (Citizen / Authority Responder).
- **Onboarding & Permission Orchestration**: Pre-emergency clearance of system permissions (Camera, Mic, Fine/Coarse Location, Notifications).
- **Emergency Profile & Contacts**: Storage of blood group, medical conditions, allergies, and trusted contacts with phone numbers.
- **Instant SOS Trigger**: Single-tap, low-latency incident initiation with immediate unique UUID generation.
- **30-Photo Alternating Evidence Capture**: Precise CameraX sequence capturing 15 rear-camera and 15 front-camera frames with metadata tagging (camera lens, timestamp, sequence index).
- **Synchronized Audio Recording**: Ambient audio capture with waveform visualization and upload status tracking.
- **Incident Timeline & Live Evidence**: Real-time state updates showing capture progress (Front Camera, Rear Camera, Audio Recording).
- **AI Multimodal Decision Support**: Assessment indicating incident category (e.g., *Possible Physical Threat*), confidence score (e.g., *84%*), and key distress indicators.
- **Authority Portal**: Map-driven incident overview, severity filtering (All, High, Medium, Low), detailed evidence viewer (30 photos + audio playback), and dispatch action controls.

---

## 5. Explicitly Excluded Features
- **Video Recording**: Fully deprecated in favor of the resilient 30-photo burst (15 rear + 15 front) to conserve bandwidth, avoid thermal throttling, and prevent video encoding failures during emergencies.
- **60-Photo Capture**: Older 60-photo bursts have been streamlined down to exactly 30 total photos (15 rear + 15 front).
- **Autonomous Emergency Dispatch**: AI will NEVER automatically command municipal responders without human responder review; AI provides advisory recommendations only.
- **Continuous Passive Tracking**: Location tracking is only activated during an active emergency lifecycle or with explicit user consent.
- **Destructive Deletion on Cancel**: Canceling an SOS does not purge collected evidence; an audit record with timestamp is preserved to prevent coercion tampering.

---

## 6. Technology Stack
- **Android Platform**: Kotlin, Native Android SDK (API 29+ target 35/37)
- **UI Framework**: Jetpack Compose, Material 3, Accompanist / Lifecycle Compose extensions
- **Architecture**: MVVM + Clean Architecture (Presentation, Domain, Data, Core)
- **Concurrency**: Kotlin Coroutines & StateFlow / SharedFlow
- **Camera**: AndroidX CameraX (Camera2 interop, ImageCapture)
- **Location**: Google Play Services Location API (FusedLocationProviderClient)
- **Audio**: Android MediaRecorder / AudioRecord API
- **Networking & Backend**: Supabase Kotlin SDK (Auth, PostgREST, Realtime, Storage)
- **Backend Database**: Supabase PostgreSQL with Row Level Security (RLS)
- **AI Processing**: Gemini API / Vertex AI multimodal analysis (Gemini 1.5 Flash / Pro)
- **Build System**: Gradle 9.6.0, Android Gradle Plugin 9.4.1+, Java 21/25+

---

## 7. Android Architecture
The app follows standard Clean Architecture with MVVM:
```text
Presentation Layer
├── Navigation (NavHost, Screen routes)
├── Screens (User: Splash, GetStarted, RoleSelect, Auth, Permissions, Contacts, Profile, SOSSetup, Home, ActiveSOS, IncidentReport, ProfileSettings)
├── Screens (Authority: Splash, GetStarted, RoleSelect, DeptLogin, Verification, Dashboard, LiveIncidents, IncidentDetails, EvidenceViewer, AIReport, TeamActions, AuthorityProfile)
├── ViewModels (AuthViewModel, SosViewModel, EvidenceViewModel, DashboardViewModel)
└── UI State (Immutable UI State data classes)

Domain Layer
├── Models (User, Incident, EvidenceItem, AIReport, EmergencyContact, MedicalProfile)
├── UseCases (TriggerSosUseCase, CaptureEvidenceUseCase, CancelSosUseCase, FetchIncidentsUseCase)
└── Repository Interfaces

Data Layer
├── Repositories (AuthRepositoryImpl, IncidentRepositoryImpl, EvidenceRepositoryImpl, ProfileRepositoryImpl)
├── Remote Data Sources (SupabaseAuthSource, SupabaseDbSource, SupabaseStorageSource)
├── Local Data Sources (EncryptedSharedPreferences / Room for offline queue)
└── DTOs & Mappers

Core Layer
├── Camera (CameraBurstManager: 15 rear + 15 front sequence)
├── Audio (AudioRecorderManager)
├── Location (LocationTrackerManager)
├── Security (SecureStorage, Keystore)
└── Utilities & Theme (Theme.kt, Color.kt, Type.kt)
```

---

## 8. Backend Architecture
The backend is architected entirely around **Supabase**:
- **Authentication**: JWT-based auth separating Citizen role (`user`) and Authority role (`authority`).
- **PostgreSQL Database**: Relational schema handling profiles, contacts, incidents, locations, evidence, AI reports, and audit logs.
- **Row Level Security (RLS)**: Fine-grained security guaranteeing citizens cannot view peer incidents, while certified authority responders access authorized jurisdictions.
- **Supabase Storage**: Dedicated storage buckets (`kavach-evidence-photos`, `kavach-evidence-audio`) with signed private URL access.
- **Supabase Realtime**: Live Postgres change replication notifying the Authority Dashboard whenever new incidents or evidence files arrive.
- **Edge Functions / AI Pipeline**: Serverless trigger upon evidence completion running multimodal AI analysis with Gemini and returning structured report schemas.

---

## 9. Supabase Architecture
- **Organization ID**: `jgoslqmnzwyfrbaijvfl` (Imsupehh)
- **Project Name**: `kavach` (to be provisioned via Supabase MCP)
- **Storage Buckets**:
  - `evidence-photos`: Private bucket for captured rear/front images (`incident_<id>/rear_XX.jpg`, `incident_<id>/front_XX.jpg`)
  - `evidence-audio`: Private bucket for emergency audio recordings (`incident_<id>/audio_ambient.m4a`)
  - `user-avatars`: Public or authenticated bucket for profile avatars
- **Realtime Channels**:
  - `incidents:live`: Realtime broadcast for authority dashboard maps and status updates
  - `incident:<id>`: Channel for real-time evidence upload count and AI report generation notifications

---

## 10. Database Schema
### Core Entities
1. **`profiles`**:
   - `id` (UUID, PK, references `auth.users`)
   - `role` (TEXT: 'citizen' | 'authority')
   - `full_name` (TEXT)
   - `phone_number` (TEXT)
   - `avatar_url` (TEXT)
   - `department` (TEXT, null for citizen)
   - `badge_id` (TEXT, null for citizen)
   - `designation` (TEXT, null for citizen)
   - `district` (TEXT, null for citizen)
   - `created_at` (TIMESTAMPTZ), `updated_at` (TIMESTAMPTZ)

2. **`emergency_contacts`**:
   - `id` (UUID, PK)
   - `user_id` (UUID, references `profiles.id`)
   - `name` (TEXT)
   - `phone` (TEXT)
   - `relationship` (TEXT)
   - `priority_order` (INT)
   - `created_at` (TIMESTAMPTZ)

3. **`medical_profiles`**:
   - `user_id` (UUID, PK, references `profiles.id`)
   - `blood_group` (TEXT)
   - `medical_conditions` (TEXT)
   - `allergies` (TEXT)
   - `medications` (TEXT)
   - `updated_at` (TIMESTAMPTZ)

4. **`incidents`**:
   - `id` (UUID, PK)
   - `incident_code` (TEXT, e.g., 'KAV-1024')
   - `user_id` (UUID, references `profiles.id`)
   - `status` (TEXT: 'active' | 'resolved' | 'false_alarm')
   - `priority` (TEXT: 'low' | 'medium' | 'high' | 'critical')
   - `category` (TEXT: 'physical_threat' | 'medical' | 'harassment' | 'accident' | 'unknown')
   - `created_at` (TIMESTAMPTZ), `resolved_at` (TIMESTAMPTZ)

5. **`incident_locations`**:
   - `id` (UUID, PK)
   - `incident_id` (UUID, references `incidents.id`)
   - `latitude` (DOUBLE PRECISION)
   - `longitude` (DOUBLE PRECISION)
   - `accuracy` (FLOAT)
   - `address_text` (TEXT)
   - `recorded_at` (TIMESTAMPTZ)

6. **`evidence_records`**:
   - `id` (UUID, PK)
   - `incident_id` (UUID, references `incidents.id`)
   - `type` (TEXT: 'photo_rear' | 'photo_front' | 'audio')
   - `sequence_number` (INT)
   - `file_path` (TEXT)
   - `file_size` (BIGINT)
   - `captured_at` (TIMESTAMPTZ)
   - `upload_status` (TEXT: 'pending' | 'uploaded' | 'failed')

7. **`ai_incident_reports`**:
   - `id` (UUID, PK)
   - `incident_id` (UUID, references `incidents.id`)
   - `classification` (TEXT: 'Possible Physical Threat', etc.)
   - `confidence_score` (FLOAT)
   - `key_indicators` (JSONB)
   - `context_summary` (TEXT)
   - `recommended_response` (TEXT: 'police_patrol' | 'medical_ambulance' | 'fire' | 'combined')
   - `created_at` (TIMESTAMPTZ)

8. **`incident_timelines`**:
   - `id` (UUID, PK)
   - `incident_id` (UUID, references `incidents.id`)
   - `event_time` (TIMESTAMPTZ)
   - `event_title` (TEXT)
   - `event_details` (TEXT)

9. **`audit_logs`**:
   - `id` (UUID, PK)
   - `incident_id` (UUID)
   - `actor_id` (UUID)
   - `action` (TEXT)
   - `metadata` (JSONB)
   - `created_at` (TIMESTAMPTZ)

---

## 11. API Integrations
- **Supabase REST / PostgREST**: Direct high-speed typed CRUD queries.
- **Supabase Auth API**: Email/Password and SSO login flows.
- **Supabase Storage API**: Resumable multipart uploads for captured media.
- **Gemini API (Google GenAI SDK)**: Multimodal reasoning prompt:
  - Input: 30 sequential images + audio transcript + location context.
  - Output: JSON structured assessment conforming to `ai_incident_reports` schema.
- **Google Maps API**: Reverse geocoding and live incident location pins.

---

## 12. Authentication & Authorization
- **Citizen Authentication**: Email/Password login or quick phone OTP signup.
- **Authority Authentication**: Official Department ID, Department Selection, and Government SSO support with badge verification.
- **Role Enforcement**:
  - `auth.jwt() -> role == 'citizen'`: Restricted strictly to user's own profile, emergency contacts, medical data, and user's own triggered incidents.
  - `auth.jwt() -> role == 'authority'`: Read access to all active incidents, evidence records, and AI reports within department jurisdiction; update rights on incident status and action logs.

---

## 13. Security Model
- **No Service Keys on Device**: Android client operates strictly with Supabase `anon` public key; administrative tasks occur via authenticated RLS policies or Edge Functions.
- **Evidence Immutability**: Evidence records cannot be modified or updated once inserted; deletion is restricted.
- **Evidence Separation**: Raw captured photos and audio are stored as read-only files in private buckets. AI interpretation is stored in a separate table and never alters raw telemetry.
- **Tamper-Resistant False SOS**: Deactivating an SOS changes the incident status to `false_alarm` and records deactivation timestamps, but leaves recorded telemetry intact for audit purposes.

---

## 14. SOS Workflow
```text
User Presses SOS Button
│
├── 1. Generate local UUID & incident code (e.g. KAV-1024)
├── 2. FusedLocationProvider obtains high-accuracy coordinates
├── 3. Create incident record in Supabase (status: 'active', priority: 'high')
├── 4. Trigger SMS / push notification to emergency contacts
├── 5. Start foreground CameraBurstManager & AudioRecorderManager
├── 6. Stream evidence uploads to Supabase storage
├── 7. Dispatch AI multimodal analysis once minimum evidence batch arrives
└── 8. Update incident timeline and push real-time event to authority dashboard
```

---

## 15. Evidence Capture Workflow
- **Total Photo Target**: Exactly 30 photos.
- **Distribution**: 15 rear-camera photos + 15 front-camera photos.
- **Interleaved Sequence**:
  $$\text{Rear } 1 \rightarrow \text{Front } 1 \rightarrow \text{Rear } 2 \rightarrow \text{Front } 2 \rightarrow \dots \rightarrow \text{Rear } 15 \rightarrow \text{Front } 15$$
- **Camera Management**: CameraX lifecycle binding switching between `LensFacing.BACK` and `LensFacing.FRONT` with fast preview-less capture or dedicated background capture surface.
- **Metadata Tagging**: Every file saved as `incident_<id>/rear_<idx>.jpg` or `incident_<id>/front_<idx>.jpg` with EXIF timestamp and sequence index.
- **Audio Capture**: AAC/M4A 16kHz stream started synchronously with SOS trigger and written to app internal storage before chunked/streamed upload.

---

## 16. AI Processing Workflow
- **Input Assembly**: Edge function collects incident metadata, location, audio transcription, and image burst keyframes.
- **Prompt Guardrails**: AI is instructed to operate strictly as an objective forensic decision-support engine. Language is conditioned on uncertainty (e.g., *"Possible physical threat"*, *"Potential indicators detected"*, never *"Crime confirmed"*).
- **Extracted Signals**:
  - Speech: Distress keywords, screaming, aggression, silence.
  - Audio: Multiple speakers, struggle sounds, background noise (traffic, indoor, public).
  - Vision: Rapid camera movement, fallen orientation, presence of weapons, aggressive posturing.
  - Context: Late night hours, isolated location.
- **Output Schema**: Structured JSON parsed and written to `ai_incident_reports`.

---

## 17. Incident Report Workflow
- Compiled report combines:
  1. Incident Metadata (ID, Citizen Name, Time, Coordinates, Address).
  2. AI Forensic Assessment (Classification, Confidence percentage, Indicators list).
  3. Evidence Vault (30 captured photos, synchronized audio player).
  4. Chronological Incident Timeline.
  5. Recommended Response (Police / Medical / Combined).
- Downloadable / shareable format for dispatch control rooms.

---

## 18. Emergency Routing
- **Routing Engine**: Dynamically evaluates AI assessment and severity:
  - *Physical Threat / Harassment* $\longrightarrow$ Local Police Station / Patrol Units.
  - *Medical / Physical Collapse* $\longrightarrow$ Ambulance Services / Nearest Trauma Center.
  - *Fire / Explosion Hazard* $\longrightarrow$ Fire & Emergency Services.
  - *Road Accident / Multi-Hazard* $\longrightarrow$ Dual Dispatch (Police + Medical).
- Always presents an **Authorized Human Action Gate**: Responder reviews recommendation and clicks "Take Action" (e.g., *Dispatch Patrol*, *Notify Medical Team*).

---

## 19. Citizen App (UI/UX)
- Designed faithfully to the provided design mockups:
  - **Screen 1: Splash Screen**: Dark theme, radiant red shield avatar, tagline *"Your Safety, Our Priority"*.
  - **Screen 2: Get Started**: Clean illustration, empowerment message, "Get Started" CTA.
  - **Screen 3: Choose Role**: Visual cards for "I am a User" (Red shield) vs "I am an Authority" (Blue badge).
  - **Screen 4: Login / Sign Up**: Minimal tabbed auth with Google / Apple integration.
  - **Screen 5: Permissions**: Progressive permission rationale cards (Location, Mic, Camera, Notifications).
  - **Screen 6: Emergency Contacts**: Contact card list with "+ Add Contact" modal.
  - **Screen 7: Emergency Profile**: Blood group selector, medical conditions, allergies.
  - **Screen 8: SOS Shortcut**: Home screen widget and quick setting toggles.
  - **Screen 9: Home Screen**: Massive glowing Red SOS button, live status bar, quick access cards (Live Evidence, Incident History, Medical Info, Settings), bottom navigation (Home, Contacts, Profile).
  - **Screen 10: Active SOS / Live Evidence**: Live timer, real-time capture checklist (Front Camera, Rear Camera, Audio), audio waveform visualizer, and "Cancel SOS" button.
  - **Screen 12: AI Incident Report**: Summary card with confidence badge, detected indicators checklist, chronological timeline.
  - **Screen 14: Profile / Settings**: User profile details, safety preferences, permissions status, logout.

---

## 20. Authority Dashboard (UI/UX)
- Professional dark navy / high-contrast responder portal:
  - **Screen 1: Splash Screen**: Navy theme with police emergency lights accent glow.
  - **Screen 2: Get Started**: Responder empowerment illustration and portal access.
  - **Screen 3: Choose Role (Authority)**: Selected officer role.
  - **Screen 4: Department Login**: Official ID, Department selector, Government SSO.
  - **Screen 5: Official Verification**: Sub Inspector / Officer verification step.
  - **Screen 6: Authority Dashboard**: Header with officer greeting, live metrics (Live Incidents, Interventions, Resolved), interactive map with incident pins, and live feed.
  - **Screen 7: Live Incidents Queue**: Tab filters (All, High, Medium, Low), incident cards with distance, priority tags, and timestamps.
  - **Screen 9: Incident Details**: Incident ID header, location banner, evidence preview, Summary / Evidence / AI Report tabs.
  - **Screen 12: Evidence Viewer**: Tabbed 30-photo grid (rear/front marked), waveform audio player with scrub bar.
  - **Screen 11: AI Analysis Report**: Full AI forensic breakdown with confidence scoring and contextual factors.
  - **Screen 12: Team Actions**: Dispatch action cards (*Dispatch Patrol*, *Notify Medical Team*, *Share with Control Room*) with "Take Action" execution button.
  - **Screen 14: Authority Profile**: Officer badge, department details, alert settings, logout.

---

## 21. UI/UX Decisions
- **Color Palette**:
  - Citizen Theme: Urgent Red (`#E53935`, `#D32F2F`), Dark Background (`#121212`, `#1E1E1E`), Clean White Cards (`#FFFFFF`).
  - Authority Theme: Official Police Navy (`#0D1B2A`, `#1B263B`), Tactical Blue (`#1E88E5`, `#2196F3`), Status Accents (High: Red `#E53935`, Medium: Amber `#FFA000`, Low: Teal `#00897B`).
- **Typography**: Clean, sans-serif typography (Inter / Roboto) with bold headers and high contrast readability under direct sunlight and high stress.
- **Button Feedback**: Haptic vibrations on SOS press and evidence capture confirmations.

---

## 22. File/Folder Structure
```text
KaVach/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── java/com/imsupehh/kavach/
│   │   │   │   ├── core/
│   │   │   │   │   ├── camera/ (CameraBurstManager.kt)
│   │   │   │   │   ├── audio/ (AudioRecorderManager.kt)
│   │   │   │   │   ├── location/ (LocationTrackerManager.kt)
│   │   │   │   │   ├── network/ (SupabaseClient.kt)
│   │   │   │   │   └── security/ (PreferencesManager.kt)
│   │   │   │   ├── data/
│   │   │   │   │   ├── models/ (User.kt, Incident.kt, Evidence.kt, AIReport.kt, etc.)
│   │   │   │   │   └── repository/ (AuthRepository.kt, IncidentRepository.kt, EvidenceRepository.kt)
│   │   │   │   ├── presentation/
│   │   │   │   │   ├── navigation/ (NavGraph.kt, Screen.kt)
│   │   │   │   │   ├── theme/ (Color.kt, Theme.kt, Type.kt)
│   │   │   │   │   ├── components/ (KavachButton.kt, TopBar.kt, SosButton.kt, IncidentCard.kt)
│   │   │   │   │   ├── citizen/
│   │   │   │   │   │   ├── splash/
│   │   │   │   │   │   ├── getstarted/
│   │   │   │   │   │   ├── role/
│   │   │   │   │   │   ├── auth/
│   │   │   │   │   │   ├── permissions/
│   │   │   │   │   │   ├── contacts/
│   │   │   │   │   │   ├── profile/
│   │   │   │   │   │   ├── shortcut/
│   │   │   │   │   │   ├── home/
│   │   │   │   │   │   ├── active_sos/
│   │   │   │   │   │   └── history/
│   │   │   │   │   └── authority/
│   │   │   │   │       ├── splash/
│   │   │   │   │       ├── getstarted/
│   │   │   │   │       ├── login/
│   │   │   │   │       ├── verification/
│   │   │   │   │       ├── dashboard/
│   │   │   │   │       ├── incidents/
│   │   │   │   │       ├── details/
│   │   │   │   │       ├── evidence/
│   │   │   │   │       ├── report/
│   │   │   │   │       └── actions/
│   │   │   │   └── MainActivity.kt
│   │   │   └── res/
│   │   │       ├── drawable/
│   │   │       ├── values/
│   │   │       └── mipmap/
│   │   └── test/
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── docs/
│   └── design_mockup.jpeg
├── gradle/
│   └── libs.versions.toml
├── build.gradle.kts
├── settings.gradle.kts
├── aihistory.md
└── README.md
```

---

## 23. Completed Work
- Inspected repository structure and identified misplaced app directory inside `.kotlin/app`.
- Moved `.kotlin/app` to standard root `app/` matching `settings.gradle.kts` `include(":app")`.
- Verified Gradle build tasks with `./gradlew tasks` (Build Successful).
- Added `.kotlin` to `.gitignore`.
- Preserved user UI design screenshot in `docs/design_mockup.jpeg`.
- Inspected Supabase organization and project availability via MCP (Organization: `Imsupehh` (`jgoslqmnzwyfrbaijvfl`), 0 projects currently existing).
- Initialized Git workflow branch `develop`.
- Authored initial comprehensive `aihistory.md`.

---

## 24. Current Work
- Establishing the 5 dedicated Git feature branches:
  - `feature/auth`
  - `feature/sos`
  - `feature/evidence`
  - `feature/backend-ai`
  - `feature/dashboard`
- Configuring project dependencies in `gradle/libs.versions.toml` and `app/build.gradle.kts` (Navigation Compose, CameraX, Play Services Location, Supabase SDK, Material Icons).

---

## 25. Pending Work
- **Phase 3**: Android Architecture & Core Design System setup (Themes, Typography, Colors, Common Components for both Citizen & Authority).
- **Phase 4**: Supabase Backend setup (Database tables, RLS policies, Storage buckets, Edge Function stubs).
- **Phase 5**: Authentication Flow implementation (Citizen & Authority login/registration).
- **Phase 6**: Citizen Onboarding & Permissions Flow (Screens 1 to 8).
- **Phase 7**: Citizen Home Screen with Glowing SOS Button (Screen 9).
- **Phase 8**: SOS Emergency Lifecycle & Incident Creation (Step 1-3).
- **Phase 9**: Location Tracking Service during active emergency.
- **Phase 10**: Camera Evidence Burst (15 rear + 15 front alternating capture with CameraX).
- **Phase 11**: Audio Evidence Recording with waveform display.
- **Phase 12**: Active SOS / Live Evidence Screen (Screen 10).
- **Phase 13**: Secure Upload & Storage synchronization.
- **Phase 14**: AI Multimodal Analysis pipeline (Gemini API integration).
- **Phase 15**: Structured AI Incident Report & History Screen (Screen 12).
- **Phase 16**: Authority Portal Screens (Screens 1 to 14 for Authority).
- **Phase 17**: Authority Dashboard Map & Live Incidents Feed (Screen 6 & 7).
- **Phase 18**: Authority Evidence Viewer (30 Photos grid + Audio player) (Screen 12).
- **Phase 19**: Authority Team Actions & Dispatch (Screen 12).
- **Phase 20**: Offline resilience queue and integration testing.

---

## 26. Known Bugs
- *None currently discovered.*

---

## 27. Known Limitations
- Android platform restricts silent background camera capture on modern Android (API 30+). Evidence capture must run while the app is in the foreground or using an active Foreground Service with `foregroundServiceType="camera|microphone|location"` with an active notification.
- True dual-camera simultaneous streaming is hardware-dependent; alternating rapid switching (`Rear` $\rightarrow$ `Front` $\rightarrow$ `Rear`) ensures universal compatibility across all Android hardware tiers.

---

## 28. Failed Approaches
- *Video capture*: Deprecated due to bandwidth limits in weak signal areas and risk of corrupted video files on crash. Replaced by reliable 30-photo alternating burst.

---

## 29. Important Technical Decisions
- **Jetpack Compose Single-Activity Architecture**: Entire navigation and UI rendered through Compose for maximum UI fidelity and fluid transitions between Citizen and Authority modes.
- **Separation of Evidence and AI**: Raw images and audio are immutable once created. AI assessments are stored in a dedicated `ai_incident_reports` table to maintain forensic chain of custody.
- **Dual-Themed Design System**: Red/Black emergency theme for Citizens; Tactical Navy/Blue for Authority Responders.

---

## 30. Environment / Configuration
- JDK: Java 21 / 25 compatible
- Android SDK: Target SDK 35/37, Min SDK 29
- Supabase Project: To be provisioned under Organization `Imsupehh`

---

## 31. API Keys & Secrets Policy
- No service-role keys or private secrets in the Android repository.
- Use Supabase public anon key in the client with Row Level Security.
- Gemini API keys stored in server-side Edge Functions / Cloud Functions.

---

## 32. Testing Status
- Gradle task graph execution verified (`gradlew tasks`).
- Unit and UI test harness to be implemented alongside feature branches.

---

## 33. Deployment Status
- Local debug build configured. Remote CI/CD and release signing to be established on `main`.

---

## 34. AI Agent Change Log

### 2026-10-07 — Agent Change
#### Agent
Lead Software Architect & Senior Android/Backend Engineer (Antigravity)

#### Objective
Project discovery, environment inspection, architecture audit, initial Git setup, and establishing master project history documentation.

#### Changes Made
- Inspected local workspace and discovered misplaced application files under `.kotlin/app`.
- Successfully moved application source code to `./app` to match `settings.gradle.kts`.
- Verified Gradle build health (`BUILD SUCCESSFUL in 6s`).
- Preserved user UI design reference image in `docs/design_mockup.jpeg`.
- Added `.kotlin` cache directory to `.gitignore`.
- Queried Supabase MCP to inspect existing organization (`Imsupehh`) and confirmed clean backend slate.
- Initialized `develop` branch from `main`.
- Created master project document `aihistory.md`.

#### Files Changed
- `settings.gradle.kts`
- `.gitignore`
- `app/` (migrated from `.kotlin/app/`)
- `docs/design_mockup.jpeg`
- `aihistory.md`

#### Database Changes
- None yet.

#### API Changes
- None yet.

#### Bugs Fixed
- Fixed broken project module path where Gradle couldn't resolve `:app`.

#### Decisions Made
- Standardize on 15 rear + 15 front photo burst sequence (30 photos total).
- Retain exact design mockup layout matching user image for both Citizen and Authority portals.

#### Remaining Issues
- Need to configure dependencies in `gradle/libs.versions.toml` and `app/build.gradle.kts`.
- Need to create 5 feature branches from `develop` and push to remote.

#### Next Step
- Complete Git branch creation and push to origin.
- Add required Android dependencies (CameraX, Location, Navigation, Supabase, Material Icons).

---

## Team & Git Workflow

### Team Members

| Member | Branch | Responsibility |
|---|---|---|
| Member 1 | `feature/auth` | Android Core / Authentication / Onboarding / Permissions |
| Member 2 | `feature/sos` | SOS Button / Emergency Flow / Location Capture / Lifecycle |
| Member 3 | `feature/evidence` | CameraX (15 Rear + 15 Front) / Audio Recording / Evidence Upload |
| Member 4 | `feature/backend-ai` | Supabase Backend / Database Schema / RLS / Gemini AI Integration |
| Member 5 | `feature/dashboard` | Authority Dashboard / Responder UI / Evidence Viewer / Actions |

### Branch Structure

```text
main
└── develop
    ├── feature/auth
    ├── feature/sos
    ├── feature/evidence
    ├── feature/backend-ai
    └── feature/dashboard
```

### Git Rules
- No direct development or pushing on `main`.
- `develop` is the primary integration branch.
- Feature branches originate from `develop`.
- Pull Requests are required for merging features into `develop`.
- Meaningful commit messages following conventional commits (`feat:`, `fix:`, `docs:`, `refactor:`, `test:`).
- Keep shared files (Gradle, Manifest, Navigation, Models) coordinated and conflict-free.

---

## 35. Next Recommended Tasks
1. Commit the directory fix, `.gitignore`, and `aihistory.md` to `develop`.
2. Push `develop` and create the 5 team feature branches (`feature/auth`, `feature/sos`, `feature/evidence`, `feature/backend-ai`, `feature/dashboard`).
3. Add the required dependencies to `gradle/libs.versions.toml` and `app/build.gradle.kts`.
4. Begin Phase 3 & 4 (Android Core Architecture & Supabase Backend provisioning).
