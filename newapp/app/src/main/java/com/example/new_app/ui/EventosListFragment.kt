package com.example.new_app.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.R
import com.example.new_app.adapter.ConferenceAdapter
import com.example.new_app.viewmodel.ConferencesViewModel

class EventosListFragment : Fragment() {
    val conferencesViewModel = ConferencesViewModel()
    private lateinit var recycler:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        this.conferencesViewModel.refresh()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val ll = inflater.inflate(R.layout.fragment_eventos_list, container, false)
        this.recycler = ll.findViewById(R.id.conferences_recycler)

        val conferencesList = this.conferencesViewModel.listConferences.value
        if(conferencesList != null) {
            recycler.adapter = ConferenceAdapter(conferencesList)
        }
        else{
            Log.w("ConferencesList", "List is null")
        }

        return ll
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        conferencesViewModel.listConferences.observe(viewLifecycleOwner, { listConferences ->
            recycler.adapter = ConferenceAdapter(listConferences)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            EventosListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}