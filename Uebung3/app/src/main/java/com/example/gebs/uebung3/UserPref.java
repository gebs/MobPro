package com.example.gebs.uebung3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UserPref extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new UserPrefFragment()).commit();
    }



}
