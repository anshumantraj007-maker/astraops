package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.DeploymentBlockEntity
import com.example.data.model.EliminationDrillEntity
import com.example.data.model.MicroTaskEntity
import com.example.data.model.SystemBootEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AstraDao {

    // Deployment Blocks
    @Query("SELECT * FROM deployment_blocks ORDER BY timestamp DESC")
    fun getAllDeployments(): Flow<List<DeploymentBlockEntity>>

    @Query("SELECT * FROM deployment_blocks WHERE examType = :exam ORDER BY timestamp DESC")
    fun getDeploymentsByExam(exam: String): Flow<List<DeploymentBlockEntity>>

    @Query("SELECT SUM(durationMinutes) FROM deployment_blocks")
    fun getTotalFocusMinutes(): Flow<Int?>

    @Query("SELECT SUM(pyqsSolved) FROM deployment_blocks")
    fun getTotalPyqsSolved(): Flow<Int?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeployment(deployment: DeploymentBlockEntity): Long

    // System Boot Protocols
    @Query("SELECT * FROM system_boot_logs WHERE dateString = :date LIMIT 1")
    fun getBootLogForDate(date: String): Flow<SystemBootEntity?>

    @Query("SELECT * FROM system_boot_logs ORDER BY dateString DESC LIMIT 7")
    fun getRecentBootLogs(): Flow<List<SystemBootEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateBootLog(bootLog: SystemBootEntity)

    // Micro-Tasks / Directives
    @Query("SELECT * FROM micro_tasks ORDER BY isCompleted ASC, createdTimestamp DESC")
    fun getAllMicroTasks(): Flow<List<MicroTaskEntity>>

    @Query("SELECT * FROM micro_tasks WHERE isCompleted = 0 ORDER BY createdTimestamp DESC")
    fun getPendingMicroTasks(): Flow<List<MicroTaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMicroTask(task: MicroTaskEntity): Long

    @Update
    suspend fun updateMicroTask(task: MicroTaskEntity)

    @Query("UPDATE micro_tasks SET isCompleted = 1, completedTimestamp = :time WHERE id = :taskId")
    suspend fun markTaskComplete(taskId: Long, time: Long)

    // Elimination Drills
    @Query("SELECT * FROM elimination_drills ORDER BY timestamp DESC")
    fun getAllDrills(): Flow<List<EliminationDrillEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrill(drill: EliminationDrillEntity): Long
}
