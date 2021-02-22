package com.example.bunk_o_meter.fragments

import android.graphics.Color
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.renderscript.Sampler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.bunk_o_meter.R
import com.example.bunk_o_meter.utils.CommonUtilities
import com.example.bunk_o_meter.utils.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MapsFragment : Fragment(),GoogleMap.OnMarkerDragListener {

    private var markerPresent=false
    private lateinit var map:GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val callback = OnMapReadyCallback { googleMap ->
        map=googleMap
       setDeviceLocation(googleMap)
        googleMap.setOnMapClickListener {
            if (!markerPresent){
                googleMap.addMarker(MarkerOptions().position(it!!).draggable(true))
                markerPresent=true
            }
        }
        googleMap.setOnMarkerDragListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Places.initialize(requireContext(),getString(R.string.google_maps_key))
        Places.createClient(requireContext())
        fusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(requireContext())
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        val search = view.findViewById<FloatingActionButton>(R.id.fabSearch)
        mapFragment?.getMapAsync(callback)

    }
private fun setDeviceLocation(googleMap: GoogleMap){
    if (!CommonUtilities.checkLocationPermission(requireActivity())){
        CommonUtilities.getLocationPermission(requireActivity())
    }else {
        val userLocation =fusedLocationProviderClient.lastLocation
        userLocation.addOnCompleteListener {
            if (it.isSuccessful){
                if (it.result!=null){
                    val location = it.result
                    /*googleMap.addCircle(CircleOptions()
                        .center(getLatLang(location!!))
                        .radius(5.0)
                        .strokeColor(ContextCompat.getColor(requireContext(),R.color.blueEyes))
                        .fillColor(
                            ContextCompat.getColor(requireContext(),R.color.deep_sky_blue)
                        ))*/
                    googleMap.addMarker(
                        MarkerOptions()
                        .position(getLatLang(location))
                    )
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                       getLatLang(location),
                        15f
                    ))

                }else{
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        LatLng(15.02,15.02),
                        15f
                    ))
                }
            }
        }
    }

}
    private fun getLatLang(location:Location):LatLng{
        return LatLng(location.latitude, location.longitude)
    }

    override fun onMarkerDragStart(p0: Marker?) {

    }

    override fun onMarkerDrag(p0: Marker?) {

    }

    override fun onMarkerDragEnd(p0: Marker?) {
     if (p0!=null){
         Constants.lectureHallLocation=p0.position!!
         map.animateCamera(CameraUpdateFactory.newLatLngZoom(p0.position!!,15f))
         CommonUtilities.showToast(requireContext(),p0.position!!.toString())
     }
    }

}