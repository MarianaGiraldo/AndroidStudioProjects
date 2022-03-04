package com.example.new_app.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.new_app.R
import com.example.new_app.models.Speaker


class SpeakerInfoFragment : DialogFragment() {
    private lateinit var tbDetSpeaker: Toolbar
    private lateinit var tvName: TextView
    private lateinit var tvJobTitle: TextView
    private lateinit var tvWorkplace: TextView
    private lateinit var ivPhoto: ImageView
    private lateinit var tvTwitter: TextView
    private lateinit var tvCategory: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val ll = inflater.inflate(R.layout.fragment_speaker_info, container, false)
        this.tbDetSpeaker = ll.findViewById(R.id.tb_det_speaker)
        this.tvName = ll.findViewById(R.id.name_speaker)
        this.tvJobTitle = ll.findViewById(R.id.jobtitle_speaker)
        this.tvWorkplace = ll.findViewById(R.id.workplace_speaker)
        this.ivPhoto = ll.findViewById(R.id.photo_speaker)
        this.tvTwitter = ll.findViewById(R.id.twitter_speaker)
        this.tvCategory = ll.findViewById(R.id.category_speaker)

        return ll
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tbDetSpeaker.navigationIcon = ContextCompat.getDrawable(view.context, R.drawable.close)
        tbDetSpeaker.setNavigationOnClickListener{
            dismiss()
        }

        val speaker = this.arguments?.getParcelable<Speaker>("speaker") as Speaker
        tbDetSpeaker.title = speaker.name
        tbDetSpeaker.setTitleTextColor(Color.WHITE)

        tvName.text = speaker.name
        tvJobTitle.text = speaker.jobtitle
        tvWorkplace.text = speaker.workplace
        tvTwitter.text = speaker.twitter
        tvCategory.text = speaker.category.toString()

        Glide.with(this)
            .load(speaker.image)
            .apply(RequestOptions.circleCropTransform())
            .into(ivPhoto)

        tvTwitter.setOnClickListener{
            var intent: Intent
            try {
                context?.packageManager?.getPackageInfo("com.twitter.android", 0)
                intent = Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=${speaker.twitter}"))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            }catch (e: Exception){
                intent = Intent(
                    Intent.ACTION_VIEW, Uri.parse("https://twitter.com/${speaker.twitter}")
                )
            }
            startActivity(intent)

        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

}