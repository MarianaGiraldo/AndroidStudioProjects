package com.example.new_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.R
import com.example.new_app.models.Speaker


class SpeakerAdapter(private val listSpeakers: List<Speaker>): RecyclerView.Adapter<SpeakerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.name)
        private val image: ImageView = view.findViewById(R.id.image)

        fun bind(speaker: Speaker) {
            name.text = speaker.name
            //image.setImageBitmap(getImageBitmap(speaker.image))
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
        holder.bind(speaker)
    }
    /*
    private fun getURI(IMAGE_URL: String): Uri? {
        return (if (URLUtil.isValidUrl(IMAGE_URL)) {
            //  an external URL
            Uri.parse(IMAGE_URL)
        } else { //  a raw resource
            null
        })
    }
    private fun getImageBitmap(url: String): Bitmap? {
        var bm: Bitmap? = null
        try {
            val aURL = URL(url)
            val conn = URL(url).openConnection() as HttpURLConnection
            val `is`: InputStream = conn.inputStream
            val bis = BufferedInputStream(`is`)
            bm = BitmapFactory.decodeStream(bis)
            bis.close()
            `is`.close()
        } catch (e: IOException) {
            Log.e("SpeakerAdapter", "Error getting bitmap", e)
        }
        return bm
    }

     */
}