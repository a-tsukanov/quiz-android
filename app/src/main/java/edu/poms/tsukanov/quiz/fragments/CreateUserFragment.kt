package edu.poms.tsukanov.quiz.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView

import edu.poms.tsukanov.quiz.R
import edu.poms.tsukanov.quiz.passage.QuizPassage


private const val QUIZ_NAME = "quizName"
private const val QUESTIONS_NUMBER = "questionsNumber"


class CreateUserFragment : Fragment() {
    private var quizName: String? = null
    private var questionsNumber: Int? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            quizName = it.getString(QUIZ_NAME)
            questionsNumber = it.getInt(QUESTIONS_NUMBER)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_create_user, container, false)
        val btn: Button = layout.findViewById(R.id.btnStart)
        btn.setOnClickListener {
            val i = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            i.hideSoftInputFromWindow(btn.windowToken, 0)


            val usernameView: TextView = layout.findViewById(R.id.usernameText)
            val username = usernameView.text
            val qp = QuizPassage(quizName!!, questionsNumber!!, username.toString())
            val fragment = QuizFragment.newInstance(qp)
            openFragment(fragment)
        }
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
        fun newInstance(quizName: String, questionsNumber: Int) =
                CreateUserFragment().apply {
                    arguments = Bundle().apply {
                        putString(QUIZ_NAME, quizName)
                        putInt(QUESTIONS_NUMBER, questionsNumber)
                    }
                }
    }
}
