package com.example.surfaceviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.surfaceviewtest.view.MySurfaceView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(new MySurfaceView(this));
    }
}
