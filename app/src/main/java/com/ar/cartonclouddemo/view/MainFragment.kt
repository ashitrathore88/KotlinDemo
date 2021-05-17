package com.ar.cartonclouddemo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ar.cartonclouddemo.R
import com.ar.cartonclouddemo.databinding.FragmentMainBinding
import com.ar.cartonclouddemo.model.WeatherData
import com.ar.cartonclouddemo.utils.Resource
import com.ar.cartonclouddemo.viewmodel.WeatherDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.aviran.cookiebar2.CookieBar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var viewModel: WeatherDataViewModel? = null
    private var binding: FragmentMainBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(WeatherDataViewModel::class.java)
        setupUi()
        setupListeners()
    }

    private fun setupListeners() {
        binding!!.btCheckWeather.setOnClickListener { navigate() }
        binding!!.btLoadWeather.setOnClickListener {
            binding!!.btLoadWeather.visibility = View.GONE
            loadData()
        }
    }

    private fun setupUi() {
        binding!!.btLoadWeather.visibility = View.VISIBLE
    }

    private fun loadData() {
        val urlPath = "1100661/$yesterdayDateString"
        viewModel!!.loadWeatherData(urlPath).observe(
            viewLifecycleOwner,
            Observer<Resource<List<WeatherData?>>> { data: Resource<List<WeatherData?>> ->
                if (data.status === Resource.Status.SUCCESS) {
                    if (data.data!!.isNotEmpty()) {
                        binding!!.pgLoading.visibility = View.GONE
                        binding!!.btLoadWeather.visibility = View.GONE
                        binding!!.btCheckWeather.visibility = View.VISIBLE
                    }
                } else if (data.status === Resource.Status.ERROR) {
                    binding!!.btLoadWeather.visibility = View.VISIBLE
                    binding!!.pgLoading.visibility = View.GONE
                    CookieBar.build(requireActivity())
                        .setTitle("Error")
                        .setMessage("Please try again later")
                        .setSwipeToDismiss(true)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show()
                } else if (data.status === Resource.Status.LOADING) {
                    binding!!.btLoadWeather.visibility = View.GONE
                    binding!!.pgLoading.visibility = View.VISIBLE
                }
            })
    }

    private fun navigate() {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            .navigate(R.id.action_mainFragment_to_weatherLoadFragment)
    }

    private val yesterdayDateString: String
        private get() {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd")
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -1)
            return dateFormat.format(cal.time)
        }
}