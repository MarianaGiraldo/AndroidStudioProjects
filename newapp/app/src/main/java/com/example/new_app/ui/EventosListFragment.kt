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
import com.example.new_app.viewmodel.ConferencesViewModel

class EventosListFragment : Fragment() {
    private val conferencesViewModel = ConferencesViewModel()
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

        return ll
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        conferencesViewModel.listConferences.observe(viewLifecycleOwner, { listConferences ->
            if(listConferences != null){
                recycler.adapter = ConferenceAdapter(listConferences)
            }
            else{
                Log.w("onViewCreatedEventos", "Conferences List is null")
            }
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