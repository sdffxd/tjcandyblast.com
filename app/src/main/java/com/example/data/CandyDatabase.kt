package com.example.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "bug_reports")
data class BugReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val name: String,
    val email: String,
    val category: String,
    val message: String,
    val deviceInfo: String,
    val status: String = "Submitted"
)

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey val id: Int = 1,
    val gems: Int = 250,
    val highScore: Int = 12500,
    val currentLevel: Int = 1,
    val totalSpins: Int = 0,
    val lastSpinTimestamp: Long = 0L,
    val dailyLoginStreak: Int = 1
)

@Dao
interface BugReportDao {
    @Query("SELECT * FROM bug_reports ORDER BY timestamp DESC")
    fun getAllReports(): Flow<List<BugReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: BugReportEntity)
}

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE id = 1")
    fun getUserProgress(): Flow<UserProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProgress(progress: UserProgressEntity)
}

@Database(entities = [BugReportEntity::class, UserProgressEntity::class], version = 1, exportSchema = false)
abstract class CandyDatabase : RoomDatabase() {
    abstract fun bugReportDao(): BugReportDao
    abstract fun userProgressDao(): UserProgressDao

    companion object {
        @Volatile
        private var INSTANCE: CandyDatabase? = null

        fun getDatabase(context: Context): CandyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CandyDatabase::class.java,
                    "tj_candy_blast_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
