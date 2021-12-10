package com.example.new_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.R
import com.example.new_app.adapter.SpeakerAdapter
import com.example.new_app.viewmodel.SpeakersViewModel

class SpeakersListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

        val recycler = view?.findViewById<RecyclerView>(R.id.speakers_recycler)
        val speakersViewModel = SpeakersViewModel()
        if (recycler != null) {
            recycler.adapter = SpeakerAdapter(speakersViewModel.listSpeakers.value!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_speakers_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SpeakersListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}