package com.example.eventer.Fragments

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

import com.example.eventer.R
import com.example.eventer.model.UsersFb
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.CameraUpdateFactory.zoomTo
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

import com.example.eventer.model.Event
import java.util.EventObject

class MapsFragment : Fragment() , OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

    //firebase
    private lateinit var user: FirebaseUser
    private lateinit var databaseReference: DatabaseReference
    private lateinit var eventDatabase: DatabaseReference

    private lateinit var auth : FirebaseAuth
    private lateinit var userFb: UsersFb
    var userId : String? = null
    private var username = ""


    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var fineLocationPermissionGranted = false
    private var coarseLocationPermissionGranted = false
    private var locationPermissionGranted = false
    private var sfsuLocation = LatLng(37.7249, -122.4783)

    //layouts
    //private var markerView: View? = null


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
        mMap.setOnMarkerClickListener(this)

        //creating marker on map
        mMap.setOnMapClickListener { latLng ->
            //create a marker
            val lat = latLng.latitude
            val lng = latLng.longitude
            val title = "$username's Event"
            val marker = mMap.addMarker(MarkerOptions().position(latLng).title(title).snippet("Created at $lat, $lng"))
            val eventObject = Event(username,title, lat, lng)
            //
        addEvent(eventObject)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_maps, container, false)

        //firebase
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!
        userId = user.uid

        databaseReference = FirebaseDatabase.getInstance().reference.child("user")
        eventDatabase = FirebaseDatabase.getInstance().reference

        getUserData()

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

    override fun onMarkerClick(marker: Marker): Boolean {

        Toast.makeText(requireContext(), "Marker Position \n"+username +"\n"+marker.position, Toast.LENGTH_SHORT).show()
        return false
    }

    //loads userdata from firebase and sets it to the profile
    private fun getUserData() {
        databaseReference.child(user.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userFb = dataSnapshot.getValue(UsersFb::class.java)!!
                username = userFb.username.toString()
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }


    //function to add event to firebase
    private fun addEvent(eventObject: Event) {
        eventDatabase.child("events").child(userId!!).push()
            .setValue(eventObject).addOnSuccessListener {
                Toast.makeText(requireContext(), "Event Created", Toast.LENGTH_SHORT).show()
            }

    }





    }




