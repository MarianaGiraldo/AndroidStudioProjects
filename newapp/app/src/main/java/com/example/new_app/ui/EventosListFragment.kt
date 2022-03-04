package com.example.new_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.R
import com.example.new_app.adapter.ConferenceAdapter
import com.example.new_app.adapter.ConferenceListener
import com.example.new_app.models.Conference
import com.example.new_app.models.Speaker
import com.example.new_app.viewmodel.ConferencesViewModel

class EventosListFragment : Fragment(), ConferenceListener {
    private val conferencesViewModel = ConferencesViewModel()
    private lateinit var recycler:RecyclerView
    private lateinit var viewAlpha:View
    private lateinit var pgbar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.conferencesViewModel.refresh()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val ll = inflater.inflate(R.layout.fragment_eventos_list, container, false)
        this.recycler = ll.findViewById(R.id.conferences_recycler)
        this.viewAlpha= ll.findViewById(R.id.alphaEventosList)
        this.pgbar= ll.findViewById(R.id.pgbar_eventosList)
        return ll
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        conferencesViewModel.listConferences.observe(viewLifecycleOwner) { listConferences ->
            if (listConferences != null) {
                recycler.adapter = ConferenceAdapter(listConferences, this)
                viewAlpha.visibility = View.INVISIBLE
                pgbar.visibility = View.INVISIBLE
            } else {
                Log.w("onViewCreatedEventos", "Conferences List is null")
            }
        }
    }

    override fun onConferenceClicked(conference: Conference, position: Int){
        var bundle = bundleOf("conference" to conference)
        findNavController().navigate(
            R.id.fragment_evento_detalle,
            bundle
        )
    }

}