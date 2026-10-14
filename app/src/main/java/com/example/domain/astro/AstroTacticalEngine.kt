package com.example.domain.astro

import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

data class AstrologicalPlacement(
    val title: String,
    val house: String,
    val planet: String,
    val psychologicalSignificance: String,
    val tacticalOperationalRule: String,
    val dangerSignal: String,
    val remedyProtocol: String
)

data class TacticalWeatherReport(
    val currentDasha: String,
    val dashaDuration: String,
    val phaseTitle: String,
    val marsStaminaScore: Int,      // 0-100
    val saturnDisciplineIndex: Int, // 0-100
    val moonKetuFogRisk: Int,       // 0-100
    val competitiveVictoryFactor: String,
    val tacticalDirective: String,
    val dayTacticalWeatherSummary: String,
    val apfcDaysRemaining: Long,
    val apfcHoursRemaining: Long,
    val roAroDaysTarget: Long
)

object AstroTacticalEngine {

    val USER_DNA = listOf(
        AstrologicalPlacement(
            title = "Ascendant Architecture",
            house = "1st House",
            planet = "Virgo (Mercury)",
            psychologicalSignificance = "Hyper-analytical, systems-oriented, detail-obsessed processor. Rejects vague philosophies; demands structured syntax and objective data.",
            tacticalOperationalRule = "Deconstruct every syllabus unit into flowchart nodes and logic gates. Avoid prose-heavy textbooks.",
            dangerSignal = "Analysis paralysis: perfecting notes instead of solving raw PYQs.",
            remedyProtocol = "Timebox note synthesis to 15 mins max; immediately switch to mechanical question testing."
        ),
        AstrologicalPlacement(
            title = "Budhaditya Yoga (Executive Core)",
            house = "10th House",
            planet = "Sun & Mercury",
            psychologicalSignificance = "Rare combination for executive authority, IT systems aptitude, and government administrative mastery. Natural inclination for high-level governance and analytical problem-solving.",
            tacticalOperationalRule = "Leverage BCA/IT background to treat Labor Codes, Accounting Standards, and Indian Polity like code algorithms and database schemas.",
            dangerSignal = "Impatience with manual rote memorization.",
            remedyProtocol = "Convert rote lists into mnemonic logic trees and decision matrices."
        ),
        AstrologicalPlacement(
            title = "Psychological Friction Vector",
            house = "8th House",
            planet = "Moon & Ketu",
            psychologicalSignificance = "Prone to sudden surges of overthinking, exam anxiety, impostor panic, and subjective fog. Ketu detaches while Moon amplifies doubt.",
            tacticalOperationalRule = "NEVER debate or negotiate with your feelings. Bypass 8th-house paralysis through purely mechanical physical action.",
            dangerSignal = "Lying in bed or doomscrolling when a study block is scheduled.",
            remedyProtocol = "Morning silver glass hydration (Moon grounding). Stray dog feeding (Ketu harmonization). Instant 5-min friction breaker."
        ),
        AstrologicalPlacement(
            title = "Competitive Bastion (The Victory Shield)",
            house = "6th House",
            planet = "Saturn (Shani)",
            psychologicalSignificance = "Saturn in the 6th house is the ancient classic signature of victory over adversaries, competitive exams, and government court/bureaucratic posts. Saturn guarantees victory, but charges admission in relentless daily discipline.",
            tacticalOperationalRule = "Embrace the daily grind. Saturn converts monotonous, unglamorous consistency into unstoppable competitive advantage.",
            dangerSignal = "Seeking fast shortcuts or instant motivation hacks.",
            remedyProtocol = "Non-negotiable daily deployment blocks. Treat each session as an immovable military deployment."
        ),
        AstrologicalPlacement(
            title = "Action Engine (D10 Dashamsha)",
            house = "1st House (D10)",
            planet = "Mars (Mangal)",
            psychologicalSignificance = "Mars in 1st house of career chart demands high-speed execution, competitive aggressiveness, and tactical dominance over passive consumption.",
            tacticalOperationalRule = "Action over theory. Test first, diagnose failures second, revise theory third.",
            dangerSignal = "Passive video-watching or endless highlighting without testing.",
            remedyProtocol = "Enforce the 'No-Guess' elimination drill before every study session."
        )
    )

