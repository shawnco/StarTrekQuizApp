package com.example.startrekquizapp.classes

data class Question(
    val id: Int,
    val question: String,
    val correctAnswer: Int,
    val answers: List<String>
)