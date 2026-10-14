package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.dao.AstraDao
import com.example.data.model.DeploymentBlockEntity
import com.example.data.model.EliminationDrillEntity
import com.example.data.model.MicroTaskEntity
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
        EliminationDrillEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AstraDatabase : RoomDatabase() {

    abstract fun astraDao(): AstraDao

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
                    val dao = database.astraDao()
                    val today = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())

                    // Initialize Today's System Boot Protocol
                    dao.insertOrUpdateBootLog(
                        SystemBootEntity(
                            dateString = today,
                            silverGlassHydrated = true,
                            strayDogsFed = true,
                            digitalPerimeterLocked = true,
                            cognitiveMomentumScore = 92
                        )
                    )

                    // Seed High-Priority Tactical Micro-Tasks
                    dao.insertMicroTask(
                        MicroTaskEntity(
                            title = "Execute IR Code 2020: Convert Grievance Redressal Committee into logic tree (>=20 workers, max 10 members)",
                            examType = "UPSC APFC",
                            subject = "Labour Laws & Industrial Relations",
                            directiveType = "SYNTAX_CODE",
                            isCompleted = false
                        )
                    )
                    dao.insertMicroTask(
                        MicroTaskEntity(
                            title = "Social Security Code: Map out EPS 1995 vs EPFO 1952 contribution splits (8.33% vs 3.67%) in binary table",
                            examType = "UPSC APFC",
                            subject = "Social Security",
                            directiveType = "OPERATION",
                            isCompleted = false
                        )
                    )
                    dao.insertMicroTask(
                        MicroTaskEntity(
                            title = "HC RO/ARO Hindi Logic Drill: 15 Tatsam-Tadbhav phonetic transformation rules",
                            examType = "Allahabad HC RO/ARO",
                            subject = "General Hindi",
                            directiveType = "NO_GUESS_DRILL",
                            isCompleted = false
                        )
                    )
                    dao.insertMicroTask(
                        MicroTaskEntity(
                            title = "Friction-Breaker: Solve 5 PYQs on Accounting Standards (AS-1 to AS-10) with 0 speculation",
                            examType = "UPSC APFC",
                            subject = "General Accounting Principles",
                            directiveType = "FRICTION_BREAKER",
                            isCompleted = false
                        )
                    )

                    // Seed baseline completed deployments
                    dao.insertDeployment(
                        DeploymentBlockEntity(
                            examType = "UPSC APFC",
                            subject = "Labour Laws & Industrial Relations",
                            topic = "Industrial Disputes & Standing Orders 2020",
                            durationMinutes = 90,
                            pyqsSolved = 25,
                            status = "COMPLETED",
                            notes = "Bypassed 8th house analysis paralysis. Converted Chapter IV into algorithmic conditions."
                        )
                    )
                    dao.insertDeployment(
                        DeploymentBlockEntity(
                            examType = "Allahabad HC RO/ARO",
                            subject = "Computer Knowledge & IT",
                            topic = "OSI Layers & Networking Protocols (BCA High Velocity Leverage)",
                            durationMinutes = 60,
                            pyqsSolved = 30,
                            status = "COMPLETED",
                            notes = "Full mastery. 100% accuracy in PYQ set."
                        )
                    )
                }
            }
        }
    }
}
