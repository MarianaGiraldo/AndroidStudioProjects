package com.example.new_app.adapter

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.R
import com.example.new_app.R.id.titulo
import com.example.new_app.models.Conference
import java.util.*

class ConferenceAdapter(private val listConferences: List<Conference>, private val conferenceListener: ConferenceListener): RecyclerView.Adapter<ConferenceAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(titulo)
        private val desc: TextView = view.findViewById(R.id.desc_evento)
        private val time: TextView = view.findViewById(R.id.hora)
        private val date: TextView = view.findViewById(R.id.fecha_evento)
        private val ampm: TextView = view.findViewById(R.id.AmPm)

        @RequiresApi(Build.VERSION_CODES.N)
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(conference: Conference) {
            desc.text = conference.description
            title.text = conference.title

            val simpleDataFormat = SimpleDateFormat("HH:ss")
            val simpleDataFormatAMPM = SimpleDateFormat("a")
            val simpleDataFormatDate = SimpleDateFormat("MM/dd/yyyy")

            val cal = Calendar.getInstance()
            cal.time = conference.datetime
            val hourFormat = simpleDataFormat.format(conference.datetime)
            val dateFormat = simpleDataFormatDate.format(conference.datetime)

            date.text = dateFormat
            time.text = hourFormat
            ampm.text = simpleDataFormatAMPM.format(conference.datetime).uppercase()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_evento, parent, false)
    )

    override fun getItemCount() = this.listConferences.size

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val conference = listConferences[position]
        holder.bind(conference)

        holder.itemView.setOnClickListener{
            conferenceListener.onConferenceClicked(conference, position)
        }
    }

}