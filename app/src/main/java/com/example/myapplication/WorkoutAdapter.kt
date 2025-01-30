package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class WorkoutAdapter : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {
    private var workouts: List<Workout> = emptyList()
    private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    fun updateWorkouts(newWorkouts: List<Workout>) {
        workouts = newWorkouts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        holder.bind(workout)
    }

    override fun getItemCount() = workouts.size

    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.workoutNameText)
        private val detailsText: TextView = itemView.findViewById(R.id.workoutDetailsText)
        private val dateText: TextView = itemView.findViewById(R.id.workoutDateText)

        fun bind(workout: Workout) {
            nameText.text = workout.name
            detailsText.text = buildString {
                append(workout.type)
                if (workout.sets > 0 && workout.reps > 0) {
                    append(" - ${workout.sets} sets × ${workout.reps} reps")
                }
                if (workout.duration > 0) {
                    append(" - ${workout.duration} minutes")
                }
            }
            dateText.text = dateFormat.format(Date(workout.date))
        }
    }
}
