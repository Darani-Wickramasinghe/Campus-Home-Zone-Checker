package com.example.campuszonechecker

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class MainActivity : AppCompatActivity() {

    // Fixed reference location for our Campus/Home Zone
    companion object {

        // Reference latitude
        const val REFERENCE_LATITUDE = 6.9271

        // Reference longitude
        const val REFERENCE_LONGITUDE = 79.8612

        // Zone radius in meters
        const val ZONE_RADIUS_METERS = 200f
    }


    // Used to access the Fused Location Provider
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    // Handles the result of the runtime location permission request
    private val locationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {

                // Permission granted, so get the current location
                getCurrentLocation()

            } else {

                // Permission denied
                Toast.makeText(
                    this,
                    "Location permission is required",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)


        // Create the Fused Location Provider client
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this)


        // Find the Check My Zone button from activity_main.xml
        val btnCheckZone =
            findViewById<Button>(R.id.btnCheckZone)


        // When the user presses the button,
        // first check location permission
        btnCheckZone.setOnClickListener {

            checkLocationPermission()
        }


        // Handles screen padding for status/navigation bars
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->

            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }
    }

    // Check Location Permission
    private fun checkLocationPermission() {

        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            // Permission already granted
            getCurrentLocation()

        } else {

            // Ask user for location permission
            locationPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    // Get Current Device Location
    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        )
            .addOnSuccessListener { location ->

                if (location != null) {

                    // Successfully received location
                    handleLocation(location)

                } else {

                    // Could not get a location
                    Toast.makeText(
                        this,
                        "Unable to get current location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            .addOnFailureListener {

                // Something went wrong while retrieving location
                Toast.makeText(
                    this,
                    "Failed to get location",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun handleLocation(location: Location) {

        // Read latitude
        val latitude = location.latitude

        // Read longitude
        val longitude = location.longitude


        // For now, show the location using a Toast
        Toast.makeText(
            this,
            "Latitude: $latitude\nLongitude: $longitude",
            Toast.LENGTH_LONG
        ).show()


        /*
         * PERSON 4 will later use this Location object
         * to calculate distance using distanceTo().
         *
         * So for Person 3, you can stop here.
         */
    }
}