package com.example.new_app.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.R
import com.example.new_app.adapter.CommentAdapter
import com.example.new_app.adapter.ConferenceAdapter
import com.example.new_app.adapter.SpeakerAdapter
import com.example.new_app.viewmodel.CommentsViewModel
import com.example.new_app.viewmodel.ConferencesViewModel

class CommentsListFragment : Fragment() {
    val commentsViewModel = CommentsViewModel()
    private lateinit var recycler:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        this.commentsViewModel.refresh()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val ll = inflater.inflate(R.layout.fragment_comments_list, container, false)
        this.recycler = ll.findViewById(R.id.comments_recycler)

        val commentsList = this.commentsViewModel.listComments.value
        if(commentsList != null) {
            recycler.adapter = CommentAdapter(commentsList)
        }
        else{
            Log.w("CommentsList", "List is null")
        }

        return ll
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        commentsViewModel.listComments.observe(viewLifecycleOwner, {
            listComments ->
            recycler.adapter = CommentAdapter(listComments)
        })
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