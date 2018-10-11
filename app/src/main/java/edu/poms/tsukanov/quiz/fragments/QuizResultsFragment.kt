package edu.poms.tsukanov.quiz.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import edu.poms.tsukanov.quiz.R


class QuizResultsFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var qp: QuizPassage

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_quiz_results, container, false)
        val text: TextView = layout.findViewById(R.id.resultsText)
        text.text = "${qp.correctCounter} of ${qp.numberOfQuestions} correct"
        return layout
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
