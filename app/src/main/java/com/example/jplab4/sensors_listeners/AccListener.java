package com.example.jplab4.sensors_listeners;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.Toast;

import com.example.jplab4.view_models.AppViewModel;

import java.text.DecimalFormat;

public class AccListener implements SensorEventListener {
    private static final float SHAKE_THRESHOLD=15.0f;// m/s^2
    private AppViewModel appViewModel;
    private Context context;
    private Long lastShakeTime=0L;
    private static final DecimalFormat df= new DecimalFormat("#.##");

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public AccListener(AppViewModel appViewModel,Context context){
        this.appViewModel=appViewModel;
        this.context=context;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            long currentTime= System.currentTimeMillis();
            float x= event.values[0];
            float y= event.values[1];
            float z= event.values[2];

            double acceleration= Math.sqrt(x*x+y*y+z*z);
            if(acceleration>SHAKE_THRESHOLD && (currentTime-lastShakeTime)>2000){
                Toast.makeText(context,"Su velocidad: "+df.format(acceleration)+" m/s^2",Toast.LENGTH_SHORT).show();
                appViewModel.getShakeAct().setValue(true);
                lastShakeTime=currentTime;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
