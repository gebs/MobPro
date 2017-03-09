package com.example.gebs.uebung2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewsDemoActivity extends AppCompatActivity {

    RatingBar rbMyRatingbar;
    TextView lblAusgabe;
    Button btnSpulen;
    EditText txtAusgabeDatetime;
    EditText txtAusgabePhone;
    EditText txtAusgabeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views_demo);

        /*Get GUI elements*/
        rbMyRatingbar = (RatingBar) this.findViewById(R.id.rbMyRatingbar);
        lblAusgabe = (TextView) this.findViewById((R.id.lblAusgabe));
        btnSpulen = (Button) this.findViewById(R.id.btnSpuhlen);
        txtAusgabeDatetime = (EditText) this.findViewById(R.id.txtAusgabeDatetime);
        txtAusgabePhone = (EditText) this.findViewById(R.id.txtAusgabePhone);
        txtAusgabeText = (EditText) this.findViewById(R.id.txtAusgabeText);


        /*Events*/
        rbMyRatingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ChangeLabelText("Ratingbar: new Rating Set (" + rating + ")");
            }
        });
        btnSpulen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeLabelText("Button clicked, set new Button Title");
                if (btnSpulen.getText() == "Spülen")
                    btnSpulen.setText("Spülen stop");
                else
                    btnSpulen.setText("Spülen");
            }
        });
        txtAusgabeDatetime.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {
                    HideKeyBoard();
                    ChangeLabelText("Datetime changed to " + txtAusgabeDatetime.getText());
                    return true;
                } else {
                    return false;
                }
            }
        });
        txtAusgabePhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {
                    HideKeyBoard();
                    ChangeLabelText("Phone changed to " + txtAusgabeDatetime.getText());
                    return true;
                } else {
                    return false;
                }
            }
        });
        txtAusgabeText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {
                    HideKeyBoard();
                    ChangeLabelText("Text changed to " + txtAusgabeDatetime.getText());
                    return true;
                } else {
                    return false;
                }
            }
        });

    }

    private void HideKeyBoard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    private void ChangeLabelText(String newText) {
        this.lblAusgabe.setText(newText);
    }
}
