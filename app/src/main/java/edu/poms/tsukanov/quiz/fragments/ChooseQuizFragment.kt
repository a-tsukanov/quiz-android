package edu.poms.tsukanov.quiz.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.AppCompatImageButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton

import edu.poms.tsukanov.quiz.R
import kotlinx.android.synthetic.main.fragment_choose_quiz.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ChooseQuizFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ChooseQuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ChooseQuizFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        val quiz1: ImageButton = activity!!.findViewById(R.id.quiz1)
        quiz1.setOnClickListener {
            val pythonQuizPassage = QuizPassage("python", 10)
            val pythonQuizFragment = QuizFragment.newInstance(pythonQuizPassage)
            openFragment(pythonQuizFragment)
        }

        val quiz2: ImageButton = activity!!.findViewById(R.id.quiz2)
        quiz2.setOnClickListener {
            val lawQuizPassage = QuizPassage("law", 11)
            val lawQuizFragment = QuizFragment.newInstance(lawQuizPassage)
            openFragment(lawQuizFragment)
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
        fun newInstance()
                = ChooseQuizFragment()
    }
}
