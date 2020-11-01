package com.ar.cartonclouddemo.view

import android.os.Bundle

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ar.cartonclouddemo.R
import com.ar.cartonclouddemo.adapter.WeatherAdaptor
import com.ar.cartonclouddemo.databinding.FragmentWeatherDataBinding
import com.ar.cartonclouddemo.utils.Resource
import com.ar.cartonclouddemo.viewmodel.WeatherDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.aviran.cookiebar2.CookieBar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class WeatherDataFragment : Fragment(){

    private lateinit var binding: FragmentWeatherDataBinding
    private val viewModel: WeatherDataViewModel by viewModels()
    private lateinit var adapter: WeatherAdaptor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherDataBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        loadData()
    }

    private fun setupUi() {
        binding.swipeContainer.setOnRefreshListener {

            loadData()
        }
        adapter = WeatherAdaptor(requireActivity())
        binding.rvWeatherList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWeatherList.adapter = adapter
    }


    private fun loadData(){
        val urlPath = "1100661/" + getYesterdayDateString()
        viewModel.loadWeatherData(urlPath).observe(requireActivity(), Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {

                    binding.swipeContainer.isRefreshing = false

                    if (!it.data.isNullOrEmpty()) adapter.setData(ArrayList(it.data))

                }
                Resource.Status.ERROR -> {

                    this.showError()
                }


                Resource.Status.LOADING -> {


                }

            }
        })
    }

    private fun showError(){
        CookieBar.build(activity)
            .setCustomView(R.layout.custom_error_layout)

            .setAction("Close") { CookieBar.dismiss(activity) }
            .setTitle(R.string.error_view_title)
            .setMessage(R.string.error_message)
            .setEnableAutoDismiss(false)
            .setSwipeToDismiss(false)
            .setCookiePosition(Gravity.BOTTOM)
            .show()
    }
    private fun getYesterdayDateString(): String? {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd")
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        return dateFormat.format(cal.time)
    }
}