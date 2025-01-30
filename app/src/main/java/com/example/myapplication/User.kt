package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String,
    val age: Int,
    val weight: Float,  // in kg
    val height: Float,  // in cm
    val profileImageUrl: String = "",
    val fitnessGoal: FitnessGoal = FitnessGoal.GENERAL_FITNESS,
    val createdAt: Long = System.currentTimeMillis()
)

enum class FitnessGoal {
    WEIGHT_LOSS,
    MUSCLE_GAIN,
    ENDURANCE,
    FLEXIBILITY,
    GENERAL_FITNESS
}
