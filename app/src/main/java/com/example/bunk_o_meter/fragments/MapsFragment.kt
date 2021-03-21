package com.example.bunk_o_meter.fragments


import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bunk_o_meter.R
import com.example.bunk_o_meter.utils.CommonUtilities
import com.example.bunk_o_meter.utils.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MapsFragment : Fragment(),GoogleMap.OnMarkerDragListener {
    private var markerPresent=false
    private lateinit var map:GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var search:FloatingActionButton
    private val callback = OnMapReadyCallback { googleMap ->
        map=googleMap
       setDeviceLocation()
        googleMap.setOnMapClickListener {
            if (!markerPresent){
              val marker= googleMap.addMarker(MarkerOptions().position(it!!).draggable(true))
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position,15f))
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
       /*  search = view.findViewById<FloatingActionButton>(R.id.fabSearch)
        search.setOnClickListener {
        }*/
        mapFragment?.getMapAsync(callback)

    }

private fun setDeviceLocation(){
    if (!CommonUtilities.checkLocationPermission(requireActivity())){
        getLocationPermission()

    }else{
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
                    map.addMarker(
                        MarkerOptions()
                        .position(getLatLang(location))
                    )
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                       getLatLang(location),
                        15f
                    ))

                }else{
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(
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
    private fun getLocationPermission() {
        requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION),Constants.locationPermission)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==Constants.locationPermission){
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                setDeviceLocation()
            }
        }
    }

}