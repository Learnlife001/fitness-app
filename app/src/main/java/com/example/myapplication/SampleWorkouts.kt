package com.example.myapplication

object SampleWorkouts {
    fun getSampleWorkouts(): List<Workout> {
        return listOf(
            // Cardio Workouts
            Workout(
                id = 1,
                name = "HIIT Running",
                type = "Running",
                description = "High-intensity interval training combining sprints and jogging. Great for burning calories and improving cardiovascular fitness.\n\n" +
                        "Instructions:\n" +
                        "1. Warm up with 5 minutes of light jogging\n" +
                        "2. Sprint for 30 seconds\n" +
                        "3. Jog slowly for 1 minute\n" +
                        "4. Repeat 8-10 times\n" +
                        "5. Cool down with 5 minutes of walking",
                imageUrl = "https://example.com/hiit_running.jpg",
                difficulty = "Intermediate",
                duration = 30,
                caloriesBurned = 400,
                category = WorkoutCategory.CARDIO
            ),

            // Strength Training
            Workout(
                id = 2,
                name = "Full Body Strength",
                type = "Strength Training",
                description = "A comprehensive strength workout targeting all major muscle groups.\n\n" +
                        "Circuit (3 rounds):\n" +
                        "1. Push-ups: 15 reps\n" +
                        "2. Squats: 20 reps\n" +
                        "3. Dumbbell rows: 12 reps each side\n" +
                        "4. Lunges: 10 reps each leg\n" +
                        "5. Plank: 30 seconds",
                imageUrl = "https://example.com/strength_training.jpg",
                difficulty = "Beginner",
                sets = 3,
                reps = 15,
                duration = 45,
                caloriesBurned = 300,
                category = WorkoutCategory.STRENGTH
            ),

            // Yoga
            Workout(
                id = 3,
                name = "Morning Yoga Flow",
                type = "Yoga Flow",
                description = "Energizing morning yoga routine to improve flexibility and start your day right.\n\n" +
                        "Sequence:\n" +
                        "1. Sun Salutation A (5 rounds)\n" +
                        "2. Warrior I & II\n" +
                        "3. Downward Dog\n" +
                        "4. Child's Pose\n" +
                        "5. Savasana",
                imageUrl = "https://example.com/morning_yoga.jpg",
                difficulty = "Beginner",
                duration = 20,
                caloriesBurned = 150,
                category = WorkoutCategory.YOGA
            ),

            // HIIT
            Workout(
                id = 4,
                name = "Tabata Challenge",
                type = "HIIT",
                description = "High-intensity Tabata workout with 20 seconds work, 10 seconds rest.\n\n" +
                        "Exercises (4 rounds each):\n" +
                        "1. Burpees\n" +
                        "2. Mountain Climbers\n" +
                        "3. Jump Squats\n" +
                        "4. High Knees",
                imageUrl = "https://example.com/tabata.jpg",
                difficulty = "Advanced",
                sets = 4,
                duration = 25,
                caloriesBurned = 350,
                category = WorkoutCategory.HIIT
            ),

            // Flexibility
            Workout(
                id = 5,
                name = "Dynamic Stretching",
                type = "Flexibility",
                description = "Dynamic stretching routine perfect for pre-workout or recovery.\n\n" +
                        "Routine:\n" +
                        "1. Arm circles: 20 seconds\n" +
                        "2. Leg swings: 30 seconds each leg\n" +
                        "3. Hip circles: 20 seconds\n" +
                        "4. Torso twists: 30 seconds\n" +
                        "5. Ankle rotations: 15 seconds each foot",
                imageUrl = "https://example.com/stretching.jpg",
                difficulty = "Beginner",
                duration = 15,
                caloriesBurned = 100,
                category = WorkoutCategory.FLEXIBILITY
            )
        )
    }
}
