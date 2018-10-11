package edu.poms.tsukanov.quiz.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView

import edu.poms.tsukanov.quiz.R


class QuizFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val layout = inflater.inflate(R.layout.fragment_quiz, container, false)

        setQuestionText(layout)
        setAnswersText(layout)

        val btnNext: Button = layout.findViewById(R.id.btnForward)
        btnNext.setOnClickListener {
            if (isAnswerCorrect()) {
                qp.correctCounter += 1
            }
            qp.currentNumber += 1
            val nextFragment = QuizFragment.newInstance()
            openFragment(nextFragment)
        }

        return layout
    }

    private fun setQuestionText(layout: View) {
        val questionLabel: TextView? = layout.findViewById(R.id.question)
        questionLabel?.text = resources.getText(
                resources.getIdentifier(
                        "${qp.name}_question${qp.currentNumber}",
                        "string",
                        activity?.packageName
                )
        )
    }

    private fun setAnswersText(layout: View) {
        val answers = resources.getStringArray(
                resources.getIdentifier(
                        "${qp.name}_answers${qp.currentNumber}",
                        "array",
                        activity?.packageName
                )
        )
        val answerViews = (1..4).map {
            layout.findViewById<RadioButton>(
                    resources.getIdentifier("answer$it", "id", activity?.packageName)
            )
        }
        for ((ans, ansView) in answers.zip(answerViews)) {
            ansView.text = ans
        }
    }

    private fun isAnswerCorrect(): Boolean {
        return true
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

        lateinit var qp: QuizPassage

        @JvmStatic
        fun newInstance(quizPassage: QuizPassage? = null): QuizFragment {
            if(quizPassage != null) {
                QuizFragment.qp = quizPassage
            }
            return QuizFragment()

        }
    }
}
