package com.o7services.androidkotlin_9_11am.maps_implementation

import com.o7services.androidkotlin_9_11am.BuildConfig
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.o7services.androidkotlin_9_11am.R
import com.o7services.androidkotlin_9_11am.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
//    private val binding: ActivityMapsBinding by lazy {
//        ActivityMapsBinding.inflate(layoutInflater)
//    }
    lateinit var binding: ActivityMapsBinding
    private val TAG = "MainActivity"
    private val fusedLocationProviderClient:FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }
    var maps:GoogleMap?=null
    var userlocation= LatLng(0.0, 0.0)
    var markerOptions=MarkerOptions()
    var mCenterMarker: Marker? = null

    private val locationPermission= arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION)

    private val requestPermissionLauncher=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()
    ){permissions->
        if (permissions.values.all { it }) {
            Toast.makeText(this, "All permissions granted", Toast.LENGTH_SHORT).show()
            getLastLocation()
        } else {
            Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
            openAppSettings()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if(!hasPermissions()){
            requestPermissionsWithRationale()
        } else {
            getLastLocation()
        }

        // Get a handle to the fragment and register the callback.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

    }





    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        Log.e(TAG, "getLastLocation:  ${isLocationEnabled()} ")
        if (isLocationEnabled()) {
            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                val location: Location = task.result
                if (location == null) {
                    requestNewLocationData()
                } else {
                    userlocation = LatLng(location.latitude, location.longitude)
                    updateMarker()
                }
            }
        } else {
            Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG)
                .show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = android.net.Uri.fromParts("package",BuildConfig.APPLICATION_ID, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun updateMarker() {
        mCenterMarker?.position = userlocation
        maps?.animateCamera(CameraUpdateFactory.newLatLng(userlocation))
        binding.tvFrom.text = MapFunctions.getLocationName(this, userlocation)
    }


    private fun requestPermissionsWithRationale() {
        val shouldShowRationale = locationPermission.any { permission ->
            ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
        }

        if (shouldShowRationale) {
            Toast.makeText(
                this,
                "Permissions are required for the app to function properly",
                Toast.LENGTH_LONG
            ).show()
            openAppSettings()
        } else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        requestPermissionLauncher.launch(
            locationPermission
        )
    }


    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest.Builder(10000)
            .build()

        fusedLocationProviderClient .requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            userlocation =LatLng(locationResult.lastLocation?.latitude?:0.0, locationResult.lastLocation?.longitude?:0.0)
            updateMarker()
        }
    }

    private fun hasPermissions(): Boolean {
        return locationPermission.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onMapReady(map: GoogleMap) {
        this.maps = map
        mCenterMarker = map.addMarker(
            MarkerOptions()
                .position(userlocation)
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(userlocation, 15f))
        map.setOnCameraIdleListener{
            userlocation = map.cameraPosition.target
            updateMarker()
        }
    }

}


