package com.example.android.nirbhaya;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Saloni_Juneja on 9/9/2016.
 */
public class BaseActivity extends ActionBarActivity {

    AlertDialog.Builder builder;
    SharedPreferences sharedpref ;
    SharedPreferences.Editor editor;
    static final int PICK_CONTACT=1;
    String name,number;
    int countt;
    int tt;
    protected void onPostCreate(Bundle savedState) {
        super.onPostCreate(savedState);
        sharedpref= getSharedPreferences("state", MODE_PRIVATE);
        editor=sharedpref.edit();
        String count=sharedpref.getString("count","");
        if(count.isEmpty()) {
            editor.putString("count", "0");
            editor.apply();
        }
        if(count.equals("0"))
        {
            View b = findViewById(R.id.remove);
            b.setEnabled(false);
            View c = findViewById(R.id.con1);
            c.setVisibility(View.GONE);
            View d = findViewById(R.id.con2);
            d.setVisibility(View.GONE);
            View e = findViewById(R.id.con3);
            e.setVisibility(View.GONE);
            View f = findViewById(R.id.con4);
            f.setVisibility(View.GONE);
            View g = findViewById(R.id.con5);
            g.setVisibility(View.GONE);
        }
        else if(count.equals("1"))
        {
            View b = findViewById(R.id.remove);
            b.setEnabled(true);
            View c = findViewById(R.id.con1);
            c.setVisibility(View.VISIBLE);
            String s1=sharedpref.getString("11","");
            Button b1=(Button)findViewById(R.id.con1);
            b1.setText(s1);
            View d = findViewById(R.id.con2);
            d.setVisibility(View.GONE);
            View e = findViewById(R.id.con3);
            e.setVisibility(View.GONE);
            View f = findViewById(R.id.con4);
            f.setVisibility(View.GONE);
            View g = findViewById(R.id.con5);
            g.setVisibility(View.GONE);
        }
        else if(count.equals("2"))
        {
            View c = findViewById(R.id.con1);
            c.setVisibility(View.VISIBLE);
            String s1=sharedpref.getString("11","");
            Button b1=(Button)findViewById(R.id.con1);
            b1.setText(s1);
            View d = findViewById(R.id.con2);
            d.setVisibility(View.VISIBLE);
            String s2=sharedpref.getString("22","");
            Button b2=(Button)findViewById(R.id.con2);
            b2.setText(s2);

            View e = findViewById(R.id.con3);
            e.setVisibility(View.GONE);
            View f = findViewById(R.id.con4);
            f.setVisibility(View.GONE);
            View g = findViewById(R.id.con5);
            g.setVisibility(View.GONE);
        }
       else if(count.equals("3"))
        {
            View c = findViewById(R.id.con1);
            c.setVisibility(View.VISIBLE);
            String s1=sharedpref.getString("11","");
            Button b1=(Button)findViewById(R.id.con1);
            b1.setText(s1);
            View d = findViewById(R.id.con2);
            d.setVisibility(View.VISIBLE);
            String s2=sharedpref.getString("22","");
            Button b2=(Button)findViewById(R.id.con2);
            b2.setText(s2);
            View e = findViewById(R.id.con3);
            e.setVisibility(View.VISIBLE);
            String s3=sharedpref.getString("33","");
            Button b3=(Button)findViewById(R.id.con3);
            b3.setText(s3);
            View f = findViewById(R.id.con4);
            f.setVisibility(View.GONE);
            View g = findViewById(R.id.con5);
            g.setVisibility(View.GONE);
        }
       else if(count.equals("4"))
        {
            View c = findViewById(R.id.con1);
            c.setVisibility(View.VISIBLE);
            String s1=sharedpref.getString("11","");
            Button b1=(Button)findViewById(R.id.con1);
            b1.setText(s1);
            View d = findViewById(R.id.con2);
            d.setVisibility(View.VISIBLE);
            String s2=sharedpref.getString("22","");
            Button b2=(Button)findViewById(R.id.con2);
            b2.setText(s2);
            View e = findViewById(R.id.con3);
            e.setVisibility(View.VISIBLE);
            String s3=sharedpref.getString("33","");
            Button b3=(Button)findViewById(R.id.con3);
            b3.setText(s3);
            View f = findViewById(R.id.con4);
            f.setVisibility(View.VISIBLE);
            String s4=sharedpref.getString("44","");
            Button b4=(Button)findViewById(R.id.con4);
            b4.setText(s4);
            View g = findViewById(R.id.con5);
            g.setVisibility(View.GONE);
        }

        if(sharedpref.getString("count","").equals("5"))
        {
            View b = findViewById(R.id.add);
            b.setEnabled(false);
        }


        if(!count.isEmpty()) {
             countt = Integer.parseInt(count);
        }
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countt++;
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);

                editor.putString("count", countt + "");
                editor.apply();

