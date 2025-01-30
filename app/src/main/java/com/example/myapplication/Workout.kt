package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: String,
    val description: String = "",
    val imageUrl: String = "",  // URL or resource ID for workout image
    val difficulty: String = "Beginner",  // Beginner, Intermediate, Advanced
    val sets: Int = 0,
    val reps: Int = 0,
    val duration: Int = 0,  // in minutes
    val caloriesBurned: Int = 0,
    val date: Long = System.currentTimeMillis(),
    val category: WorkoutCategory = WorkoutCategory.OTHER
)

enum class WorkoutCategory {
    CARDIO,
    STRENGTH,
    FLEXIBILITY,
    HIIT,
    YOGA,
    OTHER
}

// Predefined workout types
object WorkoutTypes {
    val CARDIO = listOf(
        "Running",
        "Cycling",
        "Swimming",
        "Jump Rope",
        "Rowing"
    )
    
    val STRENGTH = listOf(
        "Push-ups",
        "Pull-ups",
        "Squats",
        "Deadlifts",
        "Bench Press"
    )
    
    val FLEXIBILITY = listOf(
        "Stretching",
        "Yoga Flow",
        "Pilates",
        "Dynamic Stretching"
    )
    
    val HIIT = listOf(
        "Burpees",
        "Mountain Climbers",
        "Box Jumps",
        "Jumping Jacks"
    )
    
    val YOGA = listOf(
        "Sun Salutation",
        "Warrior Pose",
        "Downward Dog",
        "Child's Pose"
    )
}
