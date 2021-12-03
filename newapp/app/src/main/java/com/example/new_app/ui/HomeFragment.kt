package com.example.new_app.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.MediaController
import android.widget.Toast
import com.example.new_app.R
import android.widget.VideoView
import com.limerse.slider.ImageCarousel
import com.limerse.slider.model.CarouselItem


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private var videoView: VideoView? = null
    private val VIDEO_NAME = "falling_snow"
    private val PLAYBACK_TIME = "play_time"
    private var currentPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            currentPos = savedInstanceState.getInt(PLAYBACK_TIME)
        }

        val controller = MediaController(this.activity).also {
            it.setMediaPlayer(this.videoView)
        }
        this.videoView?.setMediaController(controller)

        /*
        // creating an object of media controller class
        var mediaControls = MediaController(this.activity)

        // set the anchor view for the video view
        mediaControls!!.setAnchorView(this.videoView)


        // set the media controller for video view
        videoView?.setMediaController(mediaControls)
        mediaControls!!.setAnchorView(videoView)

         */
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
            Uri.parse("android.resource://com.example.new_app" +
                    "/" + R.raw.video_bootcamp)
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
        videoView?.requestFocus()
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
        this.videoView = view.findViewById(R.id.videoView)

        val carousel : ImageCarousel = view.findViewById(R.id.carousel)
        // Register lifecycle. For activity this will be lifecycle/getLifecycle() and for fragment it will be viewLifecycleOwner/getViewLifecycleOwner().
        carousel.registerLifecycle(lifecycle)
        val list = mutableListOf<CarouselItem>()

        // Image URL with header

        list += CarouselItem(imageDrawable = R.drawable.img_1, caption = "Comienzo BootCamp")

        // Image drawable with caption
        list += CarouselItem(imageDrawable = R.drawable.img_2, caption = "BootCamp y SENA Digital")

        // Just image drawable
        list += CarouselItem(imageDrawable = R.drawable.img_3)
        list += CarouselItem(imageDrawable = R.drawable.img_4)
        carousel.setData(list)

        // display a toast message
        // after the video is completed
        this.videoView!!.setOnCompletionListener {
            Toast.makeText(this.activity, "Video completed",
                Toast.LENGTH_LONG).show()
        }

        // display a toast message if any
        // error occurs while playing the video
        this.videoView!!.setOnErrorListener { _, _, _ ->
            Toast.makeText(this.activity, "An Error Occurred " +
                    "While Playing Video !!!", Toast.LENGTH_LONG).show()
            false
        }
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
         *
         */

        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {

            }
    }
}