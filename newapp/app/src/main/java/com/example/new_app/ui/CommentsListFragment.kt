package com.example.new_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.new_app.R
import com.example.new_app.adapter.CommentAdapter
import com.example.new_app.viewmodel.CommentsViewModel

class CommentsListFragment : Fragment() {
    private val commentsViewModel = CommentsViewModel()
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
        return ll
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.commentsViewModel.listComments.observe(viewLifecycleOwner, {
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