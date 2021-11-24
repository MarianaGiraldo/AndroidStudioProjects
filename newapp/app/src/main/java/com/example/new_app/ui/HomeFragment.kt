package com.example.new_app.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import com.example.new_app.R
import android.widget.VideoView




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var videoView: VideoView? = null
    private val VIDEO_NAME = "https://www.youtube.com/watch?v=VBuhsohLxkE"
    private val PLAYBACK_TIME = "play_time"
    private var currentPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            currentPos = savedInstanceState.getInt(PLAYBACK_TIME)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun getURI(): Uri{
        return if (URLUtil.isValidUrl(VIDEO_NAME)) {
            //  an external URL
            Uri.parse(VIDEO_NAME)
        } else { //  a raw resource
            Uri.parse("android.resource:/com.example.new_app" +
                    "/raw/" + VIDEO_NAME)
        }
    }
    private fun initPlayer() {
        val videoUri:Uri = getURI()
        videoView?.setVideoURI(videoUri)
        if (currentPos > 0) {
            videoView?.seekTo(currentPos)
        } else {
            videoView?.seekTo(1)
        }
        videoView?.start()
    }
    private fun releasePlayer(){
        videoView?.stopPlayback()
    }
    override fun onStop() {
        super.onStop()
        releasePlayer()
    }
    override fun onStart() {
        super.onStart()
        initPlayer()
    }
    override fun onPause() {
        super.onPause()
        videoView?.pause()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        videoView = view.findViewById(R.id.videoView)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        videoView?.let { outState.putInt(PLAYBACK_TIME, it.currentPosition) }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}