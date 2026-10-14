package com.example.domain.coach

import com.example.BuildConfig
import com.example.domain.astro.AstroTacticalEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class CoachMessage(
    val id: String = System.currentTimeMillis().toString(),
    val sender: String, // "USER" or "TACTICAL_COMMAND"
    val content: String,
    val microTaskAction: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

object TacticalCoachEngine {

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    val INITIAL_BRIEFING = CoachMessage(
        sender = "TACTICAL_COMMAND",
        content = """
**SYSTEM BOOT: OPERATIONAL BRIEFING**

Commander, your astrological telemetry is mapped:
- **Dasha Vector:** You have transitioned into the **Moon-Mars Dasha (Oct 2026 – May 2027)**. This is your high-octane **Career Breakthrough Phase**. Lethargy is mathematically obsolete.
- **Cognitive Blueprint:** Virgo Ascendant with 10th-House Sun-Mercury Budhaditya Yoga. You possess innate systems architecture capacity. Legal and accounting syllabi are simply state machines and conditional syntax.
- **Vulnerability Check:** 8th-House Moon-Ketu creates artificial panic spikes and mental fog. **Rule Zero:** Never negotiate with emotional fog. Kill it with mechanical Mars execution.
- **Victory Baseline:** 6th-House Saturn guarantees triumph over competitors, provided you surrender to the unglamorous daily grind.

**TARGET CLOCK:**
- **UPSC APFC:** Dec 20, 2026. T-minus 68 Days.
- **Allahabad HC RO/ARO:** Q1 Target.

**MICRO-TASK DIRECTIVE #01:**
Confirm your morning System Boot (Silver glass water consumed + stray dogs fed) and select your target deployment block below. Are you ready to initiate Data Processing, or are you negotiating with friction?
        """.trimIndent(),
        microTaskAction = "Verify System Boot & Engage 90-min Block"
    )

    fun getDeterministicIntervention(userPrompt: String): CoachMessage {
        val promptLower = userPrompt.lowercase()

        val weather = AstroTacticalEngine.calculateTacticalWeather()

        return when {
            promptLower.contains("friction") || promptLower.contains("procrastinat") || promptLower.contains("fog") || promptLower.contains("anxiety") || promptLower.contains("stuck") -> {
                CoachMessage(
                    sender = "TACTICAL_COMMAND",
                    content = """
**TACTICAL DIAGNOSTIC: 8TH-HOUSE MOON-KETU INTERCEPTION**

Your 8th-House Moon is projecting illusory fatigue and overthinking loops. This is not physiological exhaustion; it is cognitive friction.

**MECHANICAL INTERVENTION PROTOCOL:**
1. **Physical Grounding:** Drink 200ml cold water from your silver glass right now. Silver stabilizes Moon fluctuations.
2. **Terminal Reset:** Stand up, execute 10 deep belly breaths, and mute all notifications.
3. **The 5-Minute Mars Bypass Rule:** You are prohibited from attempting a 3-hour theoretical session right now. Instead, open 1 single PYQ sheet and solve exactly 3 objective questions.
4. **Cognitive Momentum Handshake:** Once the 3rd question is solved, Mars takes command of your motor circuits and fog evaporates.

**DIRECT MICRO-TASK:**
Set the timer to the 5-Minute Friction Breaker right now and solve Question #1 on Labor Codes without touching your phone. Report back once completed.
                    """.trimIndent(),
                    microTaskAction = "Launch 5-Min Friction Breaker"
                )
            }

            promptLower.contains("apfc") || promptLower.contains("dec 20") || promptLower.contains("strategy") || promptLower.contains("schedule") -> {
                CoachMessage(
                    sender = "TACTICAL_COMMAND",
                    content = """
**UPSC APFC T-MINUS ${weather.apfcDaysRemaining} DAYS: OPERATIONAL WAR MAP**

Exam Date: **Dec 20, 2026**. Time for general theory reading has expired. We operate exclusively on high-yield systemic triage.

| Subject Module | Operational Strategy | IT Analogy | Target PYQs |
| :--- | :--- | :--- | :--- |
| **Labour Codes 2020** | Map 1947 vs 2020 threshold diffs | API Version Migration | 150 Questions |
| **Social Security** | EPFO 12% split & gratuity math | Database Transaction Rules | 100 Questions |
| **General Accounting** | Balance sheet invariants & Prudence | ACID State Machine | 120 Questions |
| **Indian Polity** | Writs & Constitutional Emergency configs | System Kernel Exceptions | 100 Questions |
| **Comp Apps & Quant** | High velocity execution (Your BCA domain) | Native Code Execution | 80 Questions |

**THE NO-GUESS MANDATE:**
Negative marking (-0.33) destroys candidates who rely on hunch. If an option cannot be logically deduced, it must be discarded with military discipline.

**DIRECT MICRO-TASK:**
Launch a 45-minute deployment block on Social Security Code EPFO contribution splits. Are you initiating now?
                    """.trimIndent(),
                    microTaskAction = "Deploy Social Security Drill"
                )
            }

            promptLower.contains("ro/aro") || promptLower.contains("allahabad") || promptLower.contains("high court") || promptLower.contains("hindi") -> {
                CoachMessage(
                    sender = "TACTICAL_COMMAND",
                    content = """
**ALLAHABAD HC RO/ARO: TACTICAL SUPERIORITY PROTOCOL**

The RO/ARO syllabus is tailor-made for your 10th-House Budhaditya Yoga and BCA background.

**TACTICAL EXECUTION VECTORS:**
1. **Computer Knowledge (Zero Friction):** As a BCA graduate, this section is a guaranteed 100% score in <8 minutes. Never spend hours reading basic guides; use direct PYQ tests.
2. **General Hindi Grammar (Algorithmic Processing):**
   - Apply phonetic regex transformations (क्ष -> ख/छ, व -> ब, Chandrabindu -> Tadbhav).
   - Treat Sandhi and Samas like string concatenation rules.
3. **UP Special GK & Polity:**
   - Focus on UP administrative census data, high court jurisdiction, and judicial precedents.

**DIRECT MICRO-TASK:**
Execute 15 Tatsam-Tadbhav phonetic deduction questions in the No-Guess drill terminal. What was your deduction accuracy?
                    """.trimIndent(),
                    microTaskAction = "Test Hindi Logic Regex"
                )
            }

            promptLower.contains("weather") || promptLower.contains("transit") || promptLower.contains("astrology") || promptLower.contains("chart") -> {
                CoachMessage(
                    sender = "TACTICAL_COMMAND",
                    content = """
**DAILY ASTROLOGICAL TACTICAL WEATHER REPORT**

- **Active Dasha:** ${weather.currentDasha} (${weather.dashaDuration})
- **Strategic Theme:** ${weather.phaseTitle}
- **Mars Stamina Index:** ${weather.marsStaminaScore}% (High kinetic capacity)
- **Saturn Bastion Index:** ${weather.saturnDisciplineIndex}% (6th-House Competitive Shield)
- **8th-House Moon-Ketu Fog Risk:** ${weather.moonKetuFogRisk}%

**TACTICAL ASSESSMENT:**
${weather.dayTacticalWeatherSummary}

${weather.tacticalDirective}

**DIRECT MICRO-TASK:**
Lock your digital perimeter and begin a 90-minute Deep Processing block. Do you have your operational target loaded?
                    """.trimIndent(),
                    microTaskAction = "Engage 90-Min Operational Block"
                )
            }

            else -> {
                CoachMessage(
                    sender = "TACTICAL_COMMAND",
                    content = """
**TACTICAL COMMAND DIRECTIVE: MECHANICAL EXECUTION**

I received your transmission: "$userPrompt".

**OPERATIONAL ASSESSMENT:**
- As a Virgo Ascendant under the **Moon-Mars Dasha**, hesitation is your only adversary. 
- Your 10th-House Sun-Mercury engine thrives when presented with structured challenges, not abstract contemplation.
- Saturn in the 6th House demands the payment of structured hours.

**EXECUTION DIRECTIVES:**
1. Eliminate subjective judgment of your mood or readiness.
2. Select your syllabus block (APFC Labour Laws or RO/ARO Computer/Hindi).
3. Apply the 4-step Elimination protocol to all practice items.

**DIRECT MICRO-TASK:**
Tell me specifically: Which chapter are you executing in the next 60 minutes, and have you cleared your workspace of all digital clutter?
                    """.trimIndent(),
                    microTaskAction = "Select Subject & Deploy"
                )
            }
        }
    }

    suspend fun consultCoach(userPrompt: String): CoachMessage = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig::class.java.getField("GEMINI_API_KEY").get(null) as? String
        } catch (_: Exception) {
            null
        }

        if (apiKey.isNullOrBlank() || apiKey == "MY_GEMINI_API_KEY") {
            // Use deterministic high-intensity Mars-Saturn tactical command engine
            return@withContext getDeterministicIntervention(userPrompt)
        }

        // Real Gemini API Call with Tactical Persona
        try {
            val systemInstruction = """
You are a Master Astrological Strategist and Tactical Accountability Coach. Your purpose is to guide the user to massive career and personal success by treating their Vedic birth chart not as mystical fate, but as a precise psychological and operational blueprint. Your tone is authoritative, highly logical, decisive, and grounded (embodying Mars execution and Saturn discipline). You do not offer fluffy motivation; you offer systemic solutions and demand mechanical execution.

User's Astrological DNA & Context:
- Chart Layout: Virgo Ascendant (Logical, analytical). 10th House Sun & Mercury (Budhaditya Yoga - high authority, IT/Systems aptitude). 8th House Moon & Ketu (Prone to extreme overthinking, exam anxiety, and mental fog). 6th House Saturn (Guarantees victory in competition and government sectors). D10 Chart has 1st House Mars (Demands action over theory).
- Current Phase: Transitioned out of Moon-Moon lethargy into the active Moon-Mars Dasha (Oct 2026 - May 2027). This is the "Career Breakthrough" phase.
- Immediate Targets: UPSC APFC (Exam: Dec 20, 2026) and Allahabad HC RO/ARO. 
- Learning Style: IT/BCA background. Needs visual flowcharts, logic trees, and objective data (PYQs). Must avoid thick theory books to prevent 8th-house overwhelm.
- Remedies in Place: Drinking water from a silver glass every morning (8th-house grounding), feeding stray dogs (Ketu harmonization), and maintaining a rigid, distraction-free morning "system boot" routine.

Core Directives & Behavioral Rules:
1. Enforce the Routine: Treat the user's daily study blocks as non-negotiable operational deployments. If the user hesitates, remind them to bypass their 8th-house Moon-Ketu anxiety through sheer mechanical Mars execution.
2. Astrology as Tactics: Frame all astrological events (dashas, transits) as tactical weather reports.
3. Zero Fluff: Deliver information in clean, highly structured formats—use bolding, bullet points, and tables. Do not use overly empathetic or soft language. Be a commanding, respectful partner.
4. The "No-Guess" Policy: Continuously reinforce the strategy for objective exams: logical deduction, eliminating wrong answers, and treating legal/accounting syllabi like IT code syntax.
5. ALWAYS END WITH EXECUTION: End EVERY SINGLE response by giving the user a direct micro-task or asking one sharp, tactical follow-up question to ensure they are actively moving to the next study block.
Vocabulary: "System Boot," "Cognitive Momentum," "Data Processing," "Friction Phase," "Mechanical Execution," "Tactical Pivot."
            """.trimIndent()

            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey"

            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "user")
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", "$systemInstruction\n\nUser Transmission: $userPrompt"))
                        })
                    })
                })
            }

            val request = Request.Builder()
                .url(url)
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = httpClient.newCall(request).execute()
            val responseString = response.body?.string()

            if (response.isSuccessful && !responseString.isNullOrBlank()) {
                val respJson = JSONObject(responseString)
                val candidates = respJson.optJSONArray("candidates")
                if (candidates != null && candidates.length() > 0) {
                    val candidate = candidates.getJSONObject(0)
                    val contentObj = candidate.optJSONObject("content")
                    val parts = contentObj?.optJSONArray("parts")
                    val replyText = parts?.optJSONObject(0)?.optString("text")

                    if (!replyText.isNullOrBlank()) {
                        return@withContext CoachMessage(
                            sender = "TACTICAL_COMMAND",
                            content = replyText.trim(),
                            microTaskAction = "Execute Directive Now"
                        )
                    }
                }
            }
            getDeterministicIntervention(userPrompt)
        } catch (_: Exception) {
            getDeterministicIntervention(userPrompt)
        }
    }
}
