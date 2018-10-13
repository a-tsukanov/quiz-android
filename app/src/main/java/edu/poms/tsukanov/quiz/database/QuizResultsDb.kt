package edu.poms.tsukanov.quiz.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [QuizResults::class], version = 1)
abstract class QuizResultsDb: RoomDatabase() {
    abstract fun dao(): QuizResultsDao
}