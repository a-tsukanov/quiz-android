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
import java.lang.Exception


private const val QUIZ_NAME = "quizName"
private const val QUESTIONS_NUMBER = "questionsNumber"


class CreateUserFragment : Fragment() {
    private var quizName: String? = null
    private var questionsNumber: Int? = null

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
            val downloadedName = try {
                DownloadFragment.downloadedInfo.getJSONObject(0).getString("name")
            }
            catch (e: Exception) {
                "didn't download anything"
            }
            val fragment = when(qp.name) {
                downloadedName -> DownloadedQuizFragment.newInstance(qp)
                else -> QuizFragment.newInstance(qp)
            }
            openFragment(fragment)

        }
        return layout
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
