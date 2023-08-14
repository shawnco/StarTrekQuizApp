package com.example.startrekquizapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import com.example.startrekquizapp.classes.BottomNavItem
import com.example.startrekquizapp.classes.Captain
import com.example.startrekquizapp.classes.Question
import com.example.startrekquizapp.classes.ResultComment
import com.example.startrekquizapp.classes.Starship

object Constants {
    fun getQuestions(): List<Question> {
        return listOf(
            Question(1, "Who is captain of the NX-01?", 0, listOf("Archer", "Kirk", "Picard", "Sisko")),
            Question(2, "Who is captain of the NCC-1701?", 1, listOf("Picard", "Kirk", "Sisko", "Archer")),
            Question(3, "Who is captain of the NCC-1701-D?", 2, listOf("Kirk", "Archer", "Picard", "Sisko")),
            Question(4, "Who is captain of the NX-74205?", 3, listOf("Picard", "Kirk", "Archer", "Sisko"))
        )
    }

    fun getCaptains(): List<Captain> {
        return listOf(
            Captain(1, "Jonathan Archer", "Captain of the USS Enterprise, NX-01, the first warp five capable starship"),
            Captain(2, "James Kirk", "Captain of two ships named USS Enterprise, NCC-1701 and NCC-1701-A"),
            Captain(3, "Jean-Luc Picard", "Captain of the USS Stargazer and two ships named USS Enterprise, NCC-1701-D and NCC-1701-E"),
            Captain(4, "Benjamin Sisko", "Captain of the USS Defiant and Deep Space Nine")
        )
    }

    fun getStarships(): List<Starship> {
        return listOf(
            Starship(1, "Enterprise", "NX-01", "The first warp five capable starship"),
            Starship(2, "Enterprise", "NCC-1701", "The Constitution class flagship of the Federation from 2245-2285"),
            Starship(3, "Enterprise", "NCC-1701", "The Galaxy class flagship of the Federation from 2362-2371"),
            Starship(4, "Defiant", "NX-74205", "The first ship of a class designed to fight the Borg, stationed at Deep Space Nine")
        )
    }

    fun getNavItems(): List<BottomNavItem> {
        return listOf(
            BottomNavItem("Home", "home", Icons.Default.Home),
            BottomNavItem("Quiz", "quiz", Icons.Default.Create),
            BottomNavItem("Captains", "captains", Icons.Default.AccountCircle),
            BottomNavItem("Starships", "starships", Icons.Default.Info)
        )
    }

    fun getResultComment(correctCount: Int): ResultComment? {
        val comments = listOf(
            ResultComment(0, "You don't know any of the captains!"),
            ResultComment(1, "Keep studying the captains!"),
            ResultComment(2, "You know some of the captains!"),
            ResultComment(3, "You know most of the captains!"),
            ResultComment(4, "You know all the captains!")
        )
        return comments.find { it.correctCount == correctCount }
    }
}