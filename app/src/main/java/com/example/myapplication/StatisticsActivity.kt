package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityStatisticsBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class StatisticsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var database: AppDatabase
    private lateinit var adapter: WorkoutHistoryAdapter
    private var currentUserId: Long = 1 // TODO: Get from user session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)
        setupChipGroup()
        setupRecyclerView()
        setupChart()
        loadWeekStatistics() // Default view
    }

    private fun setupChart() {
        with(binding.progressChart) {
            description.isEnabled = false
            legend.isEnabled = true
            setTouchEnabled(true)
            setScaleEnabled(true)
            setPinchZoom(true)

            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.valueFormatter = DateAxisFormatter()
            xAxis.labelRotationAngle = -45f

            axisRight.isEnabled = false
        }
    }

    private fun setupRecyclerView() {
        adapter = WorkoutHistoryAdapter { workoutHistory ->
            // TODO: Handle workout history item click
        }
        binding.workoutHistoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@StatisticsActivity)
            adapter = this@StatisticsActivity.adapter
        }
    }

    private fun setupChipGroup() {
        binding.timeFilterChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            when (checkedIds.firstOrNull()) {
                R.id.weekChip -> loadWeekStatistics()
                R.id.monthChip -> loadMonthStatistics()
                R.id.yearChip -> loadYearStatistics()
            }
        }
        binding.weekChip.isChecked = true
    }

    private fun loadWeekStatistics() {
        val endDate = System.currentTimeMillis()
        val startDate = endDate - TimeUnit.DAYS.toMillis(7)
        loadStatistics(startDate, endDate)
    }

    private fun loadMonthStatistics() {
        val endDate = System.currentTimeMillis()
        val startDate = endDate - TimeUnit.DAYS.toMillis(30)
        loadStatistics(startDate, endDate)
    }

    private fun loadYearStatistics() {
        val endDate = System.currentTimeMillis()
        val startDate = endDate - TimeUnit.DAYS.toMillis(365)
        loadStatistics(startDate, endDate)
    }

    private fun loadStatistics(startDate: Long, endDate: Long) {
        lifecycleScope.launch {
            // Load workout count
            val workoutCount = database.workoutHistoryDao()
                .getWorkoutCountBetweenDates(currentUserId, startDate, endDate)
            binding.workoutCountText.text = workoutCount.toString()

            // Load total calories
            val totalCalories = database.workoutHistoryDao()
                .getTotalCaloriesBurnedBetweenDates(currentUserId, startDate, endDate)
            binding.caloriesText.text = "${totalCalories}cal"

            // Load average duration
            val avgDuration = database.workoutHistoryDao()
                .getAverageWorkoutDuration(currentUserId, startDate, endDate)
            binding.totalTimeText.text = formatDuration(avgDuration.toInt())

            // Load most active week
            val mostActiveWeek = database.workoutHistoryDao()
                .getMostActiveWeekWorkouts(currentUserId, startDate, endDate)
            // TODO: Display most active week somewhere in the UI

            // Load workout history
            database.workoutHistoryDao()
                .getUserWorkoutHistoryBetweenDates(currentUserId, startDate, endDate)
                .observe(this@StatisticsActivity) { history ->
                    adapter.submitList(history)
                    updateProgressChart(history)
                }
        }
    }

    private fun updateProgressChart(history: List<WorkoutHistoryWithWorkout>) {
        val entries = history.map { 
            Entry(it.workoutHistory.completedDate.toFloat(), it.workoutHistory.caloriesBurned.toFloat())
        }.sortedBy { it.x }

        val dataSet = LineDataSet(entries, "Calories Burned").apply {
            setDrawFilled(true)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            lineWidth = 2f
            circleRadius = 4f
            valueTextSize = 10f
        }

        val lineData = LineData(dataSet)
        binding.progressChart.data = lineData
        binding.progressChart.invalidate()
    }

    private fun formatDuration(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return when {
            hours > 0 -> "${hours}h ${remainingMinutes}m"
            else -> "${remainingMinutes}m"
        }
    }

    inner class DateAxisFormatter : ValueFormatter() {
        private val dateFormat = SimpleDateFormat("MM/dd", Locale.getDefault())

        override fun getFormattedValue(value: Float): String {
            return dateFormat.format(Date(value.toLong()))
        }
    }
}
