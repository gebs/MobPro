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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RadioButton rbll;
    RadioButton rbrl;
    Button btnShowLayoutPage;
    Spinner spInformatikkurs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        switch (getResources().getConfiguration().orientation){
            case Configuration.ORIENTATION_PORTRAIT:
                setContentView(R.layout.activity_main);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                setContentView(R.layout.activity_main_land);
                break;
        }


        rbll = (RadioButton)this.findViewById(R.id.rbLinearlayout);
        rbrl = (RadioButton)this.findViewById(R.id.rbRelativeLayout);
        btnShowLayoutPage = (Button)this.findViewById(R.id.btnshowlayoutsites);
        spInformatikkurs = (Spinner)this.findViewById(R.id.spInformatikKurs);

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



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.media_names));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInformatikkurs.setAdapter(adapter);

    }
    private void openViewDemo(){
        Intent in = new Intent(this,ViewsDemoActivity.class);
        startActivity(in);
    }
    public void openLayoutDemoActivity(boolean isLinearlayout){
        Intent in = new Intent(this,LayoutDemoActivity.class);
        if (isLinearlayout){

            in.putExtra("Layout","LinearLayout");

        }else{
            in.putExtra("Layout","RelativeLayout");
        }
        startActivity(in);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater mi =getMenuInflater();
        mi.inflate(R.menu.main_menu,menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (super.onOptionsItemSelected(menuItem)) {
            return true;
        }

        switch (menuItem.getItemId()){

        }

        return true;
    }
}
