package edu.poms.tsukanov.quiz.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import edu.poms.tsukanov.quiz.R

class DownloadFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_download, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(json: String): DownloadFragment {
            throw NotImplementedError()
        }
    }
}
