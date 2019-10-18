package com.lionsden.ezytoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView response;
    Button buttonbook, buttoncancel,buttonhistory,buttonlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonbook = (Button) findViewById(R.id.bookingButton);
        buttoncancel = (Button) findViewById(R.id.cancelbutton);
        buttonhistory = (Button) findViewById(R.id.historybutton);
        buttonlogin = (Button) findViewById(R.id.loginbutton);

        response = (TextView) findViewById(R.id.responseTextView);



    }
    public void ticketbooking(View v) {
        Intent I = new Intent(this, LoginActivity.class);
        // get user input

        // pass user input in the intent


        startActivity(I);
    }
    public void cancelbooking(View v) {
        Intent I = new Intent(this, LoginActivity.class);
        // get user input

        // pass user input in the intent


        startActivity(I);
    }
    public void bookhistory(View v) {
        Intent I = new Intent(this, LoginActivity.class);
        // get user input

        // pass user input in the intent


        startActivity(I);
    }
    public void Onlogin(View v) {
        Intent I = new Intent(this, LoginActivity.class);
        // get user input

        // pass user input in the intent


        startActivity(I);
    }
}