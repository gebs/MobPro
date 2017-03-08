package ch.hslu.mobpro.firstappskeleton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * The main activity, displays some buttons.
 *
 * @author Ruedi Arnold
 */


public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 23; // Arbitrary number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void startLogActivity(View v) {
        Intent myint = new Intent(this,LifecycleLogActivity.class);
        startActivity(myint);
    }

    public void startBrowser(View v) {
        String url = "http://www.hslu.ch";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }

    public void startQuestionActivity(View v) {
        Intent myInt2 = new Intent(this,QuestionActivity.class);
        myInt2.putExtra("question","This is my question, do you question?");
        startActivityForResult(myInt2,MY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Use return data and print to log
        if (requestCode == MY_REQUEST_CODE && resultCode == RESULT_OK) {
            TextView textView = (TextView) findViewById(R.id.main_textView_result);
            String answer = getResources().getString(R.string.main_text_gotAnswer) + "'" + data.getExtras().getString("answer") + "'";
            textView.setText(answer);
        }
    }
}
