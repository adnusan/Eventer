package com.example.eventer.Fragments

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.example.eventer.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.CameraUpdateFactory.zoomTo

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private lateinit var mMap: GoogleMap

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var fineLocationPermissionGranted = false
    private var coarseLocationPermissionGranted = false
    private var locationPermissionGranted = false
    private var sfsuLocation = LatLng(37.7249, -122.4783)


    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        mMap = googleMap
        mMap.addMarker(MarkerOptions().position(sfsuLocation).title("Marker in SFSU").snippet("This is SFSU"))
        mMap.moveCamera(zoomTo(15f))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sfsuLocation))
        mMap.setOnMapClickListener { latLng ->
            mMap.moveCamera(zoomTo(15f))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_maps, container, false)
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            fineLocationPermissionGranted = permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] ?: fineLocationPermissionGranted
            coarseLocationPermissionGranted = permissions[android.Manifest.permission.ACCESS_COARSE_LOCATION] ?: coarseLocationPermissionGranted
        }
        permissionRequest()

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    //function to show curent location
    @SuppressWarnings("MissingPermission")
    private fun showCurrentLocation(){
        if(locationPermissionGranted){
            val userLocation = fusedLocationProviderClient?.lastLocation
            userLocation?.addOnSuccessListener { location ->
                if(location != null){
                    val currentLocation = LatLng(location.latitude, location.longitude)
                    mMap.addMarker(MarkerOptions().position(currentLocation).title("Current Location"))
                    mMap.moveCamera(zoomTo(15f))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
                }
                else{
                    Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show()
                    mMap.moveCamera(zoomTo(15f))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sfsuLocation))
                }
            }
        }

    }


    private fun permissionRequest(){
        //check if permission is granted from fragment
        fineLocationPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        locationPermissionGranted = true

        coarseLocationPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val permissionRequest : MutableList<String> = ArrayList()

        if(!fineLocationPermissionGranted){
            permissionRequest.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (!coarseLocationPermissionGranted){
            permissionRequest.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        if(permissionRequest.isNotEmpty()){
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }

    }






}