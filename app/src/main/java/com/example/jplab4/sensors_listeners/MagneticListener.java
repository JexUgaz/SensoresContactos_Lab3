package com.example.jplab4.sensors_listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.example.jplab4.ConfigGlobal;
import com.example.jplab4.view_models.AppViewModel;

public class MagneticListener implements SensorEventListener {
    AppViewModel appViewModel;
    public  MagneticListener(AppViewModel appViewModel){
        this.appViewModel=appViewModel;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
            float magneticX = event.values[0];
            float magneticY = event.values[1];

            magneticX = -magneticX;//90 grados en sentido horario
            magneticY = -magneticY;

            float newX = -magneticY;
            float newY = magneticX;

            double radianes = Math.atan2(newY, newX);
            double grados = radianes * (180 / Math.PI);
            if (Math.abs(grados) <= (180*5/100)) {//Menor al 5%
                appViewModel.getParamMagnet().setValue(ConfigGlobal.TYPE_MAGNET0);
            } else if (Math.abs(grados) <= (180*25/100)) {//Menor al 25%
                appViewModel.getParamMagnet().setValue(ConfigGlobal.TYPE_MAGNET25);
            }else if (Math.abs(grados) <= (180*50/100)) {//Menor al 50%
                appViewModel.getParamMagnet().setValue(ConfigGlobal.TYPE_MAGNET50);
            }else if (Math.abs(grados) <= (180*75/100)) {//Menor al 75%
                appViewModel.getParamMagnet().setValue(ConfigGlobal.TYPE_MAGNET75);
            }else if (Math.abs(grados) <= (180*90/100)) {//Menor al 90%
                appViewModel.getParamMagnet().setValue(ConfigGlobal.TYPE_MAGNET90);
            }else{
                appViewModel.getParamMagnet().setValue(ConfigGlobal.TYPE_MAGNET100);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
