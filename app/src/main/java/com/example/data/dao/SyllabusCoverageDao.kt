package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.StudySessionLogEntity
import com.example.data.model.SyllabusCoverageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SyllabusCoverageDao {

    // ==========================================
    // SYLLABUS COVERAGE QUERIES (APFC & RO/ARO)
    // ==========================================

    @Query("SELECT * FROM syllabus_coverage ORDER BY examType ASC, subject ASC, moduleCode ASC")
    fun getAllCoverage(): Flow<List<SyllabusCoverageEntity>>

    @Query("SELECT * FROM syllabus_coverage WHERE examType = :examType ORDER BY subject ASC, moduleCode ASC")
    fun getCoverageByExam(examType: String): Flow<List<SyllabusCoverageEntity>>

    @Query("SELECT * FROM syllabus_coverage WHERE examType = :examType AND subject = :subject ORDER BY moduleCode ASC")
    fun getCoverageBySubject(examType: String, subject: String): Flow<List<SyllabusCoverageEntity>>

    @Query("SELECT DISTINCT subject FROM syllabus_coverage WHERE examType = :examType ORDER BY subject ASC")
    fun getDistinctSubjectsForExam(examType: String): Flow<List<String>>

    @Query("SELECT * FROM syllabus_coverage WHERE id = :id LIMIT 1")
    fun getCoverageById(id: Long): Flow<SyllabusCoverageEntity?>

    @Query("SELECT * FROM syllabus_coverage WHERE moduleCode = :moduleCode LIMIT 1")
    suspend fun getCoverageByModuleCode(moduleCode: String): SyllabusCoverageEntity?

    @Query("SELECT COUNT(*) FROM syllabus_coverage WHERE examType = :examType")
    fun getTotalModulesCount(examType: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM syllabus_coverage WHERE examType = :examType AND status = 'MASTERED'")
    fun getMasteredModulesCount(examType: String): Flow<Int>

    @Query("SELECT AVG(masteryPercentage) FROM syllabus_coverage WHERE examType = :examType")
    fun getAverageMasteryForExam(examType: String): Flow<Float?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoverage(coverage: SyllabusCoverageEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoverage(coverageList: List<SyllabusCoverageEntity>)

    @Update
    suspend fun updateCoverage(coverage: SyllabusCoverageEntity)

    @Query("""
        UPDATE syllabus_coverage 
        SET completedHours = completedHours + :additionalHours,
            masteryPercentage = :newMastery,
            pyqCompletedCount = pyqCompletedCount + :additionalPyqs,
            status = :newStatus,
            lastReviewedTimestamp = :timestamp
        WHERE id = :id
    """)
    suspend fun updateCoverageProgress(
        id: Long,
        additionalHours: Float,
        newMastery: Int,
        additionalPyqs: Int,
        newStatus: String,
        timestamp: Long = System.currentTimeMillis()
    )

    @Query("""
        UPDATE syllabus_coverage 
        SET revisionCycleCount = revisionCycleCount + 1,
            lastReviewedTimestamp = :timestamp
        WHERE id = :id
    """)
    suspend fun incrementRevision(id: Long, timestamp: Long = System.currentTimeMillis())

    // ==========================================
    // STUDY SESSION LOGS QUERIES
    // ==========================================

    @Query("SELECT * FROM study_session_logs ORDER BY timestamp DESC")
    fun getAllSessionLogs(): Flow<List<StudySessionLogEntity>>

    @Query("SELECT * FROM study_session_logs WHERE examType = :examType ORDER BY timestamp DESC")
    fun getSessionLogsByExam(examType: String): Flow<List<StudySessionLogEntity>>

    @Query("SELECT * FROM study_session_logs WHERE moduleCode = :moduleCode ORDER BY timestamp DESC")
    fun getSessionLogsForModule(moduleCode: String): Flow<List<StudySessionLogEntity>>

    @Query("SELECT * FROM study_session_logs ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentSessionLogs(limit: Int = 20): Flow<List<StudySessionLogEntity>>

    @Query("SELECT SUM(durationMinutes) FROM study_session_logs WHERE examType = :examType")
    fun getTotalStudyMinutesByExam(examType: String): Flow<Int?>

    @Query("SELECT SUM(pyqsAttempted) FROM study_session_logs WHERE examType = :examType")
    fun getTotalPyqsAttemptedByExam(examType: String): Flow<Int?>

    @Query("SELECT SUM(pyqsCorrect) FROM study_session_logs WHERE examType = :examType")
    fun getTotalPyqsCorrectByExam(examType: String): Flow<Int?>

    @Query("SELECT AVG(eliminationAccuracy) FROM study_session_logs WHERE examType = :examType AND pyqsAttempted > 0")
    fun getAverageAccuracyByExam(examType: String): Flow<Float?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSessionLog(session: StudySessionLogEntity): Long

    @Query("DELETE FROM study_session_logs WHERE id = :id")
    suspend fun deleteSessionLog(id: Long)
}
