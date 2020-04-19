package com.teamtext.cafebazzar;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.teamtext.cafe.CafeBazaar;
import com.teamtext.cafe.CafeBazaarInterface;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CafeBazaar bazaar=new CafeBazaar(this, "", new CafeBazaarInterface() {
            @Override
            public void ErrorSetupIabHelper(Exception error) {

            }

            @Override
            public void ErrorLaunch() {

            }
        });
        CafeBazaar cafeBazaar =new CafeBazaar(this,"");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
