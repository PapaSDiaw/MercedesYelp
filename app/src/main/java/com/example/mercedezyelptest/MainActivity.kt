package com.example.mercedezyelptest

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.mercedezyelptest.view.navigateToList
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val requestCode: Int = 3714
    private val permissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        Log.d(TAG, "Permission Granted: $isGranted")
        if (isGranted)
            requestData()
        else {
            ActivityCompat.shouldShowRequestPermissionRationale(
                this@MainActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ).showRationale {
                displaySnackBarRationale(this)
            }
        }
    }
    private lateinit var fusedLocation: FusedLocationProviderClient

    private fun displaySnackBarRationale(shouldEducate: Boolean) {
        Snackbar.make(
            findViewById(android.R.id.content),
            if (shouldEducate)
                getString(R.string.request_permission)
            else
                getString(R.string.permission_denied),
            Snackbar.LENGTH_SHORT
        ).setAction(android.R.string.ok) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                requestCode
            )
        }.show()
    }

    private fun requestData() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
            fusedLocation.lastLocation
                .addOnSuccessListener {
                    navigateToList(it.latitude, it.longitude)
                }
                .addOnCompleteListener {
                    Toast.makeText(
                        this, "Permission is granted lat:${it.result.latitude}, lon:${it.result.longitude}",
                        Toast.LENGTH_LONG
                    ).show()
                }
    }

    private fun Boolean.showRationale(block: Boolean.() -> Unit) {
        block()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        fusedLocation = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "onRequestPermissionsResult: ")
        when (requestCode) {
            this.requestCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    requestData()
            }
            else -> {}
        }

    }
}