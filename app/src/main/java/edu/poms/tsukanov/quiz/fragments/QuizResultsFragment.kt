package edu.poms.tsukanov.quiz.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import edu.poms.tsukanov.quiz.MainActivity

import edu.poms.tsukanov.quiz.R
import edu.poms.tsukanov.quiz.database.QuizResults
import edu.poms.tsukanov.quiz.passage.QuizPassage


class QuizResultsFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var qp: QuizPassage

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val result = setResult()

        updateDb(result)

        val layout = inflater.inflate(R.layout.fragment_quiz_results, container, false)
        val text: TextView = layout.findViewById(R.id.resultsText)
        text.text =
                "${result.username}: " +
                "${result.correct} из ${result.total} правильно " +
                "(${result.percentage}%)"

        val fab: FloatingActionButton = layout.findViewById(R.id.floatingActionButton)
        fab.setOnClickListener {
            val fragment = ChooseQuizFragment.newInstance()
            openFragment(fragment)
        }

        return layout
    }

    private fun setResult(): QuizResults = QuizResults().apply {
        quizName = qp.name
        username = qp.username
        correct = qp.correctCounter
        total = qp.numberOfQuestions
        percentage = (qp.correctCounter.toDouble() / qp.numberOfQuestions.toDouble() * 100).toInt()
    }

    private fun updateDb(result: QuizResults) {
        MainActivity.db.dao().add(result)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(quizPassage: QuizPassage): QuizResultsFragment {
            return QuizResultsFragment().apply {
                qp = quizPassage
            }
        }

    }
}
