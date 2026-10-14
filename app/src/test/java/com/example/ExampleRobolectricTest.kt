package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.domain.astro.AstroTacticalEngine
import com.example.domain.noguess.NoGuessDrillRepository
import com.example.domain.syllabus.SyllabusRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

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
