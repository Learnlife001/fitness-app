package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityAddEditWorkoutBinding

class AddEditWorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditWorkoutBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinners()
        setupButtons()
    }

    private fun setupSpinners() {
        // Setup Category Spinner
        val categories = WorkoutCategory.values().map { it.name }
        (binding.categorySpinner as? AutoCompleteTextView)?.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categories)
        )

        // Setup Difficulty Spinner
        val difficulties = listOf("Beginner", "Intermediate", "Advanced")
        (binding.difficultySpinner as? AutoCompleteTextView)?.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, difficulties)
        )

        // Setup workout types based on category selection
        binding.categorySpinner.setOnItemClickListener { _, _, position, _ ->
            val selectedCategory = WorkoutCategory.values()[position]
            val workoutTypes = when (selectedCategory) {
                WorkoutCategory.CARDIO -> WorkoutTypes.CARDIO
                WorkoutCategory.STRENGTH -> WorkoutTypes.STRENGTH
                WorkoutCategory.FLEXIBILITY -> WorkoutTypes.FLEXIBILITY
                WorkoutCategory.HIIT -> WorkoutTypes.HIIT
                WorkoutCategory.YOGA -> WorkoutTypes.YOGA
                WorkoutCategory.OTHER -> listOf("Custom")
            }
            
            (binding.typeSpinner as? AutoCompleteTextView)?.setAdapter(
                ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, workoutTypes)
            )
        }
    }

    private fun setupButtons() {
        binding.saveButton.setOnClickListener {
            if (validateInput()) {
                saveWorkout()
            }
        }

        binding.selectImageButton.setOnClickListener {
            // TODO: Implement image selection
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        with(binding) {
            if (nameInput.text.isNullOrBlank()) {
                nameInputLayout.error = "Name is required"
                isValid = false
            }

            if (categorySpinner.text.isNullOrBlank()) {
                categorySpinnerLayout.error = "Category is required"
                isValid = false
            }

            if (typeSpinner.text.isNullOrBlank()) {
                typeSpinnerLayout.error = "Type is required"
                isValid = false
            }

            if (difficultySpinner.text.isNullOrBlank()) {
                difficultySpinnerLayout.error = "Difficulty is required"
                isValid = false
            }
        }

        return isValid
    }

    private fun saveWorkout() {
        val workout = Workout(
            name = binding.nameInput.text.toString(),
            type = binding.typeSpinner.text.toString(),
            category = WorkoutCategory.valueOf(binding.categorySpinner.text.toString()),
            difficulty = binding.difficultySpinner.text.toString(),
            description = binding.descriptionInput.text.toString(),
            sets = binding.setsInput.text.toString().toIntOrNull() ?: 0,
            reps = binding.repsInput.text.toString().toIntOrNull() ?: 0,
            duration = binding.durationInput.text.toString().toIntOrNull() ?: 0
        )

        // TODO: Save workout to database/repository
        setResult(RESULT_OK)
        finish()
    }

    companion object {
        const val REQUEST_CODE = 100
    }
}
