package com.didahdx.weatherforecast.presentation.weatherDetailsTabDay

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.didahdx.weatherforecast.App
import com.didahdx.weatherforecast.databinding.DayWeatherDetailsFragmentBinding
import com.didahdx.weatherforecast.presentation.BaseFragment
import com.didahdx.weatherforecast.presentation.forecast.WeatherTabTypes

class DayWeatherDetailsFragment : BaseFragment() {

    companion object {

        fun newInstance(weatherTabTypes: WeatherTabTypes): DayWeatherDetailsFragment {
            val dayWeatherDetailsFragment = DayWeatherDetailsFragment()
            dayWeatherDetailsFragment.setWeatherTabType(weatherTabTypes)
            return dayWeatherDetailsFragment
        }

    }

    private lateinit var weatherTabTypes: WeatherTabTypes

    fun setWeatherTabType(weatherTabTypes: WeatherTabTypes) {
        this.weatherTabTypes = weatherTabTypes
    }

    private val viewModel: DayWeatherDetailsViewModel by viewModels()

    private var _binding: DayWeatherDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragmentComponent = (requireNotNull(this.activity).application as App)
            .appComponent.getFragmentComponentFactory().create()
        fragmentComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DayWeatherDetailsFragmentBinding.inflate(inflater, container, false)
        val dailyWeatherAdapter = DailyWeatherAdapter()
        val hourlyWeatherAdapter = HourlyWeatherAdapter()
        binding.rvWeather.apply {
            layoutManager = LinearLayoutManager(binding.root.context)
            adapter = when (weatherTabTypes) {
                WeatherTabTypes.TODAY -> hourlyWeatherAdapter
                WeatherTabTypes.TOMORROW -> hourlyWeatherAdapter
                WeatherTabTypes.LATTER -> dailyWeatherAdapter
            }
        }
        viewModel.setWeatherTabType(weatherTabTypes)
        viewModel.hourlyWeather.observe(viewLifecycleOwner,{hourlyList->
            if(hourlyList!=null){
                hourlyWeatherAdapter.submitList(hourlyList)
            }
        })

        viewModel.dailyWeather.observe(viewLifecycleOwner,{dailyList->
            if(dailyList!=null){
             dailyWeatherAdapter.submitList(dailyList)
            }
        })
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}