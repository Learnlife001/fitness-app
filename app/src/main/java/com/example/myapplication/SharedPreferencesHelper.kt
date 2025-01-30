package com.example.myapplication

import android.content.Context

object SharedPreferencesHelper {
    private const val PREF_NAME = "fitness_app"
    private const val WORKOUT_KEY = "workouts"

    fun saveWorkout(context: Context, workout: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val currentData = sharedPreferences.getString(WORKOUT_KEY, "")
        editor.putString(WORKOUT_KEY, "$currentData\n$workout")
        editor.apply()
    }

    fun loadWorkouts(context: Context): String {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(WORKOUT_KEY, "") ?: ""
    }
}
