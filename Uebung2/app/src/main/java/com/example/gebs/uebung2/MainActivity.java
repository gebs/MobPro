package com.example.gebs.uebung2;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.MenuItemHoverListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.Duration;

public class MainActivity extends AppCompatActivity {

    RadioButton rbll;
    RadioButton rbrl;
    Button btnShowLayoutPage;
    Button btnInternalCounter;
    Spinner spInformatikkurs;
    int counter = 0;
    final String KEY_COUNTER = "KEYCOUNTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*Switch between Portrait and Landscape Layout*/
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                setContentView(R.layout.activity_main);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                setContentView(R.layout.activity_main_land);
                break;
        }

        //Get Controls
        rbll = (RadioButton) this.findViewById(R.id.rbLinearlayout);
        rbrl = (RadioButton) this.findViewById(R.id.rbRelativeLayout);
        btnShowLayoutPage = (Button) this.findViewById(R.id.btnshowlayoutsites);
        spInformatikkurs = (Spinner) this.findViewById(R.id.spInformatikKurs);
        btnInternalCounter = (Button) this.findViewById(R.id.btnInternalCounter);


        /*Set Events*/
        rbll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLayoutDemoActivity(true);
            }
        });
        rbrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLayoutDemoActivity(false);
            }
        });
        btnShowLayoutPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewDemo();
            }
        });
        btnInternalCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internalCounter();
            }
        });


        /*Init Adapter and fill Spinner*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.media_names));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInformatikkurs.setAdapter(adapter);

    }
    

    private void internalCounter() {
        counter++;
        Toast.makeText(this, "Zähler = " + counter, Toast.LENGTH_SHORT).show();
    }

    private void openViewDemo() {
        Intent in = new Intent(this, ViewsDemoActivity.class);
        startActivity(in);
    }

    public void openLayoutDemoActivity(boolean isLinearlayout) {
        Intent in = new Intent(this, LayoutDemoActivity.class);
        if (isLinearlayout) {

            in.putExtra("Layout", "LinearLayout");

        } else {
            in.putExtra("Layout", "RelativeLayout");
        }
        startActivity(in);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_COUNTER, counter);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstantState) {
        super.onRestoreInstanceState(savedInstantState);
        counter = savedInstantState.getInt(KEY_COUNTER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.main_menu, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (super.onOptionsItemSelected(menuItem)) {
            return true;
        }

        switch (menuItem.getItemId()) {

        }

        return true;
    }
}
