package com.example.gebs.uebung2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LayoutDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String layout = getIntent().getStringExtra("Layout");
        if (layout.equals("LinearLayout")){
            setContentView(R.layout.linearlayout);
        }else{
            setContentView(R.layout.relativelayout);
        }
    }
}