    fun calculateTacticalWeather(): TacticalWeatherReport {
        val now = Calendar.getInstance()
        val currentMillis = now.timeInMillis

        // UPSC APFC Exam Target: Dec 20, 2026, 09:30 AM IST
        val apfcTarget = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2026)
            set(Calendar.MONTH, Calendar.DECEMBER)
            set(Calendar.DAY_OF_MONTH, 20)
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 30)
            set(Calendar.SECOND, 0)
        }

        val apfcDiffMillis = maxOf(0L, apfcTarget.timeInMillis - currentMillis)
        val apfcDays = TimeUnit.MILLISECONDS.toDays(apfcDiffMillis)
        val apfcHours = TimeUnit.MILLISECONDS.toHours(apfcDiffMillis) % 24

        // Allahabad HC RO/ARO expected window: approx Jan 2027 (~90 days)
        val roAroDays = apfcDays + 25

        // Dynamic tactical telemetry based on planetary hourly rulership / day of week
        val dayOfWeek = now.get(Calendar.DAY_OF_WEEK)
        val hourOfDay = now.get(Calendar.HOUR_OF_DAY)

        val marsStamina: Int
        val saturnDiscipline: Int
        val fogRisk: Int
        val weatherSummary: String

        when (dayOfWeek) {
            Calendar.TUESDAY -> { // Mars day
                marsStamina = 95
                saturnDiscipline = 88
                fogRisk = 20
                weatherSummary = "MARS DOMINANT DAY: Raw execution stamina at peak. Perfect window for high-friction topics: Labor Laws & Quantitative Aptitude."
            }
            Calendar.SATURDAY -> { // Saturn day
                marsStamina = 82
                saturnDiscipline = 98
                fogRisk = 15
                weatherSummary = "SATURN BASTION DAY: Iron discipline in full force. Unstoppable endurance for 8-hour cumulative deployment blocks and mock exam synthesis."
            }
            Calendar.WEDNESDAY -> { // Mercury day (Budhaditya peak)
                marsStamina = 86
                saturnDiscipline = 90
                fogRisk = 25
                weatherSummary = "MERCURY INTELLECT DAY: Budhaditya Yoga active. High analytical processing speed. Focus on Accounting Principles & IT code syntax logic."
            }
            Calendar.SUNDAY -> { // Sun day
                marsStamina = 88
                saturnDiscipline = 85
                fogRisk = 22
                weatherSummary = "SUN AUTHORITY DAY: Executive focus on Indian Polity, Constitution, and Administrative systems."
            }
            Calendar.MONDAY -> { // Moon day (Watch 8th house)
                marsStamina = 78
                saturnDiscipline = 92
                fogRisk = 48
                weatherSummary = "MOON-KETU FRICTION WINDOW: 8th House fog hazard elevated. Strictly execute morning silver glass protocol. Do not entertain doubts; execute mechanically."
            }
            else -> {
                marsStamina = 85
                saturnDiscipline = 91
                fogRisk = 28
                weatherSummary = "HIGH VELOCITY OPERATIONAL WINDOW: Optimal cognitive momentum. Maintain steady deployment blocks."
            }
        }

        val tacticalDirective = if (fogRisk > 35) {
            "FOG INTERCEPT: 8th-House Moon interference detected. Do not analyze mental state. Lock digital perimeter and engage 5-Minute Friction Breaker immediately."
        } else {
            "DEPLOYMENT GREEN: Moon-Mars Dasha is driving breakthrough momentum. Launch next 90-minute operational block on target syllabus."
        }

        return TacticalWeatherReport(
            currentDasha = "Moon-Mars Dasha",
            dashaDuration = "Oct 2026 - May 2027",
            phaseTitle = "Career Breakthrough Phase",
            marsStaminaScore = marsStamina,
            saturnDisciplineIndex = saturnDiscipline,
            moonKetuFogRisk = fogRisk,
            competitiveVictoryFactor = "6th House Saturn: Guaranteed Competitive Victory via Rigor",
            tacticalDirective = tacticalDirective,
            dayTacticalWeatherSummary = weatherSummary,
            apfcDaysRemaining = apfcDays,
            apfcHoursRemaining = apfcHours,
            roAroDaysTarget = roAroDays
        )
    }
}
