package edu.poms.tsukanov.quiz.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "results")
class QuizResults {

    @PrimaryKey
    var resultsId: Int? = null

    var username: String? = null

    var quizName: String? = null

    var correct: Int? = null

    var total: Int? = null

    var percentage: Int? = null
}