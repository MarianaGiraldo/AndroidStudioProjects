package com.example.new_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.R
import com.example.new_app.models.Comment

class CommentAdapter(private val listComments: List<Comment>): RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        //private val user_image: TextView = view.findViewById(R.id.user_image)
        private val user_name: TextView = view.findViewById(R.id.user_name)
        private val comment_text: TextView = view.findViewById(R.id.comment_text)

        fun bind(comment: Comment) {
            //user_image.text = comment.user_image
            user_name.text = comment.user_name
            comment_text.text = comment.comment_text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
    )

    override fun getItemCount() = this.listComments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = listComments[position]
        holder.bind(comment)
    }

}