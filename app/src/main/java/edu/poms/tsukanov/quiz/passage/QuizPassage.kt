package edu.poms.tsukanov.quiz.passage

class QuizPassage(val name: String, val numberOfQuestions: Int, val username: String) {
    var correctCounter = 0
    var currentNumber = 1
}
