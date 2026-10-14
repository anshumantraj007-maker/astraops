package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.dao.AstraDao
import com.example.data.dao.SyllabusCoverageDao
import com.example.data.model.DeploymentBlockEntity
import com.example.data.model.EliminationDrillEntity
import com.example.data.model.MicroTaskEntity
import com.example.data.model.StudySessionLogEntity
import com.example.data.model.SyllabusCoverageEntity
import com.example.data.model.SystemBootEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Database(
    entities = [
        DeploymentBlockEntity::class,
        SystemBootEntity::class,
        MicroTaskEntity::class,
        EliminationDrillEntity::class,
        SyllabusCoverageEntity::class,
        StudySessionLogEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AstraDatabase : RoomDatabase() {

    abstract fun astraDao(): AstraDao
    abstract fun syllabusCoverageDao(): SyllabusCoverageDao

    companion object {
        @Volatile
        private var INSTANCE: AstraDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AstraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AstraDatabase::class.java,
                    "astra_tactical_db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    val astraDao = database.astraDao()
                    val coverageDao = database.syllabusCoverageDao()
                    val today = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())

                    // Initialize Today's System Boot Protocol
                    astraDao.insertOrUpdateBootLog(
                        SystemBootEntity(
                            dateString = today,
                            silverGlassHydrated = true,
                            strayDogsFed = true,
                            digitalPerimeterLocked = true,
                            cognitiveMomentumScore = 92
                        )
                    )

                    // Seed High-Priority Tactical Micro-Tasks
                    astraDao.insertMicroTask(
                        MicroTaskEntity(
                            title = "Execute IR Code 2020: Convert Grievance Redressal Committee into logic tree (>=20 workers, max 10 members)",
                            examType = "UPSC APFC",
                            subject = "Labour Laws & Industrial Relations",
                            directiveType = "SYNTAX_CODE",
                            isCompleted = false
                        )
                    )
                    astraDao.insertMicroTask(
                        MicroTaskEntity(
                            title = "Social Security Code: Map out EPS 1995 vs EPFO 1952 contribution splits (8.33% vs 3.67%) in binary table",
                            examType = "UPSC APFC",
                            subject = "Social Security",
                            directiveType = "OPERATION",
                            isCompleted = false
                        )
                    )
                    astraDao.insertMicroTask(
                        MicroTaskEntity(
                            title = "HC RO/ARO Hindi Logic Drill: 15 Tatsam-Tadbhav phonetic transformation rules",
                            examType = "Allahabad HC RO/ARO",
                            subject = "General Hindi",
                            directiveType = "NO_GUESS_DRILL",
                            isCompleted = false
                        )
                    )
                    astraDao.insertMicroTask(
                        MicroTaskEntity(
                            title = "Friction-Breaker: Solve 5 PYQs on Accounting Standards (AS-1 to AS-10) with 0 speculation",
                            examType = "UPSC APFC",
                            subject = "General Accounting Principles",
                            directiveType = "FRICTION_BREAKER",
                            isCompleted = false
                        )
                    )

                    // Seed Baseline Syllabus Modules for UPSC APFC
                    val apfcModules = listOf(
                        SyllabusCoverageEntity(
                            examType = "UPSC APFC",
                            subject = "Labour Laws & Industrial Relations",
                            moduleCode = "APFC_IR_01",
                            topicName = "Industrial Relations Code 2020 (Standing Orders, Retrenchment & Trade Unions)",
                            totalEstimatedHours = 14f,
                            completedHours = 8.5f,
                            masteryPercentage = 75,
                            pyqTargetCount = 60,
                            pyqCompletedCount = 45,
                            revisionCycleCount = 2,
                            status = "PYQ_PRACTICE"
                        ),
                        SyllabusCoverageEntity(
                            examType = "UPSC APFC",
                            subject = "Social Security",
                            moduleCode = "APFC_SS_01",
                            topicName = "Code on Social Security 2020 (EPFO, EPS 1995, EDLI, Gratuity Pro-Rata)",
                            totalEstimatedHours = 12f,
                            completedHours = 7.0f,
                            masteryPercentage = 70,
                            pyqTargetCount = 50,
                            pyqCompletedCount = 35,
                            revisionCycleCount = 1,
                            status = "PYQ_PRACTICE"
                        ),
                        SyllabusCoverageEntity(
                            examType = "UPSC APFC",
                            subject = "General Accounting Principles",
                            moduleCode = "APFC_ACC_01",
                            topicName = "Accounting Standards, Balance Sheet Invariants & Depreciation Rules",
                            totalEstimatedHours = 16f,
                            completedHours = 10f,
                            masteryPercentage = 68,
                            pyqTargetCount = 70,
                            pyqCompletedCount = 48,
                            revisionCycleCount = 1,
                            status = "IN_PROGRESS"
                        ),
                        SyllabusCoverageEntity(
                            examType = "UPSC APFC",
                            subject = "Indian Polity & Governance",
                            moduleCode = "APFC_POL_01",
                            topicName = "Constitutional Articles, Writs, Emergency Clauses & Executive Orders",
                            totalEstimatedHours = 15f,
                            completedHours = 6.0f,
                            masteryPercentage = 50,
                            pyqTargetCount = 50,
                            pyqCompletedCount = 25,
                            revisionCycleCount = 1,
                            status = "IN_PROGRESS"
                        ),
                        SyllabusCoverageEntity(
                            examType = "UPSC APFC",
                            subject = "Computer Applications & IT",
                            moduleCode = "APFC_COMP_01",
                            topicName = "Data Security, Networking, DBMS & Cloud Infrastructure (BCA Core Advantage)",
                            totalEstimatedHours = 8f,
                            completedHours = 8f,
                            masteryPercentage = 95,
                            pyqTargetCount = 40,
                            pyqCompletedCount = 40,
                            revisionCycleCount = 3,
                            status = "MASTERED"
                        ),
                        SyllabusCoverageEntity(
                            examType = "UPSC APFC",
                            subject = "Elementary Mathematics & Statistics",
                            moduleCode = "APFC_STAT_01",
                            topicName = "Descriptive Statistics, Probability, Ratios & Time-Speed-Distance",
                            totalEstimatedHours = 12f,
                            completedHours = 4.0f,
                            masteryPercentage = 40,
                            pyqTargetCount = 50,
                            pyqCompletedCount = 20,
                            revisionCycleCount = 0,
                            status = "IN_PROGRESS"
                        )
                    )

                    // Seed Baseline Syllabus Modules for Allahabad HC RO/ARO
                    val roAroModules = listOf(
                        SyllabusCoverageEntity(
                            examType = "Allahabad HC RO/ARO",
                            subject = "Computer Knowledge & IT",
                            moduleCode = "ROARO_COMP_01",
                            topicName = "OSI 7 Layers, Networking Protocols, MS Shortcuts & Cyber Law (BCA Dominance)",
                            totalEstimatedHours = 10f,
                            completedHours = 10f,
                            masteryPercentage = 98,
                            pyqTargetCount = 60,
                            pyqCompletedCount = 60,
                            revisionCycleCount = 3,
                            status = "MASTERED"
                        ),
                        SyllabusCoverageEntity(
                            examType = "Allahabad HC RO/ARO",
                            subject = "General Hindi",
                            moduleCode = "ROARO_HIN_01",
                            topicName = "Tatsam-Tadbhav Phonetic Transformation Regex & Shuddhi Rules",
                            totalEstimatedHours = 12f,
                            completedHours = 6.5f,
                            masteryPercentage = 65,
                            pyqTargetCount = 75,
                            pyqCompletedCount = 50,
                            revisionCycleCount = 2,
                            status = "PYQ_PRACTICE"
                        ),
                        SyllabusCoverageEntity(
                            examType = "Allahabad HC RO/ARO",
                            subject = "UP Special GK & Geography",
                            moduleCode = "ROARO_UP_01",
                            topicName = "UP Administrative Districts, Census 2011, High Court History & Precedents",
                            totalEstimatedHours = 14f,
                            completedHours = 5.0f,
                            masteryPercentage = 45,
                            pyqTargetCount = 50,
                            pyqCompletedCount = 22,
                            revisionCycleCount = 1,
                            status = "IN_PROGRESS"
                        ),
                        SyllabusCoverageEntity(
                            examType = "Allahabad HC RO/ARO",
                            subject = "General English & Grammar",
                            moduleCode = "ROARO_ENG_01",
                            topicName = "Prepositions, Sentence Correction, Vocabulary & Judicial Terminology",
                            totalEstimatedHours = 10f,
                            completedHours = 4.0f,
                            masteryPercentage = 50,
                            pyqTargetCount = 40,
                            pyqCompletedCount = 20,
                            revisionCycleCount = 1,
                            status = "IN_PROGRESS"
                        )
                    )

                    coverageDao.insertAllCoverage(apfcModules + roAroModules)

                    // Seed High-Precision Study Session Logs
                    coverageDao.insertSessionLog(
                        StudySessionLogEntity(
                            examType = "UPSC APFC",
                            subject = "Labour Laws & Industrial Relations",
                            topic = "Standing Orders (IRC 2020: 300 Workers Threshold)",
                            moduleCode = "APFC_IR_01",
                            durationMinutes = 90,
                            pyqsAttempted = 25,
                            pyqsCorrect = 23,
                            eliminationAccuracy = 92f,
                            cognitiveState = "PEAK_MOMENTUM",
                            tacticalNotes = "Applied statutory delta: 1946 Act (100) vs 2020 Code (300). Eliminated traps on prior permission requirements."
                        )
                    )
                    coverageDao.insertSessionLog(
                        StudySessionLogEntity(
                            examType = "Allahabad HC RO/ARO",
                            subject = "Computer Knowledge & IT",
                            topic = "OSI Layer 2 Switch vs Layer 3 Router Architecture",
                            moduleCode = "ROARO_COMP_01",
                            durationMinutes = 60,
                            pyqsAttempted = 30,
                            pyqsCorrect = 30,
                            eliminationAccuracy = 100f,
                            cognitiveState = "SATURN_STEADY_GRIND",
                            tacticalNotes = "100% accuracy leveraging BCA networking fundamentals. Completed 30 PYQs in 18 minutes."
                        )
                    )
                    coverageDao.insertSessionLog(
                        StudySessionLogEntity(
                            examType = "UPSC APFC",
                            subject = "Social Security",
                            topic = "EPFO 12% Contribution Split (EPS 8.33% vs EPF 3.67%)",
                            moduleCode = "APFC_SS_01",
                            durationMinutes = 45,
                            pyqsAttempted = 20,
                            pyqsCorrect = 18,
                            eliminationAccuracy = 90f,
                            cognitiveState = "MOON_FOG_BYPASS",
                            tacticalNotes = "Felt morning friction. Bypassed 8th house anxiety via silver glass hydration and dove directly into numerical math."
                        )
                    )
                }
            }
        }
    }
}
