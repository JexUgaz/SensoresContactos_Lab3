package com.example.jplab4.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jplab4.ConfigGlobal;
import com.example.jplab4.R;
import com.example.jplab4.adapters.ListaContactsAdapter;
import com.example.jplab4.databinding.FragmentProxBinding;
import com.example.jplab4.sensors_listeners.AccListener;
import com.example.jplab4.sensors_listeners.MagneticListener;
import com.example.jplab4.view_models.AppViewModel;

import java.util.ArrayList;

public class ProxFragment extends Fragment {
    FragmentProxBinding binding;
    SensorManager sensorManager;
    Sensor accelerometer;
    Sensor magneticSensor;
    AccListener accListener;
    MagneticListener magneticListener;
    AppViewModel appViewModel;
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentProxBinding.inflate(inflater,container,false);
        sensorManager=(SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager!=null){
            accelerometer= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magneticSensor=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

            appViewModel= new ViewModelProvider(requireActivity()).get(AppViewModel.class);
            accListener= new AccListener(appViewModel,requireActivity());
            magneticListener= new MagneticListener(appViewModel);
        }
        appViewModel.getInProxFragment().setValue(true);
        ListaContactsAdapter adapter= new ListaContactsAdapter(new ArrayList<>(),requireActivity());
        binding.recyclerViewProx.setAdapter(adapter);
        binding.recyclerViewProx.setLayoutManager(new LinearLayoutManager(requireActivity()));
        appViewModel.getContactsGlobal().observe(requireActivity(), contacts -> {
            adapter.setContacts(contacts);
            adapter.notifyDataSetChanged();
        });

        appViewModel.getParamMagnet().observe(requireActivity(), param -> {
            if(param== ConfigGlobal.TYPE_MAGNET0){
                binding.recyclerViewProx.setAlpha(1.0f);
            }else if (param==ConfigGlobal.TYPE_MAGNET25){
                binding.recyclerViewProx.setAlpha(0.75f);
            }else if (param==ConfigGlobal.TYPE_MAGNET50){
                binding.recyclerViewProx.setAlpha(0.5f);
            }else if (param==ConfigGlobal.TYPE_MAGNET75){
                binding.recyclerViewProx.setAlpha(0.25f);
            }else if (param==ConfigGlobal.TYPE_MAGNET90){
                binding.recyclerViewProx.setAlpha(0.1f);
            }else{
                binding.recyclerViewProx.setAlpha(0f);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(magneticSensor!=null){
            sensorManager.registerListener(magneticListener,magneticSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(magneticSensor!=null){
            sensorManager.unregisterListener(magneticListener);
        }
    }
}