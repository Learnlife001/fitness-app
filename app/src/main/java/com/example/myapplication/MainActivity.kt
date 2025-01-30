package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutScreen()
        }
    }
}


@Composable
fun WorkoutScreen() {
    var workoutType by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }
    var workoutList by remember { mutableStateOf(listOf<String>()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Fitness Tracker", modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = {
            val workout = "$workoutType: $sets sets x $reps reps"
            workoutList = workoutList + workout
            SharedPreferencesHelper.saveWorkout(context = this@MainActivity, workout)
        }) {
            Text("Add Workout")
        }

        // Input fields for workout data
        TextField(
            value = workoutType,
            onValueChange = { workoutType = it },
            label = { Text("Workout Type") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = sets,
            onValueChange = { sets = it },
            label = { Text("Sets") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = reps,
            onValueChange = { reps = it },
            label = { Text("Reps") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            val workout = "$workoutType: $sets sets x $reps reps"
            workoutList = workoutList + workout
            // Optionally save the list here (SharedPreferences or a file)
        }) {
            Text("Add Workout")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display workouts
        workoutList.forEach {
            Text(it)
        }
    }
}
val viewModel: WorkoutViewModel = viewModel()

Button(onClick = {
    val workout = "$workoutType: $sets sets x $reps reps"
    viewModel.addWorkout(workout)
    workoutList = viewModel.workoutList
})

val workoutData = SharedPreferencesHelper.loadWorkouts(this)
workoutList = workoutData.split("\n").filter { it.isNotBlank() }


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}