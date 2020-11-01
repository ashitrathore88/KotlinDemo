package com.ar.cartonclouddemo.view

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ar.cartonclouddemo.databinding.FragmentDialogWeatherDataBinding
import com.ar.cartonclouddemo.viewmodel.WeatherDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class WeatherDialogFragment: DialogFragment() {

    private val viewModel: WeatherDataViewModel by viewModels()
    private lateinit var binding: FragmentDialogWeatherDataBinding
    companion object{
        const val TAG = "WeatherDialogFragment";
        private const val ID = "ID"

        fun newInstance(id: Long): WeatherDialogFragment{
            val args = Bundle()
            args.putLong(ID,id)
            val fragment = WeatherDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogWeatherDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setupViews()
    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    fun setupViews(){
        viewModel.getWeather(arguments?.getLong(ID)).observe(requireActivity(), Observer {

            binding.applicableDate.text = it.data?.applicable_date
            binding.weatherState.text = it.data?.weather_state_name
            binding.windDirection.text = it.data?.wind_direction_compass
            binding.temprature.text = it.data?.the_temp?.roundToInt().toString()+" \u2103"
            binding.windSpeed.text = it.data?.wind_speed?.roundToInt().toString()
            binding.airePressure.text = it.data?.air_pressure.toString()
            binding.humidity.text = it.data?.humidity.toString()
            binding.visibility.text = it.data?.visibility?.roundToInt().toString()
            binding.predictability.text = it.data?.predictability.toString()
        })
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        dismiss()
    }

}