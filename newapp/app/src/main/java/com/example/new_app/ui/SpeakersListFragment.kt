package com.example.new_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.R
import com.example.new_app.adapter.SpeakerAdapter
import com.example.new_app.adapter.SpeakerListener
import com.example.new_app.models.Speaker
import com.example.new_app.viewmodel.SpeakersViewModel

class SpeakersListFragment : Fragment(), SpeakerListener {
    private val speakersViewModel = SpeakersViewModel()
    private lateinit var recycler:RecyclerView
    private lateinit var viewAlpha:View
    private lateinit var pgbar: ProgressBar
    private lateinit var rlSpeakersList: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.speakersViewModel.refresh()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val ll = inflater.inflate(R.layout.fragment_speakers_list, container, false)
        this.recycler = ll.findViewById(R.id.speakers_recycler)
        this.viewAlpha = ll.findViewById(R.id.view_speakersList)
        this.pgbar = ll.findViewById(R.id.pgbar_speakersList)
        this.rlSpeakersList = ll.findViewById(R.id.rl_speakers_list)
        recycler.apply {
            layoutManager = GridLayoutManager(context, 2)
        }
        return ll
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.speakersViewModel.listSpeakers.observe(viewLifecycleOwner) { listSpeakers ->
            if (listSpeakers != null) {
                this.recycler.adapter = SpeakerAdapter(listSpeakers, this)
                viewAlpha.visibility = View.INVISIBLE
                pgbar.visibility = View.INVISIBLE
            } else {
                Log.w("onViewCreatedSpeakers", "Speakers List is null")
            }
        }
        this.speakersViewModel.isLoading.observe(viewLifecycleOwner){
            if (it != null){
                rlSpeakersList.visibility = View.INVISIBLE
            }
        }

    }

    override fun onSpeakerClicked(speaker: Speaker, position: Int){
        val bundle: Bundle = bundleOf("speaker" to speaker)
        findNavController().navigate(
            R.id.fragment_speaker_info,
            bundle
        )
    }
}