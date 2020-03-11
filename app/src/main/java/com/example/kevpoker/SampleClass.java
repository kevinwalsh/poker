package com.example.kevpoker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SampleClass{

    String property1 = "initial testProperty, not used";

    public SampleClass(){
        property1 = "poker testProperty";
    }

    public String getMySampleProperty() {
        return property1;
    }
}
