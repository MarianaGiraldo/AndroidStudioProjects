package com.example.new_app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.R
import com.example.new_app.adapter.ConferenceAdapter
import com.example.new_app.viewmodel.ConferencesViewModel

class EventosListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

        val recycler = view?.findViewById<RecyclerView>(R.id.conferences_recycler)
        val conferencesViewModel = ConferencesViewModel()
        if (recycler != null) {
            recycler.adapter = ConferenceAdapter(conferencesViewModel.listConferences.value!!)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eventos_list, container, false)
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