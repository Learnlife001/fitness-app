package com.example.myapplication

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityWorkoutProgressBinding
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class WorkoutProgressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutProgressBinding
    private var workoutTimer: CountDownTimer? = null
    private var isPaused = false
    private var remainingTimeMillis: Long = 0
    private var currentExerciseIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workoutId = intent.getLongExtra(EXTRA_WORKOUT_ID, -1)
        if (workoutId == -1L) {
            finish()
            return
        }

        setupWorkout(workoutId)
        setupButtons()
    }

    private fun setupWorkout(workoutId: Long) {
        lifecycleScope.launch {
            val workout = AppDatabase.getDatabase(this@WorkoutProgressActivity)
                .workoutDao()
                .getWorkoutById(workoutId)

            workout?.let {
                binding.workoutNameText.text = it.name
                remainingTimeMillis = TimeUnit.MINUTES.toMillis(it.duration.toLong())
                startTimer()
                updateExerciseDisplay()
            }
        }
    }

    private fun startTimer() {
        workoutTimer?.cancel()
        workoutTimer = object : CountDownTimer(remainingTimeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeMillis = millisUntilFinished
                updateTimerDisplay(millisUntilFinished)
            }

            override fun onFinish() {
                completeWorkout()
            }
        }.start()
    }

    private fun updateTimerDisplay(millisUntilFinished: Long) {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                TimeUnit.MINUTES.toSeconds(minutes)
        binding.timerText.text = String.format("%02d:%02d", minutes, seconds)
    }

    private fun setupButtons() {
        binding.pauseResumeButton.setOnClickListener {
            if (isPaused) {
                resumeWorkout()
            } else {
                pauseWorkout()
            }
        }

        binding.previousButton.setOnClickListener {
            if (currentExerciseIndex > 0) {
                currentExerciseIndex--
                updateExerciseDisplay()
            }
        }

        binding.nextButton.setOnClickListener {
            currentExerciseIndex++
            updateExerciseDisplay()
        }
    }

    private fun pauseWorkout() {
        workoutTimer?.cancel()
        isPaused = true
        binding.pauseResumeButton.text = "Resume"
    }

    private fun resumeWorkout() {
        startTimer()
        isPaused = false
        binding.pauseResumeButton.text = "Pause"
    }

    private fun updateExerciseDisplay() {
        // TODO: Update with actual exercise data
        binding.exerciseNameText.text = "Exercise ${currentExerciseIndex + 1}"
        binding.exerciseDetailsText.text = "Complete the exercise according to your plan"
    }

    private fun completeWorkout() {
        // TODO: Save workout history
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        workoutTimer?.cancel()
    }

    companion object {
        const val EXTRA_WORKOUT_ID = "extra_workout_id"
    }
}
