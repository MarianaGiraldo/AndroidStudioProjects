package com.example.new_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.R
import com.example.new_app.adapter.ConferenceAdapter
import com.example.new_app.adapter.SpeakerAdapter
import com.example.new_app.viewmodel.ConferencesViewModel
import com.example.new_app.viewmodel.SpeakersViewModel

class SpeakersListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        /*
        val recycler = view?.findViewById<RecyclerView>(R.id.speakers_recycler)
        val speakersViewModel = SpeakersViewModel()
        if (recycler != null) {
            recycler.adapter = SpeakerAdapter(speakersViewModel.listSpeakers.value!!)
        }

         */
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val ll = inflater.inflate(R.layout.fragment_speakers_list, container, false)
        val speakersViewModel = SpeakersViewModel()
        val speakersList = speakersViewModel.listSpeakers.value
        val recycler = ll.findViewById<RecyclerView>(R.id.speakers_recycler)

        if (recycler != null) {
            if(speakersList != null) {
                recycler.adapter = SpeakerAdapter(speakersList)
            }
            else{
                Log.w("SpeakersList", "List is null")
            }
        }
        else{
            Log.w("SpeakerRecycler", "Recycler is null")
        }
        return ll
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