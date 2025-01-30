package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WorkoutHistoryDao {
    @Transaction
    @Query("SELECT * FROM workout_history WHERE userId = :userId ORDER BY completedDate DESC")
    fun getUserWorkoutHistory(userId: Long): LiveData<List<WorkoutHistoryWithWorkout>>

    @Transaction
    @Query("""
        SELECT * FROM workout_history 
        WHERE userId = :userId 
        AND completedDate >= :startDate 
        AND completedDate <= :endDate
        ORDER BY completedDate DESC
    """)
    fun getUserWorkoutHistoryBetweenDates(
        userId: Long,
        startDate: Long,
        endDate: Long
    ): LiveData<List<WorkoutHistoryWithWorkout>>

    @Query("""
        SELECT SUM(caloriesBurned) 
        FROM workout_history 
        WHERE userId = :userId 
        AND completedDate >= :startDate 
        AND completedDate <= :endDate
    """)
    suspend fun getTotalCaloriesBurnedBetweenDates(
        userId: Long,
        startDate: Long,
        endDate: Long
    ): Int

    @Query("""
        SELECT COUNT(*) 
        FROM workout_history 
        WHERE userId = :userId 
        AND completedDate >= :startDate 
        AND completedDate <= :endDate
    """)
    suspend fun getWorkoutCountBetweenDates(
        userId: Long,
        startDate: Long,
        endDate: Long
    ): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutHistory(workoutHistory: WorkoutHistory): Long

    @Update
    suspend fun updateWorkoutHistory(workoutHistory: WorkoutHistory)

    @Delete
    suspend fun deleteWorkoutHistory(workoutHistory: WorkoutHistory)

    @Query("""
        SELECT AVG(duration) 
        FROM workout_history 
        WHERE userId = :userId 
        AND completedDate >= :startDate 
        AND completedDate <= :endDate
    """)
    suspend fun getAverageWorkoutDuration(
        userId: Long,
        startDate: Long,
        endDate: Long
    ): Float

    @Query("""
        SELECT COUNT(*) 
        FROM workout_history 
        WHERE userId = :userId 
        AND completedDate >= :startDate 
        AND completedDate <= :endDate
        GROUP BY strftime('%W', datetime(completedDate/1000, 'unixepoch'))
        ORDER BY COUNT(*) DESC
        LIMIT 1
    """)
    suspend fun getMostActiveWeekWorkouts(
        userId: Long,
        startDate: Long,
        endDate: Long
    ): Int?
}
