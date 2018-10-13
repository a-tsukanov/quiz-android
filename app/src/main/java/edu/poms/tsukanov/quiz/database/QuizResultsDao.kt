package edu.poms.tsukanov.quiz.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface QuizResultsDao {
    @Insert
    fun add(quizResult: QuizResults)

    @Query("SELECT * FROM results ORDER BY percentage DESC LIMIT 10 ")
    fun getTop(): List<QuizResults>
}