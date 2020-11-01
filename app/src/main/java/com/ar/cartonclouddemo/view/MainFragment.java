package com.ar.cartonclouddemo.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.ar.cartonclouddemo.R;
import com.ar.cartonclouddemo.databinding.FragmentMainBinding;
import com.ar.cartonclouddemo.utils.Resource;
import com.ar.cartonclouddemo.viewmodel.WeatherDataViewModel;


import org.aviran.cookiebar2.CookieBar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends Fragment {

    private WeatherDataViewModel viewModel;
    private FragmentMainBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(WeatherDataViewModel.class);
        this.setupUi();
        this.setupListeners();
    }

    private void setupListeners(){
        binding.btCheckWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate();
            }
        });
        binding.btLoadWeather.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                binding.btLoadWeather.setVisibility(View.GONE);
                loadData();
            }
        });
    }

    private void setupUi(){

        binding.btLoadWeather.setVisibility(View.VISIBLE);
    }
    private void loadData(){

        String urlPath = "1100661/"+getYesterdayDateString();
        viewModel.loadWeatherData(urlPath).observe(this,data->{

            if(data.getStatus() == Resource.Status.SUCCESS){

                if(data.getData().size() > 0){
                    binding.pgLoading.setVisibility(View.GONE);
                    binding.btLoadWeather.setVisibility(View.GONE);
                    binding.btCheckWeather.setVisibility(View.VISIBLE);
                }
            }
            else if(data.getStatus() == Resource.Status.ERROR){
                binding.btLoadWeather.setVisibility(View.VISIBLE);
                binding.pgLoading.setVisibility(View.GONE);
                CookieBar.build(requireActivity())
                        .setTitle("Error")
                        .setMessage("Please try again later")
                        .setSwipeToDismiss(true)
                        .setCookiePosition(CookieBar.BOTTOM)
                        .show();
            }
            else if(data.getStatus() == Resource.Status.LOADING){
                binding.btLoadWeather.setVisibility(View.GONE);
                binding.pgLoading.setVisibility(View.VISIBLE);
            }
        });
    }

    private void navigate(){

        Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_mainFragment_to_weatherLoadFragment);
    }

    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }

}
