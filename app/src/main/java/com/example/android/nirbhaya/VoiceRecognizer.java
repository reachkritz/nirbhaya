package com.example.android.nirbhaya;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class VoiceRecognizer extends Activity implements TextToSpeech.OnInitListener {

    TextToSpeech tts;
    Button startRecognizer;
    Spinner spinnerResult;

    private static final int RQS_RECOGNITION=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recognizer);
        startRecognizer =(Button)findViewById(R.id.startrecognizer);
        startRecognizer.setEnabled(false);
        spinnerResult=(Spinner)findViewById(R.id.result);
        tts=new TextToSpeech(this,this);

    }

        public void Click(View v) {
            Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speech to recognise");
            startActivityForResult(i,RQS_RECOGNITION);
        }




    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        int i;
        String s="";
        if((requestCode==RQS_RECOGNITION) & (resultCode==RESULT_OK))
        {
            ArrayList <String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ArrayAdapter <String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,result);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerResult.setAdapter(adapter);
            spinnerResult.setOnItemSelectedListener(spinnerResultOnItemSelectedListener);
             for(i=0;i<result.size();i++)
             {
                 s=result.get(i);
                 if(s.equalsIgnoreCase("help")||s.equalsIgnoreCase("help me")||s.equalsIgnoreCase("danger"))
                 {
                     Intent j=new Intent(this,WidgetActivity.class);
                     startActivity(j);
                 }
             }

        }
    }
    private Spinner.OnItemSelectedListener spinnerResultOnItemSelectedListener=new Spinner.OnItemSelectedListener(){


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedResult=parent.getItemAtPosition(position).toString();
            Toast.makeText(VoiceRecognizer.this,selectedResult,Toast.LENGTH_SHORT).show();
            tts.speak(selectedResult,TextToSpeech.QUEUE_ADD,null);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
     @Override
    public void onInit(int arg0)
     {
         startRecognizer.setEnabled(true);
     }


}
