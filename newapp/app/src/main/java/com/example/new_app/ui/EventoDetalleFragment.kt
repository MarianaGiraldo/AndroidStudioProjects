package com.example.new_app.ui

import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.new_app.R
import com.example.new_app.models.Conference
import com.example.new_app.models.Location
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*
import kotlinx.android.synthetic.main.fragment_evento_detalle.*


class EventoDetalleFragment  : DialogFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val ll = inflater.inflate(R.layout.fragment_evento_detalle, container, false)

        //Getting map
        mapView = ll.findViewById(R.id.mapview) as MapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        return ll
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tbConfDets.navigationIcon = ContextCompat.getDrawable(view.context, R.drawable.close)
        tbConfDets.setNavigationOnClickListener{
            dismiss()
        }

        //Serializable
        val conference = arguments?.getSerializable("conference") as Conference

        tbConfDets.title = conference.title
        tbConfDets.setTitleTextColor(Color.WHITE)

        tvConfTitle.text = conference.title
        tvConfSpeakerName.text = conference.speaker
        tvConfDesc.text = conference.description
        tvConfTags.text = conference.tag

        val simpleDataFormat = SimpleDateFormat("HH:ss")
        val simpleDataFormatAMPM = SimpleDateFormat("a")
        val simpleDataFormatDate = SimpleDateFormat("MM/dd/yyyy")

        val cal = Calendar.getInstance()
        cal.time = conference.datetime
        val hourFormat = simpleDataFormat.format(conference.datetime)
        val dateFormat = simpleDataFormatDate.format(conference.datetime)

        tvConfDate.text = dateFormat
        tvConfTime.text = hourFormat
        tvConfAmPm.text = simpleDataFormatAMPM.format(conference.datetime).uppercase()

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val location = Location()

        //Zoom level
        val zoom = 16f

        //Center Map
        val centerMap = LatLng(location.latitude, location.longitude)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centerMap, zoom))

        //Manage marker
        val markerOptions = MarkerOptions()
        markerOptions.position(centerMap)

        markerOptions.title("CBA")
        googleMap.addMarker(markerOptions)

        googleMap.setOnMarkerClickListener(this)

    }

    override fun onMarkerClick(p0: Marker): Boolean {
        findNavController().navigate(R.id.locationDetailsDialogFragment)
        return true
    }


}