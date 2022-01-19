package com.didahdx.weatherforecast.presentation.weatherDetailsTabDay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.didahdx.weatherforecast.R
import com.didahdx.weatherforecast.common.Constants
import com.didahdx.weatherforecast.common.DateTimeConversion
import com.didahdx.weatherforecast.data.local.entities.DailyEntity
import com.didahdx.weatherforecast.databinding.ItemWeatherDetailsBinding

/**
 * @author by Daniel Didah on 1/20/22.
 */
class DailyWeatherAdapter :
    ListAdapter<DailyEntity, DailyWeatherAdapter.DailyViewHolder>(DailyDiffUtil()) {

    class DailyViewHolder(private val binding: ItemWeatherDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(daily: DailyEntity) {
            binding.tvTemp.text = binding.root.context.getString(R.string.temp, daily.temp)
            binding.tvTime.text= DateTimeConversion.convertToDateTime(daily.dt,daily.timezoneOffSet)
            binding.tvWeatherDescription.text= binding.root.context.getString(R.string.current_weather_status,
                daily.windSpeed.toString(),
                daily.pressure,
                daily.humidity,
            )
            Glide.with(binding.root.context)
                .load(String.format(Constants.WEATHER_ICON_URL, daily.weather?.icon))
                .centerCrop()
                .into(binding.ivWeatherIcon)

            binding.ivWeatherIcon.contentDescription = binding.root.context
                .getString(R.string.weather_image_description, daily.weather?.description)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val binding =
            ItemWeatherDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DailyDiffUtil : DiffUtil.ItemCallback<DailyEntity>() {

    override fun areItemsTheSame(oldItem: DailyEntity, newItem: DailyEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DailyEntity, newItem: DailyEntity): Boolean {
        return oldItem == newItem
    }

}