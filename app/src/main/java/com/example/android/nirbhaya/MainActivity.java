package com.example.android.nirbhaya;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;

public class MainActivity extends BaseActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
//SMS VARIABLES
    ImageButton buttonSend;
    GoogleApiClient mGoogleApiClient;
    public static final String PREFS_NAME = "MyApp_Settings";
    //SMS VARIABLES END
    private boolean on = false;
    int count=0;
    Camera mCamera;
    Camera.Parameters mParams;
    int delay = 100; // in ms
    private ImageButton button;
  MediaPlayer sir=null;
  int countsiren=0;
Location location;

    AlphaAnimation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         anim = new AlphaAnimation(1.0f, 0.3f);
        anim.setDuration(1);
        //anim.setRepeatCount(NUM_REPEATS);
        anim.setRepeatMode(Animation.REVERSE);
        ImageButton b1 = (ImageButton) findViewById(R.id.button1);
        b1.setOnClickListener(this);
         sir =MediaPlayer.create(this, R.raw.siren1);
        ImageButton b2 = (ImageButton) findViewById(R.id.button7);
        b2.setOnClickListener(this);
        ImageButton b3 = (ImageButton) findViewById(R.id.button5);
        b3.setOnClickListener(this);
        final SharedPreferences settings = getSharedPreferences("state", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        String cont=settings.getString("count", "");
        if(cont.isEmpty()) {
            editor.putString("count", "0");
            editor.apply();
        }
        editor.commit();

        buttonSend = (ImageButton) findViewById(R.id.button3);
        buttonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                buttonSend.startAnimation(anim);
                String count=settings.getString("count","");
                if(count.isEmpty()|| count.equals("0")) {
                    Toast.makeText(MainActivity.this,"No emergency contacts"+"$$"+count,Toast.LENGTH_LONG).show();
                }
                else
                {
                  int cc=Integer.parseInt(count);
                  //  Toast.makeText(MainActivity.this,"cc:"+"$$"+cc,Toast.LENGTH_LONG).show();
                for (int i = 1; i <= cc; i++) {
                    String phoneNo = settings.getString("" + i, "");
                    StringBuffer sms = new StringBuffer();
                   // StringBuffer smsBody = new StringBuffer();
                    GPSTracker obj=new GPSTracker(MainActivity.this);
                    location=obj.getLocation();
                    if(location==null)
                    {
                        sms.append("null\n");
                    }
                    sms.append("HELP !!! I'm in danger Track me : ");
                    sms.append("http://maps.google.com/maps?q=");
                    sms.append(location.getLatitude());
                    sms.append(",");
                    sms.append(location.getLongitude());

                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo, null, sms.toString(), null, null);

                        Toast.makeText(getApplicationContext(), "SMS Sent!",
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "SMS failed, please try again later!",
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }}}
            }
        });
        //Flash Light
        button = (ImageButton) findViewById(R.id.button4);


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                button.startAnimation(anim);
                count++;
                if (count % 3==2) {
                    Thread t = new Thread() {
                        public void run() {
                            try {
                                // Switch on the cam for app's life
                                if (mCamera == null) {
                                    // Turn on Cam
                                    mCamera = Camera.open();
                                    try {
                                        mCamera.setPreviewDisplay(null);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    mCamera.startPreview();
                                }
                                int times = 10;

                                for (int i = 0;; i++) {
                                    toggleFlashLight();
                                    sleep(delay);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    t.start();
                }
                else if(count%3==0)
                {
                   turnOff();
                    if (mCamera != null) {
                        mCamera.stopPreview();
                        mCamera.release();
                        mCamera = null;
                    }
                    count=0;
                }
                else if(count%3==1) {
                    mCamera = Camera.open();
                    on = false;
                    toggleFlashLight();
                }

            }

        });
    }
/** Turn the devices FlashLight on */
        public void turnOn() {
            if (mCamera != null) {
                mParams = mCamera.getParameters();
                mParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(mParams);

                on = true;
            }
        }
/** Turn the devices FlashLight off */
        public void turnOff() {
            // Turn off flashlight
            if (mCamera != null) {
                mParams = mCamera.getParameters();
                if (mParams.getFlashMode().equals(Camera.Parameters.FLASH_MODE_TORCH)) {
                    mParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    mCamera.setParameters(mParams);
                }
            }
            on = false;
        }

/** Toggle the flashlight on/off status */
        public void toggleFlashLight() {
            if (!on) { // Off, turn it on
                turnOn();
            } else { // On, turn it off
                turnOff();
            }
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.selfHelp:
                // Red item was selected
                Intent i=new Intent(MainActivity.this,selfHelping.class);
                startActivity(i);
                return true;
            case R.id.About:
                // Green item was selected
                Intent j=new Intent(MainActivity.this,sensor.class);
                startActivity(j);
                return true;
            case R.id.Voice:
                Intent k=new Intent(MainActivity.this,VoiceRecognizer.class);
                startActivity(k);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.button1:
                v.startAnimation(anim);
                Intent i = new Intent(this, gps.class);
                startActivity(i);
                break;
            case R.id.button7:
                v.startAnimation(anim);
                if(sir.isPlaying() == true&&sir!=null) {
                    sir.stop();
                }
                else{
                    sir =MediaPlayer.create(this, R.raw.siren1);
                    sir.setLooping(true);
                    //set volume
                    AudioManager audio=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    audio.setStreamVolume(AudioManager.STREAM_MUSIC, audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

                    sir.start();
                    }
                     break;
            case R.id.button5:
                v.startAnimation(anim);
                    Intent i1=new Intent(this,rec1.class);
                    startActivity(i1);
                    finish();
                  break;
            default:
                Toast.makeText(this,"No button clicked",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
