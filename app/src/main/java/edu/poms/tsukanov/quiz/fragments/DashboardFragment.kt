package edu.poms.tsukanov.quiz.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.poms.tsukanov.quiz.MainActivity
import edu.poms.tsukanov.quiz.R

import edu.poms.tsukanov.quiz.fragments.dummy.DashboardContent
import edu.poms.tsukanov.quiz.fragments.dummy.DashboardContent.DashboardItem
import kotlinx.android.synthetic.main.fragment_dashboard_list.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [DashboardFragment.OnListFragmentInteractionListener] interface.
 */
class DashboardFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard_list, container, false)

        val viewManager = LinearLayoutManager(activity)

        val results = MainActivity.db.dao().getTop()
        val viewAdapter = DashboardAdapter(results)

        view.findViewById<RecyclerView>(R.id.dashboardList).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction()
    }

    companion object {


        @JvmStatic
        fun newInstance() =
                DashboardFragment()
    }
}
