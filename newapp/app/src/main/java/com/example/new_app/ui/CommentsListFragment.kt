package com.example.new_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.R
import com.example.new_app.adapter.CommentAdapter
import com.example.new_app.models.Comment
import com.example.new_app.viewmodel.CommentsViewModel

class CommentsListFragment : Fragment() {
    private val commentsViewModel = CommentsViewModel()
    private lateinit var recycler:RecyclerView
    private lateinit var viewAlpha:View
    private lateinit var pgbar: ProgressBar

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
        this.viewAlpha = ll.findViewById(R.id.view_commentsList)
        this.pgbar = ll.findViewById(R.id.pgbar_commentsList)

        return ll
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.commentsViewModel.listComments.observe(viewLifecycleOwner) { listComments ->
            if (listComments != null){
                recycler.adapter = CommentAdapter(listComments)
                viewAlpha.visibility = View.INVISIBLE
                pgbar.visibility = View.INVISIBLE

            }else {
                Log.w("onViewCreatedComments", "Comments List is null")
            }
        }
    }



}