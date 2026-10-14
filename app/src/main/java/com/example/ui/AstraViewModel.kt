package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AstraDatabase
import com.example.data.model.DeploymentBlockEntity
import com.example.data.model.MicroTaskEntity
import com.example.data.model.SystemBootEntity
import com.example.domain.astro.AstroTacticalEngine
import com.example.domain.astro.TacticalWeatherReport
import com.example.domain.coach.CoachMessage
import com.example.domain.coach.TacticalCoachEngine
import com.example.domain.noguess.EliminationQuestion
import com.example.domain.noguess.NoGuessDrillRepository
import com.example.domain.syllabus.CodeSyntaxModule
import com.example.domain.syllabus.SyllabusRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class AstraScreen(val title: String, val iconTag: String) {
    COMMAND_DASHBOARD("Ops Radar", "radar"),
    DEPLOYMENT_TIMER("Mechanical Execution", "timer"),
    SYLLABUS_SYNTAX("IT Logic Trees", "code"),
    NO_GUESS_PROTOCOL("No-Guess Drills", "shield"),
    COACH_TERMINAL("Tactical Coach", "terminal"),
    ASTRO_BLUEPRINT("Astrological DNA", "dna")
}

enum class TimerState { IDLE, RUNNING, PAUSED, COMPLETED }

class AstraViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AstraDatabase.getDatabase(application, viewModelScope)
    private val dao = db.astraDao()

    // Screen Navigation
    private val _currentScreen = MutableStateFlow(AstraScreen.COMMAND_DASHBOARD)
    val currentScreen: StateFlow<AstraScreen> = _currentScreen.asStateFlow()

    // Tactical Astrological Telemetry
    private val _tacticalWeather = MutableStateFlow(AstroTacticalEngine.calculateTacticalWeather())
    val tacticalWeather: StateFlow<TacticalWeatherReport> = _tacticalWeather.asStateFlow()

    // Room Database Flows
    val deployments = dao.getAllDeployments().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val microTasks = dao.getAllMicroTasks().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val totalMinutes = dao.getTotalFocusMinutes().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        150
    )

    val totalPyqs = dao.getTotalPyqsSolved().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        55
    )

    // Today's System Boot State
    private val todayString = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
    private val _bootState = MutableStateFlow(
        SystemBootEntity(
            dateString = todayString,
            silverGlassHydrated = true,
            strayDogsFed = true,
            digitalPerimeterLocked = true,
            cognitiveMomentumScore = 90
        )
    )
    val bootState: StateFlow<SystemBootEntity> = _bootState.asStateFlow()

    // Timer & Execution Engine
    private val _timerState = MutableStateFlow(TimerState.IDLE)
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    private val _targetDurationMinutes = MutableStateFlow(90)
    val targetDurationMinutes: StateFlow<Int> = _targetDurationMinutes.asStateFlow()

    private val _remainingSeconds = MutableStateFlow(90 * 60)
    val remainingSeconds: StateFlow<Int> = _remainingSeconds.asStateFlow()

    private val _activeSubject = MutableStateFlow("Labour Laws & Industrial Relations")
    val activeSubject: StateFlow<String> = _activeSubject.asStateFlow()

    private val _activeExam = MutableStateFlow("UPSC APFC")
    val activeExam: StateFlow<String> = _activeExam.asStateFlow()

    private val _activeTopic = MutableStateFlow("Standing Orders & Dispute Resolution (Code 2020)")
    val activeTopic: StateFlow<String> = _activeTopic.asStateFlow()

    private val _sessionPyqs = MutableStateFlow(0)
    val sessionPyqs: StateFlow<Int> = _sessionPyqs.asStateFlow()

    private var timerJob: Job? = null

    // Syllabus Syntax Selected Module
    private val _selectedModule = MutableStateFlow<CodeSyntaxModule>(SyllabusRepository.MODULES[0])
    val selectedModule: StateFlow<CodeSyntaxModule> = _selectedModule.asStateFlow()

    // No-Guess Drill Engine
    private val _activeQuestionIndex = MutableStateFlow(0)
    val activeQuestionIndex: StateFlow<Int> = _activeQuestionIndex.asStateFlow()

    private val _eliminatedOptions = MutableStateFlow<Set<Int>>(emptySet())
    val eliminatedOptions: StateFlow<Set<Int>> = _eliminatedOptions.asStateFlow()

    private val _selectedAnswerIndex = MutableStateFlow<Int?>(null)
    val selectedAnswerIndex: StateFlow<Int?> = _selectedAnswerIndex.asStateFlow()

    private val _drillSubmitted = MutableStateFlow(false)
    val drillSubmitted: StateFlow<Boolean> = _drillSubmitted.asStateFlow()

    // Coach Terminal
    private val _coachMessages = MutableStateFlow<List<CoachMessage>>(
        listOf(TacticalCoachEngine.INITIAL_BRIEFING)
    )
    val coachMessages: StateFlow<List<CoachMessage>> = _coachMessages.asStateFlow()

    private val _coachLoading = MutableStateFlow(false)
    val coachLoading: StateFlow<Boolean> = _coachLoading.asStateFlow()

    init {
        // Observe boot log
        viewModelScope.launch {
            dao.getBootLogForDate(todayString).collect { entity ->
                if (entity != null) {
                    _bootState.value = entity
                }
            }
        }
    }

    fun navigateTo(screen: AstraScreen) {
        _currentScreen.value = screen
    }

    // System Boot Protocols
    fun toggleSilverGlass() {
        val updated = _bootState.value.copy(
            silverGlassHydrated = !_bootState.value.silverGlassHydrated
        )
        saveBootLog(updated)
    }

    fun toggleStrayDogs() {
        val updated = _bootState.value.copy(
            strayDogsFed = !_bootState.value.strayDogsFed
        )
        saveBootLog(updated)
    }

    fun toggleDigitalPerimeter() {
        val updated = _bootState.value.copy(
            digitalPerimeterLocked = !_bootState.value.digitalPerimeterLocked
        )
        saveBootLog(updated)
    }

    private fun saveBootLog(entity: SystemBootEntity) {
        _bootState.value = entity
        viewModelScope.launch {
            dao.insertOrUpdateBootLog(entity)
        }
    }

    // Timer Controls
    fun setDuration(minutes: Int) {
        if (_timerState.value == TimerState.IDLE) {
            _targetDurationMinutes.value = minutes
            _remainingSeconds.value = minutes * 60
        }
    }

    fun setDeploymentDetails(exam: String, subject: String, topic: String) {
        _activeExam.value = exam
        _activeSubject.value = subject
        _activeTopic.value = topic
    }

    fun startTimer() {
        if (_timerState.value == TimerState.IDLE || _timerState.value == TimerState.PAUSED) {
            _timerState.value = TimerState.RUNNING
            timerJob?.cancel()
            timerJob = viewModelScope.launch {
                while (_remainingSeconds.value > 0 && _timerState.value == TimerState.RUNNING) {
                    delay(1000)
                    _remainingSeconds.value -= 1
                }
                if (_remainingSeconds.value <= 0) {
                    _timerState.value = TimerState.COMPLETED
                    completeDeployment(isFull = true)
                }
            }
        }
    }

    fun pauseTimer() {
        if (_timerState.value == TimerState.RUNNING) {
            _timerState.value = TimerState.PAUSED
            timerJob?.cancel()
        }
    }

    fun resetTimer() {
        timerJob?.cancel()
        _timerState.value = TimerState.IDLE
        _remainingSeconds.value = _targetDurationMinutes.value * 60
    }

    fun incrementPyq() {
        _sessionPyqs.value += 1
    }

    fun decrementPyq() {
        if (_sessionPyqs.value > 0) {
            _sessionPyqs.value -= 1
        }
    }

    fun completeDeployment(isFull: Boolean = true) {
        val spentMinutes = if (isFull) {
            _targetDurationMinutes.value
        } else {
            maxOf(1, (_targetDurationMinutes.value * 60 - _remainingSeconds.value) / 60)
        }

        viewModelScope.launch {
            dao.insertDeployment(
                DeploymentBlockEntity(
                    examType = _activeExam.value,
                    subject = _activeSubject.value,
                    topic = _activeTopic.value,
                    durationMinutes = spentMinutes,
                    pyqsSolved = _sessionPyqs.value,
                    status = if (isFull) "COMPLETED" else "INTERRUPTED",
                    notes = "Mechanical Mars execution block completed. Bypassed mental friction."
                )
            )
            resetTimer()
            _sessionPyqs.value = 0
            _tacticalWeather.value = AstroTacticalEngine.calculateTacticalWeather()
        }
    }

    // Micro-Tasks
    fun toggleMicroTask(task: MicroTaskEntity) {
        viewModelScope.launch {
            if (task.isCompleted) {
                dao.updateMicroTask(task.copy(isCompleted = false, completedTimestamp = null))
            } else {
                dao.markTaskComplete(task.id, System.currentTimeMillis())
            }
        }
    }

    fun addMicroTask(title: String, exam: String, subject: String, type: String = "OPERATION") {
        viewModelScope.launch {
            dao.insertMicroTask(
                MicroTaskEntity(
                    title = title,
                    examType = exam,
                    subject = subject,
                    directiveType = type,
                    isCompleted = false
                )
            )
        }
    }

    // Syllabus Module
    fun selectModule(module: CodeSyntaxModule) {
        _selectedModule.value = module
    }

    // No-Guess Drill Actions
    fun toggleEliminateOption(optionIndex: Int) {
        val current = _eliminatedOptions.value.toMutableSet()
        if (current.contains(optionIndex)) {
            current.remove(optionIndex)
        } else {
            current.add(optionIndex)
            // If it was selected as answer, unselect it
            if (_selectedAnswerIndex.value == optionIndex) {
                _selectedAnswerIndex.value = null
            }
        }
        _eliminatedOptions.value = current
    }

    fun selectFinalAnswer(optionIndex: Int) {
        if (!_eliminatedOptions.value.contains(optionIndex)) {
            _selectedAnswerIndex.value = optionIndex
        }
    }

    fun submitDrillAnswer() {
        if (_selectedAnswerIndex.value != null) {
            _drillSubmitted.value = true
        }
    }

    fun nextDrillQuestion() {
        val total = NoGuessDrillRepository.SAMPLE_DRILLS.size
        _activeQuestionIndex.value = (_activeQuestionIndex.value + 1) % total
        _eliminatedOptions.value = emptySet()
        _selectedAnswerIndex.value = null
        _drillSubmitted.value = false
    }

    // Tactical Coach
    fun sendCoachPrompt(prompt: String) {
        if (prompt.isBlank()) return
        val userMsg = CoachMessage(sender = "USER", content = prompt)
        _coachMessages.value = _coachMessages.value + userMsg
        _coachLoading.value = true

        viewModelScope.launch {
            val response = TacticalCoachEngine.consultCoach(prompt)
            _coachMessages.value = _coachMessages.value + response
            _coachLoading.value = false
        }
    }
}
