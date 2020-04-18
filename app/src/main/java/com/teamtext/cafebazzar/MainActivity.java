package com.teamtext.cafebazzar;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.teamtext.cafe.CafeBazzar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CafeBazzar cafeBazzar=new CafeBazzar(this,"");
    }
}
