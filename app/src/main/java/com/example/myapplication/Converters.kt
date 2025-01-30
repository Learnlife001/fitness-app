package com.example.myapplication

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromWorkoutCategory(value: WorkoutCategory): String {
        return value.name
    }

    @TypeConverter
    fun toWorkoutCategory(value: String): WorkoutCategory {
        return WorkoutCategory.valueOf(value)
    }

    @TypeConverter
    fun fromFitnessGoal(value: FitnessGoal): String {
        return value.name
    }

    @TypeConverter
    fun toFitnessGoal(value: String): FitnessGoal {
        return FitnessGoal.valueOf(value)
    }
}
