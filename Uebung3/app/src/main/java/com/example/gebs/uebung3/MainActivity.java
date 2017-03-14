package com.example.gebs.uebung3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private final String COUNTER_KEY = "CounterKey";
    private TextView lblResCounter;
    private Button btnEditPrefs;
    private TextView lblPrefs;
    private Button btnDefaultPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblResCounter = (TextView) findViewById(R.id.txtResumeText);
        btnEditPrefs = (Button) findViewById(R.id.btnEditPrefs);
        btnDefaultPrefs = (Button) findViewById(R.id.btnDefaultPrefs);
        lblPrefs = (TextView) findViewById(R.id.lblLieblingsTee);

        btnEditPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPrefActivity();
            }
        });
        btnDefaultPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultPrefs();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        final SharedPreferences pref = getPreferences(MODE_PRIVATE);
        final int resCount = pref.getInt(COUNTER_KEY, 0) + 1;
        final SharedPreferences.Editor editor = pref.edit();
        editor.putInt(COUNTER_KEY, resCount);
        editor.apply();
        lblResCounter.setText("MainActivity.onResume() wurde seit der Installation dieser App " + resCount + " mal aufgerufen");
        setPrefString();
    }

    private void setPrefString() {

        final SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(this);

        String myText = "Ich trinke am liebsten " + pref2.getString("teaPreffered", "Hopfentee");

        if (pref2.getBoolean("teaWithSugar", true)) {
            int i = Arrays.asList(getResources().getStringArray(R.array.teaSweetenerValues)).indexOf(pref2.getString("teaSweetener1", "natural"))-1;
            myText += " mit " + getResources().getStringArray(R.array.teaSweetener)[i];
            myText += " gesüsst";
        } else {
            myText += " ungesüsst";
        }

        lblPrefs.setText(myText);
    }

    private void setDefaultPrefs() {
        final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString("teaPreffered", "Hopfentee");
        editor.putString("teaSweetener1", "natural");
        editor.putBoolean("teaWithSugar", true);
        editor.apply();

        lblPrefs.setText("Ich trinke am liebsten Hopfentee mit Rohrzucker gseüsst");
    }

    private void startPrefActivity() {
        Intent myint = new Intent(this, UserPref.class);
        startActivity(myint);
    }
}
