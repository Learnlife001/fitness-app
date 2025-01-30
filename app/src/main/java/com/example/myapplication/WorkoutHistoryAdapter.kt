package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemWorkoutHistoryBinding
import java.text.SimpleDateFormat
import java.util.*

class WorkoutHistoryAdapter(
    private val onItemClick: (WorkoutHistory) -> Unit
) : ListAdapter<WorkoutHistoryWithWorkout, WorkoutHistoryAdapter.ViewHolder>(WorkoutHistoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWorkoutHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemWorkoutHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position).workoutHistory)
                }
            }
        }

        fun bind(item: WorkoutHistoryWithWorkout) {
            with(binding) {
                workoutNameText.text = item.workout.name
                dateText.text = dateFormat.format(Date(item.workoutHistory.completedDate))
                durationText.text = "${item.workoutHistory.duration} min"
                caloriesText.text = "${item.workoutHistory.caloriesBurned} cal"
                
                // Convert difficulty string to rating (assuming "Beginner" = 1, "Intermediate" = 3, "Advanced" = 5)
                val rating = when (item.workoutHistory.difficulty.lowercase()) {
                    "beginner" -> 1
                    "intermediate" -> 3
                    "advanced" -> 5
                    else -> 0
                }
                difficultyRating.rating = rating.toFloat()
            }
        }
    }
}

class WorkoutHistoryDiffCallback : DiffUtil.ItemCallback<WorkoutHistoryWithWorkout>() {
    override fun areItemsTheSame(oldItem: WorkoutHistoryWithWorkout, newItem: WorkoutHistoryWithWorkout): Boolean {
        return oldItem.workoutHistory.id == newItem.workoutHistory.id
    }

    override fun areContentsTheSame(oldItem: WorkoutHistoryWithWorkout, newItem: WorkoutHistoryWithWorkout): Boolean {
        return oldItem == newItem
    }
}
