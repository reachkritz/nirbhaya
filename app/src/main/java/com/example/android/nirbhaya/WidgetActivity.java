package com.example.android.nirbhaya;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WidgetActivity extends AppCompatActivity {
MediaPlayer sir2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget2);
        sir2 =MediaPlayer.create(this, R.raw.siren1);
        sir2.setLooping(true);
        sir2.start();
    }
    public void stop(View v)
    {
        sir2.stop();
        sir2.release();
        finish();
    }
}
