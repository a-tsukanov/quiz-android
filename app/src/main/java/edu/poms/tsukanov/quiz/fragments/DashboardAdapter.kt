package edu.poms.tsukanov.quiz.fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import edu.poms.tsukanov.quiz.R
import edu.poms.tsukanov.quiz.database.QuizResults


import edu.poms.tsukanov.quiz.fragments.DashboardFragment.OnListFragmentInteractionListener
import edu.poms.tsukanov.quiz.fragments.dummy.DashboardContent.DashboardItem

import kotlinx.android.synthetic.main.fragment_dashboard.view.*


class DashboardAdapter(private val records: List<QuizResults>)
    : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_dashboard, parent, false) as LinearLayout
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val layout = holder.linearLayout
        val percantageText: TextView = layout.findViewById(R.id.percentageText)
        val usernameText: TextView = layout.findViewById(R.id.usernameText)
        val topicText: TextView = layout.findViewById(R.id.topicText)

        val record = records[position]
        percantageText.text = "${record.percentage}%"
        usernameText.text = record.username
        topicText.text = record.quizName
    }

    override fun getItemCount(): Int = records.size

    inner class ViewHolder(val linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout)
}
