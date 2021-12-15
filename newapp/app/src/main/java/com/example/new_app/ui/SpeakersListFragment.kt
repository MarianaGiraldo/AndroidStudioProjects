package com.example.new_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.R
import com.example.new_app.adapter.SpeakerAdapter
import com.example.new_app.viewmodel.SpeakersViewModel

class SpeakersListFragment : Fragment() {
    private val speakersViewModel = SpeakersViewModel()
    private lateinit var recycler:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        this.speakersViewModel.refresh()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val ll = inflater.inflate(R.layout.fragment_speakers_list, container, false)
        this.recycler = ll.findViewById(R.id.speakers_recycler)

        return ll
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.speakersViewModel.listSpeakers.observe(viewLifecycleOwner, { listSpeakers ->
            if(listSpeakers != null){
                recycler.adapter = SpeakerAdapter(listSpeakers)
            }
            else{
                Log.w("onViewCreatedSpeakers", "Speakers List is null")
            }
        })
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