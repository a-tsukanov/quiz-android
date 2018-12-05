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
import edu.poms.tsukanov.quiz.recyclerview.DashboardAdapter


class DashboardFragment : Fragment() {

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


    companion object {


        @JvmStatic
        fun newInstance() =
                DashboardFragment()
    }
}
