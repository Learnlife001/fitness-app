package com.example.myapplication

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutHistoryWithWorkout(
    @Embedded
    val workoutHistory: WorkoutHistory,
    
    @Relation(
        parentColumn = "workoutId",
        entityColumn = "id"
    )
    val workout: Workout
)
