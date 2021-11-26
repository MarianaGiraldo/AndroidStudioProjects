package com.example.new_app.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.MediaController
import android.widget.Toast
import com.example.new_app.R
import android.widget.VideoView
import androidx.constraintlayout.helper.widget.Carousel
import com.limerse.slider.ImageCarousel
import com.limerse.slider.model.CarouselItem
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener


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
    private var mediaControls: MediaController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            currentPos = savedInstanceState.getInt(PLAYBACK_TIME)
        }
        /*
        val carouselView: CarouselView? = view?.findViewById(R.id.carouselView)
        carouselView?.pageCount = sampleImages.size;
        carouselView?.setImageListener(imageListener);
        */

        if (mediaControls == null) {
            // creating an object of media controller class
            mediaControls = MediaController(this.activity)

            // set the anchor view for the video view
            mediaControls!!.setAnchorView(this.videoView)
        }

        // set the media controller for video view
        videoView?.setMediaController(this.mediaControls)
        this.mediaControls!!.setAnchorView(videoView)

    }
/*
    private var imageListener: ImageListener = ImageListener { position, imageView ->
        imageView.setImageResource(sampleImages[position])
    } */

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
        videoView = view.findViewById(R.id.videoView)

        val carousel : ImageCarousel = view.findViewById(R.id.carousel)
        // Register lifecycle. For activity this will be lifecycle/getLifecycle() and for fragment it will be viewLifecycleOwner/getViewLifecycleOwner().
        carousel.registerLifecycle(lifecycle)
        val list = mutableListOf<CarouselItem>()

        // Image URL with header

        list.add(
            CarouselItem(
                imageDrawable = R.drawable.img_1,
                caption = "Comienzo BootCamp"
            )
        )

        // Image drawable with caption
        list.add(
            CarouselItem(
                imageDrawable = R.drawable.img_2,
                caption = "BootCamp y SENA Digital"
            )
        )

        // Just image drawable
        list.add(
            CarouselItem(
                imageDrawable = R.drawable.img_3
            )
        )
        list.add(
            CarouselItem(
                imageDrawable = R.drawable.img_4
            )
        )
        carousel.setData(list)



        // display a toast message
        // after the video is completed
        videoView!!.setOnCompletionListener {
            Toast.makeText(this.activity, "Video completed",
                Toast.LENGTH_LONG).show()
        }

        // display a toast message if any
        // error occurs while playing the video
        videoView!!.setOnErrorListener { _, _, _ ->
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