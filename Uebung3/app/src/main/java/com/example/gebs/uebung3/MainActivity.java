package com.example.gebs.uebung3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.jar.Manifest;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private final String COUNTER_KEY = "CounterKey";
    private TextView lblResCounter;
    private Button btnEditPrefs;
    private TextView lblPrefs;
    private Button btnDefaultPrefs;
    private final String filename = "MyInternalFile";
    private TextView lblextStorage;
    private Button btnSaveTextFile;
    private Button btnLoadTextFile;
    private EditText txtFileText;
    private TextView lblFileContent;
    private CheckBox ckbUseExternalStorage;
    private Button btnCreateNote;
    private Button btnShowNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblResCounter = (TextView) findViewById(R.id.txtResumeText);
        btnEditPrefs = (Button) findViewById(R.id.btnEditPrefs);
        btnDefaultPrefs = (Button) findViewById(R.id.btnDefaultPrefs);
        lblPrefs = (TextView) findViewById(R.id.lblLieblingsTee);
        lblextStorage = (TextView) findViewById(R.id.lblExtStorage);
        btnSaveTextFile = (Button) findViewById(R.id.btnSaveTextFile);
        btnLoadTextFile = (Button) findViewById(R.id.btnLoadTextFile);
        txtFileText = (EditText) findViewById(R.id.txtFileText);
        lblFileContent = (TextView) findViewById(R.id.lblFileContent);
        ckbUseExternalStorage = (CheckBox) findViewById(R.id.ckbUseExternalStorage);
        btnCreateNote = (Button) findViewById(R.id.btnCreateNote);
        btnShowNotes = (Button) findViewById(R.id.btnShowNotes);

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
        btnSaveTextFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveFile();
            }
        });
        btnLoadTextFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadFile();
            }
        });
        btnCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNote();
            }
        });
        btnShowNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowNotes();
            }
        });


        if (isExternalStorageWritable())
            lblextStorage.setText("External storage is mounted (writable)");
        else if (isExternalStorageReadable())
            lblextStorage.setText("External storage is mounted (readable)");
        else
            lblextStorage.setText("External storage is not mounted");

    }

    private void CreateNote() {
        Intent i = new Intent(this, NoteActivity.class);
        startActivity(i);
    }

    private void ShowNotes() {
        Intent i = new Intent(this, NoteListActivity.class);
        startActivity(i);
    }

    private void SaveFile() {

        if (ckbUseExternalStorage.isChecked() && isExternalStorageWritable()) {
            MainActivityPermissionsDispatcher.WriteFileToExternalStorageWithCheck(this, this.txtFileText.getText().toString());
        } else {
            writeFileToInternalStorage(txtFileText.getText().toString());
        }
        this.txtFileText.setText("");
    }

    private void ReadFile() {
        if (ckbUseExternalStorage.isChecked()) {
            MainActivityPermissionsDispatcher.ReadFileToExternalStorageWithCheck(this);
        } else {
            lblFileContent.setText(readInternalFile());
        }
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

    private void writeFileToInternalStorage(String text) {
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(text.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String readInternalFile() {
        FileInputStream fis = null;
        try {
            fis = getApplicationContext().openFileInput(filename);

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void setPrefString() {

        final SharedPreferences pref2 = PreferenceManager.getDefaultSharedPreferences(this);

        String myText = "Ich trinke am liebsten " + pref2.getString("teaPreffered", "Hopfentee");

        if (pref2.getBoolean("teaWithSugar", true)) {
            myText += " mit " + getSweetenerString(pref2);
            myText += " gesüsst";
        } else {
            myText += " ungesüsst";
        }

        lblPrefs.setText(myText);
    }

    private String getSweetenerString(SharedPreferences pref) {
        return getResources().getStringArray(R.array.teaSweetener)[Arrays.asList(getResources().getStringArray(R.array.teaSweetenerValues)).indexOf(pref.getString("teaSweetener1", "natural"))];
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

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    @NeedsPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void WriteFileToExternalStorage(String content) {
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/Uebung3");
        dir.mkdirs();
        File file = new File(dir, filename);

        try {
            FileOutputStream f = new FileOutputStream(file);
            f.write(content.getBytes());
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnShowRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationalforExternalStorage(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("Die App benötigt zugriff auf den Externen Speicher bla bla bal")
                .setPositiveButton("Erlauben", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }


    @OnPermissionDenied(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDeniedForCamera() {
        Toast.makeText(this, "Waaasss sonä chääs", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showNeverAskForCamera() {
        Toast.makeText(this, "ha ha säbler schuld", Toast.LENGTH_SHORT).show();
    }

    @NeedsPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    void ReadFileToExternalStorage() {
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/Uebung3");
        dir.mkdirs();
        File file = new File(dir, filename);

        String myData = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                myData = myData + strLine;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lblFileContent.setText(myData);
    }

    @OnShowRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    void showRationalforReadExternalStorage(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("Die App benötigt zugriff auf den Externen Speicher bla bla bal")
                .setPositiveButton("Erlauben", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }


    @OnPermissionDenied(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    void showDeniedForReadExtStorage() {
        Toast.makeText(this, "Waaasss sonä chääs", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    void showNeverAskForReadExtStorage() {
        Toast.makeText(this, "ha ha säbler schuld", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