                //startActivity(new Intent(BaseActivity.this, DayActivity.class));

            }
        });

        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(BaseActivity.this);


                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
               // dialog.setTitle("Contacts:");
                dialog.setContentView(R.layout.radiobutton_dialog);
                List<String> stringList=new ArrayList<>();  // here is list
                for(int i=1;i<=countt;i++) {

                    stringList.add(i + "." + sharedpref.getString(i + ""+i,""));
                }
                RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);

                for(int i=0;i<stringList.size();i++){
                    RadioButton rb=new RadioButton(BaseActivity.this); // dynamically creating RadioButton and adding to RadioGroup.
                    rb.setText(stringList.get(i));
                    rg.addView(rb);
                }
                  tt=5;
                dialog.show();
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        int childCount = group.getChildCount();
                        for (int x = 0; x < childCount; x++) {
                            RadioButton btn = (RadioButton) group.getChildAt(x);
                            if (btn.getId() == checkedId) {
                                String ss = btn.getText().toString();
                                tt = Integer.parseInt(ss.substring(0, 1));

                            }
                        }
                        dialog.dismiss();

                     //   Toast.makeText(BaseActivity.this, tt + "", Toast.LENGTH_LONG).show();
                        sharedpref.edit().remove("" + tt).commit();
                        sharedpref.edit().remove(tt+""+tt).commit();
                        int i = tt + 1;
                        editor = sharedpref.edit();
                        Button a = (Button) findViewById(R.id.con1);
                        Button b = (Button) findViewById(R.id.con2);
                        Button c = (Button) findViewById(R.id.con3);
                        Button d = (Button) findViewById(R.id.con4);
                        Button e = (Button) findViewById(R.id.con5);
                        View l = findViewById(R.id.con1);
                        View m = findViewById(R.id.con2);
                        View n = findViewById(R.id.con3);
                        View o = findViewById(R.id.con4);
                        View p = findViewById(R.id.con5);
                        while (i <= countt) {
                            String kr = sharedpref.getString("" + i, "");
                            String kd = sharedpref.getString(i + "" + i, "");
                            sharedpref.edit().remove(""+i).commit();
                            sharedpref.edit().remove(i+""+i).commit();
                            i--;
                            editor.putString("" + i, kr);
                            editor.putString(i + "" + i, kd);

                            switch (++i) {
                                case 2:
                                    a.setText(b.getText());
                                    break;
                                case 3:
                                    b.setText(c.getText());
                                    break;
                                case 4:
                                    c.setText(d.getText());
                                    break;
                                case 5:
                                    d.setText(e.getText());
                                    break;

                            }
                            i++;
                        }
                        switch (i) {
                            case 6:
                                p.setVisibility(View.GONE);
                                break;
                            case 5:
                                o.setVisibility(View.GONE);
                                break;
                            case 4:
                                n.setVisibility(View.GONE);
                                break;
                            case 3:
                                m.setVisibility(View.GONE);
                                break;
                            case 2:
                                l.setVisibility(View.GONE);
                                break;
                        }
                        countt--;
                        if (countt == 4) {
                            View abc = findViewById(R.id.add);
                            abc.setEnabled(true);
                        }
                        if (countt == 0) {
                            View abc = findViewById(R.id.remove);
                            abc.setEnabled(false);


                        }

                        editor.putString("count", countt + "");
                        editor.apply();
                        String ax=sharedpref.getString(countt+""+countt,"");
                     //   Toast.makeText(BaseActivity.this,"count:"+countt+" "+ax,Toast.LENGTH_LONG).show();

                    }
                });
                //startActivity(new Intent(BaseActivity.this, DayActivity.class));

            }
        });

    }
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            number = phones.getString(phones.getColumnIndex("data1"));
                          //  Toast.makeText(this, "number is:" + number + " count: " + countt, Toast.LENGTH_LONG).show();
                            name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                           // Toast.makeText(this, name, Toast.LENGTH_LONG).show();
                          //  countt++;
                            switch (countt) {
                                case 1:
                                    View h = findViewById(R.id.con1);
                                    h.setVisibility(View.VISIBLE);
                                    editor.putString("" + countt, number);
                                    editor.putString(countt+""+countt, name);
                                    Button b1 = (Button) findViewById(R.id.con1);
                                    b1.setText(name);
                                    break;
                                case 2:
                                    View i = findViewById(R.id.con2);
                                    i.setVisibility(View.VISIBLE);
                                    editor.putString(countt + "", number);
                                    editor.putString(countt+""+countt, name);
                                    Button b2 = (Button) findViewById(R.id.con2);
                                    b2.setText(name);
                                    break;
                                case 3:
                                    View j = findViewById(R.id.con3);
                                    j.setVisibility(View.VISIBLE);
                                    editor.putString(countt + "", number);
                                    editor.putString(countt+""+countt, name);
                                    Button b3 = (Button) findViewById(R.id.con3);
                                    b3.setText(name);
                                    break;
                                case 4:
                                    View k = findViewById(R.id.con4);
                                    k.setVisibility(View.VISIBLE);
                                    editor.putString(countt + "", number);
                                    editor.putString(countt+""+countt, name);
                                    Button b4 = (Button) findViewById(R.id.con4);
                                    b4.setText(name);
                                    break;
                                case 5:
                                    View l = findViewById(R.id.con5);
                                    l.setVisibility(View.VISIBLE);
                                    editor.putString(countt + "", number);
                                    editor.putString(countt+""+countt, name);
                                    Button b5 = (Button) findViewById(R.id.con5);
                                    b5.setText(name);
                                    break;

                            }
                            editor.apply();
                        }


                    }
                }
                break;
        }
        if (countt == 5) {
            View abc = findViewById(R.id.add);
            abc.setEnabled(false);
        }
        if (countt > 0) {
            View abc = findViewById(R.id.remove);
            abc.setEnabled(true);
        }
    }

}
