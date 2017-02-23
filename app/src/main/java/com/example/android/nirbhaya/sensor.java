package com.example.android.nirbhaya;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class sensor extends AppCompatActivity implements SensorEventListener {
    SensorManager mSensorManager;
    Sensor mSensor;
    float gravity[];

    float x[];
    boolean flag=false;
    MediaPlayer  sir1=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        gravity=new float[4];
        ImageButton btn= (ImageButton)findViewById(R.id.safe);

        x=new float[4];
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor , SensorManager.SENSOR_DELAY_NORMAL);
        sir1 =MediaPlayer.create(sensor.this, R.raw.siren1);

       /* sir.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                flag=true;

            }
        });
   */
    // sir.prepareAsync();

    }
    public void markSafe(View v)
    {
        sir1.stop();
        sir1.release();
        sir1 =MediaPlayer.create(sensor.this, R.raw.siren1);

    }
    public void onSensorChanged(SensorEvent event){
        // In this example, alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.
      //  sir1 =MediaPlayer.create(this, R.raw.siren1);
        final float alpha = 0.8f;

        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = 0;
        gravity[1] = 0;
        gravity[2] = 9.81f;

        // Remove the gravity contribution with the high-pass filter.
       x[0] = event.values[0] - gravity[0];
        x[1] = event.values[1] - gravity[1];
        x[2] = event.values[2] - gravity[2];
        int sum=(int)Math.sqrt(x[2]*x[2]);
        Toast.makeText(sensor.this,""+sum+"$$"+x[0]+"$$"+x[1]+"$$"+x[2],Toast.LENGTH_SHORT).show();
       if(sum>7)
       {
     //      if(flag==true) {
               //sir.prepareAsync();

         sir1.setLooping(true);

           sir1.start();


              
    //       }

          // finish();
       }
        else
       {
         Toast.makeText(sensor.this,"landed",Toast.LENGTH_SHORT).show();
          // if(sir1.isPlaying())
           //sir1.stop();
       }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


}