package com.example.new_app.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.new_app.R
import com.example.new_app.models.Speaker


class SpeakerAdapter(private val listSpeakers: List<Speaker>, private val speakerListener:SpeakerListener): RecyclerView.Adapter<SpeakerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.name)
        val image: ImageView = view.findViewById(R.id.image)

        fun bind(speaker: Speaker) {
            name.text = speaker.name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_speaker, parent, false)
    )

    override fun getItemCount() = this.listSpeakers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val speaker = listSpeakers[position]
        try {
            Glide.with(holder.itemView.context)
                .load(speaker.image)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.image)
            holder.bind(speaker)

            holder.itemView.setOnClickListener{
                speakerListener.onSpeakerClicked(speaker, position)
            }

        }catch (e: Exception){
            Log.w("Imagen", "No cargó la imagen")
        }

    }

}