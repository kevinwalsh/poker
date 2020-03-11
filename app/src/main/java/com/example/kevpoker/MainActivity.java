package com.example.kevpoker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.hi);
        SampleClass sc = new SampleClass();
        tv.setText("changed text, \n and used sampleclass \n text "+sc.getMySampleProperty());
    }
}
