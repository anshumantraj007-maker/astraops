package com.example.data.repository

import com.example.data.dao.SyllabusCoverageDao
import com.example.data.model.StudySessionLogEntity
import com.example.data.model.SyllabusCoverageEntity
import kotlinx.coroutines.flow.Flow

class SyllabusCoverageRepository(
    private val dao: SyllabusCoverageDao
) {

    // Reactive streams
    fun getCoverageByExam(examType: String): Flow<List<SyllabusCoverageEntity>> =
        dao.getCoverageByExam(examType)

    fun getSubjectsByExam(examType: String): Flow<List<String>> =
        dao.getDistinctSubjectsForExam(examType)

    fun getCoverageBySubject(examType: String, subject: String): Flow<List<SyllabusCoverageEntity>> =
        dao.getCoverageBySubject(examType, subject)

    fun getSessionLogsByExam(examType: String): Flow<List<StudySessionLogEntity>> =
        dao.getSessionLogsByExam(examType)

    fun getRecentSessionLogs(limit: Int = 15): Flow<List<StudySessionLogEntity>> =
        dao.getRecentSessionLogs(limit)

    fun getTotalStudyMinutes(examType: String): Flow<Int?> =
        dao.getTotalStudyMinutesByExam(examType)

    fun getTotalPyqs(examType: String): Flow<Int?> =
        dao.getTotalPyqsAttemptedByExam(examType)

    fun getAverageAccuracy(examType: String): Flow<Float?> =
        dao.getAverageAccuracyByExam(examType)

    fun getMasteredCount(examType: String): Flow<Int> =
        dao.getMasteredModulesCount(examType)

    fun getTotalModulesCount(examType: String): Flow<Int> =
        dao.getTotalModulesCount(examType)

    // Mutation Operations
    suspend fun recordStudySession(session: StudySessionLogEntity): Long {
        val sessionId = dao.insertSessionLog(session)

        // Automatically update the corresponding syllabus coverage module if moduleCode is provided
        if (!session.moduleCode.isNullOrBlank()) {
            val module = dao.getCoverageByModuleCode(session.moduleCode)
            if (module != null) {
                val additionalHours = session.durationMinutes / 60.0f
                val newPyqCount = module.pyqCompletedCount + session.pyqsAttempted

                // Calculate updated mastery percentage based on hours + PYQs solved
                val hoursFactor = minOf(1.0f, (module.completedHours + additionalHours) / module.totalEstimatedHours)
                val pyqFactor = minOf(1.0f, newPyqCount.toFloat() / maxOf(1, module.pyqTargetCount).toFloat())
                val newMastery = ((hoursFactor * 0.4f + pyqFactor * 0.6f) * 100).toInt().coerceIn(0, 100)

                val newStatus = when {
                    newMastery >= 90 -> "MASTERED"
                    newMastery >= 50 -> "PYQ_PRACTICE"
                    else -> "IN_PROGRESS"
                }

                dao.updateCoverageProgress(
                    id = module.id,
                    additionalHours = additionalHours,
                    newMastery = newMastery,
                    additionalPyqs = session.pyqsAttempted,
                    newStatus = newStatus,
                    timestamp = System.currentTimeMillis()
                )
            }
        }
        return sessionId
    }

    suspend fun insertCoverageModule(coverage: SyllabusCoverageEntity): Long =
        dao.insertCoverage(coverage)

    suspend fun incrementRevision(id: Long) =
        dao.incrementRevision(id)

    suspend fun deleteSessionLog(id: Long) =
        dao.deleteSessionLog(id)
}
