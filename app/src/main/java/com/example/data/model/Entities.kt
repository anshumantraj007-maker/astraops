package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deployment_blocks")
data class DeploymentBlockEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val examType: String,
    val subject: String,
    val topic: String,
    val durationMinutes: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val pyqsSolved: Int = 0,
    val status: String = "COMPLETED", // COMPLETED, INTERRUPTED
    val notes: String = ""
)

@Entity(tableName = "system_boot_logs")
data class SystemBootEntity(
    @PrimaryKey val dateString: String, // e.g. "2026-10-13"
    val silverGlassHydrated: Boolean = false,
    val strayDogsFed: Boolean = false,
    val digitalPerimeterLocked: Boolean = false,
    val cognitiveMomentumScore: Int = 85, // 0-100
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "micro_tasks")
data class MicroTaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val examType: String,
    val subject: String,
    val directiveType: String, // FRICTION_BREAKER, DRILL, SYNTAX_CODE, OPERATION
    val isCompleted: Boolean = false,
    val createdTimestamp: Long = System.currentTimeMillis(),
    val completedTimestamp: Long? = null
)

@Entity(tableName = "elimination_drills")
data class EliminationDrillEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val drillTitle: String,
    val exam: String,
    val totalQuestions: Int,
    val correctCount: Int,
    val wrongEliminated: Int,
    val wildGuessesAvoided: Int,
    val netScore: Float,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Room schema entity for tracking granular syllabus coverage for
 * UPSC APFC (Dec 20, 2026) and Allahabad HC RO/ARO competitive exams.
 */
@Entity(tableName = "syllabus_coverage")
data class SyllabusCoverageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val examType: String,                // "UPSC APFC" or "Allahabad HC RO/ARO"
    val subject: String,                 // e.g. "Labour Laws & Industrial Relations", "General Hindi"
    val moduleCode: String,              // e.g. "APFC_IR_01", "ROARO_HIN_01"
    val topicName: String,               // e.g. "Standing Orders & Dispute Resolution (Code 2020)"
    val totalEstimatedHours: Float,      // Estimated deep study allocation
    val completedHours: Float = 0f,      // Actual logged study hours
    val masteryPercentage: Int = 0,      // 0 to 100 based on PYQ output & test accuracy
    val pyqTargetCount: Int = 50,        // Target PYQs to master
    val pyqCompletedCount: Int = 0,      // PYQs solved without speculation
    val revisionCycleCount: Int = 0,     // Spaced repetition counter
    val lastReviewedTimestamp: Long = System.currentTimeMillis(),
    val status: String = "NOT_STARTED"   // "NOT_STARTED", "IN_PROGRESS", "PYQ_PRACTICE", "MASTERED"
)

/**
 * Granular study session log entity capturing tactical telemetry,
 * duration, PYQ precision, and psychological state (Moon-Ketu vs Mars-Saturn).
 */
@Entity(tableName = "study_session_logs")
data class StudySessionLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val examType: String,                // "UPSC APFC" or "Allahabad HC RO/ARO"
    val subject: String,                 // e.g. "Labour Laws", "General Accounting", "Computer Knowledge"
    val topic: String,                   // Specific section or chapter
    val moduleCode: String? = null,      // Associated syllabus module code
    val durationMinutes: Int,            // Duration of the block
    val pyqsAttempted: Int = 0,          // Total questions engaged
    val pyqsCorrect: Int = 0,            // Questions correctly deduced
    val eliminationAccuracy: Float = 0f, // Deduction efficiency % (0-100)
    val cognitiveState: String = "SATURN_STEADY_GRIND", // "PEAK_MOMENTUM", "SATURN_STEADY_GRIND", "MOON_FOG_BYPASS", "FRICTION_BREAKER"
    val tacticalNotes: String = "",      // Operational insights or formula deductions
    val timestamp: Long = System.currentTimeMillis()
)
