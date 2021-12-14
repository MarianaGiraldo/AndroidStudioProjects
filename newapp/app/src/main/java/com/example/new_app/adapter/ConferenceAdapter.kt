package com.example.new_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.R
import com.example.new_app.R.id.titulo
import com.example.new_app.models.Conference

class ConferenceAdapter(private val listConferences: List<Conference>): RecyclerView.Adapter<ConferenceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_evento, parent, false)
    )

    override fun getItemCount() = this.listConferences.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val conference = listConferences[position]
        holder.bind(conference)
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(titulo)
        private val desc: TextView = view.findViewById(R.id.desc_evento)
        private val time: TextView = view.findViewById(R.id.hora)
        private val date: TextView = view.findViewById(R.id.fecha_evento)

        fun bind(conference: Conference) {
            desc.text = conference.description
            time.text = conference.datetime.time.toString()
            date.text = conference.datetime.toString()
            title.text = conference.title
        }
    }
}