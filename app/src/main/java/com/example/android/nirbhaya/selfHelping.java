package com.example.android.nirbhaya;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class selfHelping extends AppCompatActivity {
ViewPager viewPager;
    CustomSwipeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        setContentView(R.layout.activity_self_helping);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter= new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);
    }
}
