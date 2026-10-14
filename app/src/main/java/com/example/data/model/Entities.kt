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
