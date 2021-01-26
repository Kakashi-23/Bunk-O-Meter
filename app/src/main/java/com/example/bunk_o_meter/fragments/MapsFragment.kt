package com.example.bunk_o_meter.fragments

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.bunk_o_meter.R
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.fragment_maps.*

open class MapsFragment : Fragment(),OnMapReadyCallback, GoogleMap.OnMarkerDragListener {
    lateinit var googleMap: GoogleMap
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val permissionCode=1234
    lateinit var searchTxt:TextView
    private  val apiKey="AIzaSyDizJiy5h59ADUCAWLemaLwfmAMvkR7Q60"
    lateinit var placesClient:PlacesClient
    val aUTOCOMPLETE_REQUEST_CODE = 1
   var finallatlng:LatLng?=null
    var markerPresent=false
    // 1

    // 2
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        // 3
        private const val REQUEST_CHECK_SETTINGS = 2
    }






    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        searchTxt=view.findViewById(R.id.mapFragEdtxt)



        Places.initialize(activity as Context,apiKey)
        placesClient=Places.createClient(activity as Context)
        createLocationRequest()
        return view

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMapReady(map: GoogleMap?) {
        map?.let {
            googleMap = it
        }

            CurrentLocation()
            onClickMarker()
            searchTxt.setOnClickListener { searchLocationNew() }

    }
// when map is clicked and a marker is shown
    private fun onClickMarker() {

            googleMap.setOnMapClickListener {
                if (!markerPresent) {
                        markerPresent = true
                       var marker = googleMap.addMarker(MarkerOptions().position(it).draggable(true))
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
                     googleMap.setOnMarkerDragListener(this)
                    }
            }
}




    // get cuurent location of device
@RequiresApi(Build.VERSION_CODES.M)
fun CurrentLocation() {
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(activity as Context)
            try {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                    val latLng = LatLng(location!!.latitude, location.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    googleMap.isMyLocationEnabled=true


                }.addOnFailureListener {
                    Toast.makeText(
                        activity as Context,
                        "Could not fetch data",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            } catch (e: SecurityException) {
                Toast.makeText(activity as Context,"Could not fetch data", Toast.LENGTH_SHORT).show()

            }
        }



// autocomplete wideget
fun searchLocationNew(){
    val field= listOf(Place.Field.ID,Place.Field.NAME,Place.Field.ADDRESS, Place.Field.LAT_LNG)
    val intent=Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,field).build(activity as Context)
    startActivityForResult(intent,aUTOCOMPLETE_REQUEST_CODE)



}


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == aUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == AutocompleteActivity.RESULT_OK) {
                markerPresent=true
                val place = Autocomplete.getPlaceFromIntent(data!!)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.latLng,15f))
                val markerOptions=MarkerOptions().position(place.latLng!!).title(place.address).draggable(true)
              googleMap.addMarker(markerOptions)
                googleMap.setOnMarkerDragListener(this)


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                val status = Autocomplete.getStatusFromIntent(data!!)
                Toast.makeText(activity as Context,"$status",Toast.LENGTH_SHORT).show()

            }
        }
    }



    override fun onMarkerDragEnd(marker: Marker?) {
         finallatlng=marker?.position
        val geocoder=Geocoder(activity as Context)
        val list=geocoder.getFromLocation(marker!!.position.latitude, marker.position.longitude,1)
        marker.title= list[0].getAddressLine(0)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(finallatlng,15f))
        // saving this lat lang info

    }

    override fun onMarkerDragStart(p0: Marker?) {

    }

    override fun onMarkerDrag(p0: Marker?) {

    }
    // location permissions
    @RequiresApi(Build.VERSION_CODES.M)
    private fun startLocationUpdates() {
        //1
        if (ActivityCompat.checkSelfPermission(activity as Context,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        CurrentLocation()

    }
    // requesting
    @RequiresApi(Build.VERSION_CODES.M)
    private fun createLocationRequest() {
        // 1
        locationRequest = LocationRequest()
        // 2
        locationRequest.interval = 10000
        // 3
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        // 4
        val client = LocationServices.getSettingsClient(activity as Context)
        val task = client.checkLocationSettings(builder.build())

        // 5
        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->
            // 6
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    val dialog=AlertDialog.Builder(activity as Context)
                    dialog.setTitle("Permission")
                    dialog.setMessage("Allow app to access location")
                    dialog.setPositiveButton("Open settings"){text,listener->
                        val settings=Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startActivity(settings)
                    }
                    dialog.setNegativeButton("No"){text,Listener->
                        ActivityCompat.finishAffinity(activity!!)
                    }
                    dialog.create()
                    dialog.show()

                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }
    // 1


    // 2
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onPause() {
        super.onPause()
       CurrentLocation()
    }

    // 3
    @RequiresApi(Build.VERSION_CODES.M)
    public override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }
    }




}





