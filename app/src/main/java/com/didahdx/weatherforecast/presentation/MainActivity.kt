package com.didahdx.weatherforecast.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.didahdx.weatherforecast.App
import com.didahdx.weatherforecast.R
import com.didahdx.weatherforecast.databinding.ActivityMainBinding
import com.didahdx.weatherforecast.di.InjectedSavedStateViewModelFactory
import com.didahdx.weatherforecast.di.components.ActivitySubComponent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var activitySubComponent: ActivitySubComponent

    private lateinit var navController :NavController
    lateinit var appBarConfiguration: AppBarConfiguration



    override fun onCreate(savedInstanceState: Bundle?) {
        activitySubComponent = (application as App).appComponent.getActivityComponentFactory().create()
        activitySubComponent.inject(this)
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

         navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
         appBarConfiguration = AppBarConfiguration(navController.graph)
       binding.toolbar
            .setupWithNavController(navController, appBarConfiguration)
        requestLocationPermission()


    }


    private fun requestLocationPermission(){
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.get(Manifest.permission.ACCESS_FINE_LOCATION) == true  -> {
                    // Precise location access granted.
                }
                permissions.get(Manifest.permission.ACCESS_COARSE_LOCATION) == true  -> {
                    // Only approximate location access granted.
                } else -> {
                // No location access granted.
            }
            }
        }

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))

    }


}