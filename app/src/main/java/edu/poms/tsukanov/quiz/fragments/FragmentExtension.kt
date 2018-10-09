package edu.poms.tsukanov.quiz.fragments

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import edu.poms.tsukanov.quiz.R

fun Fragment.openFragment(newFragment: Fragment) {
    activity?.supportFragmentManager!!.beginTransaction()
            .replace(R.id.fragments_container, newFragment)
            .addToBackStack(newFragment.toString())
            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
}