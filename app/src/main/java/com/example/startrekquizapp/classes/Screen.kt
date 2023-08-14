package com.example.startrekquizapp.classes

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home")
    object QuizScreen : Screen("quiz")
    object CaptainsScreen : Screen("captains")
    object StarshipsScreen : Screen("starships")
    object ResultsScreen : Screen("results")

    fun withIntArgs(vararg args: Int): String {
        return buildString {
            append(route)
            args.forEach { arg -> append("/$arg") }
        }
    }
}