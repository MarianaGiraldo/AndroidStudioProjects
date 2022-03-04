package com.example.new_app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.new_app.R
import com.example.new_app.models.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class UbicationFragment : Fragment(), OnMapReadyCallback {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ubication, container, false)
    }
/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
*/
    override fun onMapReady(googleMap: GoogleMap) {
    /*
        val ubication = Location()

        //Zoom level
        val zoom = 16f

        //Center Map
        val centerMap = LatLng(ubication.latitude, ubication.longitude)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centerMap, zoom))

        //Manage marker
        val markerOptions = MarkerOptions()
        val centerMarker = centerMap
        markerOptions.position(centerMarker)

        markerOptions.title("CBA")
        googleMap.addMarker(markerOptions)
        //TODO Listener

     */
    }

}