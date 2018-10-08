package edu.poms.tsukanov.quiz.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView

import edu.poms.tsukanov.quiz.R

private const val QUIZ_NAME = "quizName"
private const val QUESTION_NUMBER = "questionNumber"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [QuizFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class QuizFragment : Fragment() {

    private var quizName: String? = null
    private var questionNumber: Int? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            quizName = it.getString(QUIZ_NAME)
            questionNumber = it.getInt(QUESTION_NUMBER)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_quiz, container, false)

        val questionLabel: TextView? = v.findViewById(R.id.question)
        questionLabel?.text = resources.getText(
                resources.getIdentifier(
                        "${quizName}_question$questionNumber",
                        "string",
                        activity?.packageName
                )
        )

        val answers = resources.getStringArray(
                resources.getIdentifier(
                        "${quizName}_answers$questionNumber",
                        "array",
                        activity?.packageName
                )
        )
        val answerViews = (1..4).map {
            v.findViewById<RadioButton>(
                    resources.getIdentifier("answer$it", "id", activity?.packageName)
            )
        }
        for ((ans, ansView) in answers.zip(answerViews)) {
            ansView.text = ans
        }

        return v
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(quizName: String, questionNumber: Int) =
                QuizFragment().apply {
                    arguments = Bundle().apply {
                        putString(QUIZ_NAME, quizName)
                        putInt(QUESTION_NUMBER, questionNumber)
                    }
                }
    }
}
