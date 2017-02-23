package com.example.android.nirbhaya;

import java.io.File;
import java.io.IOException;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.widget.Button;
import android.view.View;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.android.nirbhaya.R;

public class rec1 extends ActionBarActivity {
    int i;
    SharedPreferences sharedpref ;
    SharedPreferences.Editor editor;
    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mediaPlayer;

    private static String audioFilePath;
    private static Button stopButton;
    private static Button playButton;
    private static Button recordButton;

    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec1);
        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/NirbhayaAudio");
        folder.mkdirs();


        if(folder.exists()){
            Toast.makeText(getApplicationContext(), "exists", Toast.LENGTH_LONG).show();
        }else{

            Toast.makeText(getApplicationContext()," not exists",Toast.LENGTH_LONG).show();
        }
        recordButton = (Button) findViewById(R.id.record);
        playButton = (Button) findViewById(R.id.play);
        stopButton = (Button) findViewById(R.id.stop);

        if (!hasMicrophone()) {
            stopButton.setEnabled(false);
            playButton.setEnabled(false);
            recordButton.setEnabled(false);
        } else {
            playButton.setEnabled(false);
            stopButton.setEnabled(false);
        }


        sharedpref= getSharedPreferences("state", MODE_PRIVATE);
        editor=sharedpref.edit();
        String count=sharedpref.getString("countAudio","");
        if(count.isEmpty()) {
            editor.putString("countAudio", "0");
            i=0;
            editor.apply();
        }
        else
        {
            i=Integer.parseInt(count);
        }

    }

    protected boolean hasMicrophone() {
        PackageManager pmanager = this.getPackageManager();
        return pmanager.hasSystemFeature(
                PackageManager.FEATURE_MICROPHONE);
    }
    public void recordAudio (View view) throws IOException
    {
        audioFilePath =
                Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/NirbhayaAudio"+"/myaudio"+i+".3gp";
        isRecording = true;
        stopButton.setEnabled(true);
        playButton.setEnabled(false);
        recordButton.setEnabled(false);

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
           i++;
           editor.putString("countAudio",""+i);
        editor.apply();
        mediaRecorder.start();
    }
    public void stopAudio (View view)
    {

        stopButton.setEnabled(false);
        playButton.setEnabled(true);

        if (isRecording)
        {
            recordButton.setEnabled(false);
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
        } else {
            mediaPlayer.release();
            mediaPlayer = null;
            recordButton.setEnabled(true);
        }
    }
    public void playAudio (View view) throws IOException
    {
        playButton.setEnabled(false);
        recordButton.setEnabled(false);
        stopButton.setEnabled(true);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(audioFilePath);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }
}