package edu.poms.tsukanov.quiz.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

import edu.poms.tsukanov.quiz.R
import edu.poms.tsukanov.quiz.passage.QuizPassage


class ChooseQuizFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        val quiz1: ImageButton = activity!!.findViewById(R.id.quiz1)
        quiz1.setOnClickListener {
            val pythonSetUserFragment = CreateUserFragment.newInstance("python", 10)
            openFragment(pythonSetUserFragment)
        }

        val quiz2: ImageButton = activity!!.findViewById(R.id.quiz2)
        quiz2.setOnClickListener {
            val lawSetUserFragment = CreateUserFragment.newInstance("law", 11)
            openFragment(lawSetUserFragment)
        }

        val quiz3: ImageButton = activity!!.findViewById(R.id.quiz3)
        quiz3.setOnClickListener {
            val gravityFallsSetUserFragment = CreateUserFragment.newInstance("gravity_falls", 10)
            openFragment(gravityFallsSetUserFragment)
        }

        val quiz4: ImageButton = activity!!.findViewById(R.id.quiz4)
        quiz4.setOnClickListener {
            val museSetUserFragment = CreateUserFragment.newInstance("muse", 10)
            openFragment(museSetUserFragment)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_quiz, container, false)
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
        fun newInstance()
                = ChooseQuizFragment()
    }
}
