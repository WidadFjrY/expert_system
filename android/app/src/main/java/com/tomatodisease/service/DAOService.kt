package com.tomatodisease.service

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tomatodisease.model.DiagnosisEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DiagnosisDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiagnosis(diagnosis: DiagnosisEntity)

    @Query("SELECT * FROM diagnosis_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<DiagnosisEntity>>

    @Query("DELETE FROM diagnosis_history")
    suspend fun deleteAll()
}

@Database(entities = [DiagnosisEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diagnosisDao(): DiagnosisDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "diagnosis_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
