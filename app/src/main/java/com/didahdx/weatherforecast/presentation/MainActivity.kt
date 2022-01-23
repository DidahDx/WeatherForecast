package com.didahdx.weatherforecast.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.didahdx.weatherforecast.App
import com.didahdx.weatherforecast.R
import com.didahdx.weatherforecast.databinding.ActivityMainBinding
import com.didahdx.weatherforecast.di.components.ActivitySubComponent

class MainActivity : AppCompatActivity() {

    lateinit var activitySubComponent: ActivitySubComponent

    private lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
   private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        activitySubComponent =
            (application as App).appComponent.getActivityComponentFactory().create()
        activitySubComponent.inject(this)
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar
            .setupWithNavController(navController, appBarConfiguration)

    }


    fun setToolBarTitle(title: String) {
        binding.toolbar.title=title
    }
}