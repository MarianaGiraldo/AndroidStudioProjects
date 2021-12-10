package com.example.new_app.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.R
import com.example.new_app.adapter.CommentAdapter
import com.example.new_app.adapter.ConferenceAdapter
import com.example.new_app.viewmodel.CommentsViewModel
import com.example.new_app.viewmodel.ConferencesViewModel

class CommentsListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val ll = inflater.inflate(R.layout.fragment_comments_list, container, false)
        val commentsViewModel = CommentsViewModel()
        val commentsList = commentsViewModel.listComments.value
        val recycler = ll.findViewById<RecyclerView>(R.id.comments_recycler)

        if (recycler != null) {
            recycler.adapter = commentsList?.let { CommentAdapter(it) }
        }
        else{
            Log.d("CommentsRecycler", "Recycler is null")
        }
        return ll
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CommentsListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}