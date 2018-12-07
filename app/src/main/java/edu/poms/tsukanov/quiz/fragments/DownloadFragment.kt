package edu.poms.tsukanov.quiz.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.json.JSONObject
import org.json.JSONArray

import edu.poms.tsukanov.quiz.R

class DownloadFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val layout =  inflater.inflate(R.layout.fragment_download, container, false)
        val quizName: TextView = layout.findViewById(R.id.downloaded_quiz)
        val downloadedName =  downloadedInfo
                .getJSONObject(0)
                .getString("name")
        quizName.text = downloadedName
        quizName.setOnClickListener {
            openFragment(CreateUserFragment.newInstance(downloadedName, 2))
        }
        return layout
    }

    companion object {

        lateinit var downloadedInfo: JSONArray

        @JvmStatic
        fun newInstance(json: String): DownloadFragment {
            downloadedInfo = JSONArray(json)
            return DownloadFragment()
        }
    }
}
