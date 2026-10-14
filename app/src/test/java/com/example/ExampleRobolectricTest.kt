package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.AstraDatabase
import com.example.data.model.StudySessionLogEntity
import com.example.data.model.SyllabusCoverageEntity
import com.example.domain.astro.AstroTacticalEngine
import com.example.domain.noguess.NoGuessDrillRepository
import com.example.domain.syllabus.SyllabusRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    private lateinit var database: AstraDatabase

    @Before
    fun setupDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AstraDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun `read string from context matches AstraOps`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("AstraOps", appName)
    }

    @Test
    fun `tactical weather report calculates valid telemetry`() {
        val weather = AstroTacticalEngine.calculateTacticalWeather()
        assertEquals("Moon-Mars Dasha", weather.currentDasha)
        assertEquals("Career Breakthrough Phase", weather.phaseTitle)
        assertTrue(weather.marsStaminaScore in 50..100)
        assertTrue(weather.saturnDisciplineIndex in 50..100)
        assertTrue(weather.apfcDaysRemaining >= 0)
    }

    @Test
    fun `syllabus coverage entity and DAO insert and query works`() = runBlocking {
        val dao = database.syllabusCoverageDao()

        val apfcModule = SyllabusCoverageEntity(
            examType = "UPSC APFC",
            subject = "Labour Laws & Industrial Relations",
            moduleCode = "APFC_IR_01",
            topicName = "Industrial Relations Code 2020",
            totalEstimatedHours = 14f,
            completedHours = 4.5f,
            masteryPercentage = 60,
            pyqTargetCount = 50,
            pyqCompletedCount = 30,
            status = "IN_PROGRESS"
        )

        val id = dao.insertCoverage(apfcModule)
        assertTrue(id > 0)

        val list = dao.getCoverageByExam("UPSC APFC").first()
        assertEquals(1, list.size)
        assertEquals("APFC_IR_01", list[0].moduleCode)
        assertEquals("UPSC APFC", list[0].examType)
    }

    @Test
    fun `study session log entity and DAO tracking works`() = runBlocking {
        val dao = database.syllabusCoverageDao()

        val session = StudySessionLogEntity(
            examType = "UPSC APFC",
            subject = "Labour Laws & Industrial Relations",
            topic = "Standing Orders 300 Workers Threshold",
            moduleCode = "APFC_IR_01",
            durationMinutes = 90,
            pyqsAttempted = 25,
            pyqsCorrect = 23,
            eliminationAccuracy = 92f,
            cognitiveState = "PEAK_MOMENTUM",
            tacticalNotes = "Zero speculation. Applied 1946 vs 2020 delta."
        )

        val logId = dao.insertSessionLog(session)
        assertTrue(logId > 0)

        val logs = dao.getSessionLogsByExam("UPSC APFC").first()
        assertEquals(1, logs.size)
        assertEquals(90, logs[0].durationMinutes)
        assertEquals(25, logs[0].pyqsAttempted)
        assertEquals("PEAK_MOMENTUM", logs[0].cognitiveState)

        val totalMinutes = dao.getTotalStudyMinutesByExam("UPSC APFC").first()
        assertEquals(90, totalMinutes)
    }

    @Test
    fun `syllabus repository has high yield code modules`() {
        val modules = SyllabusRepository.MODULES
        assertTrue(modules.isNotEmpty())
        val irCode = modules.find { it.id == "ir_code_standing_orders" }
        assertNotNull(irCode)
        assertTrue(irCode!!.pseudoCode.contains("300"))
    }

    @Test
    fun `no guess drills have valid deduction logic`() {
        val drills = NoGuessDrillRepository.SAMPLE_DRILLS
        assertTrue(drills.isNotEmpty())
        drills.forEach { drill ->
            assertTrue(drill.correctIndex in drill.options.indices)
            assertTrue(drill.trapIndex in drill.options.indices)
            assertTrue(drill.deductionSteps.isNotEmpty())
        }
    }
}
