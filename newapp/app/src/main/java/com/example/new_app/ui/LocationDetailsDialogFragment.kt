package com.example.new_app.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.new_app.R
import com.example.new_app.models.Location
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*
import kotlinx.android.synthetic.main.fragment_locationdetails_dialog.*


class LocationDetailsDialogFragment  : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_locationdetails_dialog, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tbLocationDets.navigationIcon = ContextCompat.getDrawable(view.context, R.drawable.close)
        tbLocationDets.setNavigationOnClickListener{
            dismiss()
        }

        val location = Location()

        tbLocationDets.title = location.name
        tvLocationName.text = location.name
        tvLocationAddress.text = location.address
        tvLocationPhone.text = location.phone
        tvLocationUrl.text = location.website

        //Tratamiento de la imagen por Glide

        Glide.with(this)
            .load(location.photo)
            .apply(RequestOptions.circleCropTransform())
            .into(ivLocationImg) //Id de la imagen

        //Open Marker and dial phone
        //llLocationPhone linear la
        llLocationPhone.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL).apply{
                data = Uri.parse("tel:${location.phone}")
            }
            //Iniciar
            startActivity(intent)
        }

        //Para abrir el navegador
        //ll_web_ubi es el id del linearlayout
        llLocationUrl.setOnClickListener{
            val intent  = Intent(Intent.ACTION_VIEW). apply{
                data = Uri.parse(location.website)
            }
            startActivity(intent)
        }


    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

}