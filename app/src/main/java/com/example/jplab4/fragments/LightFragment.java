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

import com.example.jplab4.R;
import com.example.jplab4.adapters.ListaContactsAdapter;
import com.example.jplab4.databinding.FragmentLightBinding;
import com.example.jplab4.sensors_listeners.AccListener;
import com.example.jplab4.view_models.AppViewModel;

import java.util.ArrayList;

public class LightFragment extends Fragment {
    FragmentLightBinding binding;
    SensorManager sensorManager;
    Sensor accelerometer;
    AccListener accListener;
    Observer<Boolean> shakeActObserver;
    AppViewModel appViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentLightBinding.inflate(inflater,container,false);
        sensorManager=(SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager!=null){
            accelerometer= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            appViewModel= new ViewModelProvider(requireActivity()).get(AppViewModel.class);
            accListener= new AccListener(appViewModel,requireActivity());
            appViewModel.getInProxFragment().setValue(false);

            shakeActObserver= shakeAct -> {
                binding.recyclerViewLight.smoothScrollBy(0,400);
            };
            appViewModel.getShakeAct().observe(requireActivity(),shakeActObserver);
        }

        ListaContactsAdapter adapter= new ListaContactsAdapter(new ArrayList<>(),requireActivity());
        binding.recyclerViewLight.setAdapter(adapter);
        binding.recyclerViewLight.setLayoutManager(new LinearLayoutManager(requireActivity()));
        appViewModel.getContactsGlobal2().observe(requireActivity(), contacts -> {
            adapter.setContacts(contacts);
            adapter.notifyDataSetChanged();
        });
        return binding.getRoot();
    }
    @Override
    public void onResume() {
        super.onResume();
        if(accelerometer!=null){
            sensorManager.registerListener(accListener,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(accelerometer!=null){
            sensorManager.unregisterListener(accListener);
            appViewModel.getShakeAct().removeObserver(shakeActObserver);
        }
    }
}