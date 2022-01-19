package com.didahdx.weatherforecast.presentation.weatherDetailsTabDay

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import com.didahdx.weatherforecast.R
import com.didahdx.weatherforecast.databinding.DayWeatherDetailsFragmentBinding
import com.didahdx.weatherforecast.presentation.BaseFragment
import com.didahdx.weatherforecast.presentation.forecast.WeatherTabTypes
import timber.log.Timber

class DayWeatherDetailsFragment() : BaseFragment() {

    companion object {
        fun newInstance(weatherTabTypes: WeatherTabTypes) = DayWeatherDetailsFragment()
    }

    private lateinit var viewModel: DayWeatherDetailsViewModel

    private var _binding: DayWeatherDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DayWeatherDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}