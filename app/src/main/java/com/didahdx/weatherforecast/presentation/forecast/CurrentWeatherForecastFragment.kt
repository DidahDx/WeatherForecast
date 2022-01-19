package com.didahdx.weatherforecast.presentation.forecast

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.didahdx.weatherforecast.R
import com.didahdx.weatherforecast.databinding.CurrentWeatherForecastFragmentBinding
import com.didahdx.weatherforecast.presentation.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import android.R.menu
import android.content.Context
import android.text.TextUtils

import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import com.didahdx.weatherforecast.App
import com.didahdx.weatherforecast.common.DateTimeConversion
import timber.log.Timber


class CurrentWeatherForecastFragment : BaseFragment() {

    companion object {
        fun newInstance() = CurrentWeatherForecastFragment()
    }

    private var _binding: CurrentWeatherForecastFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrentWeatherForecastViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragmentComponent = (requireNotNull(this.activity).application as App)
            .appComponent.getFragmentComponentFactory().create()
        fragmentComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= CurrentWeatherForecastFragmentBinding.inflate(inflater,container,false)
        val fm: FragmentManager = childFragmentManager
        val lifecycle = viewLifecycleOwner.lifecycle
        val forecastpager = ForecastPageAdapter(fm, lifecycle)
        binding.weatherPager.adapter = forecastpager

        viewModel.currentWeather.observe(viewLifecycleOwner, { currentWeather->
            if(currentWeather!=null){
                binding.tvTemp.text = currentWeather.temp.toString()
                val sunrise=DateTimeConversion.convertToTime(currentWeather.sunrise.toLong())
                val sunset=DateTimeConversion.convertToTime(currentWeather.sunset.toLong())
                binding.tvCurrentWeatherDetails.text = resources.getString(R.string.current_weather,currentWeather.windSpeed.toString(),currentWeather.pressure,currentWeather.humidity,sunrise,sunset)}
            })


        val tabTitles = resources.getStringArray(R.array.weather_tab_days)
        TabLayoutMediator(
            binding.tabLayout, binding.weatherPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = tabTitles[position]
            binding.weatherPager.setCurrentItem(tab.position, true)
        }.attach()

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_by_city_name)


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(query: String?): Boolean {
                Timber.e("$query")
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Timber.e("$query")
                return true
            }
        })

    }



    override fun onDestroyView() {
        binding.weatherPager.adapter = null
        super.onDestroyView()
        _binding = null
    }

}