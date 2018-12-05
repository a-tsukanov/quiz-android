package edu.poms.tsukanov.quiz.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

import edu.poms.tsukanov.quiz.R


class ChooseQuizFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        fun attachFragmentToButton(button: ImageButton, fragment: CreateUserFragment) {
            button.setOnClickListener {
                openFragment(fragment)
            }
        }

        fun findButton(id: Int): ImageButton = activity!!.findViewById(id)

        data class BtnAndQuizParams(
                val btnId: Int,
                val quizName: String,
                val questionsNum: Int
        )
        val mappings = listOf(
                BtnAndQuizParams(R.id.quiz1, "python", 10),
                BtnAndQuizParams(R.id.quiz2, "law", 11),
                BtnAndQuizParams(R.id.quiz3, "gravity_falls", 10),
                BtnAndQuizParams(R.id.quiz4, "muse", 10)
        )
        mappings.forEach { m ->
            val btn = findButton(m.btnId)
            val frag = CreateUserFragment.newInstance(m.quizName, m.questionsNum)
            attachFragmentToButton(btn, frag)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_quiz, container, false)
    }

}
