package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkoutViewModel : ViewModel() {
    private val _workouts = MutableLiveData<List<Workout>>(emptyList())
    val workouts: LiveData<List<Workout>> = _workouts

    fun addWorkout(workout: Workout) {
        val currentList = _workouts.value.orEmpty().toMutableList()
        currentList.add(workout)
        _workouts.value = currentList
    }

    fun deleteWorkout(workout: Workout) {
        val currentList = _workouts.value.orEmpty().toMutableList()
        currentList.remove(workout)
        _workouts.value = currentList
    }
}
