package edu.poms.tsukanov.quiz.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import edu.poms.tsukanov.quiz.R
import kotlinx.android.synthetic.main.fragment_quiz.*


class QuizFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var thisPackage: String
    private lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thisPackage = activity!!.packageName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val layout = inflater.inflate(R.layout.fragment_quiz, container, false)

        setQuestionText(layout)
        setAnswersText(layout)

        val btnConfirm: Button = layout.findViewById(R.id.btnForward)
        btnConfirm.setOnClickListener {
            handleAnswerButton()
        }

        radioGroup = layout.findViewById(R.id.answers_radio_group)
        radioGroup.setOnCheckedChangeListener { _, _ ->
            btnConfirm.isEnabled = true
        }

        return layout
    }

    private fun setQuestionText(layout: View) {
        val questionLabel: TextView? = layout.findViewById(R.id.question)
        questionLabel?.text = resources.getText(
                resources.getIdentifier(
                        "${qp.name}_question${qp.currentNumber}",
                        "string",
                        thisPackage
                )
        )
    }

    private fun setAnswersText(layout: View) {
        val answers = resources.getStringArray(
                resources.getIdentifier(
                        "${qp.name}_answers${qp.currentNumber}",
                        "array",
                        thisPackage
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

    private fun getCorrectAnswer(): Int =
            resources.getInteger(
                    resources.getIdentifier(
                            "${qp.name}_correct${qp.currentNumber}",
                            "integer",
                            thisPackage
                    )
            )

    private val isAnswerCorrect: Boolean
        get() {

            fun getUserAnswer(): Int {
                val selectedId = radioGroup.checkedRadioButtonId
                val selectedBtn: Button = radioGroup.findViewById(selectedId)
                return radioGroup.indexOfChild(selectedBtn) + 1
            }

            val (correctAnswer, userAnswer) = listOf(getCorrectAnswer(), getUserAnswer())
            return correctAnswer == userAnswer
        }

    private fun createNextFragment() = if (qp.currentNumber < qp.numberOfQuestions) {
            QuizFragment.newInstance()
        } else {
            QuizResultsFragment.newInstance(qp)
    }

    private fun getCorrectAnswerStr(): String {
        val correct = getCorrectAnswer()
        return when (correct) {
            1 -> "1st"
            2 -> "2nd"
            3 -> "3rd"
            4 -> "4th"
            else -> throw IllegalArgumentException()
        }
    }

    private fun handleAnswerButton() {
        fun prepareAndOpenFragment() {
            val nextFragment = createNextFragment()
            qp.currentNumber += 1

            openFragment(nextFragment)
        }
        if (isAnswerCorrect) {
            qp.correctCounter += 1
            prepareAndOpenFragment()
        } else {
            btnForward.isEnabled = false
            (0..3).map {radioGroup.getChildAt(it).isEnabled = false }
            Snackbar.make(view!!, "Correct answer is the ${getCorrectAnswerStr()}",
                        Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK") { _ ->
                        prepareAndOpenFragment()
                    }
                    .show()
        }
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
            if (quizPassage != null) {
                QuizFragment.qp = quizPassage
            }
            return QuizFragment()
        }
    }
}
