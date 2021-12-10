package com.example.new_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.R
import com.example.new_app.models.Speaker

class SpeakerAdapter(private val listSpeakers: List<Speaker>): RecyclerView.Adapter<SpeakerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_speaker, parent, false)
    )

    override fun getItemCount() = this.listSpeakers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val speaker = listSpeakers[position]
        holder.bind(speaker)
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.name)
        private val image: TextView = view.findViewById(R.id.image)

        fun bind(speaker: Speaker) {
            name.text = speaker.name
            image.text = speaker.image
        }
    }
}