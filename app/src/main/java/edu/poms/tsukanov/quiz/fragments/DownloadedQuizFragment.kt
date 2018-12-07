package edu.poms.tsukanov.quiz.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import edu.poms.tsukanov.quiz.R
import edu.poms.tsukanov.quiz.passage.QuizPassage
import kotlinx.android.synthetic.main.fragment_quiz.*
import org.json.JSONObject


class DownloadedQuizFragment : Fragment() {

    private lateinit var thisPackage: String
    private lateinit var radioGroup: RadioGroup

    private val downloadedQuiz = DownloadFragment.downloadedInfo
            .getJSONObject(0)

    private val questions = downloadedQuiz
            .getJSONArray("question_set")

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
        questionLabel?.text = questions
                .getJSONObject(qp.currentNumber - 1)  //question numbers are 1-based,
                                                            // JSON array indices are 0-based

                .getString("question")
    }

    private fun setAnswersText(layout: View) {
        val answers = questions
                .getJSONObject(qp.currentNumber - 1)  //question numbers are 1-based,
                                                            // JSON array indices are 0-based
                .getJSONArray("answer_set")
        val answersList = mutableListOf<String>()
        val answerViews = (1..4).map {
            val answer = answers
                    .getJSONObject(it - 1)
                    .getString("answer")
            answersList.add(answer)
            layout.findViewById<RadioButton>(
                    resources.getIdentifier("answer$it", "id", activity?.packageName)
            )
        }
        for ((ans, ansView) in answersList.zip(answerViews)) {
            ansView.text = ans
        }
    }

    private fun getCorrectAnswer(): Int {
        val flags = questions
                .getJSONObject(qp.currentNumber - 1)
                .getJSONArray("answer_set")
        for (i in 0..3) {
            if (flags.getJSONObject(i).getBoolean("is_correct"))
                return i + 1
        }
        throw IllegalArgumentException()
    }


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
        DownloadedQuizFragment.newInstance()
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
                    .setAction("OK") {
                        prepareAndOpenFragment()
                    }
                    .show()
        }
    }


    companion object {

        lateinit var qp: QuizPassage

        @JvmStatic
        fun newInstance(quizPassage: QuizPassage? = null): DownloadedQuizFragment {
            if (quizPassage != null) {
                DownloadedQuizFragment.qp = quizPassage
            }
            return DownloadedQuizFragment()
        }
    }
}
